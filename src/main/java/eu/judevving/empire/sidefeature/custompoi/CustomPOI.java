package eu.judevving.empire.sidefeature.custompoi;

import eu.judevving.empire.map.pl3xmap.layer.PoiLayer;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.marker.Icon;

public class CustomPOI {

    private int x, z;
    private Icon icon;

    public CustomPOI(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public long distanceSquared(int x, int z) {
        return square(x - this.x) + square(z - this.z);
    }

    public Icon getIcon() {
        if (icon == null) {
            icon = PoiLayer.getPOIIcon(MapFinals.ICON_POI_CUSTOM_KEY, x, z);
        }
        return icon;
    }

    public static CustomPOI fromString(String string) {
        try {
            int o = string.indexOf('{');
            int co = string.indexOf(';');
            int c = string.indexOf('}');
            int x = Integer.parseInt(string.substring(o + 1, co));
            int z = Integer.parseInt(string.substring(co + 1, c));
            return new CustomPOI(x, z);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "{" + x + ";" + z + "}";
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    private int square(int a) {
        return a * a;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CustomPOI)) return false;
        return x == ((CustomPOI) obj).x && z == ((CustomPOI) obj).z;
    }
}
