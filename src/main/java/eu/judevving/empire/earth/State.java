package eu.judevving.empire.earth;

import eu.judevving.empire.earth.relation.RelationManager;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.earth.setting.SettingsManager;
import eu.judevving.empire.earth.storage.ClaimResult;
import eu.judevving.empire.earth.storage.StateColor;
import eu.judevving.empire.earth.storage.StateLevel;
import eu.toastlawine.empire.earth.storage.*;
import eu.judevving.empire.file.FileSaver;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.territory.Territory;
import eu.judevving.empire.file.StateSaver;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import eu.judevving.empire.inventory.ItemEditor;
import eu.judevving.empire.map.marker.MarkerManager;
import eu.judevving.empire.quest.Quest;
import eu.judevving.empire.quest.QuestManager;
import net.kyori.adventure.text.Component;
import net.pl3x.map.core.markers.marker.Circle;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.MultiPolygon;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class State implements Comparable<State> {

    private final String stateId; // UID
    private FileSaver fileSaver;
    private String name, tag, description, welcome, enemyWelcome;
    private String capitalName;
    private int power;
    private Territory territory;
    private MarkerManager markerManager;
    private final QuestManager questManager;
    private RelationManager relationManager;
    private final SettingsManager settingsManager;
    private final ArrayList<Human> members;
    private final HashSet<UUID> invitations;
    private StateLevel stateLevel;
    private int levelProgress;
    private StateColor stateColor;
    private ItemStack flag;
    private int rank;
    private String popupText;
    private Circle claimRadiusCircle;
    private State lastSender;
    private Icon capitalIcon;

    public static State create(String stateId, FileSaver fileSaver) {
        State state = new State(stateId, fileSaver);
        state.init();
        return state;
    }

    private void init() {
        this.markerManager = new MarkerManager(this);
        this.relationManager = new RelationManager(this);
    }

    private State(String stateId, FileSaver fileSaver) {
        this.stateId = stateId;
        if (fileSaver == null) fileSaver = createFileSaver(stateId);
        this.fileSaver = fileSaver;
        setDefaultStrings();
        this.power = GlobalFinals.STATE_DEFAULT_POWER;
        this.flag = GlobalFinals.STATE_DEFAULT_FLAG;
        this.settingsManager = new SettingsManager(fileSaver);
        this.questManager = new QuestManager(this);
        this.stateLevel = StateLevel.LEVEL_1;
        this.stateColor = GlobalFinals.STATE_DEFAULT_COLOR;
        this.members = new ArrayList<>();
        this.invitations = new HashSet<>();
        this.rank = 0;
        this.capitalIcon = null;
        newTerritory();
    }

    public static FileSaver createFileSaver(String stateId) {
        return new FileSaver(GlobalFinals.FILES_FOLDER_STATES + '/' + stateId + GlobalFinals.FILES_DEFAULT_EXTENSION);
    }

    public void setDefaultStrings() {
        this.name = GlobalFinals.STATE_DEFAULT_NAME;
        this.description = GlobalFinals.STATE_DEFAULT_DESCRIPTION;
        this.welcome = GlobalFinals.STATE_DEFAULT_WELCOME;
        this.enemyWelcome = GlobalFinals.STATE_DEFAULT_ENEMY_WELCOME;
        this.capitalName = GlobalFinals.STATE_DEFAULT_CAPITAL_NAME;
        setTag(GlobalFinals.STATE_DEFAULT_TAG);
        if (markerManager != null) markerManager.setDefaultNames();
    }

    public void delete() {
        if (members.isEmpty()) {
            deleteForReal();
            return;
        }
        List<Human> list = members.stream().toList();
        for (Human member : list) {
            removeMember(member);
        }
    }

    private void deleteForReal() {
        Main.getPlugin().getEarth().unclaimAll(this);
        Main.getPlugin().getEarth().removeState(stateId);
        StateSaver.deleteStateFile(stateId);
    }

    public boolean isInvited(Human h) {
        return invitations.contains(h.getUniqueId());
    }

    public void invite(Human h) {
        invitations.add(h.getUniqueId());
    }

    public void sendQuestCompletedMessage(Quest quest) {
        for (Human h : members) {
            h.sendMessage(Text.STATE_QUEST_COMPLETED, quest.getType().getTask(h.getLanguage()));
            h.playSound(GlobalFinals.SOUND_QUEST, GlobalFinals.SOUND_QUEST_VOLUME, GlobalFinals.SOUND_QUEST_PITCH);
        }
    }

    public void sendMessage(Text text, String... args) {
        for (Human h : members) {
            h.sendMessage(text, args);
        }
    }

    public void playSound(Sound sound, float volume, float pitch) {
        for (Human h : members) {
            h.playSound(sound, volume, pitch);
        }
    }

    public int getOnlineMemberCount() {
        int count = 0;
        for (Human h : members) {
            if (h.isOffline()) continue;
            count++;
        }
        return count;
    }

    public LinkedList<Human> getOnlineMembers() {
        LinkedList<Human> list = new LinkedList<>();
        for (Human h : members) {
            if (h.isOffline()) continue;
            list.add(h);
        }
        return list;
    }

    public void addMember(Human h) {
        members.add(h);
        h.setState(this);
    }

    ClaimResult canClaim(Square square, boolean capital) {
        if (getCapital() != null) {
            if (getCapital().getBlockDistance(square) >= getClaimRadius()) return ClaimResult.TOO_FAR;
        }
        if (getUnusedPower() < getOneSquarePowerCost()) return ClaimResult.NOT_ENOUGH_POWER;
        if (capital) {
            if (getCapital() == null) {
                return ClaimResult.SUCCESS_CAPITAL;
            }
            return ClaimResult.CAPITAL_ALREADY_CLAIMED;
        }
        if (getCapital() == null) return ClaimResult.NO_CAPITAL;
        if (isCloseCapital(square)) return ClaimResult.SUCCESS;
        return ClaimResult.SUCCESS_RECENT;
    }

    public boolean isCloseCapital(Square square) {
        return getCapital().getBlockDistance(square) <= GlobalFinals.TAKEOVER_PROTECTION_RADIUS;
    }

    public int countEdges(Square square) {
        return territory.countEdges(square);
    }

    public void validate() {
        relationManager.validate();
    }

    public int getUnusedPower() {
        return power - GlobalFinals.getEntirePowerCost(getTerritorySize());
    }

    public int getOneSquarePowerCost() {
        return GlobalFinals.getOneSquarePowerCost(getTerritorySize());
    }

    void forceClaim(Square square) {
        territory.add(square);
    }

    void forceUnclaim(Square square) {
        territory.remove(square);
    }

    public Iterator<Square> getTerritory() {
        return territory.getSquares();
    }

    void newTerritory() {
        territory = new Territory();
    }

    public boolean isTerritory(Square square) {
        return territory.isTerritory(square);
    }

    public MultiPolygon getMultiPolygon(String key) {
        return territory.getMultipolygon(key);
    }

    public int getMaxTerritorySize() {
        return GlobalFinals.getMaxSize(power);
    }

    public boolean hasTerritory() {
        return getTerritorySize() > 0;
    }

    public int getTerritorySize() {
        return territory.getSize();
    }

    @Override
    public int compareTo(@NotNull State state) {
        if (power < state.getPower()) return -1;
        if (power > state.getPower()) return 1;
        if (stateLevel.getLevel() < state.getLevel()) return -1;
        if (stateLevel.getLevel() > state.getLevel()) return 1;
        return state.getStateId().compareTo(stateId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof State)) return false;
        return stateId.equals(((State) obj).stateId);
    }

    @Override
    public int hashCode() {
        return stateId.hashCode();
    }

    public void progressLevel(int amount) {
        if (amount <= 0) return;
        levelProgress += amount;
        StateLevel nextLevel = stateLevel.getNext();
        if (levelProgress < nextLevel.getPriceAmount()) return;
        levelProgress = nextLevel.getPriceAmount();
        levelUp();
    }

    public void levelUp() {
        if (getLevel() >= StateLevel.getMax()) return;
        levelProgress = 0;
        StateLevel next = StateLevel.values()[getLevel()];
        setStateLevel(next);
        questManager.replaceNull();
        sendMessage(Text.STATE_LEVEL_UP, getColoredName(), next.getLevel() + "");
        playSound(GlobalFinals.SOUND_DAILY_REWARD, GlobalFinals.SOUND_DAILY_REWARD_VOLUME, GlobalFinals.SOUND_DAILY_REWARD_PITCH);
        if (getLevel() == 55) updateListNames();
    }

    public String getPopupText() {
        if (popupText == null ||
                Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_MAP_STATE_DESCRIPTION_REFRESH == 0) {
            popupText = MapFinals.MULTIPOLYGON_STATE_POPUP_TEXT
                    .replace("{allyList}", getMapAllyList())
                    .replace("{description}", getDescription())
                    .replace("{enemyList}", getMapEnemyList())
                    .replace("{id}", getStateId())
                    .replace("{level}", getLevel() + (getStateLevel() == StateLevel.MAX_LEGITIMATE ? GlobalFinals.STRING_HINT_MAX : ""))
                    .replace("{maxSize}", getMaxTerritorySize() + "")
                    .replace("{memberList}", getMapMemberList())
                    //.replace("{members}", getMemberCount() + "")
                    .replace("{name}", getName())
                    //.replace("{online}", getOnlineMemberCount() + "")
                    .replace("{power}", getPower() + "")
                    .replace("{rank}", getRank() + "")
                    .replace("{size}", getTerritorySize() + "")
                    .replace("{tag}", getTag());
        }
        return popupText;
    }

    public Circle getClaimRadiusCircle() {
        if (getCapital() == null) return null;
        if (claimRadiusCircle == null ||
                Main.getPlugin().getClock().getTick() % GlobalFinals.PERIOD_MAP_STATE_DESCRIPTION_REFRESH == 0) {
            claimRadiusCircle = new Circle(MapFinals.CIRCLE_CLAIM_RADIUS_KEY_PREFIX + getStateId(),
                    getCapital().getCenterBlockX(), getCapital().getCenterBlockZ(), getStateLevel().getClaimRadius());
        }
        return claimRadiusCircle;
    }

    public Human getMember(String name) {
        if (name == null) return null;
        for (Human member : members) {
            if (member.getName() == null) continue;
            if (member.getName().equalsIgnoreCase(name)) return member;
        }
        return null;
    }

    public Human getMember(int i) {
        if (i < 0) return null;
        if (i >= members.size()) return null;
        return members.get(i);
    }

    public String getMapMemberList() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = getMemberCount();
        for (Human h : members) {
            i--;
            stringBuilder.append(h.getName());
            if (i > 0) stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    public String getMapAllyList() {
        return relationManager.getMapAllyList();
    }

    public String getMapEnemyList() {
        return relationManager.getMapEnemyList();
    }

    public void memberDied(Human h) {
        int loss = losePower();
        int territoryLoss = shrinkTerritoryToMax();
        if (loss <= 0) return;
        Human killer = h.getKiller();
        if (killer != null) {
            if (killer.getState() == null) {
                killer = null;
            } else if (killer.getState().equals(this)) killer = null;
        }
        if (killer == null) {
            Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_POWER_LOSS,
                    getColoredNameAndId(), loss + "");
        } else {
            Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_POWER_LOSS_TRANSFER,
                    getColoredNameAndId(), loss + "", killer.getState().getColoredNameAndId());
            killer.getState().addPower(loss);
        }
        sendMessage(Text.STATE_TERRITORY_LOSS, territoryLoss + "");
        Main.getPlugin().getLogger().info(getStateId() + " lost " + loss + " power");
    }

    private int losePower() {
        int newPower = power - (int) (power * GlobalFinals.POWER_LOSS_DEATH);
        if (newPower < GlobalFinals.STATE_DEFAULT_POWER) newPower = GlobalFinals.STATE_DEFAULT_POWER;
        int loss = power - newPower;
        setPower(newPower);
        return loss;
    }

    public void removeMember(Human h) {
        members.remove(h);
        h.setState(null);
        if (getMemberCount() > 0) return;
        deleteForReal();
        Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_DELETED, getColoredNameAndId());
    }

    public void leave(Human h) {
        removeMember(h);
        Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_LEFT, h.getName(), getColoredNameAndId());
        if (members.isEmpty()) return;
        int loss = losePower();
        int territoryLoss = shrinkTerritoryToMax();
        Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_POWER_LOSS,
                getColoredNameAndId(), loss + "");
        sendMessage(Text.STATE_TERRITORY_LOSS, territoryLoss + "");
    }

    public int shrinkTerritoryToMax() {
        return territory.shrink(getMaxTerritorySize());
    }

    public void addPower(int power) {
        setPower(this.power + power);
        if (power <= 0) return;
        sendMessage(Text.STATE_POWER_GAIN, power + "");
    }

    public AccessLevel getAccessLevel(State state1) {
        if (equals(state1)) return AccessLevel.OWN;
        if (state1 != null) {
            if (settingsManager.getStateAccessLevel(state1.getStateId()) != AccessLevel.DEFAULT)
                return settingsManager.getStateAccessLevel(state1.getStateId());
        } else return settingsManager.getAccessLevelNeutrals();
        if (relationManager.isEnemy(state1)) return settingsManager.getAccessLevelEnemies();
        if (relationManager.isAlly(state1)) return settingsManager.getAccessLevelAllies();
        return settingsManager.getAccessLevelNeutrals();
    }

    public ItemStack getFlags(int amount) {
        if (amount <= 0) return null;
        ItemStack itemStack = getFlag();
        itemStack.setAmount(amount);
        return itemStack;
    }

    public ItemStack getFlag() {
        return flag.clone();
    }

    public void setFlag(ItemStack flag) {
        if (flag == null) return;
        ItemEditor.setName(flag, getColoredName());
        flag.setAmount(1);
        this.flag = flag;
    }

    public Location getCapitalRespawnLocation() {
        Location loc = new Location(Main.getPlugin().getEarth().getWorld(),
                getCapital().getCenterBlockX() + 0.5, 0, getCapital().getCenterBlockZ() + 0.5);
        Earth.putOnHighestBlock(loc);
        return loc;
    }

    public int getMemberCount() {
        return members.size();
    }

    public void setStateColor(StateColor stateColor) {
        if (stateColor == null) return;
        this.stateColor = stateColor;
        updateListNames();
    }

    public void updateListNames() {
        if (members == null) return;
        for (Human h : members) {
            h.updateNameTag();
        }
    }

    public boolean isOnline() {
        for (Human human : members) {
            if (human.isOnline()) return true;
        }
        return false;
    }

    public void setCapital(Square square) {
        territory.setCenter(square);
    }

    public StateColor getStateColor() {
        return stateColor;
    }

    public String getStateId() {
        return stateId;
    }

    public int getLevel() {
        return stateLevel.getLevel();
    }

    public StateLevel getStateLevel() {
        return stateLevel;
    }

    public void setStateLevel(StateLevel stateLevel) {
        this.stateLevel = stateLevel;
        this.levelProgress = 0;
    }

    public String getDescription() {
        return description;
    }

    public Component getChatPrefix() {
        return Component.text(getColoredTag() + ' ');
    }

    public String getColoredNameAndId() {
        return getColoredName() + "§7 (" + getStateId() + ")";
    }

    public String getColoredName() {
        return stateColor.getMcCode() + getName();
    }

    public String getName() {
        return name;
    }

    public boolean hasMaxMembers() {
        return getMemberCount() >= GlobalFinals.STATE_MAX_MEMBERS;
    }

    public String getColoredEnemyWelcome() {
        return GlobalFinals.STATE_ENEMY_WELCOME_COLOR + enemyWelcome;
    }

    public String getEnemyWelcome() {
        return enemyWelcome;
    }

    public void setEnemyWelcome(String enemyWelcome) {
        if (enemyWelcome == null) return;
        this.enemyWelcome = enemyWelcome;
    }

    public String getWelcome() {
        return welcome;
    }

    public int getClaimRadius() {
        return stateLevel.getClaimRadius();
    }

    public void setDescription(String description) {
        if (description == null) return;
        this.description = description;
    }

    public void setName(String name) {
        if (name == null) return;
        this.name = name;
    }

    public void setTag(String tag) {
        if (tag == null) return;
        this.tag = tag;
        updateListNames();
    }

    public void setWelcome(String welcome) {
        if (welcome == null) return;
        this.welcome = welcome;
    }

    public String getColoredTagAndId() {
        return getColoredTag() + "§7 (" + getStateId() + ")";
    }

    public String getColoredTag() {
        if (getLevel() < 55)
            return "§7[" + stateColor.getMcCode() + tag + "§7]";
        return "§e[" + stateColor.getMcCode() + tag + "§e]";
    }

    public String getTag() {
        return '[' + tag + ']';
    }

    public String getRawTag() {
        return tag;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public Square getCapital() {
        return territory.getCenter();
    }

    public int getRank() {
        return rank;
    }

    public int getDescendingRank() {
        return Main.getPlugin().getEarth().getMaxRank() - rank + 1;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public int getQuests() {
        return stateLevel.getQuests();
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        if (capitalName == null) return;
        this.capitalName = capitalName;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public RelationManager getRelationManager() {
        return relationManager;
    }

    public State getLastSender() {
        return lastSender;
    }

    public void setLastSender(State lastSender) {
        this.lastSender = lastSender;
    }

    public int getLevelProgress() {
        return levelProgress;
    }

    public void setLevelProgress(int levelProgress) {
        this.levelProgress = levelProgress;
    }

    public Icon getCapitalIcon() {
        return capitalIcon;
    }

    public void setCapitalIcon(Icon capitalIcon) {
        this.capitalIcon = capitalIcon;
    }

    public MarkerManager getMarkerManager() {
        return markerManager;
    }

    public FileSaver getFileSaver() {
        return fileSaver;
    }

    public void setFileSaver(FileSaver fileSaver) {
        this.fileSaver = fileSaver;
    }
}
