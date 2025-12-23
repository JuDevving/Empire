package eu.judevving.empire.sidefeature.custompoi;

import net.pl3x.map.core.markers.marker.Marker;

import java.util.List;
import java.util.Set;

public class CustomPOIManager {

    private List<CustomPOI> customPOIs;

    public boolean removeClosestCustomPOI(int x, int z) {
        CustomPOI closest = null;
        long d = Long.MAX_VALUE;
        for (CustomPOI customPOI : customPOIs) {
            if (customPOI.distanceSquared(x, z) >= d) continue;
            closest = customPOI;
            d = customPOI.distanceSquared(x, z);
        }
        if (closest == null) return false;
        customPOIs.remove(closest);
        return true;
    }

    public void addCustomPOI(int x, int z) {
        customPOIs.add(new CustomPOI(x, z));
    }

    public void addCustomPOIs(Set<Marker<?>> markers) {
        for (CustomPOI customPOI : this.customPOIs) {
            markers.add(customPOI.getIcon());
        }
    }

    public void setCustomPOIs(List<CustomPOI> customPOIs) {
        this.customPOIs = customPOIs;
    }

    public List<CustomPOI> getCustomPOIs() {
        return customPOIs;
    }
}
