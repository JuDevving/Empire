package eu.judevving.empire.earth.railway;

import eu.judevving.empire.file.Converter;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.MultiPolyline;
import net.pl3x.map.core.markers.marker.Polyline;
import net.pl3x.map.core.markers.option.Options;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RailwayManager {

    private final List<Railway> railways;
    private List<Point> pointQueue;
    private MultiPolyline multiPolyline;
    private boolean changed;

    public RailwayManager() {
        railways = new LinkedList<>();
        changed = true;
    }

    public void sort() {
        Collections.sort(railways);
    }

    public MultiPolyline getMultiPolyline() {
        if (changed) {
            List<Polyline> polylines = new LinkedList<>();
            for (Railway railway : railways) polylines.addLast(railway.getPolyline());
            multiPolyline = MultiPolyline.of(MapFinals.MULTIPOLYLINE_RAILWAY_KEY, polylines);
            multiPolyline.setOptions(Options.builder().strokeColor(MapFinals.COLOR_RAILWAY.getRGB()).build());
            changed = false;
        }
        return multiPolyline;
    }

    public void startQueue() {
        pointQueue = new LinkedList<>();
    }

    public void queuePoint(Point point) {
        if (point == null) return;
        if (pointQueue == null) return;
        pointQueue.addLast(point);
    }

    private void addRailway(List<Point> points) {
        if (points.getFirst().x() > points.getLast().x() ||
                (points.getFirst().x() == points.getLast().x() && points.getFirst().z() > points.getLast().z())) {
            Collections.reverse(points);
        }
        railways.addLast(new Railway(points));
        changed = true;
    }

    public boolean createRailway() {
        if (pointQueue == null) return false;
        if (pointQueue.size() <= 1) return false;
        if (pointQueue.getFirst().equals(pointQueue.getLast())) return false;
        addRailway(pointQueue);
        pointQueue = null;
        return true;
    }

    public Railway getRailway(int id) {
        if (id < 0) return null;
        if (id >= railways.size()) return null;
        return railways.get(id);
    }

    public boolean removeRailway(int id) {
        if (id < 0) return false;
        if (id >= railways.size()) return false;
        railways.remove(id);
        changed = true;
        return true;
    }

    public String getRailwayString(int id) {
        if (id < 0) return null;
        if (id >= railways.size()) return null;
        List<Point> points = railways.get(id).getPolyline().getPoints();
        Integer[] c = new Integer[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            c[2 * i] = points.get(i).x();
            c[2 * i + 1] = points.get(i).z();
        }
        return Converter.toBracketList((Object[]) c);
    }

    public boolean addRailwayFromString(String string) {
        List<String> strings = Converter.divideBracketList(string);
        if (strings == null) return false;
        if (strings.size() % 2 != 0) return false;
        if (strings.size() <= 2) return false;
        List<Point> points = new LinkedList<>();
        for (int i = 0; i < strings.size() >> 1; i++) {
            try {
                points.addLast(new Point(Integer.parseInt(strings.get(2 * i)), Integer.parseInt(strings.get(2 * i + 1))));
            } catch (Exception e) {
                return false;
            }
        }
        addRailway(points);
        return true;
    }

    public int size() {
        return railways.size();
    }

}
