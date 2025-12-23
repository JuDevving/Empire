package eu.judevving.empire.earth;

import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.type.Light;
import org.bukkit.entity.Player;

public class MiniatureEarth {

    public static final int SIZE_X = 24, SIZE_Z = 12;
    public static final int MIN_X = 3000, MIN_Z = 15000;
    public static final int MAX_X = MIN_X + SIZE_X, MAX_Z = MIN_Z + SIZE_Z;
    public static final int MIN_Y = 128;
    public static final int HEIGHT = 24;
    public static final int HEIGHT_PLATFORM = 18;

    private static final int[][] texture = new int[][]{
            {0, 0, 0, 0, 0, 1, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 0, 2, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 0, 0, 3, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 3, 3, 3, 1, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 1, 4, 4, 4, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 1, 1, 1, 1, 1, 0, 4, 0, 4, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 4, 4, 0, 0, 0, 3, 4, 0, 0, 0, 0, 0, 3, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0},
            {0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2}};

    private static final int[][] holes = new int[][]{
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 2, 1, 2, 0, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 1, 2, 0, 0, 0, 0, 2, 2, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 2, 1, 2, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 2, 1, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    private World world;
    private Location spawn;

    public void generate(World world) {
        this.world = world;
        Location loc = new Location(world, MIN_X, MIN_Y, MIN_Z);
        Light lightData = (Light) Bukkit.createBlockData(Material.LIGHT);
        lightData.setLevel(15);

        for (int x = 0; x < SIZE_X; x++) {
            loc.setX(MIN_X + x);
            for (int z = 0; z < SIZE_Z; z++) {
                loc.setZ(MIN_Z + z);
                loc.setY(MIN_Y);
                Material material = Material.BLUE_CONCRETE;
                switch (texture[z][x]) {
                    case 1:
                        material = Material.LIME_CONCRETE;
                        break;
                    case 2:
                        material = Material.WHITE_CONCRETE;
                        break;
                    case 3:
                        material = Material.YELLOW_CONCRETE;
                        break;
                    case 4:
                        material = Material.GREEN_CONCRETE;
                        break;
                    default:
                        break;
                }
                loc.getBlock().setType(material);

                loc.setY(MIN_Y + 1);
                loc.getBlock().setType(Material.LIGHT);
                loc.getBlock().setBlockData(lightData);
                loc.setY(MIN_Y + HEIGHT_PLATFORM - 1);
                loc.getBlock().setType(Material.LIGHT);
                loc.getBlock().setBlockData(lightData);

                loc.setY(MIN_Y + HEIGHT_PLATFORM);
                material = Material.BARRIER;
                switch (holes[z][x]) {
                    case 1:
                        material = Material.AIR;
                        break;
                    case 2:
                        material = Material.LIME_STAINED_GLASS;
                        break;
                    default:
                        break;
                }
                loc.getBlock().setType(material);
            }
        }
        for (int x = -1; x < SIZE_X + 1; x++) {
            loc.setX(MIN_X + x);
            for (int y = -1; y < HEIGHT + 1; y++) {
                loc.setY(MIN_Y + y);
                for (int z = -1; z < SIZE_Z + 1; z++) {
                    loc.setZ(MIN_Z + z);
                    if (x == -1 || x == SIZE_X) {
                        loc.getBlock().setType(Material.BARRIER);
                        continue;
                    }
                    if (y == -1 || y == HEIGHT) {
                        loc.getBlock().setType(Material.BARRIER);
                        continue;
                    }
                    if (z == -1 || z == SIZE_Z) {
                        loc.getBlock().setType(Material.BARRIER);
                        continue;
                    }
                }
            }
        }
        this.spawn = new Location(world, MIN_X + (SIZE_X >> 1), MIN_Y + HEIGHT_PLATFORM, MIN_Z + (SIZE_Z >> 1));
        this.spawn.add(0, 1, 0);
        this.spawn.setYaw(180);
        this.spawn.setPitch(0);
        world.setSpawnLocation(spawn);
    }

    public boolean isInside(Location location) {
        if (!world.getUID().equals(location.getWorld().getUID())) return false;
        if (location.getX() < MIN_X || location.getX() > MAX_X) return false;
        if (location.getZ() < MIN_Z || location.getZ() > MAX_Z) return false;
        return true;
    }

    public void teleportAccordingly(Player p) {
        Location to = getTeleportLocation(p.getLocation());
        if (Main.getPlugin().getEarth().isOutOfBounds(to)) {
            toSpawn(p);
            return;
        }
        State state = Main.getPlugin().getEarth().getState(to);
        if (state != null) {
            State pState = Main.getPlugin().getEarth().getState(p);
            if (pState != null) {
                if (!state.equals(pState)) {
                    toSpawn(p);
                    return;
                }
            } else {
                toSpawn(p);
                return;
            }
        }
        Earth.putOnHighestBlock(to);
        p.teleport(to);
        Main.getPlugin().getEarth().getHuman(p).setCollides(true);
    }

    private void toSpawn(Player p) {
        p.teleport(spawn);
        Human h = Main.getPlugin().getEarth().getHuman(p);
        h.sendMessage(Text.RESPAWN_LOCATION_ILLEGAL);
    }

    private Location getTeleportLocation(Location location) {
        double aX = (location.getX() - MIN_X) / SIZE_X;
        double aZ = (location.getZ() - MIN_Z) / SIZE_Z;
        return new Location(Main.getPlugin().getEarth().getWorld(),
                GlobalFinals.EARTH_BLOCK_MIN_X + GlobalFinals.EARTH_SIZE_X * aX,
                0,
                GlobalFinals.EARTH_BLOCK_MIN_Z + GlobalFinals.EARTH_SIZE_Z * aZ,
                location.getYaw(), location.getPitch());
    }

    public Location getSpawn() {
        return spawn;
    }
}
