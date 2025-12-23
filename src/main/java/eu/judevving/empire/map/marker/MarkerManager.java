package eu.judevving.empire.map.marker;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.storage.StateLevel;
import eu.judevving.empire.main.GlobalFinals;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MarkerManager {

    private final State state;
    private final Marker[] markers;

    public void removeInvalidMarkers() {
        for (Marker marker : markers) {
            if (marker == null) continue;
            if (marker.getSquare() == null) continue;
            if (!marker.getSquare().equals(state.getCapital())
                    && state.isTerritory(marker.getSquare())
                    && marker.getId() <= state.getStateLevel().getMarkers()) continue;
            marker.setSquare(null);
        }
    }

    public List<String> getMarkerArgs() {
        List<String> list = new LinkedList<>();
        for (int i = 1; i <= state.getStateLevel().getMarkers(); i++) {
            list.addLast(i + "");
        }
        return list;
    }

    public void addMarkers(Set<net.pl3x.map.core.markers.marker.Marker<?>> markers) {
        for (Marker marker : this.markers) {
            if (marker == null) continue;
            if (marker.getSquare() == null) continue;
            markers.add(marker.getIcon());
        }
    }

    public void putMarker(Marker marker) {
        if (marker == null) return;
        markers[marker.getId() - 1] = marker;
    }

    public void removeMarker(int id) {
        if (markers[id - 1] == null) return;
        markers[id - 1].setSquare(null);
    }

    public Marker getMarker(int id) {
        if (id < 1 || id > markers.length) return null;
        return markers[id - 1];
    }

    public Marker getMarker(Square square) {
        for (Marker marker : markers) {
            if (marker == null) continue;
            if (marker.getSquare() == null) continue;
            if (marker.getSquare().equals(square)) return marker;
        }
        return null;
    }

    public MarkerManager(State state) {
        this.state = state;
        markers = new Marker[StateLevel.MAX_LEGITIMATE.getMarkers()];
    }

    public void setDefaultNames() {
        for (Marker marker : markers) {
            marker.setName(GlobalFinals.MARKER_DEFAULT_NAME);
            marker.setDescription(GlobalFinals.MARKER_DEFAULT_DESCRIPTION);
        }
    }

    public int size() {
        if (markers == null) return 0;
        return markers.length;
    }

}
