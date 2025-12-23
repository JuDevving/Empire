package eu.judevving.empire.earth;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Objects;

public record Square(int x, int z) implements Comparable<Square> {

    public boolean containsPlayerOfDifferentState(State otherThan) {
        if (Bukkit.getOnlinePlayers().size() <= 1) return false;
        World world = Main.getPlugin().getEarth().getWorld();
        Collection<Player> players =
                world.getNearbyEntitiesByType(Player.class,
                        new Location(world, getCenterBlockX(), world.getMaxHeight() >> 1, getCenterBlockZ()),
                        GlobalFinals.CLAIM_PLAYER_BLOCK_WIDTH >> 1,
                        world.getMaxHeight() >> 1);
        State playerState;
        for (Player p : players) {
            if (p.getGameMode() == GameMode.CREATIVE) continue;
            if (p.getGameMode() == GameMode.SPECTATOR) continue;
            if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) continue;
            playerState = Main.getPlugin().getEarth().getHuman(p).getState();
            if (playerState == null) return true;
            if (otherThan.equals(playerState)) continue;
            return true;
        }
        return false;
    }

    public Square cloneInvert() {
        return new Square(-x, -z);
    }

    public Square cloneSubtract(Square square) {
        return new Square(x - square.x, z - square.z);
    }

    public Square rotate90Flat() {
        if (x == 1) return new Square(0, -1);
        if (x == -1) return new Square(0, 1);
        if (z == 1) return new Square(1, 0);
        return new Square(-1, 0);
    }

    public boolean isAdjacentNeighbor(Square square) {
        int dX = x - square.x;
        int dZ = z - square.z;
        return dX * dZ == 0 && Math.abs(dX + dZ) == 1;
    }

    public boolean isOutOfBounds() {
        if (x < GlobalFinals.EARTH_CHUNK_MIN_X) return true;
        if (x > GlobalFinals.EARTH_CHUNK_MAX_X) return true;
        if (z < GlobalFinals.EARTH_CHUNK_MIN_Z) return true;
        return z > GlobalFinals.EARTH_CHUNK_MAX_Z;
    }

    public Square[] getNeighbors() {
        Square[] re = new Square[8];
        int i = 0;
        for (int xx = -1; xx <= 1; xx++) {
            for (int zz = -1; zz <= 1; zz++) {
                if (xx == 0 && zz == 0) continue;
                re[i] = cloneAdd(xx, zz);
                i++;
            }
        }
        return re;
    }

    public Square cloneAdd(Square square) {
        return new Square(x() + square.x(), z() + square.z());
    }

    public Square cloneAdd(int x, int z) {
        return new Square(x() + x, z() + z);
    }

    public static Square fromLocation(@NotNull Location location) {
        return fromLocation(location.getBlockX(), location.getBlockZ());
    }

    public static Square fromLocation(int x, int z) {
        int xx = x / GlobalFinals.EARTH_SQUARE_SIZE;
        if (x < 0) xx--;
        int zz = z / GlobalFinals.EARTH_SQUARE_SIZE;
        if (z < 0) zz--;
        return new Square(xx, zz);
    }

    public double getBlockDistance(Square square) {
        return getDistance(square) * GlobalFinals.EARTH_SQUARE_SIZE;
    }

    public double getDistance(Square square) {
        double dX = square.x() - x();
        double dZ = square.z() - z();
        return Math.sqrt(dX * dX + dZ * dZ);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Square square) {
            return square.x() == x && square.z() == z;
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new Square(x, z);
    }

    @Override
    public int compareTo(@NotNull Square square) {
        if (x < square.x()) return -1;
        if (x > square.x()) return 1;
        return Integer.compare(z, square.z());
    }

    public static Square fromString(String string) {
        if (string == null) return null;
        int bo = string.indexOf('[');
        int c = string.indexOf(',');
        int bc = string.indexOf(']');
        if (bo >= c) return null;
        if (c >= bc) return null;
        try {
            return new Square(Integer.parseInt(string.substring(bo + 1, c)),
                    Integer.parseInt(string.substring(c + 1, bc)));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getCenterBlockString() {
        return getCenterBlockX() + " " + getCenterBlockZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    @Override
    public String toString() {
        return "[" + x + ',' + z + "]";
    }

    public int getCenterBlockX() {
        return getBlockX() + (GlobalFinals.EARTH_SQUARE_SIZE >> 1);
    }

    public int getCenterBlockZ() {
        return getBlockZ() + (GlobalFinals.EARTH_SQUARE_SIZE >> 1);
    }

    public int getBlockX() {
        return x * GlobalFinals.EARTH_SQUARE_SIZE;
    }

    public int getBlockZ() {
        return z * GlobalFinals.EARTH_SQUARE_SIZE;
    }
}
