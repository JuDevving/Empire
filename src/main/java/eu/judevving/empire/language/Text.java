package eu.judevving.empire.language;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.main.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum Text {

    BROADCAST_NEW_STATE("{0}§7 created a new state", true),
    BROADCAST_STATE_DELETED("{0}§7 has been deleted", true),
    BROADCAST_STATE_JOINED("{0}§7 has joined {1}§7", true),
    BROADCAST_STATE_LEFT("{0}§7 has left {1}§7", true),
    BROADCAST_STATE_POWER_LOSS("{0}§7 has lost §b{1}§7 power", true),
    BROADCAST_STATE_POWER_LOSS_TRANSFER("{0}§7 has lost §b{1}§7 power to {2}§7", true),

    BIOME_BADLANDS("badlands"),
    BIOME_BAMBOO_JUNGLE("bamboo jungle"),
    BIOME_BASALT_DELTAS("basalt deltas"),
    BIOME_CHERRY_GROVE("cherry grove"),
    BIOME_CRIMSON_FOREST("crimson forest"),
    BIOME_DARK_FOREST("dark forest"),
    BIOME_DESERT("desert"),
    BIOME_DRIPSTONE_CAVES("dripstone caves"),
    BIOME_FOREST("forest"),
    BIOME_FROZEN_RIVER("frozen river"),
    BIOME_JAGGED_PEAKS("jagged peaks"),
    BIOME_JUNGLE("jungle"),
    BIOME_LUSH_CAVES("lush caves"),
    BIOME_MANGROVE_SWAMP("mangrove swamp"),
    BIOME_MUSHROOM_FIELDS("mushroom fields"),
    BIOME_NETHER_WASTES("nether wastes"),
    BIOME_PALE_GARDEN("pale garden"),
    BIOME_RIVER("river"),
    BIOME_SAVANNA("savanna"),
    BIOME_SOUL_SAND_VALLEY("soul sand valley"),
    BIOME_SUNFLOWER_PLAINS("sunflower plains"),
    BIOME_SWAMP("swamp"),
    BIOME_WARM_OCEAN("warm ocean"),
    BIOME_WARPED_FOREST("warped forest"),

    BLOCK_ANDESITE("andesite"),
    BLOCK_BASALT("basalt"),
    BLOCK_BLACKSTONE("blackstone"),
    BLOCK_DIORITE("diorite"),
    BLOCK_DIRT("dirt"),
    BLOCK_DEEPSLATE("deepslate"),
    BLOCK_GRANITE("granite"),
    BLOCK_GRAVEL("gravel"),
    BLOCK_LOGS("logs"),
    BLOCK_NETHERRACK("netherrack"),
    BLOCK_SAND("sand"),
    BLOCK_STONE("stone"),
    BLOCK_TUFF("tuff"),

    CLAIM_RESULT_ALREADY_CLAIMED("{0} is already claimed by {1}§7 and too old to be overtaken", true),
    CLAIM_RESULT_ALREADY_CLAIMED_SELF("{0} is already claimed by your state", true),
    CLAIM_RESULT_CANNOT_UNCLAIM_CAPITAL("Could not unclaim {0} because it's the capital. If you really want to unclaim it, use \"/unclaim all\".", true),
    CLAIM_RESULT_CAPITAL_ALREADY_CLAIMED("The capital is already claimed", true),
    CLAIM_RESULT_DIFFERENT_STATE_PLAYER_NEARBY("Could not claim {0} because a player that is not member of your state is nearby", true),
    CLAIM_RESULT_NOT_CLAIMED("{0} is not claimed", true),
    CLAIM_RESULT_TOO_MANY_EDGES("{0} has too many edges ({1}) for takeover", true),
    CLAIM_RESULT_NOT_ENOUGH_POWER("Not enough power ({1})", true),
    CLAIM_RESULT_NO_CAPITAL("You need to claim the capital first", true),
    CLAIM_RESULT_OUT_OF_BOUNDS("{0} is out of bounds", true),
    CLAIM_RESULT_TOO_CLOSE("{0} is too close ({1}) to the capital of {2} for takeover", true),
    CLAIM_RESULT_TOO_FAR("{0} is too far away ({1}) from the capital", true),
    CLAIM_RESULT_TOO_FAR_TAKEOVER("{0} is too far away ({1}) from the capital to take away", true),
    CLAIM_RESULT_SUCCESS("{0} claimed", true),
    CLAIM_RESULT_SUCCESS_RECENT("{0} claimed (recent until {1})", true),
    CLAIM_RESULT_SUCCESS_CAPITAL("{0} claimed as capital", true),
    CLAIM_RESULT_SUCCESS_TAKEOVER("{0} taken over from {1}", true),
    CLAIM_RESULT_SUCCESS_UNCLAIM("{0} unclaimed", true),
    CLAIM_RESULT_SUCCESS_UNCLAIM_TAKEOVER("{0} unclaimed from {1}", true),
    CLAIM_RESULT_UNCLAIMABLE("§b{0}§7 is marked as unclaimable", true),
    CLAIM_RESULT_WRONG_WORLD("You can only claim in the overworld", true),

    COLOR_AQUA("aqua"),
    COLOR_BLACK("black"),
    COLOR_BLUE("blue"),
    COLOR_CYAN("cyan"),
    COLOR_DARK_BLUE("dark blue"),
    COLOR_DARK_RED("dark red"),
    COLOR_GRAY("gray"),
    COLOR_GREEN("green"),
    COLOR_LIGHT_GRAY("light gray"),
    COLOR_LIME("lime"),
    COLOR_ORANGE("orange"),
    COLOR_PINK("pink"),
    COLOR_PURPLE("purple"),
    COLOR_RED("red"),
    COLOR_WHITE("white"),
    COLOR_YELLOW("yellow"),

    COMMAND_FOR_PLAYERS("This command is only for players", true),
    COMMAND_NO_PERMISSION("§cNo permission", true),
    COMMAND_NO_STATE("You are not member of a state", true),
    COMMAND_NO_TERRITORY("Nothing claimed", true),
    COMMAND_PLAYER_NOT_FOUND("Player {0} not found", true),
    COMMAND_STATE_NOT_FOUND("State {0} not found. Make sure to use its ID.", true),

    COMMAND_CLAIM_AUTO_TURNED_OFF("Auto-claim turned off", true),
    COMMAND_CLAIM_AUTO_TURNED_ON("Auto-claim turned on", true),

    COMMAND_CONVERTCOORDINATES_RESULT("{0}N {1}E is at {2} {3}", true),

    COMMAND_DOUBLEJUMP_TOGGLED_OFF("Double jumps disabled", true),
    COMMAND_DOUBLEJUMP_TOGGLED_ON("Double jumps enabled", true),

    COMMAND_EMPIREADMIN_BACKUP_CREATED("Backup created", true),
    COMMAND_EMPIREADMIN_BACKUP_FAILED("Backup failed", true),
    COMMAND_EMPIREADMIN_CAPITAL_SET("Capital set", true),
    COMMAND_EMPIREADMIN_CLAIM_CLAIMED("Claimed", true),
    COMMAND_EMPIREADMIN_DELETE_DELETED("State deleted", true),
    COMMAND_EMPIREADMIN_DEFAULT_STRINGS_SET("Default strings set", true),
    COMMAND_EMPIREADMIN_DONATER_SET("Donater set to {0}", true),
    COMMAND_EMPIREADMIN_LEVEL_SET("Level set", true),
    COMMAND_EMPIREADMIN_POWER_SET("Power set", true),
    COMMAND_EMPIREADMIN_POI_ADDED("POI added", true),
    COMMAND_EMPIREADMIN_POI_REMOVED("Closest POI removed", true),
    COMMAND_EMPIREADMIN_QUESTS_SET("New quests set", true),
    COMMAND_EMPIREADMIN_RAILWAY_END_SUCCESS("Railway created", true),
    COMMAND_EMPIREADMIN_RAILWAY_INFO_INFO("Railway {0}: from {1} {2} to {3} {4}, length: {5}", true),
    COMMAND_EMPIREADMIN_RAILWAY_POINT_ADDED("Railway point added", true),
    COMMAND_EMPIREADMIN_RAILWAY_REMOVE_REMOVED("Railway removed", true),
    COMMAND_EMPIREADMIN_RAILWAY_START_SET("Started new railway", true),
    COMMAND_EMPIREADMIN_REMOVE_MEMBER_REMOVED("Member removed", true),
    COMMAND_EMPIREADMIN_SHRINK_SHRUNK("State shrunk", true),
    COMMAND_EMPIREADMIN_UNCLAIM_UNCLAIMED("Unclaimed", true),

    COMMAND_EMPIREDEBUG_LANUGAGES_RELOADED("Languages reloaded", true),

    COMMAND_HOME_TELEPORT_CANCELLED("You moved. Teleport cancelled.", true),
    COMMAND_HOME_TELEPORT_START("Teleporting to the capital in {0} seconds. Don't move.", true),

    COMMAND_LANGUAGE_SET("Language set to {0}", true),

    COMMAND_MARKER_DESCRIPTION_SET("Description of marker {0} set to \"{1}\"", true),
    COMMAND_MARKER_ICON_SET("Icon of marker {0} set to {1}", true),
    COMMAND_MARKER_INFO_HEADER("Marker §b{0}§7:", true),
    COMMAND_MARKER_INFO_ICON("Icon: §b{0}", true),
    COMMAND_MARKER_INFO_NAME("Name: §b{0}", true),
    COMMAND_MARKER_INFO_POSITION("Position: §b{0}", true),
    COMMAND_MARKER_NAME_SET("Name of marker {0} set to \"{1}\"", true),
    COMMAND_MARKER_NONE_AVAILABLE("Your state doesn't have markers yet", true),
    COMMAND_MARKER_NOT_PLACED("Marker {0} is not placed", true),
    COMMAND_MARKER_PLACE_CAPITAL("You cannot place markers at the capital", true),
    COMMAND_MARKER_PLACE_OCCUPIED("Marker {0} is already placed here", true),
    COMMAND_MARKER_PLACE_OUTSIDE_TERRITORY("You can only place markers in your territory", true),
    COMMAND_MARKER_PLACE_PLACED("Marker {0} placed at {1}", true),
    COMMAND_MARKER_REMOVE_REMOVED("Marker {0} removed", true),

    COMMAND_MESSAGE_NONE_ONLINE("No member of {0}§7 is online", true),
    COMMAND_MESSAGE_SELF("You can't message your own state", true),

    COMMAND_REPLY_NO_LAST_SENDER("No state has messaged yours. Use \"/message\".", true),

    COMMAND_SQUARE_OUT_OF_BOUNDS("You are not in a claimable area", true),
    COMMAND_SQUARE_INFO_CAPITAL("Capital: §b{0}", true),
    COMMAND_SQUARE_INFO_CLAIM_TIME("Claim time: §b{0}", true),
    COMMAND_SQUARE_INFO_HEADER("Square §b{0}§7:", true),
    COMMAND_SQUARE_INFO_POSITION("Position: §b{0}", true),
    COMMAND_SQUARE_INFO_STATE("State: {0}", true),
    COMMAND_SQUARE_INFO_TAKEOVER_POSSIBLE("Takeover possible: §b{0}", true),
    COMMAND_SQUARE_INFO_UNCLAIMABLE("Unclaimable: §b{0}", true),

    COMMAND_STATE_ACCEPT_NOT_INVITED("You have not been invited to {0}§7", true),
    COMMAND_STATE_CAPITAL_NAME_SET("Capital name set to \"{0}\"§7", true),
    COMMAND_STATE_COLOR_SET("State color set to {0}§7", true),
    COMMAND_STATE_CREATE_ALREADY_IN_STATE("You are already member of {0}§7", true),
    COMMAND_STATE_CREATE_ID_ALREADY_IN_USE("The ID §b{0}§7 is already in use by {1}§7", true),
    COMMAND_STATE_DESCRIPTION_SET("State description set to \"{0}\"", true),
    COMMAND_STATE_FLAG_SET("State flag set", true),
    COMMAND_STATE_ENEMY_WELCOME_SET("Enemy \"welcome\" message set to \"{0}\"", true),
    COMMAND_STATE_INVITE_ALREADY_IN_STATE("{0} is already member of {1}§7", true),
    COMMAND_STATE_INVITE_ALREADY_INVITED("{0} is already invited", true),
    COMMAND_STATE_INVITE_GOTTEN("You have been invited to join {0}§7. Use \"/state accept {1}\" to accept.", true),
    COMMAND_STATE_INVITE_MAX_MEMBERS("{0}§7 already has the maximum amount of members", true),
    COMMAND_STATE_INVITE_SENT("{0} has been invited to join your state", true),
    COMMAND_STATE_NAME_SET("State name set to \"{0}§7\"", true),
    COMMAND_STATE_TAG_SET("State tag set to {0}", true),
    COMMAND_STATE_WELCOME_SET("Welcome message set to \"{0}\"", true),

    COMMAND_UNCLAIM_ALL_SUCCESS("Unclaimed all squares", true),
    COMMAND_UNCLAIM_AUTO_TURNED_OFF("Auto-unclaim turned off", true),
    COMMAND_UNCLAIM_AUTO_TURNED_ON("Auto-unclaim turned on", true),

    COMMAND_USAGE_CLAIM("/claim <auto/capital>", true),
    COMMAND_USAGE_CLAIM_AUTO("/claim auto", true),
    COMMAND_USAGE_CLAIM_CAPITAL("/claim capital", true),
    COMMAND_USAGE_CONVERTCOORDINATES("/convertcoordinates <-90.0 - 90.0> <-180.0 - 180.0>", true),
    COMMAND_USAGE_DOUBLEJUMP("/doublejump", true),
    COMMAND_USAGE_EMPIREDEBUG("/empiredebug <1-10/heads/lang/langrl/missingtranslations/randomcoordinates>", true),
    COMMAND_USAGE_HOME("/home", true),
    COMMAND_USAGE_LANGUAGE("/language <{0}>", true),
    COMMAND_USAGE_MARKER("/marker <description/icon/info/name/place/remove> <ID>", true),
    COMMAND_USAGE_MARKER_DESCRIPTION("/marker description <ID> <description>", true),
    COMMAND_USAGE_MARKER_ICON("/marker icon <ID> <{0}>", true),
    COMMAND_USAGE_MARKER_INFO("/marker info <ID>", true),
    COMMAND_USAGE_MARKER_MARKER_NOT_FOUND("Marker not found. Available markers: {0}", true),
    COMMAND_USAGE_MARKER_NAME("/marker name <ID> <name>", true),
    COMMAND_USAGE_MARKER_PLACE("/marker place <ID>", true),
    COMMAND_USAGE_MARKER_REMOVE("/marker remove <ID>", true),
    COMMAND_USAGE_MESSAGE("/message <ID> <message>", true),
    COMMAND_USAGE_MINIMAP("/minimap", true),
    COMMAND_USAGE_REPLY("/reply <message>", true),
    COMMAND_USAGE_SQUARE("/square <x> <z>", true),
    COMMAND_USAGE_STATE("/state <accept/capitalname/color/create/description/enemywelcome/flag/invite/leave/name/tag/welcome>", true),
    COMMAND_USAGE_STATE_ACCEPT("/state accept <ID>", true),
    COMMAND_USAGE_STATE_CAPITAL_NAME("/state capitalname <name>", true),
    COMMAND_USAGE_STATE_COLOR("/state color <{0}>", true),
    COMMAND_USAGE_STATE_CREATE("/state create <ID>", true),
    COMMAND_USAGE_STATE_DESCRIPTION("/state description <description>", true),
    COMMAND_USAGE_STATE_ENEMY_WELCOME("/state enemywelcome <welcome message>", true),
    COMMAND_USAGE_STATE_FLAG("/state flag", true),
    COMMAND_USAGE_STATE_FLAG_NO_BANNER("Hold a banner in your main hand and try again", true),
    COMMAND_USAGE_STATE_INVITE("/state invite <player>", true),
    COMMAND_USAGE_STATE_LEAVE("Are you sure you want to leave your state? Use \"/state leave confirm\" to confirm.", true),
    COMMAND_USAGE_STATE_NAME("/state name <name>", true),
    COMMAND_USAGE_STATE_TAG("/state tag <tag>", true),
    COMMAND_USAGE_STATE_WELCOME("/state welcome <welcome message>", true),
    COMMAND_USAGE_UNCLAIM("/unclaim <all/auto>", true),
    COMMAND_USAGE_UNCLAIM_ALL("Are you sure you want to unclaim §call§7 squares? Use \"/unclaim all confirm\" to confirm.", true),
    COMMAND_USAGE_UNCLAIM_AUTO("/unclaim auto", true),
    COMMAND_USAGE_WELCOMEMESSAGE("/welcomemessage <chat/off/title>", true),

    COMMAND_WELCOMEMESSAGE_SET("Welcome message set to {0}", true),

    CRATE_SPAWNED("A loot crate spawned at {0} {1}", true),

    DEATH_ITEM_LOSS("You dropped {0} item(s)", true),

    INFO_CAPITAL_DESCRIPTION("The capital is the center of a state. It limits where you can claim territory and can only be moved after using \"/unclaim all\".", true),
    INFO_ELYTRAS_DISABLED("You can only use elytras in the end", true),
    INFO_FIRST_JOIN_HEADER("§aWelcome to Empire 2!", true),
    INFO_FIRST_JOIN_MESSAGE("Jump on the earth to choose your starting location and manage your state with \"/state\"", true),
    INFO_JOIN_STATE("Create a state with \"/state create\" or ask a friend to invite you with \"/state invite\"", true),
    INFO_LEAVE_STATE_LAST("§cSince you are the only member, leaving will delete the state", true),
    INFO_LEAVE_STATE_LOSS("Note that leaving your state will make it lose power as if you died", true),
    INFO_STATE_ID_DESCRIPTION("The ID of a state is its unique identifier. A state's name and tag can be changed, but not its ID.", true),
    INFO_FORMAT_ILLEGAL("§cIllegal format", true),
    INFO_FORMAT_LENGTH("Length: {0}-{1} characters", true),
    INFO_FORMAT_LEGAL_CHARACTERS("Legal characters: {0}", true),
    INFO_WITHER_SPAWN("Withers are disabled in the overworld"),

    LANGUAGE_NAME_ENGLISH("DefaultEnglish"),
    LANGUAGE_NAME_NATIVE("DefaultEnglish"),

    MATERIAL_ACACIA_LOGS("acacia logs"),
    MATERIAL_AMETHYST_SHARDS("amethyst shards"),
    MATERIAL_BAMBOO_BLOCKS("bamboo blocks"),
    MATERIAL_BEETROOTS("beetroots"),
    MATERIAL_BIRCH_LOGS("birch logs"),
    MATERIAL_BONES("bones"),
    MATERIAL_CACTI("cacti"),
    MATERIAL_CARROTS("carrots"),
    MATERIAL_CHERRY_LOGS("cherry logs"),
    MATERIAL_COAL("coal"),
    MATERIAL_COPPER_INGOTS("copper ingots"),
    MATERIAL_CRIMSON_STEMS("crimson stems"),
    MATERIAL_DARK_OAK_LOGS("dark oak logs"),
    MATERIAL_DIAMONDS("diamonds"),
    MATERIAL_EGGS("eggs"),
    MATERIAL_EMERALDS("emeralds"),
    MATERIAL_GLOWSTONE("glowstone"),
    MATERIAL_GOLD_INGOTS("gold ingots"),
    MATERIAL_GUNPOWDER("gunpowder"),
    MATERIAL_IRON_INGOTS("iron ingots"),
    MATERIAL_JUNGLE_LOGS("jungle logs"),
    MATERIAL_LAPIS_LAZULI("lapis lazuli"),
    MATERIAL_LEATHER("leather"),
    MATERIAL_MANGROVE_LOGS("mangrove logs"),
    MATERIAL_MELONS("melons"),
    MATERIAL_NETHER_WARTS("nether warts"),
    MATERIAL_NETHERITE_SCRAP("netherite scrap"),
    MATERIAL_OAK_LOGS("oak logs"),
    MATERIAL_PALE_OAK_LOGS("pale oak logs"),
    MATERIAL_POTATOES("potatoes"),
    MATERIAL_PUMPKINS("pumpkins"),
    MATERIAL_QUARTZ("quartz"),
    MATERIAL_REDSTONE("redstone"),
    MATERIAL_ROTTEN_FLESH("rotten flesh"),
    MATERIAL_SPIDER_EYES("spider eyes"),
    MATERIAL_SPRUCE_LOGS("spruce logs"),
    MATERIAL_STRINGS("strings"),
    MATERIAL_SUGAR_CANE("sugar cane"),
    MATERIAL_SWEET_BERRIES("sweet berries"),
    MATERIAL_WARPED_STEMS("warped stems"),
    MATERIAL_WHEAT("wheat"),

    MENU_FOOTER_CLICK_TO_PAY("§7Click to pay"),
    MENU_FOOTER_ONLY_PAID_IF_COMPLETE("§7Only paid if all items are present"),
    MENU_FOOTER_QUEST_NEW_TOMORROW("§7New quest tomorrow"),

    MENU_INVENTORY_NAME_LANGUAGES("Languages"),
    MENU_INVENTORY_NAME_MAIN("Menu"),

    MENU_LABEL_AVERAGE_SQUARE_COST("§7Square cost: "),
    MENU_LABEL_CAPITAL("§7Capital: "),
    MENU_LABEL_CAPITAL_NONE("-"),
    MENU_LABEL_DAILY_REWARD_FLAGS("§7Flags: "),
    MENU_LABEL_DAILY_REWARD_POWER("§7Power: "),
    MENU_LABEL_DAILY_REWARD_STREAK("§7Streak: "),
    MENU_LABEL_DAILY_REWARD_TAKEN_HEADER("§7Return tomorrow for:"),
    MENU_LABEL_DEATHS("§7Deaths: "),
    MENU_LABEL_DESCRIPTION("§7Description: "),
    MENU_LABEL_ENEMY_WELCOME("§7Enemy \"welcome\" message: "),
    MENU_LABEL_ID("§7ID: "),
    MENU_LABEL_LAST_ONLINE("§7Last online: "),
    MENU_LABEL_LEVEL("§7Level: "),
    MENU_LABEL_LEVELING_CLAIM_RADIUS("§7Claim radius: "),
    MENU_LABEL_LEVELING_MARKERS("§7Markers: "),
    MENU_LABEL_LEVELING_QUESTS("§7Quests: "),
    MENU_LABEL_LEVELING_UNKNOWN_DESCRIPTION("§7???"),
    MENU_LABEL_LEVELING_UNKNOWN_DESCRIPTION_SPECIAL("§e???"),
    MENU_LABEL_ONLINE("§7Online: "),
    MENU_LABEL_POWER("§7Power: "),
    MENU_LABEL_PROGRESS("§7Progress: "),
    MENU_LABEL_QUEST_COMPLETED_COUNT("§7Quests completed: "),
    MENU_LABEL_RANK("§7Rank: "),
    MENU_LABEL_RELATION("§7Relation: "),
    MENU_LABEL_RELATION_ALLY("§aAlly"),
    MENU_LABEL_RELATION_ENEMY("§cEnemy ->"),
    MENU_LABEL_RELATION_ENEMY_SELF("§cEnemy <-"),
    MENU_LABEL_RELATION_ENEMY_BOTH("§cEnemy -><-"),
    MENU_LABEL_RELATION_NEUTRAL("§fNeutral"),
    MENU_LABEL_RELATION_NONE("§7-"),
    MENU_LABEL_SIZE("§7Size: "),
    MENU_LABEL_STATE("§7State: "),
    MENU_LABEL_STATUS("§7Status: "),
    MENU_LABEL_STATUS_OFFLINE("§cOffline"),
    MENU_LABEL_STATUS_ONLINE("§aOnline"),
    MENU_LABEL_TAG("§7Tag: "),
    MENU_LABEL_WELCOME("§7Welcome message: "),

    MENU_LEVELING_PAYMENT_DESCRIPTION_ARMOR_TRIMS("§7Pay every armor trim"),
    MENU_LEVELING_PAYMENT_DESCRIPTION_FLOWERS("§7Pay every flower"),
    MENU_LEVELING_PAYMENT_DESCRIPTION_HEADS("§7Pay every mob head"),
    MENU_LEVELING_PAYMENT_DESCRIPTION_MUSIC_DISCS("§7Pay every music disc"),
    MENU_LEVELING_PAYMENT_DESCRIPTION_ARMOR_SHERDS("§7Pay every pottery sherd"),

    MENU_NAME_BACK_TO("§eBack to {0}"),
    MENU_NAME_BACK_TO_STATE_LIST("§eBack to state list"),
    MENU_NAME_DAILY_REWARD("§eDaily reward"),
    MENU_NAME_DAILY_REWARD_TAKEN("§fDaily reward"),
    MENU_NAME_HOME("§eMain menu"),
    MENU_NAME_LEVELING("§eLeveling"),
    MENU_NAME_LEVELING_BONUS_DOUBLE_JUMPS("§7Unlocks double jumps in own non-recent territory (\"/dj\" to toggle)"),
    MENU_NAME_LEVELING_BONUS_FARMLAND_TRAMPLE("§7Unlocks farmland trample toggle"),
    MENU_NAME_LEVELING_BONUS_HOME_GOLDEN_TAG_BRACKETS("§7Unlocks /home and tag brackets become golden"),
    MENU_NAME_LEVELING_BONUS_MOB_SWITCH("§7Unlocks monster spawn toggle"),
    MENU_NAME_LEVELING_BONUS_NETHER_ELYTRA("§7Unlocks elytras in the nether"),
    MENU_NAME_LEVELING_BONUS_NO_FALL_DAMAGE("§7No fall damage in own non-recent territory"),
    MENU_NAME_LEVELING_LEVEL("§eLevel {0}"),
    MENU_NAME_LEVELING_LEVEL_NEXT("§eLevel {0}"),
    MENU_NAME_LIST_ALLIES("§eAlly list §7({0})"),
    MENU_NAME_LIST_ALLIES_INFO("§fAllies of {0}"),
    MENU_NAME_LIST_ENEMIES("§eEnemy list §7({0})"),
    MENU_NAME_LIST_ENEMIES_INFO("§fEnemies of {0}"),
    MENU_NAME_LIST_MEMBERS("§eMember list §7({0})"),
    MENU_NAME_LIST_MEMBERS_INFO("§fMembers of {0}"),
    MENU_NAME_MORE_INFO("§fMore info"),
    MENU_NAME_OFF("§cOff"),
    MENU_NAME_ON("§aOn"),
    MENU_NAME_PAGE_NEXT("§eNext page"),
    MENU_NAME_PAGE_PREVIOUS("§ePrevious page"),
    MENU_NAME_PLAYERS("§ePlayers"),
    MENU_NAME_QUEST_LOCKED("§fLevel up to unlock"),
    MENU_NAME_QUEST_SKIP_BUTTON("§eSkip a quest"),
    MENU_NAME_QUEST_SKIP_INFO("§fClick the quest you want to skip"),
    MENU_NAME_QUEST_SKIPPED("§fSkipped quest"),
    MENU_NAME_QUESTS("§eQuests"),
    MENU_NAME_RELATION_ALLIANCE_ACCEPT("§aAccept alliance"),
    MENU_NAME_RELATION_ALLIANCE_BREAK("§fBreak alliance"),
    MENU_NAME_RELATION_ALLIANCE_OFFER("§aOffer alliance"),
    MENU_NAME_RELATION_ALLIANCE_REJECT("§fReject alliance"),
    MENU_NAME_RELATION_ENEMY_DECLARE("§cDeclare as enemy"),
    MENU_NAME_RELATION_NEUTRAL_DECLARE("§fDeclare as neutral"),
    MENU_NAME_SELECT_LANGUAGE("§eSelect language"),
    MENU_NAME_SELECT_LANGUAGE_LANGUAGE_PREFIX("§e"),
    MENU_NAME_SETTINGS("§eSettings"),
    MENU_NAME_SETTINGS_LEVEL_BUILD("§fBuild"),
    MENU_NAME_SETTINGS_LEVEL_BUILD_DESCRIPTION("§fBreak, build, hit"),
    MENU_NAME_SETTINGS_LEVEL_DEFAULT("§fDefault"),
    MENU_NAME_SETTINGS_LEVEL_DEFAULT_DESCRIPTION("§fUse relation"),
    MENU_NAME_SETTINGS_LEVEL_INTERACT("§fInteract"),
    MENU_NAME_SETTINGS_LEVEL_INTERACT_DESCRIPTION("§fDoors, trapdoors, crafting tables, ender chests, etc."),
    MENU_NAME_SETTINGS_LEVEL_LOOT("§fLoot"),
    MENU_NAME_SETTINGS_LEVEL_LOOT_DESCRIPTION("§fChests, fence gates, item frames, mobs, etc."),
    MENU_NAME_SETTINGS_LEVEL_NONE("§fMinimal"),
    MENU_NAME_SETTINGS_LEVEL_NONE_DESCRIPTION("§fEnter, pressure plates, etc."),
    MENU_NAME_SETTINGS_LEVEL_USE("§fUse"),
    MENU_NAME_SETTINGS_LEVEL_USE_DESCRIPTION("§fButtons, levers, anvils, enchanting tables, etc."),
    MENU_NAME_SETTINGS_RELATION_ALLY("§fAllies"),
    MENU_NAME_SETTINGS_RELATION_ENEMY("§fEnemies"),
    MENU_NAME_SETTINGS_RELATION_NEUTRAL("§fNeutrals"),
    MENU_NAME_SETTINGS_TOGGLE_FARMLAND_CAN_BE_TRAMPLED("§fFarmland can be trampled"),
    MENU_NAME_SETTINGS_TOGGLE_FIRE_SPREAD("§fFire spreads"),
    MENU_NAME_SETTINGS_TOGGLE_FRIENDLY_FIRE("§fFriendly fire"),
    MENU_NAME_SETTINGS_TOGGLE_LOCKED("§fLevel up to unlock"),
    MENU_NAME_SETTINGS_TOGGLE_MONSTERS_SPAWN("§fMonsters can spawn"),
    MENU_NAME_SETTINGS_TOGGLE_NATURAL_LIGHTNING("§fNatural lightning strikes"),
    MENU_NAME_SETTINGS_TOGGLE_OWNERLESS_TNT("§fOwnerless TNT explodes"),
    MENU_NAME_SETTINGS_TOGGLE_PUBLIC_BOATS("§fPublic boats"),
    MENU_NAME_SETTINGS_TOGGLE_PUBLIC_MINECARTS("§fPublic minecarts"),
    MENU_NAME_SETTINGS_TOGGLE_SNOW_GOLEM_SNOW("§fSnow golems create snow"),
    MENU_NAME_SETTINGS_TOGGLE_VILLAGER_CROPS("§fVillagers can harvest crops"),
    MENU_NAME_SETTINGS_TOGGLE_VILLAGER_DOORS("§fVillagers can open doors"),
    MENU_NAME_STATES("§eStates"),

    MINIMAP_NAME("Minimap"),

    MOB_BLAZES("blazes"),
    MOB_BOGGED("bogged"),
    MOB_CREEPERS("creepers"),
    MOB_DROWNED("drowned"),
    MOB_ENDERMEN("endermen"),
    MOB_GHASTS("ghasts"),
    MOB_HOGLINS("hoglins"),
    MOB_HUSKS("husks"),
    MOB_MAGMA_CUBES("magma cubes"),
    MOB_PIGLINS("piglins"),
    MOB_SKELETONS("skeletons"),
    MOB_SLIMES("slimes"),
    MOB_SPIDERS("spiders"),
    MOB_STRAYS("strays"),
    MOB_WITCHES("witches"),
    MOB_WITHER_SKELETONS("wither skeletons"),
    MOB_ZOMBIES("zombies"),
    MOB_ZOMBIFIED_PIGLINS("zombified piglins"),

    PLAYER_LIST_FOOTER("§7Map: §emap.toastlawine.eu"),
    PLAYER_LIST_HEADER("§bEmpire 2"),

    QUEST_CATEGORY_KILL("§eKill {0} {1}"),
    QUEST_CATEGORY_MINE("§eMine {0} {1}"),
    QUEST_CATEGORY_PAY("§ePay {0} {1}"),
    QUEST_CATEGORY_VISIT("§eVisit the biome {1}"),

    RELATION_ALLIANCE_BROKEN_BROADCAST("{0}§7 broke their alliance with {1}§7", true),
    RELATION_ALLIANCE_FORMED_BROADCAST("§7An alliance between {0}§7 and {1}§7 was formed", true),
    RELATION_ALLIANCE_OFFER_GOTTEN("{0}§7 offered you an alliance", true),
    RELATION_ALLIANCE_OFFER_SENT("You offered {0}§7 an alliance", true),
    RELATION_ALLIANCE_OFFER_REJECTION_GOTTEN("{0}§7 rejected an alliance", true),
    RELATION_ALLIANCE_OFFER_REJECTION_SENT("You rejected an alliance with {0}§7", true),
    RELATION_ENEMY_DECLARED_BROADCAST("{0}§c declared {1}§c as their enemy", true),
    RELATION_NEUTRAL_DECLARED_BROADCAST("{0}§7 declared {1}§7 as neutral", true),

    RESPAWN_LOCATION_ILLEGAL("You jumped on another state. Try again.", true),

    SHOP_CREATED("Shop created", true),
    SHOP_INVALID_AMOUNT("Invalid product amount", true),
    SHOP_INVALID_CHEST("No chest or invalid chest", true),
    SHOP_INVALID_PRICE("Invalid price", true),
    SHOP_INVALID_PRODUCT("Invalid product", true),
    SHOP_NOT_ENOUGH_MONEY("You don't have enough diamonds", true),
    SHOP_OUT_OF_STOCK("This shop is out of stock", true),

    STATE_LEVEL_UP("{0}§7 is now level {1}", true),
    STATE_MESSAGE("{0}§7 -> {1}§7: §f{2}", false),
    STATE_NONE("-", false),
    STATE_POWER_GAIN("§b+{0}§7 power", true),
    STATE_QUEST_COMPLETED("Completed quest \"{0}§7\"", true),
    STATE_TERRITORY_LOSS("{0} squares have been unclaimed", true),

    TITLE_ENTER_NO_MANS_LAND("No man's land"),
    TITLE_ENTER_NO_MANS_LAND_SUBTITLE(" "),

    WELCOME_MESSAGE("You entered §f{0}§7{1}", true),

    WORD_NO("no"),
    WORD_YES("yes");

    private final Translation defaultTranslation;
    private final boolean prefix;
    private static final Language defaultLanguage = new Language("DefaultLanguage");
    public static final String PREFIX = "§7[§bEmpire§7] ";
    public static final String MISSING_ARGUMENT = "§cMISSING ARGUMENT";
    public static final String MISSING_TRANSLATION = "§cMISSING TRANSLATION";

    Text(String defaultTranslation, boolean prefix) {
        this.defaultTranslation = new Translation(defaultTranslation);
        this.prefix = prefix;
    }

    Text(String defaultTranslation) {
        this(defaultTranslation, false);
    }

    public String get(CommandSender sender, String... args) {
        if (sender instanceof Player p) {
            return get(p, args);
        }
        return get(defaultLanguage, args);
    }

    public String get(Human h, String... args) {
        return get(h.getLanguage(), args);
    }

    public String get(Player p, String... args) {
        Language language = Main.getPlugin().getEarth().getHuman(p).getLanguage();
        return get(language, args);
    }

    public String get(String... args) {
        return get(defaultLanguage, args);
    }

    public String get(Language language, String... args) {
        Translation translation = null;
        if (language != null) translation = language.getTranslation(this);
        if (translation == null) translation = defaultTranslation;
        return (prefix ? PREFIX : "") + translation.get(args);
    }

    public boolean hasTranslation(Language language) {
        return language.getTranslation(this) != null;
    }

    public Text fromString(String string) {
        for (Text text : Text.values()) {
            if (text.name().equals(string)) return text;
        }
        return null;
    }

    public String getDefaultTranslationRaw() {
        return defaultTranslation.getRawTranslation();
    }
}
