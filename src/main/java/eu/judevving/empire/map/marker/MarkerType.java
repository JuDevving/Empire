package eu.judevving.empire.map.marker;

import eu.judevving.empire.map.MapFinals;

import java.util.LinkedList;
import java.util.List;

public enum MarkerType {

    BRIDGE("bridge"),
    CITY("city"),
    FACTORY("factory"),
    FARM("farm"),
    HUT("hut"),
    ISLAND("island"),
    MINE("mine"),
    POINT("point", 2),
    SHIP("ship"),
    SHOP("shop"),
    STATION("station"),
    TANK("tank"),
    TEMPLE("temple"),
    TOWER("tower"),
    TREE("tree");

    private final String name;
    private String key;
    private double size;

    MarkerType(String name) {
        this(name, 1);
    }

    MarkerType(String name, double sizeFactor) {
        this.name = name;
        this.size = MapFinals.ICON_SIZE * sizeFactor;
    }

    public static MarkerType fromName(String string) {
        if (string == null) return null;
        for (MarkerType markerType : MarkerType.values()) {
            if (markerType.getName().equals(string)) return markerType;
        }
        return null;
    }

    public static List<String> getNames() {
        List<String> list = new LinkedList<>();
        for (MarkerType markerType : values()) {
            list.addLast(markerType.getName());
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        if (key == null) key = MapFinals.ICON_KEY_PREFIX + name;
        return key;
    }

    public String getPath() {
        return MapFinals.ICON_FOLDER + "/" + name + MapFinals.ICON_EXTENSION;
    }

    public double getSize() {
        return size;
    }
}
