package eu.judevving.empire.earth.storage;

import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public enum StateLevel {

    LEVEL_1(1, 30, 0, 0, Material.GRASS_BLOCK, 1),
    LEVEL_2(2, 30, 1, 0, Material.COAL, 16),
    LEVEL_3(3, 50, 1, 0, Material.CRAFTING_TABLE, 64),
    LEVEL_4(4, 50, 2, 0, Material.IRON_INGOT, 16),
    LEVEL_5(5, 75, 2, 0, Material.APPLE, 16),
    LEVEL_6(6, 75, 3, 0, Material.COPPER_INGOT, 64),
    LEVEL_7(7, 100, 3, 0, Material.REDSTONE, 64),
    LEVEL_8(8, 100, 4, 0, Material.CRIMSON_STEM, 64),
    LEVEL_9(9, 150, 4, 0, Material.LAPIS_LAZULI, 64),
    LEVEL_10(10, 150, 5, 0, Material.TNT, 16),
    LEVEL_11(11, 200, 5, 0, Material.QUARTZ_BLOCK, 64),
    LEVEL_12(12, 300, 5, 0, Material.AMETHYST_SHARD, 32),
    LEVEL_13(13, 300, 6, 0, Material.LEATHER, 64),
    LEVEL_14(14, 300, 6, 1, Material.RABBIT_STEW, 4),
    LEVEL_15(15, 400, 6, 1, Material.GOLD_INGOT, 64),
    LEVEL_16(16, 400, 7, 1, Material.DIAMOND, 8),
    LEVEL_17(17, 500, 7, 1, Material.RABBIT_FOOT, 1),
    LEVEL_18(18, 750, 7, 1, Material.WITHER_SKELETON_SKULL, 1),
    LEVEL_19(19, 750, 8, 1, Material.MUSIC_DISC_11, 1),
    LEVEL_20(20, 750, 8, 2, Material.GOAT_HORN, 1),
    LEVEL_21(21, 1000, 8, 2, Material.TRIDENT, 1),
    LEVEL_22(22, 1000, 9, 2, Material.HONEYCOMB, 64),
    LEVEL_23(23, 1250, 9, 2, Material.CRYING_OBSIDIAN, 64, Text.MENU_NAME_LEVELING_BONUS_FARMLAND_TRAMPLE),
    LEVEL_24(24, 1500, 9, 2, Material.TOTEM_OF_UNDYING, 4),
    LEVEL_25(25, 1500, 10, 2, Material.NAUTILUS_SHELL, 8),
    LEVEL_26(26, 1750, 10, 2, Material.NETHERITE_INGOT, 2),
    LEVEL_27(27, 2000, 10, 2, Material.SNIFFER_EGG, 8),
    LEVEL_28(28, 2000, 11, 2, Material.EMERALD, 256),
    LEVEL_29(29, 2250, 11, 2, Material.OMINOUS_TRIAL_KEY, 8),
    LEVEL_30(30, 2500, 11, 3, Material.ENDER_EYE, 256),
    LEVEL_31(31, 2500, 12, 3, Material.VERDANT_FROGLIGHT, 64),
    LEVEL_32(32, 3000, 12, 3, Material.MUSIC_DISC_PIGSTEP, 1),
    LEVEL_33(33, 3500, 12, 3, Material.NETHER_BRICK, 8192),
    LEVEL_34(34, 3500, 13, 3, Material.CREEPER_HEAD, 1),
    LEVEL_35(35, 4000, 13, 3, Material.WITHER_ROSE, 64),
    LEVEL_36(36, 4500, 13, 3, Material.HEAVY_CORE, 1),
    LEVEL_37(37, 4500, 14, 3, Material.BELL, 64),
    LEVEL_38(38, 5000, 14, 3, Material.ELYTRA, 4),
    LEVEL_39(39, 5000, 14, 4, Material.END_CRYSTAL, 64),
    LEVEL_40(40, 5000, 15, 4, Material.NETHER_STAR, 4),
    LEVEL_41(41, 6000, 15, 4, Material.SPIDER_EYE, 1024),
    LEVEL_42(42, 7500, 15, 4, Material.LAPIS_BLOCK, 256),
    LEVEL_43(43, 7500, 16, 4, Material.EMERALD_BLOCK, 256),
    LEVEL_44(44, 9000, 16, 4, Material.MUSIC_DISC_LAVA_CHICKEN, 2),
    LEVEL_45(45, 12000, 16, 4, Material.SHULKER_SHELL, 128),
    LEVEL_46(46, 12000, 17, 4, Material.TRIDENT, 6),
    LEVEL_47(47, 15000, 17, 4, Material.NETHERITE_INGOT, 16),
    LEVEL_48(48, 20000, 17, 4, Material.POISONOUS_POTATO, 128),
    LEVEL_49(49, 20000, 18, 4, Material.SEA_LANTERN, 128),
    LEVEL_50(50, 20000, 18, 4, Material.DIAMOND_BLOCK, 64, Text.MENU_NAME_LEVELING_BONUS_MOB_SWITCH),
    LEVEL_51(51, 30000, 18, 4, Material.ROSE_BUSH, null, StateLevelItems.FLOWERS),
    LEVEL_52(52, 30000, 18, 4, Material.MUSIC_DISC_5, Text.MENU_NAME_LEVELING_BONUS_NO_FALL_DAMAGE,StateLevelItems.MUSIC_DISCS),
    LEVEL_53(53, 30000, 18, 4, Material.ZOMBIE_HEAD, Text.MENU_NAME_LEVELING_BONUS_DOUBLE_JUMPS, StateLevelItems.HEADS),
    LEVEL_54(54, 30000, 18, 4, Material.DANGER_POTTERY_SHERD, Text.MENU_NAME_LEVELING_BONUS_NETHER_ELYTRA, StateLevelItems.POTTERY_SHERDS),
    LEVEL_55(55, 30000, 18, 4, Material.FLOW_ARMOR_TRIM_SMITHING_TEMPLATE,Text.MENU_NAME_LEVELING_BONUS_HOME_GOLDEN_TAG_BRACKETS, StateLevelItems.ARMOR_TRIMS),
    LEVEL_100(100, 1000000, 18, 4, Material.COMMAND_BLOCK, 1),
    LEVEL_0(0, -1, -1, -1, Material.COMMAND_BLOCK, 1);

    private final int level, claimRadius, quests, markers;
    private final Material priceMaterial;
    private final int priceAmount;
    private final ItemStack item;
    private final Text bonus;
    private final StateLevelItems items;
    public static final StateLevel MAX_LEGITIMATE = LEVEL_55;

    StateLevel(int level, int claimRadius, int quests, int markers, Material priceMaterial, int priceAmount, Text bonus, StateLevelItems items) {
        this.level = level;
        this.claimRadius = claimRadius;
        this.quests = quests;
        this.markers = markers;
        this.priceMaterial = priceMaterial;
        this.priceAmount = priceAmount;
        this.bonus = bonus;
        this.item = new ItemStack(priceMaterial);
        this.items = items;
    }

    StateLevel(int level, int claimRadius, int quests, int markers, Material priceMaterial, Text bonus, StateLevelItems items) {
        this(level, claimRadius, quests, markers, priceMaterial, items.size(), bonus, items);
    }

    StateLevel(int level, int claimRadius, int quests, int markers, Material priceMaterial, int priceAmount, Text bonus) {
        this(level, claimRadius, quests, markers, priceMaterial, priceAmount, bonus, null);
    }

    StateLevel(int level, int claimRadius, int quests, int markers, Material priceMaterial, int priceAmount) {
        this(level, claimRadius, quests, markers, priceMaterial, priceAmount, null);
    }

    public Text getBonus() {
        return bonus;
    }

    public static StateLevel fromInt(int l) {
        for (StateLevel stateLevel : values()) {
            if (stateLevel.getLevel() != l) continue;
            return stateLevel;
        }
        return null;
    }

    public List<Component> getChange(Language lang) {
        StateLevel previous = getPrevious();
        List<Component> change = new LinkedList<>();
        if (claimRadius > previous.getClaimRadius())
            change.addLast(Component.text(Text.MENU_LABEL_LEVELING_CLAIM_RADIUS.get(lang) + getClaimRadius()));
        if (quests > previous.getQuests())
            change.addLast(Component.text(Text.MENU_LABEL_LEVELING_QUESTS.get(lang) + getQuests()));
        if (markers > previous.getMarkers())
            change.addLast(Component.text(Text.MENU_LABEL_LEVELING_MARKERS.get(lang) + getMarkers()));
        if (bonus != null)
            change.addLast(Component.text(bonus.get(lang)));
        return change;
    }

    public @NotNull StateLevel getNext() {
        if (level >= MAX_LEGITIMATE.level) return this;
        return values()[level];
    }

    public @NotNull StateLevel getPrevious() {
        if (level <= 1) return LEVEL_0;
        return values()[level - 2];
    }

    public static int getMax() {
        return MAX_LEGITIMATE.getLevel();
    }

    public int getClaimRadius() {
        return claimRadius;
    }

    public int getLevel() {
        return level;
    }

    public int getQuests() {
        return quests;
    }

    public int getMarkers() {
        return markers;
    }

    public Material getPriceMaterial() {
        return priceMaterial;
    }

    public int getPriceAmount() {
        return priceAmount;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public StateLevelItems getItems() {
        return items;
    }

    public boolean isAdvanced() {
        return items != null;
    }
}
