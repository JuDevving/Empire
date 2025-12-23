package eu.judevving.empire.quest;

import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.inventory.CustomHead;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public enum QuestType {

    KILL_BLAZES(QuestCategory.KILL, 40, EntityType.BLAZE, 10, CustomHead.MOB_BLAZE.get(), Text.MOB_BLAZES),
    KILL_BOGGED(QuestCategory.KILL, 50, EntityType.BOGGED, 5, CustomHead.MOB_BOGGED.get(), Text.MOB_BOGGED),
    KILL_CREEPERS(QuestCategory.KILL, 30, EntityType.CREEPER, 15, Material.CREEPER_HEAD, Text.MOB_CREEPERS),
    KILL_DROWNED(QuestCategory.KILL, 40, EntityType.DROWNED, 20, CustomHead.MOB_DROWNED.get(), Text.MOB_DROWNED),
    KILL_ENDERMEN(QuestCategory.KILL, 50, EntityType.ENDERMAN, 10, CustomHead.MOB_ENDERMAN.get(), Text.MOB_ENDERMEN),
    KILL_GHASTS(QuestCategory.KILL, 40, EntityType.GHAST, 5, CustomHead.MOB_GHAST.get(), Text.MOB_GHASTS),
    KILL_HOGLINS(QuestCategory.KILL, 50, EntityType.HOGLIN, 10, CustomHead.MOB_HOGLIN.get(), Text.MOB_HOGLINS),
    KILL_HUSKS(QuestCategory.KILL, 50, EntityType.HUSK, 20, CustomHead.MOB_HUSK.get(), Text.MOB_HUSKS),
    KILL_MAGMA_CUBES(QuestCategory.KILL, 40, EntityType.MAGMA_CUBE, 40, CustomHead.MOB_MAGMA_CUBE.get(), Text.MOB_MAGMA_CUBES),
    KILL_PIGLINS(QuestCategory.KILL, 50, EntityType.PIGLIN, 20, Material.PIGLIN_HEAD, Text.MOB_PIGLINS),
    KILL_SKELETONS(QuestCategory.KILL, 30, EntityType.SKELETON, 20, Material.SKELETON_SKULL, Text.MOB_SKELETONS),
    KILL_SLIMES(QuestCategory.KILL, 40, EntityType.SLIME, 40, CustomHead.MOB_SLIME.get(), Text.MOB_SLIMES),
    KILL_SPIDERS(QuestCategory.KILL, 30, EntityType.SPIDER, 20, CustomHead.MOB_SPIDER.get(), Text.MOB_SPIDERS),
    //KILL_STRAYS(QuestCategory.KILL, 50, EntityType.STRAY, 20, CustomHead.MOB_STRAY.get(), Text.MOB_STRAYS),
    KILL_WITCHES(QuestCategory.KILL, 50, EntityType.WITCH, 5, CustomHead.MOB_WITCH.get(), Text.MOB_WITCHES),
    KILL_WITHER_SKELETONS(QuestCategory.KILL, 50, EntityType.WITHER_SKELETON, 20, Material.WITHER_SKELETON_SKULL, Text.MOB_WITHER_SKELETONS),
    KILL_ZOMBIES(QuestCategory.KILL, 30, EntityType.ZOMBIE, 20, Material.ZOMBIE_HEAD, Text.MOB_ZOMBIES),
    KILL_ZOMBIFIED_PIGLINS(QuestCategory.KILL, 40, EntityType.ZOMBIFIED_PIGLIN, 20, CustomHead.MOB_ZOMBIFIED_PIGLIN.get(), Text.MOB_ZOMBIFIED_PIGLINS),

    MINE_ANDESITE(QuestCategory.MINE, 30, Material.ANDESITE, 128, Material.ANDESITE, Text.BLOCK_ANDESITE),
    MINE_BASALT(QuestCategory.MINE, 30, Material.BASALT, 128, Material.BASALT, Text.BLOCK_BASALT),
    MINE_BLACKSTONE(QuestCategory.MINE, 30, Material.BLACKSTONE, 128, Material.BLACKSTONE, Text.BLOCK_BLACKSTONE),
    MINE_DEEPSLATE(QuestCategory.MINE, 40, Material.DEEPSLATE, 256, Material.DEEPSLATE, Text.BLOCK_DEEPSLATE),
    MINE_DEEPSLATE_2(QuestCategory.MINE, 100, Material.DEEPSLATE, 512, Material.DEEPSLATE, Text.BLOCK_DEEPSLATE),
    MINE_DIRT(QuestCategory.MINE, 20, MaterialCategory.DIRT, 128, Material.DIRT, Text.BLOCK_DIRT),
    MINE_DIORITE(QuestCategory.MINE, 30, Material.DIORITE, 128, Material.DIORITE, Text.BLOCK_DIORITE),
    MINE_GRANITE(QuestCategory.MINE, 30, Material.GRANITE, 128, Material.GRANITE, Text.BLOCK_GRANITE),
    MINE_GRAVEL(QuestCategory.MINE, 20, Material.GRAVEL, 128, Material.GRAVEL, Text.BLOCK_GRAVEL),
    MINE_LOGS(QuestCategory.MINE, 30, MaterialCategory.LOGS, 128, Material.OAK_LOG, Text.BLOCK_LOGS),
    MINE_LOGS_2(QuestCategory.MINE, 70, MaterialCategory.LOGS, 256, Material.OAK_LOG, Text.BLOCK_LOGS),
    MINE_NETHERRACK(QuestCategory.MINE, 30, Material.NETHERRACK, 256, Material.NETHERRACK, Text.BLOCK_NETHERRACK),
    MINE_SAND(QuestCategory.MINE, 20, MaterialCategory.SAND, 128, Material.SAND, Text.BLOCK_SAND),
    MINE_STONE(QuestCategory.MINE, 30, Material.STONE, 256, Material.STONE, Text.BLOCK_STONE),
    MINE_STONE_2(QuestCategory.MINE, 70, Material.STONE, 512, Material.STONE, Text.BLOCK_STONE),
    MINE_TUFF(QuestCategory.MINE, 30, Material.TUFF, 128, Material.TUFF, Text.BLOCK_TUFF),

    PAY_AMETHYST_SHARDS(QuestCategory.PAY, 30, Material.AMETHYST_SHARD, 32, Material.AMETHYST_SHARD, Text.MATERIAL_AMETHYST_SHARDS),
    PAY_BEETROOT(QuestCategory.PAY, 30, Material.BEETROOT, 32, Material.BEETROOT, Text.MATERIAL_BEETROOTS),
    PAY_BONES(QuestCategory.PAY, 30, Material.BONE, 16, Material.BONE, Text.MATERIAL_BONES),
    PAY_CACTI(QuestCategory.PAY, 30, Material.CACTUS, 32, Material.CACTUS, Text.MATERIAL_CACTI),
    PAY_CARROTS(QuestCategory.PAY, 30, Material.CARROT, 64, Material.CARROT, Text.MATERIAL_CARROTS),
    PAY_COAL(QuestCategory.PAY, 30, Material.COAL, 48, Material.COAL, Text.MATERIAL_COAL),
    PAY_COPPER(QuestCategory.PAY, 20, Material.COPPER_INGOT, 64, Material.COPPER_INGOT, Text.MATERIAL_COPPER_INGOTS),
    PAY_DIAMONDS(QuestCategory.PAY, 50, Material.DIAMOND, 4, Material.DIAMOND, Text.MATERIAL_DIAMONDS),
    //PAY_EGGS(QuestCategory.PAY, 20, Material.EGG, 16, Material.EGG, Text.MATERIAL_EGGS),
    PAY_EMERALDS(QuestCategory.PAY, 30, Material.EMERALD, 8, Material.EMERALD, Text.MATERIAL_EMERALDS),
    PAY_GLOWSTONE(QuestCategory.PAY, 30, Material.GLOWSTONE, 16, Material.GLOWSTONE, Text.MATERIAL_GLOWSTONE),
    PAY_GOLD(QuestCategory.PAY, 50, Material.GOLD_INGOT, 32, Material.GOLD_INGOT, Text.MATERIAL_GOLD_INGOTS),
    PAY_GUNPOWDER(QuestCategory.PAY, 30, Material.GUNPOWDER, 16, Material.GUNPOWDER, Text.MATERIAL_GUNPOWDER),
    PAY_IRON(QuestCategory.PAY, 30, Material.IRON_INGOT, 32, Material.IRON_INGOT, Text.MATERIAL_IRON_INGOTS),
    PAY_LAPIS_LAZULI(QuestCategory.PAY, 30, Material.LAPIS_LAZULI, 48, Material.LAPIS_LAZULI, Text.MATERIAL_LAPIS_LAZULI),
    PAY_LEATHER(QuestCategory.PAY, 30, Material.LEATHER, 16, Material.LEATHER, Text.MATERIAL_LEATHER),
    PAY_MELONS(QuestCategory.PAY, 30, Material.MELON, 32, Material.MELON, Text.MATERIAL_MELONS),
    PAY_NETHER_WARTS(QuestCategory.PAY, 30, Material.NETHER_WART, 32, Material.NETHER_WART, Text.MATERIAL_NETHER_WARTS),
    PAY_NETHERITE_SCRAP(QuestCategory.PAY, 50, Material.NETHERITE_SCRAP, 1, Material.NETHERITE_SCRAP, Text.MATERIAL_NETHERITE_SCRAP),
    PAY_POTATOES(QuestCategory.PAY, 30, Material.POTATO, 64, Material.POTATO, Text.MATERIAL_POTATOES),
    PAY_PUMPKINS(QuestCategory.PAY, 30, Material.PUMPKIN, 32, Material.PUMPKIN, Text.MATERIAL_PUMPKINS),
    PAY_QUARTZ(QuestCategory.PAY, 40, Material.QUARTZ, 64, Material.QUARTZ, Text.MATERIAL_QUARTZ),
    PAY_REDSTONE(QuestCategory.PAY, 30, Material.REDSTONE, 64, Material.REDSTONE, Text.MATERIAL_REDSTONE),
    PAY_ROTTEN_FLESH(QuestCategory.PAY, 20, Material.ROTTEN_FLESH, 16, Material.ROTTEN_FLESH, Text.MATERIAL_ROTTEN_FLESH),
    PAY_SPIDER_EYES(QuestCategory.PAY, 40, Material.SPIDER_EYE, 16, Material.SPIDER_EYE, Text.MATERIAL_SPIDER_EYES),
    PAY_STRINGS(QuestCategory.PAY, 30, Material.STRING, 16, Material.STRING, Text.MATERIAL_STRINGS),
    PAY_SUGAR_CANE(QuestCategory.PAY, 20, Material.SUGAR_CANE, 32, Material.SUGAR_CANE, Text.MATERIAL_SUGAR_CANE),
    PAY_SWEET_BERRIES(QuestCategory.PAY, 30, Material.SWEET_BERRIES, 32, Material.SWEET_BERRIES, Text.MATERIAL_SWEET_BERRIES),
    PAY_WHEAT(QuestCategory.PAY, 30, Material.WHEAT, 32, Material.WHEAT, Text.MATERIAL_WHEAT),

    PAY_ACACIA_LOGS(QuestCategory.PAY, 30, Material.ACACIA_LOG, 64, Material.ACACIA_LOG, Text.MATERIAL_ACACIA_LOGS),
    PAY_BAMBOO_BLOCKS(QuestCategory.PAY, 30, Material.BAMBOO_BLOCK, 64, Material.BAMBOO_BLOCK, Text.MATERIAL_BAMBOO_BLOCKS),
    PAY_BIRCH_LOGS(QuestCategory.PAY, 30, Material.BIRCH_LOG, 64, Material.BIRCH_LOG, Text.MATERIAL_BIRCH_LOGS),
    PAY_CHERRY_LOGS(QuestCategory.PAY, 30, Material.CHERRY_LOG, 64, Material.CHERRY_LOG, Text.MATERIAL_CHERRY_LOGS),
    PAY_CRIMSON_STEMS(QuestCategory.PAY, 40, Material.CRIMSON_STEM, 64, Material.CRIMSON_STEM, Text.MATERIAL_CRIMSON_STEMS),
    PAY_DARK_OAK_LOGS(QuestCategory.PAY, 30, Material.DARK_OAK_LOG, 64, Material.DARK_OAK_LOG, Text.MATERIAL_DARK_OAK_LOGS),
    PAY_JUNGLE_LOGS(QuestCategory.PAY, 30, Material.JUNGLE_LOG, 64, Material.JUNGLE_LOG, Text.MATERIAL_JUNGLE_LOGS),
    PAY_MANGROVE_LOGS(QuestCategory.PAY, 30, Material.MANGROVE_LOG, 64, Material.MANGROVE_LOG, Text.MATERIAL_MANGROVE_LOGS),
    PAY_OAK_LOGS(QuestCategory.PAY, 30, Material.OAK_LOG, 64, Material.OAK_LOG, Text.MATERIAL_OAK_LOGS),
    PAY_PALE_OAK_LOGS(QuestCategory.PAY, 30, Material.PALE_OAK_LOG, 64, Material.PALE_OAK_LOG, Text.MATERIAL_PALE_OAK_LOGS),
    PAY_SPRUCE_LOGS(QuestCategory.PAY, 30, Material.SPRUCE_LOG, 64, Material.SPRUCE_LOG, Text.MATERIAL_SPRUCE_LOGS),
    PAY_WARPED_STEMS(QuestCategory.PAY, 40, Material.WARPED_STEM, 64, Material.WARPED_STEM, Text.MATERIAL_WARPED_STEMS),

    VISIT_BADLANDS(QuestCategory.VISIT, 100, Biome.BADLANDS, Material.DEAD_BUSH, Text.BIOME_BADLANDS),
    VISIT_BAMBOO_JUNGLE(QuestCategory.VISIT, 100, Biome.BAMBOO_JUNGLE, Material.BAMBOO, Text.BIOME_BAMBOO_JUNGLE),
    VISIT_BASALT_DELTAS(QuestCategory.VISIT, 30, Biome.BASALT_DELTAS, Material.BASALT, Text.BIOME_BASALT_DELTAS),
    VISIT_CHERRY_GROVE(QuestCategory.VISIT, 100, Biome.CHERRY_GROVE, Material.CHERRY_SAPLING, Text.BIOME_CHERRY_GROVE),
    VISIT_CRIMSON_FOREST(QuestCategory.VISIT, 30, Biome.CRIMSON_FOREST, Material.CRIMSON_FUNGUS, Text.BIOME_CRIMSON_FOREST),
    VISIT_DARK_FOREST(QuestCategory.VISIT, 100, Biome.DARK_FOREST, Material.DARK_OAK_SAPLING, Text.BIOME_DARK_FOREST),
    VISIT_DESERT(QuestCategory.VISIT, 30, Biome.DESERT, Material.CACTUS, Text.BIOME_DESERT),
    VISIT_DRIPSTONE_CAVES(QuestCategory.VISIT, 30, Biome.DRIPSTONE_CAVES, Material.POINTED_DRIPSTONE, Text.BIOME_DRIPSTONE_CAVES),
    VISIT_FOREST(QuestCategory.VISIT, 30, Biome.FOREST, Material.OAK_SAPLING, Text.BIOME_FOREST),
    VISIT_FROZEN_RIVER(QuestCategory.VISIT, 50, Biome.FROZEN_RIVER, Material.ICE, Text.BIOME_FROZEN_RIVER),
    VISIT_JAGGED_PEAKS(QuestCategory.VISIT, 50, Biome.JAGGED_PEAKS, Material.SNOW_BLOCK, Text.BIOME_JAGGED_PEAKS),
    VISIT_JUNGLE(QuestCategory.VISIT, 30, Biome.JUNGLE, Material.JUNGLE_SAPLING, Text.BIOME_JUNGLE),
    VISIT_LUSH_CAVES(QuestCategory.VISIT, 20, Biome.LUSH_CAVES, Material.FLOWERING_AZALEA, Text.BIOME_LUSH_CAVES),
    VISIT_MANGROVE_SWAMP(QuestCategory.VISIT, 50, Biome.MANGROVE_SWAMP, Material.MANGROVE_PROPAGULE, Text.BIOME_MANGROVE_SWAMP),
    VISIT_MUSHROOM_FIELDS(QuestCategory.VISIT, 100, Biome.MUSHROOM_FIELDS, Material.RED_MUSHROOM, Text.BIOME_MUSHROOM_FIELDS),
    VISIT_NETHER_WASTES(QuestCategory.VISIT, 30, Biome.NETHER_WASTES, Material.NETHERRACK, Text.BIOME_NETHER_WASTES),
    VISIT_PALE_GARDEN(QuestCategory.VISIT, 100, Biome.PALE_GARDEN, Material.PALE_OAK_SAPLING, Text.BIOME_PALE_GARDEN),
    VISIT_RIVER(QuestCategory.VISIT, 20, Biome.RIVER, Material.SEAGRASS, Text.BIOME_RIVER),
    VISIT_SAVANNA(QuestCategory.VISIT, 30, Biome.SAVANNA, Material.ACACIA_SAPLING, Text.BIOME_SAVANNA),
    VISIT_SOUL_SAND_VALLEY(QuestCategory.VISIT, 30, Biome.SOUL_SAND_VALLEY, Material.SOUL_SAND, Text.BIOME_SOUL_SAND_VALLEY),
    VISIT_SUNFLOWER_PLAINS(QuestCategory.VISIT, 100, Biome.SUNFLOWER_PLAINS, Material.SUNFLOWER, Text.BIOME_SUNFLOWER_PLAINS),
    VISIT_SWAMP(QuestCategory.VISIT, 50, Biome.SWAMP, Material.LILY_PAD, Text.BIOME_SWAMP),
    VISIT_WARM_OCEAN(QuestCategory.VISIT, 50, Biome.WARM_OCEAN, Material.TUBE_CORAL, Text.BIOME_WARM_OCEAN),
    VISIT_WARPED_FOREST(QuestCategory.VISIT, 30, Biome.WARPED_FOREST, Material.WARPED_FUNGUS, Text.BIOME_WARPED_FOREST);

    private static final Random random = new Random();

    private final int power;
    private final int goal;
    private final Object subject;
    private final Text subjectName;
    private final ItemStack item;
    private final QuestCategory category;

    QuestType(QuestCategory category, int power, Object subject, int goal, ItemStack item, Text subjectName) {
        this.category = category;
        this.power = power;
        this.subject = subject;
        this.goal = goal;
        this.item = item;
        this.subjectName = subjectName;
    }

    QuestType(QuestCategory category, int power, Object subject, int goal, Material material, Text subjectName) {
        this(category, power, subject, goal, new ItemStack(material), subjectName);
    }

    QuestType(QuestCategory category, int power, Object subject, Material material, Text subjectName) {
        this(category, power, subject, 1, new ItemStack(material), subjectName);
    }

    public static QuestType random() {
        return values()[random.nextInt(QuestType.count())];
    }

    public boolean progresses(QuestCategory category, Object subject) {
        if (category != this.category) return false;
        if (this.subject == subject) return true;
        if (this.subject instanceof MaterialCategory) return ((MaterialCategory) this.subject).is(subject);
        return false;
    }

    public String getTask(Language language) {
        return category.getTask().get(language, getGoal() + "", subjectName.get(language));
    }

    public Object getSubject() {
        return subject;
    }

    public int getGoal() {
        return goal;
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public int getPower() {
        return power;
    }

    public static int count() {
        return values().length;
    }

    public QuestCategory getCategory() {
        return category;
    }
}
