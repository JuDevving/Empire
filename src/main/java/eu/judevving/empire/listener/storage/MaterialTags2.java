package eu.judevving.empire.listener.storage;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public enum MaterialTags2 {

    BUTTONS("_BUTTON"),
    BOATS_NO_CHEST("_BOAT", "_CHEST_BOAT", Material.BAMBOO_RAFT);

    private HashSet<Material> materials;

    MaterialTags2(String suffix, String antiSuffix, Material... extra) {
        materials = new HashSet<>();
        for (Material material : Material.values()) {
            if (antiSuffix != null) {
                if (material.name().endsWith(antiSuffix)) continue;
            }
            if (!material.name().endsWith(suffix)) continue;
            materials.add(material);
        }
        materials.addAll(Arrays.asList(extra));
    }

    MaterialTags2(String suffix, Material... extra) {
        this(suffix, null, extra);
    }

    public boolean isTagged(Material material) {
        return materials.contains(material);
    }

}
