package eu.judevving.empire.earth.railway;

import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Polyline;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Railway implements Comparable<Railway> {

    private Polyline polyline;
    private double lenght;
    private int x, z;

    public Railway(List<Point> points) {
        this.polyline = Polyline.of(MapFinals.POLYLINE_RAILWAY_PREFIX + Math.random(), points);
        this.lenght = -1;
        this.x = points.getFirst().x();
        this.z = points.getFirst().z();
    }

    public int compareTo(@NotNull Railway r) {
        if (getX() != r.getX()) return Integer.compare(getX(), r.getX());
        return Integer.compare(getZ(), r.getZ());
    }

    public double getLength() {
        if (lenght < 0) {
            List<Point> points = polyline.getPoints();
            lenght = 0;
            for (int i = 1; i < points.size(); i++) {
                Point p1 = points.get(i - 1);
                Point p2 = points.get(i);
                lenght += Math.sqrt((p2.x() - p1.x()) * (p2.x() - p1.x()) + (p2.z() - p1.z()) * (p2.z() - p1.z()));
            }
        }
        return lenght;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
