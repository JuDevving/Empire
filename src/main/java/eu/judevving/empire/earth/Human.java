package eu.judevving.empire.earth;

import eu.judevving.empire.clock.Clock;
import eu.judevving.empire.earth.storage.AutoClaimState;
import eu.judevving.empire.earth.storage.WelcomeMessage;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.inventory.menu.ItemMenu;
import eu.judevving.empire.inventory.recipe.BannerPatternRecipe;
import eu.judevving.empire.earth.storage.ClaimResult;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.quest.QuestCategory;
import eu.judevving.empire.clock.Day;
import io.papermc.paper.entity.TeleportFlag;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Human implements Comparable<Human> {

    private final UUID uuid;
    private String name;
    private Player p;
    private Language language;
    private State state, visitingState;
    private Location netherPortalLocation;
    private AutoClaimState autoClaimState;
    private ItemMenu itemMenu;
    private Day lastTakenDailyReward;
    private int dailyRewardStreak;
    private boolean donater;
    private long lastOnline;
    private int deaths;
    private WelcomeMessage welcomeMessage;
    private Scoreboard minimapScoreboard;
    private int shopCooldown;
    private Team team;
    private boolean doubleJumpEnabled;
    private long homeTeleportTick;

    void synchronousTick() {
        if (isOffline()) return;
        lastOnline = Main.getPlugin().getClock().getTimeInMillis();
        if (!Main.getPlugin().getEarth().isOutOfBounds(getPlayer().getLocation()))
            Main.getPlugin().getEarth().progressQuests(p, QuestCategory.VISIT, p.getLocation().getBlock().getBiome());
        performAutoClaim();
        updateVisitingState();
        //antiInfiltrate();
        teleportAroundGlobe();
        updateMinimap();
        checkHomeTeleport();
        if (shopCooldown > 0) shopCooldown--;
    }

    public void shopUsed() {
        shopCooldown = GlobalFinals.DELAY_SHOP;
    }

    public boolean toggleMinimap() {
        if (minimapScoreboard != null) {
            minimapScoreboard = null;
            p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            return false;
        }
        minimapScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = minimapScoreboard.registerNewObjective(GlobalFinals.MINIMAP_OBJECTIVE, Criteria.DUMMY, Component.text(Text.MINIMAP_NAME.get(this)));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int z = 0; z < GlobalFinals.MINIMAP_SIZE; z++) {
            Team team = minimapScoreboard.registerNewTeam(z + "");
            team.addEntry("§" + z);
            objective.getScore("§" + z).setScore(GlobalFinals.MINIMAP_SIZE - z);
        }
        updateMinimap();
        p.setScoreboard(minimapScoreboard);
        return true;
    }

    public boolean isWaitingForHomeTeleport() {
        return homeTeleportTick >= 0;
    }

    public void cancelHomeTeleport() {
        if (homeTeleportTick < 0) return;
        homeTeleportTick = -1;
        sendMessage(Text.COMMAND_HOME_TELEPORT_CANCELLED);
    }

    public void startHomeTeleport() {
        if (homeTeleportTick >= 0) return;
        homeTeleportTick = Main.getPlugin().getClock().getTick() + GlobalFinals.DELAY_HOME_TELEPORT_TICKS;
        sendMessage(Text.COMMAND_HOME_TELEPORT_START.get(GlobalFinals.DELAY_HOME_TELEPORT_SECONDS + ""));
    }

    private void checkHomeTeleport() {
        if (homeTeleportTick < 0) return;
        if (Main.getPlugin().getClock().getTick() < homeTeleportTick) return;
        homeTeleportTick = -1;
        if (getState() == null || getState().getCapital() == null) {
            cancelHomeTeleport();
            return;
        }
        teleport(getState().getCapitalRespawnLocation());
    }

    public void updateMinimap() {
        if (minimapScoreboard == null) return;
        State state = null;
        boolean onEarth = Main.getPlugin().getEarth().isWorld(getWorld());
        Square northWest = Square.fromLocation(p.getLocation()).cloneAdd(-(GlobalFinals.MINIMAP_SIZE >> 1), -(GlobalFinals.MINIMAP_SIZE >> 1));
        for (int z = 0; z < GlobalFinals.MINIMAP_SIZE; z++) {
            Team team = minimapScoreboard.getTeam(z + "");
            if (team == null) continue;
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < GlobalFinals.MINIMAP_SIZE; x++) {
                if (onEarth) state = Main.getPlugin().getEarth().getState(northWest.cloneAdd(x, z));
                if (state == null) {
                    row.append(GlobalFinals.MINIMAP_NO_MANS_LAND_COLOR);
                } else {
                    row.append(state.getStateColor().getMcCode());
                }
                if (z == GlobalFinals.MINIMAP_SIZE >> 1 && x == GlobalFinals.MINIMAP_SIZE >> 1) {
                    row.append(GlobalFinals.MINIMAP_CHAR_PLAYER);
                } else row.append(state == null ?
                        GlobalFinals.MINIMAP_CHAR_NO_MANS_LAND : GlobalFinals.MINIMAP_CHAR_STATE);
            }
            row.append("§").append(z);
            team.prefix(Component.text(row.toString()));
        }
    }

    public boolean tryPurchase(Material material, int amount) {
        if (isOffline()) return false;
        if (countMaterial(material) < amount) return false;
        removeMaterial(material, amount);
        return true;
    }

    public int purchasePart(Material material, int max) {
        if (max < 0) max = 0;
        if (isOffline()) return 0;
        int count = countMaterial(material);
        if (count > max) count = max;
        removeMaterial(material, count);
        return count;
    }

    public void removeMaterial(Material material, int amount) {
        if (amount <= 0) return;
        ItemStack itemStack;
        for (int i = 0; i < getInventory().getSize(); i++) {
            if (amount <= 0) return;
            itemStack = getInventory().getItem(i);
            if (itemStack == null) continue;
            if (itemStack.getType() != material) continue;
            if (itemStack.getAmount() <= amount) {
                amount = amount - itemStack.getAmount();
                getInventory().setItem(i, null);
                continue;
            }
            itemStack.setAmount(itemStack.getAmount() - amount);
            return;
        }
    }

    public int countMaterial(Material material) {
        int amount = 0;
        ItemStack itemStack;
        for (int i = 0; i < getInventory().getSize(); i++) {
            itemStack = getInventory().getItem(i);
            if (itemStack == null) continue;
            if (itemStack.getType() != material) continue;
            amount += itemStack.getAmount();
        }
        return amount;
    }

    private void teleportAroundGlobe() {
        if (getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!Main.getPlugin().getEarth().isGlobeTeleportationArea(getLocation())) return;
        Location to = getLocation().clone();
        if (to.getX() < GlobalFinals.EARTH_BLOCK_MIN_X) {
            to.setX(GlobalFinals.EARTH_BLOCK_MAX_X - GlobalFinals.EARTH_TELEPORT_EDGE_DISTANCE - 0.5);
        } else if (to.getX() > GlobalFinals.EARTH_BLOCK_MAX_X) {
            to.setX(GlobalFinals.EARTH_BLOCK_MIN_X + GlobalFinals.EARTH_TELEPORT_EDGE_DISTANCE + 0.5);
        }
        boolean b = false;
        if (to.getZ() < GlobalFinals.EARTH_BLOCK_MIN_Z) {
            b = true;
            to.setZ(GlobalFinals.EARTH_BLOCK_MIN_Z + GlobalFinals.EARTH_TELEPORT_EDGE_DISTANCE + 0.5);
        } else if (to.getZ() > GlobalFinals.EARTH_BLOCK_MAX_Z) {
            b = true;
            to.setZ(GlobalFinals.EARTH_BLOCK_MAX_Z - GlobalFinals.EARTH_TELEPORT_EDGE_DISTANCE - 0.5);
        }
        if (b) {
            to.setYaw(to.getYaw() + 180);
            to.setX((to.getX() - GlobalFinals.EARTH_BLOCK_MIN_X + (GlobalFinals.EARTH_SIZE_X >> 1))
                    % GlobalFinals.EARTH_SIZE_X + GlobalFinals.EARTH_BLOCK_MIN_X);
        }
        Entity target = getPlayer().getVehicle();
        if (target == null) target = getPlayer();
        if (to.getY() >= GlobalFinals.EARTH_VOID_BEDROCK_MAX_Y) {
            Earth.putOnHighestBlock(to);
        } else to.setY(target.getY());
        if (b) { // cap speed
            Vector velocity = target.getVelocity();
            velocity.setZ(-velocity.getZ());
            if (velocity.length() > GlobalFinals.EARTH_TELEPORT_MAX_VELOCITY)
                velocity.normalize().multiply(GlobalFinals.EARTH_TELEPORT_MAX_VELOCITY);
            target.setVelocity(velocity);
        }
        if (target instanceof Minecart minecart) { // fix wanky minecart teleport
            minecart.eject();
            getPlayer().teleport(to);
            Minecart minecart2 = (Minecart) to.getWorld().spawnEntity(to, EntityType.MINECART);
            minecart2.setVelocity(minecart.getVelocity());
            minecart2.setMaxSpeed(minecart.getMaxSpeed());
            minecart2.setRotation(minecart.getYaw(), minecart.getPitch());
            minecart2.addPassenger(getPlayer());
            minecart.remove();
        } else
            target.teleport(to, PlayerTeleportEvent.TeleportCause.PLUGIN, TeleportFlag.EntityState.RETAIN_PASSENGERS);
    }

    public void teleport(Location location) {
        if (isOffline()) return;
        p.teleport(location);
    }

    private void antiInfiltrate() {
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (visitingState == null) return;
        if (visitingState.equals(getState())) return;
        getPlayer().removePotionEffect(PotionEffectType.SLOW_FALLING);
        getPlayer().removePotionEffect(PotionEffectType.LEVITATION);
    }

    private void performAutoClaim() {
        if (autoClaimState == AutoClaimState.OFF) return;
        if (state == null) return;
        ClaimResult claimResult;
        Square square = Square.fromLocation(getLocation());
        State owner = Main.getPlugin().getEarth().getState(square);
        if (autoClaimState == AutoClaimState.ON_CLAIM) {
            claimResult = Main.getPlugin().getEarth().tryClaim(state, square, getWorld());
        } else claimResult = Main.getPlugin().getEarth().tryUnclaim(state, square, getWorld());
        if (claimResult.isSuccess()) {
            sendMessage(ClaimResult.getMessage(this, square, owner, claimResult));
            return;
        }
        if (!claimResult.cancelsAuto()) return;
        sendMessage(ClaimResult.getMessage(this, square, owner, claimResult));
        if (autoClaimState == AutoClaimState.ON_CLAIM) {
            sendMessage(Text.COMMAND_CLAIM_AUTO_TURNED_OFF);
        } else sendMessage(Text.COMMAND_UNCLAIM_AUTO_TURNED_OFF);
        autoClaimState = AutoClaimState.OFF;
    }

    private void updateVisitingState() {
        State previousState = visitingState;
        visitingState = Main.getPlugin().getEarth().getState(getPlayer().getLocation());
        if (visitingState == previousState) return;
        sendWelcome(visitingState);
        updateAllowFlight();
    }

    private void sendWelcome(State to) {
        if (welcomeMessage == WelcomeMessage.OFF) return;
        String title;
        String subtitle;
        if (visitingState == null) {
            title = Text.TITLE_ENTER_NO_MANS_LAND.get(language);
            subtitle = Text.TITLE_ENTER_NO_MANS_LAND_SUBTITLE.get(language);
        } else {
            title = to.getColoredName();
            if (to.getRelationManager().isEnemy(getState())) {
                subtitle = to.getColoredEnemyWelcome();
            } else {
                subtitle = to.getWelcome();
                if (getState() != null) {
                    if (getState().getRelationManager().isAlly(to) || getState() == to)
                        subtitle = GlobalFinals.STATE_ALLY_WELCOME_COLOR + subtitle;
                }
            }
        }
        if (welcomeMessage == WelcomeMessage.TITLE) {
            p.showTitle(Title.title(Component.text(title), Component.text(subtitle)));
        } else {
            if (!subtitle.isEmpty() && !subtitle.equals(GlobalFinals.STRING_BLANK)) {
                subtitle = GlobalFinals.STRING_WELCOME_MESSAGE_BUFFER + subtitle;
            } else subtitle = "";
            sendMessage(Text.WELCOME_MESSAGE.get(language, title, subtitle));
        }
    }

    public static Human create(Player p) {
        Human h = create(p.getUniqueId());
        h.setPlayer(p);
        return h;
    }

    public static Human create(UUID uuid) {
        Human h = new Human(uuid);
        h.init();
        return h;
    }

    private Human(UUID uuid) {
        this.uuid = uuid;
        this.visitingState = null;
        this.autoClaimState = AutoClaimState.OFF;
        this.lastTakenDailyReward = Day.ZERO;
        this.minimapScoreboard = null;
        this.welcomeMessage = GlobalFinals.PLAYER_WELCOME_MESSAGE_DEFAULT;
        this.shopCooldown = 0;
        this.donater = false;
        this.lastOnline = -1;
        this.doubleJumpEnabled = false;
        this.homeTeleportTick = -1;
        initTeam();
    }

    public void initTeam() {
        if (Main.getPlugin().getServer().getScoreboardManager() == null) return;
        this.team = Main.getPlugin().getServer().getScoreboardManager()
                .getMainScoreboard().getTeam(uuid.toString());
        if (team == null) team = Main.getPlugin().getServer().getScoreboardManager()
                .getMainScoreboard().registerNewTeam(uuid.toString());
    }

    private void init() {
        this.itemMenu = new ItemMenu(this);
    }

    public void openMainMenu() {
        if (state == null) return;
        itemMenu.openMainMenu(this);
    }

    public void openLanguageMenu() {
        itemMenu.openLanguageMenu(this);
    }

    public void openLanguageMenu(boolean sendWelcomeAfterSelection) {
        itemMenu.openLanguageMenu(this, sendWelcomeAfterSelection);
    }

    private void learnRecipes() {
        if (isOffline()) return;
        BannerPatternRecipe.learnAll(p);
        //SaddleRecipe.learn(p);
    }

    public World getWorld() {
        if (isOffline()) return null;
        return p.getWorld();
    }

    public void setCollides(boolean collides) {
        team.setOption(Team.Option.COLLISION_RULE, collides ? Team.OptionStatus.ALWAYS : Team.OptionStatus.NEVER);
    }

    public void updateNameTag() {
        if (isOffline()) return;
        team.prefix(Component.text(state == null ? "" : state.getColoredTag() + " "));
        team.color(donater ? GlobalFinals.PLAYER_COLOR_TEAM_DONATER : GlobalFinals.PLAYER_COLOR_TEAM_DEFAULT);
        p.setPlayerListOrder(getState() == null ? GlobalFinals.PLAYER_LIST_ORDER_NO_STATE :
                getState().getDescendingRank());
    }

    public String getNameColor() {
        return donater ? GlobalFinals.PLAYER_COLOR_CHAT_DONATER : GlobalFinals.PLAYER_COLOR_CHAT_DEFAULT;
    }

    public void sendMessage(Text text, String... args) {
        sendMessage(text.get(language, args));
    }

    public void sendMessage(String message) {
        if (isOffline()) return;
        p.sendMessage(message);
    }

    public void playSound(Sound sound, float volume, float pitch) {
        if (isOffline()) return;
        getPlayer().playSound(getPlayer(), sound, volume, pitch);
    }

    public void closeInventory() {
        if (isOffline()) return;
        getPlayer().closeInventory();
    }

    public boolean isOffline() {
        return !isOnline();
    }

    public boolean isOnline() {
        if (p == null) return false;
        return p.isOnline();
    }

    public void setPlayer(Player p) {
        if (p == null) return;
        if (!uuid.equals(p.getUniqueId())) return;
        this.p = p;
        name = p.getName();
        team.addPlayer(p);
        updateNameTag();
        learnRecipes();
        updatePlayerList();
        updateAllowFlight();
        if (minimapScoreboard != null) p.setScoreboard(minimapScoreboard);
    }

    public void updateAllowFlight() {
        if (isOffline()) return;
        if (p.getGameMode() != GameMode.SURVIVAL) return;
        if (doubleJumpEnabled) {
            p.setAllowFlight(visitingState == state);
        } else p.setAllowFlight(false);
    }

    private void updatePlayerList() {
        if (isOffline()) return;
        p.sendPlayerListHeaderAndFooter(Component.text(Text.PLAYER_LIST_HEADER.get(language)),
                Component.text(Text.PLAYER_LIST_FOOTER.get(language)));
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
        updatePlayerList();
    }

    public ItemMenu getInventoryMenu() {
        return itemMenu;
    }

    public Location getNetherPortalLocation() {
        if (netherPortalLocation == null && p != null) {
            if (isOnline()) {
                netherPortalLocation = p.getLocation();
                netherPortalLocation.setY(GlobalFinals.NETHER_PORTAL_Y_OVERWORLD);
                netherPortalLocation.setWorld(Main.getPlugin().getEarth().getWorld());
            }
        }
        if (netherPortalLocation != null) {
            if (netherPortalLocation.getWorld() == null) {
                netherPortalLocation.setWorld(Main.getPlugin().getEarth().getWorld());
            }
        }
        return netherPortalLocation;
    }

    public void setNetherPortalLocation(Location netherPortalLocation) {
        this.netherPortalLocation = netherPortalLocation;
    }

    public void setName(String name) {
        if (name == null) name = "???";
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player getPlayer() {
        return p;
    }

    public void setState(State state) {
        this.state = state;
        updateNameTag();
    }

    public Human getKiller() {
        if (getPlayer().getKiller() == null) return null;
        return Main.getPlugin().getEarth().getHuman(getPlayer().getKiller());
    }

    public AutoClaimState getAutoClaimState() {
        return autoClaimState;
    }

    public void setAutoClaimState(AutoClaimState autoClaimState) {
        this.autoClaimState = autoClaimState;
    }

    public String getColoredStateNameAndId() {
        if (state == null) return Text.STATE_NONE.get(language);
        return state.getColoredNameAndId();
    }

    public State getState() {
        return state;
    }

    public Location getLocation() {
        return p.getLocation();
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public void takeDailyReward() {
        if (dailyRewardTaken()) return;
        if (isOffline()) return;
        if (state == null) return;
        state.addPower(getNextDailyRewardPower());
        getPlayer().getInventory().addItem(state.getFlags(getNextDailyRewardFlags()));
        dailyRewardStreak++;
        lastTakenDailyReward = Main.getPlugin().getClock().getDay();
        playSound(GlobalFinals.SOUND_DAILY_REWARD, GlobalFinals.SOUND_DAILY_REWARD_VOLUME, GlobalFinals.SOUND_DAILY_REWARD_PITCH);
    }

    public int getNextDailyRewardFlags() {
        return Math.min(GlobalFinals.DAILY_REWARD_FLAGS_CAP, dailyRewardStreak + 1);
    }

    public int getNextDailyRewardPower() {
        return Math.min(GlobalFinals.DAILY_REWARD_POWER_CAP,
                (dailyRewardStreak + 1) * GlobalFinals.DAILY_REWARD_POWER_STEP);
    }

    public int getDailyRewardStreak() {
        return dailyRewardStreak;
    }

    public Day getLastTakenDailyReward() {
        return lastTakenDailyReward;
    }

    public void setLastTakenDailyReward(Day lastTakenDailyReward) {
        this.lastTakenDailyReward = lastTakenDailyReward;
        if (Main.getPlugin().getEarth().preservesStreak(lastTakenDailyReward)) return;
        dailyRewardStreak = 0;
    }

    public void setDailyRewardStreak(int dailyRewardStreak) {
        if (dailyRewardStreak < 0) dailyRewardStreak = 0;
        this.dailyRewardStreak = dailyRewardStreak;
    }

    public void increaseDailyRewardStreak() {
        dailyRewardStreak++;
    }

    public boolean dailyRewardTaken() {
        return lastTakenDailyReward.equals(Main.getPlugin().getClock().getDay());
    }

    public PlayerInventory getInventory() {
        if (isOffline()) return null;
        return p.getInventory();
    }

    public boolean canShop() {
        return shopCooldown == 0;
    }

    @Override
    public int compareTo(@NotNull Human h) {
        if (lastOnline != h.lastOnline)
            return -Long.compare(lastOnline, h.lastOnline);
        if (name == null) {
            if (h.name == null) return uuid.compareTo(h.getUniqueId());
            return 1;
        }
        if (h.name == null) return -1;
        return name.compareTo(h.name);
    }

    public boolean isDonater() {
        return donater;
    }

    public void setDonater(boolean donater, boolean update) {
        this.donater = donater;
        if (update) updateNameTag();
    }

    public WelcomeMessage getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(WelcomeMessage welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getLastOnlineString() {
        return Clock.millisToString(lastOnline);
    }

    public long getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(long lastOnline) {
        this.lastOnline = lastOnline;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        if (this.deaths < 0) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            this.deaths = offlinePlayer.getStatistic(Statistic.DEATHS);
        }
    }

    public void addDeath() {
        deaths++;
    }

    public void setDoubleJumpEnabled(boolean doubleJumpEnabled) {
        this.doubleJumpEnabled = doubleJumpEnabled;
    }

    public boolean getDoubleJumpEnabled() {
        return doubleJumpEnabled;
    }
}
