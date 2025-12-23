package eu.judevving.empire.file;

import eu.judevving.empire.earth.railway.RailwayManager;
import eu.judevving.empire.sidefeature.crate.Crate;
import eu.judevving.empire.sidefeature.custompoi.CustomPOI;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.clock.Day;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EarthSaver {

    private FileSaver fileSaver;

    public EarthSaver() {
        this.fileSaver = new FileSaver(GlobalFinals.FILES_FILE_EARTH);
    }

    public void load() {
        fileSaver.load();
        Main.getPlugin().getEarth().setLastOnlineDay(Day.fromInt(fileSaver.getIntSafely("lastOnlineDay", 0)), false);
    }

    public List<Location> loadCrateLocations(World world) {
        List<Location> locations = new LinkedList<>();
        List<String> strings = fileSaver.getList("crates");
        if (strings == null) return locations;
        for (String string : strings) {
            Location location = Crate.locationFromString(world, string);
            if (location == null) continue;
            locations.add(location);
        }
        return locations;
    }

    public List<CustomPOI> loadCustomPOIs() {
        List<CustomPOI> customPOIs = new LinkedList<>();
        List<String> strings = fileSaver.getList("customPOIs");
        if (strings == null) return customPOIs;
        for (String string : strings) {
            CustomPOI customPOI = CustomPOI.fromString(string);
            if (customPOI == null) continue;
            customPOIs.add(customPOI);
        }
        return customPOIs;
    }

    public void addRailways(RailwayManager railwayManager) {
        if (railwayManager == null) return;
        int i = 0;
        while (railwayManager.addRailwayFromString(fileSaver.getString("railway" + i))) {
            i++;
        }
        railwayManager.sort();
    }

    public void save(Collection<Crate> crates, Collection<CustomPOI> customPOIs, RailwayManager railwayManager) {
        fileSaver.put("lastOnlineDay", Main.getPlugin().getClock().getDay().asInt());
        List<String> crateList = new LinkedList<>();
        for (Crate crate : crates) crateList.add(crate.toString());
        fileSaver.put("crates", crateList);
        List<String> customPOIList = new LinkedList<>();
        for (CustomPOI customPOI : customPOIs) customPOIList.add(customPOI.toString());
        fileSaver.put("customPOIs", customPOIList);
        for (int i = 0; i < railwayManager.size(); i++) {
            fileSaver.put("railway" + i, railwayManager.getRailwayString(i));
        }
        fileSaver.save();
    }

}
