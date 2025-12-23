package eu.judevving.empire.main;

import eu.judevving.empire.earth.storage.StateColor;
import eu.judevving.empire.earth.storage.WelcomeMessage;
import eu.judevving.empire.map.marker.MarkerType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class GlobalFinals {

    public static final int CLOCK_INTERVAL_TICKS = 5;
    public static final int CLOCK_NEW_DAY_HOUR = 4;
    public static final int DELAY_SHOP = 2;
    public static final int DELAY_HEAD_DOWNLOAD = 4 * 60;
    public static final int PERIOD_CRATE_SPAWN = 4 * 60 * 60;
    public static final int DELAY_CRATE_SPAWN = 4 * 250;
    public static final int DELAY_HOME_TELEPORT_SECONDS = 10;
    public static final int DELAY_HOME_TELEPORT_TICKS = 4 * DELAY_HOME_TELEPORT_SECONDS;
    public static final int PERIOD_MAP_STATE_DESCRIPTION_REFRESH = 4 * 5;
    public static final int PERIOD_MARKER_UPDATE = 4 * 10 + 1;
    public static final int PERIOD_STATE_SORT = 4 * 5 + 2;
    public static final int PERIOD_HUMAN_SORT = 4 * 5 + 3;
    public static final int PERIOD_RECENT_CLAIM_UPDATE = 4 * 60 + 1;
    public static final int PERIOD_SAVE = 4 * 60 * 30;

    public static final int WORLD_OVERWORLD_ID = 0;
    public static final String WORLD_OVERWORLD_NAME = "world";
    public static final int WORLD_NETHER_ID = 1;
    public static final int NETHER_ELYTRA_MAX_Y = 128;
    public static final int NETHER_PORTAL_Y_NETHER = 64;
    public static final int NETHER_PORTAL_Y_OVERWORLD = 80;
    public static final String EARTH_UNCLAIMABLE_TERRITORY_NAME = "unclaimable";

    public static final String COMMAND_STATE_TEXT_FOR_EMPTY = "none";

    public static final String CHAT_NAME_SUFFIX = "§f: ";

    public static final int CRATE_HEADS = 5;
    public static final Material CRATE_MATERIAL = Material.REINFORCED_DEEPSLATE;
    public static final int CRATE_MAX = 7;

    public static final int DAILY_REWARD_FLAGS_CAP = 3;
    public static final int DAILY_REWARD_POWER_CAP = 50;
    public static final int DAILY_REWARD_POWER_STEP = 10;

    public static final boolean DEATH_HEAD_DROP = true;
    public static final double DEATH_ITEM_LOSS = 0.5;
    public static final boolean DEATH_LEVEL_LOSS = false;

    public static final String FILES_FOLDER = "plugins/Empire2";
    public static final String FILES_FOLDER_BACKUPS = "plugins/Empire2-Backups";
    public static final String FILES_FOLDER_HUMANS = FILES_FOLDER + "/humans";
    public static final String FILES_FOLDER_STATES = FILES_FOLDER + "/states";
    public static final String FILES_DEFAULT_EXTENSION = ".txt";
    public static final String FILES_YML_EXTENSION = ".yml";
    public static final String FILES_FILE_CLAIM_TIMES = FILES_FOLDER + "/claim_times" + FILES_DEFAULT_EXTENSION;
    public static final String FILES_FILE_EARTH = FILES_FOLDER + "/earth" + FILES_DEFAULT_EXTENSION;
    public static final String FILES_FILE_FLAGS = FILES_FOLDER + "/flags" + FILES_YML_EXTENSION;
    public static final String FILES_FILE_HEADS = FILES_FOLDER + "/heads" + FILES_DEFAULT_EXTENSION;
    public static final String FILES_FILE_SQUARES = FILES_FOLDER + "/squares" + FILES_DEFAULT_EXTENSION;

    public static final MarkerType MARKER_DEFAULT_ICON = MarkerType.POINT;
    public static final String MARKER_DEFAULT_DESCRIPTION = "A marker";
    public static final String MARKER_DEFAULT_NAME = "Marker";

    public static final double MINECART_SPEED_MAX = 2; // default: 0.4 = 8m/s
    public static final double MINECART_SPEED_REDUCED_COLLISIONS = 1;

    // same size: ░▒█回☀☽
    public static final char MINIMAP_CHAR_NO_MANS_LAND = '▒';
    public static final char MINIMAP_CHAR_PLAYER = '☀';
    public static final char MINIMAP_CHAR_STATE = '█';
    public static final String MINIMAP_NO_MANS_LAND_COLOR = "§f";
    public static final int MINIMAP_SIZE = 7;
    public static final String MINIMAP_OBJECTIVE = "minimap";

    public static final Component MOTD = Component.text("§e§lEmpire 2").appendNewline().append(Component.text("§bAsk for whitelisting on Discord"));

    public static final int PEARL_MAX_DISTANCE = 512;

    public static final String PERMISSION_PREFIX = "eu.toastlawine.empire.";

    public static final NamedTextColor PLAYER_COLOR_TEAM_DEFAULT = NamedTextColor.WHITE;
    public static final NamedTextColor PLAYER_COLOR_TEAM_DONATER = NamedTextColor.YELLOW;
    public static final String PLAYER_COLOR_CHAT_DEFAULT = "§f";
    public static final String PLAYER_COLOR_CHAT_DONATER = "§e";
    public static final int PLAYER_LIST_ORDER_NO_STATE = 0;

    public static final WelcomeMessage PLAYER_WELCOME_MESSAGE_DEFAULT = WelcomeMessage.TITLE;

    public static final double POWER_LOSS_DEATH = 0.1;
    public static final double POWER_LOSS_KILL = 0.02;
    public static final double POWER_LOSS_LEAVE = 0.01;

    public static final String RECIPE_PREFIX = "custom_recipe_";

    public static final int TAKEOVER_HOURS = 48;
    public static final long TAKEOVER_MILLIS = TAKEOVER_HOURS * 60 * 60 * 1000;
    public static final int TAKEOVER_MAX_EDGES = 2;
    public static final int TAKEOVER_PROTECTION_RADIUS = 100;

    public static final int RESPAWN_RESISTANCE_TICKS = 10 * 20;

    public static final String SHOP_LINE_0 = "Shop";
    public static final String SHOP_LINE_3_MIDDLE = "->";
    public static final Material SHOP_MONEY = Material.DIAMOND;

    public static final Sound SOUND_CRATE = Sound.ENTITY_CHICKEN_EGG;
    public static final float SOUND_CRATE_PITCH = 1f;
    public static final float SOUND_CRATE_VOLUME = 1f;
    public static final Sound SOUND_DAILY_REWARD = Sound.ENTITY_PLAYER_LEVELUP;
    public static final float SOUND_DAILY_REWARD_PITCH = 1f;
    public static final float SOUND_DAILY_REWARD_VOLUME = 1f;
    public static final Sound SOUND_QUEST = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    public static final float SOUND_QUEST_PITCH = 1f;
    public static final float SOUND_QUEST_VOLUME = 0.5f;
    public static final Sound SOUND_SHOP = Sound.ENTITY_CHICKEN_EGG;
    public static final float SOUND_SHOP_PITCH = 1f;
    public static final float SOUND_SHOP_VOLUME = 0.5f;

    public static final int SPAWN_MONSTER_EDGE_BUFFER = 64;
    public static final int SPAWN_MONSTER_EDGE_BUFFER_MIN_Y = 60;
    public static final int SPAWN_WITHER_MESSAGE_RADIUS = 8;

    public static final String STATE_ALLY_WELCOME_COLOR = "§a";
    public static final StateColor STATE_DEFAULT_COLOR = StateColor.WHITE;
    public static final String STATE_DEFAULT_DESCRIPTION = "A state";
    public static final String STATE_ENEMY_WELCOME_COLOR = "§c";
    public static final String STATE_DEFAULT_ENEMY_WELCOME = "Get lost!";
    public static final String STATE_DEFAULT_CAPITAL_NAME = "Capital";
    public static final ItemStack STATE_DEFAULT_FLAG = new ItemStack(Material.WHITE_BANNER);
    public static final String STATE_DEFAULT_NAME = "State";
    public static final int STATE_DEFAULT_POWER = 30;
    public static final String STATE_DEFAULT_TAG = "S";
    public static final String STATE_DEFAULT_WELCOME = "Welcome!";
    public static final int STATE_MAX_MEMBERS = 5;

    public static final String STRING_BLANK = " ";
    public static final String STRING_HINT_MAX = " (max)";
    public static final String STRING_OF = " / ";
    public static final String STRING_OF_SHORT = "/";
    public static final String STRING_UNKNOWN = "???";
    public static final String STRING_WELCOME_MESSAGE_BUFFER = ": §f";

    public static final int EARTH_BLOCK_MIN_X = 0;
    public static final int EARTH_BLOCK_MIN_Z = 0;
    public static final int EARTH_SIZE_X = 4096 * 6;
    public static final int EARTH_SIZE_Z = 4096 * 3;
    public static final int EARTH_BLOCK_MAX_X = EARTH_BLOCK_MIN_X + EARTH_SIZE_X;
    public static final int EARTH_BLOCK_MAX_Z = EARTH_BLOCK_MIN_Z + EARTH_SIZE_Z;
    public static final int EARTH_SQUARE_SIZE = 16;
    public static final int EARTH_CHUNK_MIN_X = EARTH_BLOCK_MIN_X / EARTH_SQUARE_SIZE;
    public static final int EARTH_CHUNK_MIN_Z = EARTH_BLOCK_MIN_Z / EARTH_SQUARE_SIZE;
    public static final int EARTH_CHUNK_MAX_X = (EARTH_BLOCK_MIN_X + EARTH_SIZE_X) / EARTH_SQUARE_SIZE - 1;
    public static final int EARTH_CHUNK_MAX_Z = (EARTH_BLOCK_MIN_Z + EARTH_SIZE_Z) / EARTH_SQUARE_SIZE - 1;
    public static final int EARTH_TELEPORT_WIDTH = 256;
    public static final int EARTH_TELEPORT_EDGE_DISTANCE = 16;
    public static final double EARTH_TELEPORT_MAX_VELOCITY = 0.4;
    public static final int EARTH_LATITUDE_ZERO = EARTH_BLOCK_MIN_Z + (EARTH_SIZE_Z >> 1);
    public static final int EARTH_LONGITUDE_ZERO = EARTH_BLOCK_MIN_X + (EARTH_SIZE_X >> 1) - 3 * 256;
    public static final int EARTH_VOID_BEDROCK_MAX_Y = 62; //water level

    public static final int CLAIM_PLAYER_BLOCK_WIDTH = 3 * EARTH_SQUARE_SIZE;

    public static final int POWER_DIVISOR = 200; // the smaller, the quicker squares cost more power
    public static final int POWER_FACTOR = 10; // the bigger, the less valuable power is

    public static int getOneSquarePowerCost(int size) {
        return getPowerDifference(size, size + 1);
    }

    public static int getPowerDifference(int oldSize, int newSize) {
        return getEntirePowerCost(newSize) - getEntirePowerCost(oldSize);
    }

    // Inverse of getEntirePowerCost
    public static int getMaxSize(int power) {
        int maxSize = (int) (-POWER_DIVISOR
                + Math.sqrt(POWER_DIVISOR * POWER_DIVISOR + 2 * POWER_DIVISOR * (long) power / (double) POWER_FACTOR));
        if (getEntirePowerCost(maxSize + 1) <= power) maxSize++;
        return maxSize;
    }

    public static int getEntirePowerCost(int size) {
        double x = size;
        return (int) (POWER_FACTOR * x + POWER_FACTOR * x * x / (2 * POWER_DIVISOR));
    }

    // Derivative of getEntirePowerCost
    public static double getAverageSquareCost(int size) {
        double x = size;
        double a = POWER_FACTOR * (1 + x / POWER_DIVISOR);
        return Math.round(a * 100) / 100d;
    }

    public static Location coordinatesToLocation(double northDeg, double eastDeg) {
        if (northDeg < -90 || northDeg > 90 || eastDeg < -180 || eastDeg > 180) return null;
        double x = EARTH_LONGITUDE_ZERO + eastDeg / 180 / 2 * EARTH_SIZE_X;
        if (x < EARTH_BLOCK_MIN_X) x = x + EARTH_SIZE_X;
        if (x > EARTH_BLOCK_MAX_X) x = x - EARTH_SIZE_X;
        double z = EARTH_LATITUDE_ZERO + northDeg / 90 / -2 * EARTH_SIZE_Z;
        if (z < EARTH_BLOCK_MIN_Z) z = z + EARTH_SIZE_Z;
        if (z > EARTH_BLOCK_MAX_Z) z = z - EARTH_SIZE_Z;
        return new Location(null, x, 0, z);
    }

}
