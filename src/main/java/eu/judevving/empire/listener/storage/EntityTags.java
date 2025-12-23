package eu.judevving.empire.listener.storage;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.HashSet;

public enum EntityTags {

    BOATS_NO_CHEST("_BOAT", "_CHEST_BOAT", EntityType.BAMBOO_RAFT);

    private HashSet<EntityType> entityTypes;

    EntityTags(String suffix, String antiSuffix, EntityType... extra) {
        entityTypes = new HashSet<>();
        for (EntityType entityType : EntityType.values()) {
            if (entityType.name().endsWith(antiSuffix)) continue;
            if (!entityType.name().endsWith(suffix)) continue;
            entityTypes.add(entityType);
        }
        entityTypes.addAll(Arrays.asList(extra));
    }

    public boolean isTagged(EntityType entityType) {
        return entityTypes.contains(entityType);
    }

}
