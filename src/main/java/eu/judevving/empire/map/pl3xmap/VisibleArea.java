package eu.judevving.empire.map.pl3xmap;

import net.pl3x.map.core.markers.area.Area;
import net.pl3x.map.core.markers.area.Border;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class VisibleArea implements Area {

    private int minX, maxX, minZ, maxZ;

    // max included
    public VisibleArea(int minX, int minZ, int maxX, int maxZ) {
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxZ() {
        return maxZ;
    }

    @Override
    public boolean containsBlock(int blockX, int blockZ) {
        return blockX >= getMinX() && blockX <= getMaxX() && blockZ >= getMinZ() && blockZ <= getMaxZ();
    }

    @Override
    public boolean containsChunk(int chunkX, int chunkZ) {
        return chunkX >= (getMinX() >> 4) && chunkX <= (getMaxX() >> 4) && chunkZ >= (getMinZ() >> 4) && chunkZ <= (getMaxZ() >> 4);
    }

    @Override
    public boolean containsRegion(int regionX, int regionZ) {
        return regionX >= (getMinX() >> 9) && regionX <= (getMaxX() >> 9) && regionZ >= (getMinZ() >> 9) && regionZ <= (getMaxZ() >> 9);
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "earth-area");
        return map;
    }

    public static @NotNull Border deserialize(World world, @SuppressWarnings("unused") Map<String, Object> map) {
        return new Border(world);
    }

    @Override
    public @NotNull String toString() {
        return "EarthArea{"
                + "minX=" + getMinX()
                + ",minZ=" + getMinZ()
                + ",maxX=" + getMaxX()
                + ",maxZ=" + getMaxZ()
                + "}";
    }

}
