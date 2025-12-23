package eu.judevving.empire.sidefeature.crate;

import eu.judevving.empire.file.Converter;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.pl3xmap.layer.PoiLayer;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.marker.Icon;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Crate {

    private Location location;
    private Icon icon;

    public void pop() {
        for (ItemStack itemStack : CrateGenerator.randomLoot()) {
            location.getWorld().dropItemNaturally(location, itemStack);
        }
        location.getBlock().setType(Material.AIR);
    }

    public Crate(Location location) {
        this.location = location;
        location.setX(location.getBlockX() + 0.5);
        location.setY(location.getBlockY() + 0.1);
        location.setZ(location.getBlockZ() + 0.5);
        location.getBlock().setType(GlobalFinals.CRATE_MATERIAL);
        initIcon();
    }

    private void initIcon() {
        if (!Main.getPlugin().getPl3xmapConnection().isInitialized()) return;
        icon = PoiLayer.getPOIIcon(MapFinals.ICON_CRATE_KEY, location.getBlockX(), location.getBlockZ());
    }

    public Icon getIcon() {
        if (icon == null) initIcon();
        return icon;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return Converter.toBracketList(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Location locationFromString(World world, String string) {
        try {
            List<String> list = Converter.divideBracketList(string);
            int x = Integer.parseInt(list.get(0));
            int y = Integer.parseInt(list.get(1));
            int z = Integer.parseInt(list.get(2));
            return new Location(world, x, y, z);
        } catch (Exception e) {
            return null;
        }
    }
}
