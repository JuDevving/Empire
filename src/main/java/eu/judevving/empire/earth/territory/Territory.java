package eu.judevving.empire.earth.territory;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.main.Main;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.MultiPolygon;
import net.pl3x.map.core.markers.marker.Polygon;
import net.pl3x.map.core.markers.marker.Polyline;

import java.util.*;

public class Territory {

    private static final Square down = new Square(0, 1);
    private static final Square right = new Square(1, 0);

    private TreeSet<Square> squares;
    private TreeSet<TerritorySquare> centeredSquares;
    private MultiPolygon multiPolygon;
    private LinkedList<LinkedList<Square>> polygons;
    private Square center;
    private boolean changed;

    public int shrink(int to) {
        if (to < 1) return 0;
        if (to >= getSize()) return 0;
        if (center == null) return 0;
        int remove = getSize() - to;
        for (int i = 0; i < remove; i++) {
            Main.getPlugin().getEarth().forceUnclaim(centeredSquares.getLast().getSquare());
        }
        return remove;
    }

    public void add(Square square) {
        changed = true;
        squares.add(square);
        addToCenteredSquares(square);
    }

    public void remove(Square square) {
        changed = true;
        squares.remove(square);
        removeFromCenteredSquares(square);
    }

    private void addToCenteredSquares(Square square) {
        if (center == null) return;
        centeredSquares.add(new TerritorySquare(square, this));
    }

    private void removeFromCenteredSquares(Square square) {
        if (center == null) return;
        centeredSquares.remove(new TerritorySquare(square, this));
    }

    public MultiPolygon getMultipolygon(String key) {
        if (changed) {
            try {
                polygons = getPolygons();
            } catch (ConcurrentModificationException e) {
                Main.getPlugin().getLogger().info(e.getClass().getSimpleName());
                return multiPolygon;
            }
        }
        if (changed) {
            changed = false;
            multiPolygon = new MultiPolygon(key);
            for (LinkedList<Square> list : polygons) {
                Polyline polyline = new Polyline(key);
                Square cur;
                for (int i = 0; i < list.size(); i++) {
                    cur = list.get(i);
                    polyline.addPoint(new Point(cur.getBlockX(), cur.getBlockZ()));
                }
                multiPolygon.addPolygon(new Polygon(key, polyline));
            }
        }
        return multiPolygon;
    }

    private LinkedList<LinkedList<Square>> getPolygons() {
        LinkedList<LinkedList<Square>> polygons = new LinkedList<>();
        HashSet<Square> leftEdges = new HashSet<>();
        for (Square square : squares) {
            if (isTerritory(square.cloneAdd(-1, 0))) continue;
            leftEdges.add(square);
        }
        while (!leftEdges.isEmpty()) {
            Square start = null;
            for (Square square : leftEdges) { // Take and left edge as start
                start = square;
                break;
            }
            boolean startHasTouchingCorners = hasTouchingCorners(start);
            LinkedList<Square> polygon = new LinkedList<>();
            polygon.addLast(start);
            leftEdges.remove(start);
            Square previous = start;
            Square current = start.cloneAdd(down); // always go down as first move
            polygon.addLast(current);
            leftEdges.remove(current);
            Square direction = current.cloneSubtract(previous), previousDirection; // first direction is always down
            while (!current.equals(polygon.getFirst()) || (startHasTouchingCorners && !direction.equals(right))) { // Find your way one lap around
                previousDirection = direction;
                if (hasTouchingCorners(current)) {
                    direction = getDirectionForTouchingCorners(current, previousDirection);
                } else {
                    direction = previousDirection.cloneInvert().rotate90Flat();
                    while (!isEdge(current, current.cloneAdd(direction))) {
                        direction = direction.rotate90Flat();
                    }
                }
                if (direction.equals(down)) leftEdges.remove(current);
                current = current.cloneAdd(direction);
                if (direction.equals(previousDirection)) polygon.removeLast();
                polygon.addLast(current);
            }
            polygons.add(polygon);
        }
        return polygons;
    }

    private Square getDirectionForTouchingCorners(Square current, Square previousDirection) {
        int x = previousDirection.z(); // hardcode
        int z = previousDirection.x();
        if (!isTerritory(current)) {
            x = -x;
            z = -z;
        }
        return new Square(x, z);
    }

    private boolean hasTouchingCorners(Square square) {
        if (isTerritory(square) && isTerritory(square.cloneAdd(-1, -1))) {
            return !isTerritory(square.cloneAdd(-1, 0)) && !isTerritory(square.cloneAdd(0, -1));
        }
        if (isTerritory(square.cloneAdd(-1, 0)) && isTerritory(square.cloneAdd(0, -1))) {
            return !isTerritory(square) && !isTerritory(square.cloneAdd(-1, -1));
        }
        return false;
    }

    private boolean isEdge(Square a, Square b) {
        if (!a.isAdjacentNeighbor(b)) return false;
        if (a.compareTo(b) > 0) return isEdge(b, a);
        if (b.x() > a.x())
            return isTerritory(a) ^ isTerritory(a.cloneAdd(0, -1)); // Horizontal neighbors
        return isTerritory(a) ^ isTerritory(a.cloneAdd(-1, 0)); // Vertical neighbors
    }

    public int countEdges(Square square) {
        int edges = 0;
        if (isTerritory(square.cloneAdd(-1,0))) edges++;
        if (isTerritory(square.cloneAdd(1,0))) edges++;
        if (isTerritory(square.cloneAdd(0,-1))) edges++;
        if (isTerritory(square.cloneAdd(0,1))) edges++;
        return edges;
    }

    public int getSize() {
        return squares.size();
    }

    public boolean isTerritory(Square square) {
        return squares.contains(square);
    }

    public Territory() {
        changed = true;
        squares = new TreeSet<>();
    }

    public void setCenter(Square center) {
        this.center = center;
        centeredSquares = new TreeSet<>();
        squares.forEach(this::addToCenteredSquares);
    }

    public Iterator<Square> getSquares() {
        return squares.iterator();
    }

    public Square getCenter() {
        return center;
    }
}
