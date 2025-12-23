package eu.judevving.empire.sidefeature.crate;

import eu.judevving.empire.earth.Earth;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import net.pl3x.map.core.markers.marker.Marker;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CrateManager {

    private HashMap<Square, Crate> crates;

    public void popCrate(Player p, Location location) {
        Square square = Square.fromLocation(location);
        if (!crates.containsKey(square)) return;
        Crate crate = crates.get(square);
        if (location.distance(crate.getLocation()) > 2) return;
        crate.pop();
        crates.remove(square);
        p.playSound(p, GlobalFinals.SOUND_CRATE, GlobalFinals.SOUND_CRATE_VOLUME, GlobalFinals.SOUND_CRATE_PITCH);
    }

    public Location getACrateLocation() {
        if (crates == null) return null;
        for (Crate crate : crates.values()) {
            return crate.getLocation();
        }
        return null;
    }

    public void addCrateIcons(Set<Marker<?>> markers) {
        for (Crate crate : crates.values()) markers.add(crate.getIcon());
    }

    public void spawnCrate(boolean force) {
        Earth earth = Main.getPlugin().getEarth();
        if (!force) {
            if (crates.size() >= GlobalFinals.CRATE_MAX) return;
        }
        Location location = CrateGenerator.randomLocation(earth.getWorld());
        if (earth.getState(location) != null) return;
        Square square = Square.fromLocation(location);
        if (crates.get(square) != null) return;
        Earth.putOnHighestBlock(location);
        spawnCrate(location);
        earth.sendMessage(Text.CRATE_SPAWNED, location.getBlockX() + "", location.getBlockZ() + "");
    }

    private void spawnCrate(Location location) {
        crates.put(Square.fromLocation(location), new Crate(location));
    }

    public void loadCrates(List<Location> crateLocations) {
        for (Location location : crateLocations) {
            spawnCrate(location);
        }
    }

    public CrateManager() {
        this.crates = new HashMap<>();
    }

    public Collection<Crate> getCrates() {
        return crates.values();
    }

}
