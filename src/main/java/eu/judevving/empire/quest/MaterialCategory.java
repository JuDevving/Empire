package eu.judevving.empire.quest;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.List;

public enum MaterialCategory {

    DIRT(new HashSet<>(List.of(Material.COARSE_DIRT, Material.DIRT, Material.DIRT_PATH, Material.GRASS_BLOCK,
            Material.MYCELIUM, Material.PODZOL, Material.ROOTED_DIRT))),
    //EGGS(new HashSet<>(List.of(Material.BLUE_EGG, Material.BROWN_EGG, Material.EGG))),
    LOGS(new HashSet<>(List.of(Material.ACACIA_LOG, Material.BIRCH_LOG, Material.CHERRY_LOG, Material.CRIMSON_STEM,
            Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.MANGROVE_LOG, Material.OAK_LOG,
            Material.PALE_OAK_LOG, Material.SPRUCE_LOG, Material.WARPED_STEM))),
    SAND(new HashSet<>(List.of(Material.RED_SAND, Material.SAND)));

    private final HashSet<Material> materials;

    MaterialCategory(HashSet<Material> materials) {
        this.materials = materials;
    }

    public boolean is(Object object) {
        if (!(object instanceof Material)) return false;
        return materials.contains(object);
    }

}
