package eu.judevving.empire.map.marker;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.file.Converter;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;

import java.util.List;

public class Marker {

    private final int id;
    private MarkerType markerType;
    private String name;
    private String description;
    private Square square;
    private Icon icon;
    private boolean changed;

    public Marker(int id, Square square) {
        this(id, square, GlobalFinals.MARKER_DEFAULT_ICON, GlobalFinals.MARKER_DEFAULT_NAME);
    }

    public Marker(int id, Square square, MarkerType markerType, String name) {
        this(id, square, GlobalFinals.MARKER_DEFAULT_ICON, GlobalFinals.MARKER_DEFAULT_NAME, GlobalFinals.MARKER_DEFAULT_DESCRIPTION);
    }

    public Marker(int id, Square square, MarkerType markerType, String name, String description) {
        this.id = id;
        this.square = square;
        this.markerType = markerType;
        this.name = name;
        this.changed = true;
        this.description = description;
    }

    public Icon getIcon() {
        if (square == null) return null;
        if (changed) {
            changed = false;
            icon = net.pl3x.map.core.markers.marker.Marker.icon(
                    Math.random() + "", new Point(square.getCenterBlockX(), square.getCenterBlockZ()), markerType.getKey(), markerType.getSize());
            icon.setOptions(Options.builder()
                    .tooltipContent(MapFinals.MARKER_POPUP_TEXT
                            .replace("{name}", name)
                            .replace("{description}", description))
                    .tooltipSticky(true)
                    .tooltipDirection(Tooltip.Direction.TOP).popupContent(null).build());
        }
        return icon;
    }

    public void setName(String name) {
        this.name = name;
        changed = true;
    }

    public void setDescription(String description) {
        this.description = description;
        changed = true;
    }

    public void setMarkerIcon(MarkerType markerType) {
        this.markerType = markerType;
        changed = true;
    }

    public void setSquare(Square square) {
        this.square = square;
        changed = true;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MarkerType getMarkerIcon() {
        return markerType;
    }

    public Square getSquare() {
        return square;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        if (square == null) return "null";
        return Converter.toBracketList(id, square.x(), square.z(), markerType.name(), getName(), getDescription());
    }

    public static Marker fromString(String string) {
        try {
            List<String> list = Converter.divideBracketList(string);
            int id = Integer.parseInt(list.get(0));
            int x = Integer.parseInt(list.get(1));
            int z = Integer.parseInt(list.get(2));
            MarkerType markerType = MarkerType.valueOf(list.get(3));
            String name = list.get(4);
            String description = GlobalFinals.MARKER_DEFAULT_DESCRIPTION;
            if (list.size() >= 6) description = list.get(5);
            return new Marker(id, new Square(x, z), markerType, name, description);
        } catch (Exception e) {
            return null;
        }
    }

    public String getLocationString() {
        return square.getCenterBlockX() + " " + square.getCenterBlockZ();
    }

}
