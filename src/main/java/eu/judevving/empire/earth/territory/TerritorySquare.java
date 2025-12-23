package eu.judevving.empire.earth.territory;

import eu.judevving.empire.earth.Square;
import org.jetbrains.annotations.NotNull;

class TerritorySquare implements Comparable<TerritorySquare> {

    private Square square;
    private Territory territory;

    TerritorySquare(Square square, Territory territory) {
        this.square = square;
        this.territory = territory;
    }

    Square getSquare() {
        return square;
    }

    @Override
    public int hashCode() {
        return square.hashCode();
    }

    @Override
    public int compareTo(@NotNull TerritorySquare territorySquare) {
        int c = Double.compare(getDistanceToCenter(), territorySquare.getDistanceToCenter());
        if (c != 0) return c;
        return getSquare().compareTo(territorySquare.getSquare());
    }

    double getDistanceToCenter() {
        return getSquare().getDistance(territory.getCenter());
    }
}
