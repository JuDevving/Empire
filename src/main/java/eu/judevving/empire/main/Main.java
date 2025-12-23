package eu.judevving.empire.main;

import eu.judevving.empire.clock.Clock;
import eu.judevving.empire.command.*;
import eu.judevving.empire.command.tabcompleter.*;
import eu.judevving.empire.listener.*;
import eu.toastlawine.empire.command.*;
import eu.toastlawine.empire.command.tabcompleter.*;
import eu.judevving.empire.earth.Earth;
import eu.judevving.empire.earth.VoidGenerator;
import eu.judevving.empire.file.BackupCreator;
import eu.judevving.empire.language.Languages;
import eu.toastlawine.empire.listener.*;
import eu.judevving.empire.inventory.PlayerHeads;
import eu.judevving.empire.map.pl3xmap.Pl3xmapConnection;
import eu.judevving.empire.inventory.recipe.BannerPatternRecipe;
import eu.judevving.empire.inventory.CustomHead;
import eu.judevving.empire.listener.storage.Griefing;
import eu.judevving.empire.sidefeature.WanderingTraderTrades;
import org.bukkit.Bukkit;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;

public final class Main extends JavaPlugin {

    private static Main plugin;

    private Earth earth;
    private Languages languages;
    private Pl3xmapConnection pl3xmapConnection;
    private PlayerHeads playerHeads;
    private Clock clock;

    @Override
    public void onEnable() {
        plugin = this;

        try {
            Griefing.init();
            BannerPatternRecipe.registerAll();
            CustomHead.init();
            WanderingTraderTrades.init();

            pl3xmapConnection = new Pl3xmapConnection();
            playerHeads = new PlayerHeads();
            languages = new Languages();
            clock = new Clock();
            earth = new Earth();
            earth.init();
            getLogger().info("Loaded save files");

            Bukkit.getPluginCommand("claim").setExecutor(new ClaimCommand());
            Bukkit.getPluginCommand("convertcoordinates").setExecutor(new ConvertCoordinatesCommand());
            Bukkit.getPluginCommand("doublejump").setExecutor(new DoublejumpCommand());
            Bukkit.getPluginCommand("empireadmin").setExecutor(new EmpireAdminCommand());
            Bukkit.getPluginCommand("empiredebug").setExecutor(new EmpireDebugCommand());
            Bukkit.getPluginCommand("home").setExecutor(new HomeCommand());
            Bukkit.getPluginCommand("language").setExecutor(new LanguageCommand());
            Bukkit.getPluginCommand("marker").setExecutor(new MarkerCommand());
            Bukkit.getPluginCommand("message").setExecutor(new MessageCommand());
            Bukkit.getPluginCommand("minimap").setExecutor(new MinimapCommand());
            Bukkit.getPluginCommand("reply").setExecutor(new ReplyCommand());
            Bukkit.getPluginCommand("square").setExecutor(new SquareCommand());
            Bukkit.getPluginCommand("state").setExecutor(new StateCommand());
            Bukkit.getPluginCommand("suicide").setExecutor(new SuicideCommand());
            Bukkit.getPluginCommand("unclaim").setExecutor(new UnclaimCommand());
            Bukkit.getPluginCommand("welcomemessage").setExecutor(new WelcomeMessageCommand());

            Bukkit.getPluginCommand("claim").setTabCompleter(new ClaimTabCompleter());
            Bukkit.getPluginCommand("convertcoordinates").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("doublejump").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("empireadmin").setTabCompleter(new EmpireAdminTabCompleter());
            Bukkit.getPluginCommand("home").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("language").setTabCompleter(new LanguageTabCompleter());
            Bukkit.getPluginCommand("marker").setTabCompleter(new MarkerTabCompleter());
            Bukkit.getPluginCommand("message").setTabCompleter(new MsgTabCompleter());
            Bukkit.getPluginCommand("minimap").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("reply").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("square").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("state").setTabCompleter(new StateTabCompleter());
            Bukkit.getPluginCommand("suicide").setTabCompleter(new EmptyTabCompleter());
            Bukkit.getPluginCommand("unclaim").setTabCompleter(new UnclaimTabCompleter());
            Bukkit.getPluginCommand("welcomemessage").setTabCompleter(new WelcomeMessageTabCompleter());

            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new AsyncChatListener(), plugin);
            pluginManager.registerEvents(new BlockBreakListener(), plugin);
            pluginManager.registerEvents(new BlockExplodeListener(), plugin);
            pluginManager.registerEvents(new BlockFromToListener(), plugin);
            pluginManager.registerEvents(new BlockIgniteListener(), plugin);
            pluginManager.registerEvents(new BlockPistonListener(), plugin);
            pluginManager.registerEvents(new BlockPlaceListener(), plugin);
            pluginManager.registerEvents(new BlockPreDispenseListener(), plugin);
            pluginManager.registerEvents(new EntityBlockFormListener(), plugin);
            pluginManager.registerEvents(new EntityBreakDoorListener(), plugin);
            pluginManager.registerEvents(new EntityChangeBlockListener(), plugin);
            pluginManager.registerEvents(new EntityDamageByBlockListener(), plugin);
            pluginManager.registerEvents(new EntityDamageByEntityListener(), plugin);
            pluginManager.registerEvents(new EntityDamageListener(), plugin);
            pluginManager.registerEvents(new EntityDeathListener(), plugin);
            pluginManager.registerEvents(new EntityExplodeListener(), plugin);
            pluginManager.registerEvents(new EntityInteractListener(), plugin);
            pluginManager.registerEvents(new EntityKnockbackByEntityListener(), plugin);
            pluginManager.registerEvents(new EntityMoveListener(), plugin);
            pluginManager.registerEvents(new EntityPortalListener(), plugin);
            pluginManager.registerEvents(new EntityPushedByEntityAttackListener(), plugin);
            pluginManager.registerEvents(new CreatureSpawnListener(), plugin);
            pluginManager.registerEvents(new EntityToggleGlideListener(), plugin);
            pluginManager.registerEvents(new ExplosionPrimeListener(), plugin);
            pluginManager.registerEvents(new HangingBreakByEntityListener(), plugin);
            pluginManager.registerEvents(new HangingBreakListener(), plugin);
            pluginManager.registerEvents(new InventoryClickListener(), plugin);
            pluginManager.registerEvents(new LightningStrikeListener(), plugin);
            pluginManager.registerEvents(new PlayerBucketListener(), plugin);
            pluginManager.registerEvents(new PlayerDeathListener(), plugin);
            pluginManager.registerEvents(new PlayerFishListener(), plugin);
            pluginManager.registerEvents(new PlayerInteractAtEntityListener(), plugin);
            pluginManager.registerEvents(new PlayerInteractEntityListener(), plugin);
            pluginManager.registerEvents(new PlayerInteractListener(), plugin);
            pluginManager.registerEvents(new PlayerJoinListener(), plugin);
            pluginManager.registerEvents(new PlayerMoveListener(), plugin);
            pluginManager.registerEvents(new PlayerPortalListener(), plugin);
            pluginManager.registerEvents(new PlayerPostRespawnListener(), plugin);
            pluginManager.registerEvents(new PlayerRespawnListener(), plugin);
            pluginManager.registerEvents(new PlayerTeleportListener(), plugin);
            pluginManager.registerEvents(new PlayerToggleFlightListener(), plugin);
            pluginManager.registerEvents(new PortalCreateListener(), plugin);
            pluginManager.registerEvents(new ProjectileHitListener(), plugin);
            pluginManager.registerEvents(new ProjectileLaunchListener(), plugin);
            pluginManager.registerEvents(new RaidTriggerListener(), plugin);
            pluginManager.registerEvents(new ServerLoadListener(), plugin);
            pluginManager.registerEvents(new SignChangeListener(), plugin);
            pluginManager.registerEvents(new SpongeAbsorbListener(), plugin);
            pluginManager.registerEvents(new ThrownEggHatchListener(), plugin);
            pluginManager.registerEvents(new TNTPrimeListener(), plugin);
            pluginManager.registerEvents(new VehicleCreateListener(), plugin);
            pluginManager.registerEvents(new VehicleDamageListener(), plugin);
            pluginManager.registerEvents(new VehicleEnterListener(), plugin);
            pluginManager.registerEvents(new VehicleEntityCollisionListener(), plugin);

            getServer().motd(GlobalFinals.MOTD);
        } catch (Exception e) {
            e.printStackTrace();
            BackupCreator.createBackup(false);
            getLogger().info("An error occurred while enabling Empire. Shutting down server to prevent griefing.");
            getServer().shutdown();
        }
    }

    @Override
    public void onDisable() {
        BackupCreator.save();
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(String worldName, @Nullable String id) {
        getLogger().info("VoidGenerator enabled");
        return new VoidGenerator();
    }

    public static void debug(String message) {
        getPlugin().getLogger().info("DEBUG: " + message);
    }

    public Clock getClock() {
        return clock;
    }

    public PlayerHeads getPlayerHeads() {
        return playerHeads;
    }

    public Earth getEarth() {
        return earth;
    }

    public Languages getLanguages() {
        return languages;
    }

    public static Main getPlugin() {
        return plugin;
    }

    public Pl3xmapConnection getPl3xmapConnection() {
        return pl3xmapConnection;
    }
}
