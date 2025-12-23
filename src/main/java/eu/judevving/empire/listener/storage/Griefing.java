package eu.judevving.empire.listener.storage;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.HashSet;

public class Griefing {

    public static final int WARDEN_MAX_Y = 0;

    private static HashSet<Material> dispenserBlacklist;
    private static HashSet<EntityType> explosionBlacklist;
    private static HashSet<Material> farmBlocks;

    public static void init() {
        dispenserBlacklist = new HashSet<>();
        dispenserBlacklist.add(Material.BONE_MEAL);
        dispenserBlacklist.add(Material.FLINT_AND_STEEL);
        dispenserBlacklist.add(Material.HONEYCOMB);
        dispenserBlacklist.add(Material.POTION);

        dispenserBlacklist.add(Material.AXOLOTL_BUCKET);
        dispenserBlacklist.add(Material.BUCKET);
        dispenserBlacklist.add(Material.COD_BUCKET);
        dispenserBlacklist.add(Material.LAVA_BUCKET);
        dispenserBlacklist.add(Material.PUFFERFISH_BUCKET);
        dispenserBlacklist.add(Material.POWDER_SNOW_BUCKET);
        dispenserBlacklist.add(Material.SALMON_BUCKET);
        dispenserBlacklist.add(Material.TADPOLE_BUCKET);
        dispenserBlacklist.add(Material.WATER_BUCKET);

        explosionBlacklist = new HashSet<>();
        explosionBlacklist.add(EntityType.END_CRYSTAL);
        explosionBlacklist.add(EntityType.FIREBALL);
        explosionBlacklist.add(EntityType.WITHER);
        explosionBlacklist.add(EntityType.WITHER_SKULL);

        farmBlocks = new HashSet<>();
        farmBlocks.add(Material.CARROTS);
        farmBlocks.add(Material.BEETROOTS);
        farmBlocks.add(Material.FARMLAND);
        farmBlocks.add(Material.MELON_STEM);
        farmBlocks.add(Material.PITCHER_CROP);
        farmBlocks.add(Material.POTATOES);
        farmBlocks.add(Material.PUMPKIN_STEM);
        farmBlocks.add(Material.TORCHFLOWER_CROP);
        farmBlocks.add(Material.WHEAT);

        farmBlocks.add(Material.ACACIA_LEAVES);
        farmBlocks.add(Material.AZALEA_LEAVES);
        farmBlocks.add(Material.BIRCH_LEAVES);
        farmBlocks.add(Material.CHERRY_LEAVES);
        farmBlocks.add(Material.DARK_OAK_LEAVES);
        farmBlocks.add(Material.FLOWERING_AZALEA_LEAVES);
        farmBlocks.add(Material.JUNGLE_LEAVES);
        farmBlocks.add(Material.MANGROVE_LEAVES);
        farmBlocks.add(Material.OAK_LEAVES);
        farmBlocks.add(Material.PALE_OAK_LEAVES);
        farmBlocks.add(Material.SPRUCE_LEAVES);

        farmBlocks.add(Material.LILY_PAD);
    }

    public static boolean isOnDispenserBlacklist(Material material) {
        return dispenserBlacklist.contains(material);
    }

    public static boolean isOnExplosionBlacklist(EntityType entityType) {
        return explosionBlacklist.contains(entityType);
    }

    public static boolean isFarmBlock(Material material) {
        return farmBlocks.contains(material);
    }

}
