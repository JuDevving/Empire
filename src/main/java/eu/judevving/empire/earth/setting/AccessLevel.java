package eu.judevving.empire.earth.setting;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.listener.storage.EntityInteraction;
import eu.judevving.empire.listener.storage.EntityTags;
import eu.judevving.empire.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.*;

import java.util.HashSet;

public enum AccessLevel {

    DEFAULT(-1, null), // use relation instead of state
    NONE(0, null), // nothing
    INTERACT(1, NONE), // doors, trapdoors, buttons
    USE(2, INTERACT), // anvils, ride, shear, echests
    LOOT(3, USE), // chests, armor stands, kill
    BUILD(4, LOOT), // everything
    OWN(5, BUILD); // everything

    private int level;
    private AccessLevel lower;
    private HashSet<Material> allowedBlocks, allowedItems;
    private HashSet<EntityType> allowedEntities;

    AccessLevel(int level, AccessLevel lower) {
        this.level = level;
        this.lower = lower;
        init();
    }

    public boolean deniesBlock(Material material) {
        return !allowsBlock(material);
    }

    public boolean allowsBlock(Material material) {
        if (level >= BUILD.getLevel()) return true;
        if (getLower() != null) {
            if (getLower().allowsBlock(material)) return true;
        }
        return allowedBlocks.contains(material);
    }

    public boolean deniesItem(Material material) {
        return !allowsItem(material);
    }

    public boolean allowsItem(Material material) {
        if (level >= BUILD.getLevel()) return true;
        if (getLower() != null) {
            if (getLower().allowsItem(material)) return true;
        }
        return allowedItems.contains(material);
    }

    private boolean deniesEntity(EntityType entityType) {
        return !allowsEntity(entityType);
    }

    private boolean allowsEntity(EntityType entityType) {
        if (level >= LOOT.getLevel()) return true;
        if (getLower() != null) {
            if (getLower().allowsEntity(entityType)) return true;
        }
        return allowedEntities.contains(entityType);
    }

    public static boolean deniesEntity(Entity entity, Player p, EntityInteraction interaction) {
        return !allowsEntity(entity, p, interaction);
    }

    public static boolean allowsEntity(Entity entity, Player p, EntityInteraction interaction) {
        if (entity instanceof Player) {
            if (interaction != EntityInteraction.ATTACK) return true;
            State state1 = Main.getPlugin().getEarth().getState(p);
            State state2 = Main.getPlugin().getEarth().getState((Player) entity);
            if (state1 == null) return true;
            if (state2 == null) return true;
            if (state1.equals(state2)) return state1.getSettingsManager().getToggle(Toggle.FRIENDLY_FIRE);
            if (!state1.getRelationManager().isAlly(state2)) return true;
            return state1.getSettingsManager().getToggle(Toggle.FRIENDLY_FIRE) && state2.getSettingsManager().getToggle(Toggle.FRIENDLY_FIRE);
        }
        if (entity instanceof Monster) return true;
        if (entity instanceof Tameable tameable) {
            if (tameable.getOwner() != null) {
                if (tameable.getOwner().getUniqueId().equals(p.getUniqueId())) return true;
            }
        }
        if (entity instanceof Mob mob) {
            if (mob.getTarget() != null) {
                if (mob.getTarget().getUniqueId().equals(p.getUniqueId())) return true;
            }
        }
        State in;
        if (interaction == EntityInteraction.EXPLODE
                && Main.getPlugin().getEarth().isBorderRegion(entity.getLocation())) {
            in = Main.getPlugin().getEarth().getBorderRegionStateIfSole(entity.getLocation());
            if (in == null) return false;
        } else in = Main.getPlugin().getEarth().getState(Square.fromLocation(entity.getLocation()));
        if (in == null) return true;
        Human h = Main.getPlugin().getEarth().getHuman(p);
        if (interaction == EntityInteraction.ATTACK) {
            if (!in.getRelationManager().isEnemy(h.getState())) {
                if (entity.getType() == EntityType.MINECART) {
                    if (in.getSettingsManager().getToggle(Toggle.PUBLIC_MINECARTS)) return true;
                }
                if (EntityTags.BOATS_NO_CHEST.isTagged(entity.getType())) {
                    if (in.getSettingsManager().getToggle(Toggle.PUBLIC_BOATS)) return true;
                }
            }
            return in.getAccessLevel(h.getState()).allowsAttack();
        }
        return in.getAccessLevel(h.getState()).allowsEntity(entity.getType());
    }

    private boolean allowsAttack() {
        return level >= BUILD.getLevel();
    }

    private AccessLevel getLower() {
        return lower;
    }

    private void init() {
        this.allowedBlocks = new HashSet<>();
        this.allowedEntities = new HashSet<>();
        this.allowedItems = new HashSet<>();
        switch (this) {
            case NONE:
                allowedBlocks.add(Material.ACACIA_PRESSURE_PLATE);
                allowedBlocks.add(Material.BAMBOO_PRESSURE_PLATE);
                allowedBlocks.add(Material.BIRCH_PRESSURE_PLATE);
                allowedBlocks.add(Material.CHERRY_PRESSURE_PLATE);
                allowedBlocks.add(Material.CRIMSON_PRESSURE_PLATE);
                allowedBlocks.add(Material.DARK_OAK_PRESSURE_PLATE);
                allowedBlocks.add(Material.JUNGLE_PRESSURE_PLATE);
                allowedBlocks.add(Material.MANGROVE_PRESSURE_PLATE);
                allowedBlocks.add(Material.OAK_PRESSURE_PLATE);
                allowedBlocks.add(Material.PALE_OAK_PRESSURE_PLATE);
                allowedBlocks.add(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE);
                allowedBlocks.add(Material.SPRUCE_PRESSURE_PLATE);
                allowedBlocks.add(Material.STONE_PRESSURE_PLATE);
                allowedBlocks.add(Material.WARPED_PRESSURE_PLATE);

                allowedBlocks.add(Material.HEAVY_WEIGHTED_PRESSURE_PLATE);
                allowedBlocks.add(Material.LIGHT_WEIGHTED_PRESSURE_PLATE);

                allowedEntities.add(EntityType.ACACIA_BOAT);
                allowedEntities.add(EntityType.BAMBOO_RAFT);
                allowedEntities.add(EntityType.BIRCH_BOAT);
                allowedEntities.add(EntityType.CHERRY_BOAT);
                allowedEntities.add(EntityType.DARK_OAK_BOAT);
                allowedEntities.add(EntityType.JUNGLE_BOAT);
                allowedEntities.add(EntityType.MANGROVE_BOAT);
                allowedEntities.add(EntityType.OAK_BOAT);
                allowedEntities.add(EntityType.PALE_OAK_BOAT);
                allowedEntities.add(EntityType.PLAYER);
                allowedEntities.add(EntityType.SPRUCE_BOAT);

                allowedEntities.add(EntityType.MINECART);

                allowedEntities.add(EntityType.WANDERING_TRADER);

                allowedItems.add(Material.BLUE_EGG);
                allowedItems.add(Material.BOW);
                allowedItems.add(Material.BROWN_EGG);
                allowedItems.add(Material.CROSSBOW);
                allowedItems.add(Material.EGG);
                allowedItems.add(Material.ENDER_PEARL);
                allowedItems.add(Material.GOAT_HORN);
                allowedItems.add(Material.POTION);
                allowedItems.add(Material.SHIELD);
                allowedItems.add(Material.SNOWBALL);
                allowedItems.add(Material.SPYGLASS);
                allowedItems.add(Material.TRIDENT);
                allowedItems.add(Material.WIND_CHARGE);

                allowedItems.add(Material.CHAINMAIL_BOOTS);
                allowedItems.add(Material.CHAINMAIL_CHESTPLATE);
                allowedItems.add(Material.CHAINMAIL_HELMET);
                allowedItems.add(Material.CHAINMAIL_LEGGINGS);
                allowedItems.add(Material.COPPER_BOOTS);
                allowedItems.add(Material.COPPER_CHESTPLATE);
                allowedItems.add(Material.COPPER_HELMET);
                allowedItems.add(Material.COPPER_LEGGINGS);
                allowedItems.add(Material.DIAMOND_BOOTS);
                allowedItems.add(Material.DIAMOND_CHESTPLATE);
                allowedItems.add(Material.DIAMOND_HELMET);
                allowedItems.add(Material.DIAMOND_LEGGINGS);
                allowedItems.add(Material.ELYTRA);
                allowedItems.add(Material.GOLDEN_BOOTS);
                allowedItems.add(Material.GOLDEN_CHESTPLATE);
                allowedItems.add(Material.GOLDEN_HELMET);
                allowedItems.add(Material.GOLDEN_LEGGINGS);
                allowedItems.add(Material.IRON_BOOTS);
                allowedItems.add(Material.IRON_CHESTPLATE);
                allowedItems.add(Material.IRON_HELMET);
                allowedItems.add(Material.IRON_LEGGINGS);
                allowedItems.add(Material.LEATHER_BOOTS);
                allowedItems.add(Material.LEATHER_CHESTPLATE);
                allowedItems.add(Material.LEATHER_HELMET);
                allowedItems.add(Material.LEATHER_LEGGINGS);
                allowedItems.add(Material.NETHERITE_BOOTS);
                allowedItems.add(Material.NETHERITE_CHESTPLATE);
                allowedItems.add(Material.NETHERITE_HELMET);
                allowedItems.add(Material.NETHERITE_LEGGINGS);
                allowedItems.add(Material.TURTLE_HELMET);

                allowedItems.add(Material.BUNDLE);
                allowedItems.add(Material.BLACK_BUNDLE);
                allowedItems.add(Material.BLUE_BUNDLE);
                allowedItems.add(Material.BROWN_BUNDLE);
                allowedItems.add(Material.CYAN_BUNDLE);
                allowedItems.add(Material.GRAY_BUNDLE);
                allowedItems.add(Material.GREEN_BUNDLE);
                allowedItems.add(Material.LIGHT_BLUE_BUNDLE);
                allowedItems.add(Material.LIGHT_GRAY_BUNDLE);
                allowedItems.add(Material.LIME_BUNDLE);
                allowedItems.add(Material.MAGENTA_BUNDLE);
                allowedItems.add(Material.ORANGE_BUNDLE);
                allowedItems.add(Material.PINK_BUNDLE);
                allowedItems.add(Material.PURPLE_BUNDLE);
                allowedItems.add(Material.RED_BUNDLE);
                allowedItems.add(Material.WHITE_BUNDLE);
                allowedItems.add(Material.YELLOW_BUNDLE);

                for (Material material : Material.values()) {
                    if (!material.isEdible()) continue;
                    allowedItems.add(material);
                }
                return;
            case INTERACT:
                allowedBlocks.add(Material.ACACIA_DOOR);
                allowedBlocks.add(Material.BAMBOO_DOOR);
                allowedBlocks.add(Material.BIRCH_DOOR);
                allowedBlocks.add(Material.CHERRY_DOOR);
                allowedBlocks.add(Material.COPPER_DOOR);
                allowedBlocks.add(Material.EXPOSED_COPPER_DOOR);
                allowedBlocks.add(Material.OXIDIZED_COPPER_DOOR);
                allowedBlocks.add(Material.WEATHERED_COPPER_DOOR);
                allowedBlocks.add(Material.WAXED_COPPER_DOOR);
                allowedBlocks.add(Material.WAXED_EXPOSED_COPPER_DOOR);
                allowedBlocks.add(Material.WAXED_OXIDIZED_COPPER_DOOR);
                allowedBlocks.add(Material.WAXED_WEATHERED_COPPER_DOOR);
                allowedBlocks.add(Material.CRIMSON_DOOR);
                allowedBlocks.add(Material.DARK_OAK_DOOR);
                allowedBlocks.add(Material.JUNGLE_DOOR);
                allowedBlocks.add(Material.MANGROVE_DOOR);
                allowedBlocks.add(Material.OAK_DOOR);
                allowedBlocks.add(Material.PALE_OAK_DOOR);
                allowedBlocks.add(Material.SPRUCE_DOOR);
                allowedBlocks.add(Material.WARPED_DOOR);

                allowedBlocks.add(Material.ACACIA_TRAPDOOR);
                allowedBlocks.add(Material.BAMBOO_TRAPDOOR);
                allowedBlocks.add(Material.BIRCH_TRAPDOOR);
                allowedBlocks.add(Material.CHERRY_TRAPDOOR);
                allowedBlocks.add(Material.COPPER_TRAPDOOR);
                allowedBlocks.add(Material.EXPOSED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.OXIDIZED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.WEATHERED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.WAXED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.WAXED_EXPOSED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.WAXED_OXIDIZED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.WAXED_WEATHERED_COPPER_TRAPDOOR);
                allowedBlocks.add(Material.CRIMSON_TRAPDOOR);
                allowedBlocks.add(Material.DARK_OAK_TRAPDOOR);
                allowedBlocks.add(Material.JUNGLE_TRAPDOOR);
                allowedBlocks.add(Material.MANGROVE_TRAPDOOR);
                allowedBlocks.add(Material.OAK_TRAPDOOR);
                allowedBlocks.add(Material.PALE_OAK_TRAPDOOR);
                allowedBlocks.add(Material.SPRUCE_TRAPDOOR);
                allowedBlocks.add(Material.WARPED_TRAPDOOR);

                allowedBlocks.add(Material.CARTOGRAPHY_TABLE);
                allowedBlocks.add(Material.CRAFTING_TABLE);
                allowedBlocks.add(Material.ENDER_CHEST);
                allowedBlocks.add(Material.GRINDSTONE);
                allowedBlocks.add(Material.LOOM);
                allowedBlocks.add(Material.SMITHING_TABLE);
                allowedBlocks.add(Material.STONECUTTER);
                return;
            case USE:
                allowedBlocks.add(Material.ACACIA_BUTTON);
                allowedBlocks.add(Material.BAMBOO_BUTTON);
                allowedBlocks.add(Material.BIRCH_BUTTON);
                allowedBlocks.add(Material.CHERRY_BUTTON);
                allowedBlocks.add(Material.CRIMSON_BUTTON);
                allowedBlocks.add(Material.DARK_OAK_BUTTON);
                allowedBlocks.add(Material.JUNGLE_BUTTON);
                allowedBlocks.add(Material.MANGROVE_BUTTON);
                allowedBlocks.add(Material.OAK_BUTTON);
                allowedBlocks.add(Material.PALE_OAK_BUTTON);
                allowedBlocks.add(Material.POLISHED_BLACKSTONE_BUTTON);
                allowedBlocks.add(Material.SPRUCE_BUTTON);
                allowedBlocks.add(Material.STONE_BUTTON);
                allowedBlocks.add(Material.WARPED_BUTTON);

                allowedBlocks.add(Material.LEVER);

                allowedBlocks.add(Material.ANVIL);
                allowedBlocks.add(Material.BELL);
                allowedBlocks.add(Material.CHIPPED_ANVIL);
                allowedBlocks.add(Material.DAMAGED_ANVIL);
                allowedBlocks.add(Material.LODESTONE);

                allowedBlocks.add(Material.BLACK_BED);
                allowedBlocks.add(Material.BLUE_BED);
                allowedBlocks.add(Material.BROWN_BED);
                allowedBlocks.add(Material.CYAN_BED);
                allowedBlocks.add(Material.GRAY_BED);
                allowedBlocks.add(Material.GREEN_BED);
                allowedBlocks.add(Material.LIGHT_BLUE_BED);
                allowedBlocks.add(Material.LIGHT_GRAY_BED);
                allowedBlocks.add(Material.LIME_BED);
                allowedBlocks.add(Material.MAGENTA_BED);
                allowedBlocks.add(Material.ORANGE_BED);
                allowedBlocks.add(Material.PINK_BED);
                allowedBlocks.add(Material.PURPLE_BED);
                allowedBlocks.add(Material.RED_BED);
                allowedBlocks.add(Material.WHITE_BED);
                allowedBlocks.add(Material.YELLOW_BED);
                return;
            case LOOT:
                allowedBlocks.add(Material.BARREL);
                allowedBlocks.add(Material.BEACON);
                allowedBlocks.add(Material.BEE_NEST);
                allowedBlocks.add(Material.BEEHIVE);
                allowedBlocks.add(Material.BLAST_FURNACE);
                allowedBlocks.add(Material.CAKE);
                allowedBlocks.add(Material.CAMPFIRE);
                allowedBlocks.add(Material.CHEST);
                allowedBlocks.add(Material.CHISELED_BOOKSHELF);
                allowedBlocks.add(Material.DECORATED_POT);
                allowedBlocks.add(Material.DISPENSER);
                allowedBlocks.add(Material.DROPPER);
                allowedBlocks.add(Material.ENCHANTING_TABLE);
                allowedBlocks.add(Material.FURNACE);
                allowedBlocks.add(Material.HOPPER);
                allowedBlocks.add(Material.JUKEBOX);
                allowedBlocks.add(Material.LECTERN);
                allowedBlocks.add(Material.NOTE_BLOCK);
                allowedBlocks.add(Material.SMOKER);
                allowedBlocks.add(Material.SOUL_CAMPFIRE);
                allowedBlocks.add(Material.SWEET_BERRY_BUSH);
                allowedBlocks.add(Material.TRAPPED_CHEST);

                allowedBlocks.add(Material.ACACIA_FENCE_GATE);
                allowedBlocks.add(Material.BAMBOO_FENCE_GATE);
                allowedBlocks.add(Material.BIRCH_FENCE_GATE);
                allowedBlocks.add(Material.CHERRY_FENCE_GATE);
                allowedBlocks.add(Material.CRIMSON_FENCE_GATE);
                allowedBlocks.add(Material.DARK_OAK_FENCE_GATE);
                allowedBlocks.add(Material.JUNGLE_FENCE_GATE);
                allowedBlocks.add(Material.MANGROVE_FENCE_GATE);
                allowedBlocks.add(Material.OAK_FENCE_GATE);
                allowedBlocks.add(Material.PALE_OAK_FENCE_GATE);
                allowedBlocks.add(Material.SPRUCE_FENCE_GATE);
                allowedBlocks.add(Material.WARPED_FENCE_GATE);

                allowedBlocks.add(Material.SHULKER_BOX);
                allowedBlocks.add(Material.BLACK_SHULKER_BOX);
                allowedBlocks.add(Material.BLUE_SHULKER_BOX);
                allowedBlocks.add(Material.BROWN_SHULKER_BOX);
                allowedBlocks.add(Material.CYAN_SHULKER_BOX);
                allowedBlocks.add(Material.GRAY_SHULKER_BOX);
                allowedBlocks.add(Material.GREEN_SHULKER_BOX);
                allowedBlocks.add(Material.LIGHT_BLUE_SHULKER_BOX);
                allowedBlocks.add(Material.LIGHT_GRAY_SHULKER_BOX);
                allowedBlocks.add(Material.LIME_SHULKER_BOX);
                allowedBlocks.add(Material.MAGENTA_SHULKER_BOX);
                allowedBlocks.add(Material.ORANGE_SHULKER_BOX);
                allowedBlocks.add(Material.PINK_SHULKER_BOX);
                allowedBlocks.add(Material.PURPLE_SHULKER_BOX);
                allowedBlocks.add(Material.RED_SHULKER_BOX);
                allowedBlocks.add(Material.WHITE_SHULKER_BOX);
                allowedBlocks.add(Material.YELLOW_SHULKER_BOX);

                allowedEntities.add(EntityType.ARMOR_STAND);
                allowedEntities.add(EntityType.CHEST_MINECART);
                allowedEntities.add(EntityType.FURNACE_MINECART);
                allowedEntities.add(EntityType.GLOW_ITEM_FRAME);
                allowedEntities.add(EntityType.HOPPER_MINECART);
                allowedEntities.add(EntityType.ITEM_FRAME);

                allowedEntities.add(EntityType.ACACIA_CHEST_BOAT);
                allowedEntities.add(EntityType.BAMBOO_CHEST_RAFT);
                allowedEntities.add(EntityType.BIRCH_CHEST_BOAT);
                allowedEntities.add(EntityType.CHERRY_CHEST_BOAT);
                allowedEntities.add(EntityType.DARK_OAK_CHEST_BOAT);
                allowedEntities.add(EntityType.JUNGLE_CHEST_BOAT);
                allowedEntities.add(EntityType.MANGROVE_CHEST_BOAT);
                allowedEntities.add(EntityType.OAK_CHEST_BOAT);
                allowedEntities.add(EntityType.PALE_OAK_CHEST_BOAT);
                allowedEntities.add(EntityType.SPRUCE_CHEST_BOAT);

                allowedBlocks.add(Material.COPPER_CHEST);
                allowedBlocks.add(Material.EXPOSED_COPPER_CHEST);
                allowedBlocks.add(Material.WEATHERED_COPPER_CHEST);
                allowedBlocks.add(Material.OXIDIZED_COPPER_CHEST);
                allowedBlocks.add(Material.WAXED_COPPER_CHEST);
                allowedBlocks.add(Material.WAXED_EXPOSED_COPPER_CHEST);
                allowedBlocks.add(Material.WAXED_WEATHERED_COPPER_CHEST);
                allowedBlocks.add(Material.WAXED_OXIDIZED_COPPER_CHEST);
                return;
            default:
                return;
        }
    }

    public static AccessLevel fromLevel(int level) {
        for (AccessLevel accessLevel : AccessLevel.values()) {
            if (accessLevel.getLevel() == level) return accessLevel;
        }
        return AccessLevel.NONE;
    }

    public int getLevel() {
        return level;
    }
}
