package eu.judevving.empire.earth;

import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidGenerator extends ChunkGenerator {

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int cx, int cz, @NotNull ChunkData chunkData) {
        if (isEarth(cx, cz)) {
            for (int y = chunkData.getMinHeight(); y <= GlobalFinals.EARTH_VOID_BEDROCK_MAX_Y && y < chunkData.getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (y == chunkData.getMinHeight()) {
                            chunkData.setBlock(x, y, z, Material.BEDROCK);
                        } else chunkData.setBlock(x, y, z, Material.STONE);
                    }
                }
            }
            return;
        }
        if (!isDeepVoid(cx, cz)) {
            for (int y = chunkData.getMinHeight(); y <= GlobalFinals.EARTH_VOID_BEDROCK_MAX_Y && y < chunkData.getMaxHeight(); y++) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        if (y < GlobalFinals.EARTH_VOID_BEDROCK_MAX_Y) {
                            chunkData.setBlock(x, y, z, Material.BEDROCK);
                        } else chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
            return;
        }
        // else complete void
    }

    private boolean isEarth(int cx, int cz) {
        if (cx < GlobalFinals.EARTH_CHUNK_MIN_X) return false;
        if (cx > GlobalFinals.EARTH_CHUNK_MAX_X) return false;
        if (cz < GlobalFinals.EARTH_CHUNK_MIN_Z) return false;
        return cz <= GlobalFinals.EARTH_CHUNK_MAX_Z;
    }

    private boolean isDeepVoid(int cx, int cz) {
        if (cx < GlobalFinals.EARTH_CHUNK_MIN_X - (GlobalFinals.EARTH_TELEPORT_WIDTH >> 4)) return true;
        if (cx > GlobalFinals.EARTH_CHUNK_MAX_X + (GlobalFinals.EARTH_TELEPORT_WIDTH >> 4)) return true;
        if (cz < GlobalFinals.EARTH_CHUNK_MIN_Z - (GlobalFinals.EARTH_TELEPORT_WIDTH >> 4)) return true;
        return cz > GlobalFinals.EARTH_CHUNK_MAX_Z + (GlobalFinals.EARTH_TELEPORT_WIDTH >> 4);
    }

}
