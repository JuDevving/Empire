package eu.judevving.empire.earth;

import eu.judevving.empire.earth.territory.Territory;
import eu.judevving.empire.file.ClaimTimeSaver;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.MapFinals;
import net.pl3x.map.core.markers.marker.MultiPolygon;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class ClaimTimeManager {

    // TODO inefficient for large n

    private TreeMap<Square, Long> claimTimes;
    private Territory recent;

    public void updateRecent() {
        Iterator<Square> iterator = recent.getSquares();
        HashSet<Square> toRemove = new HashSet<>();
        while (iterator.hasNext()) {
            Square current = iterator.next();
            if (isRecent(current)) continue;
            toRemove.add(current);
        }
        for (Square square : toRemove) recent.remove(square);
    }

    public long get(Square square) {
        if (square == null) return 0;
        if (!claimTimes.containsKey(square)) return 0;
        return claimTimes.get(square);
    }

    public void set(Square square, long time) {
        claimTimes.put(square, time);
        if (isRecent(time)) recent.add(square);
    }

    public void remove(Square square) {
        if (claimTimes == null) return;
        claimTimes.remove(square);
        recent.remove(square);
    }

    public void load() {
        claimTimes = new TreeMap<>();
        recent = new Territory();
        ClaimTimeSaver claimTimeSaver = new ClaimTimeSaver();
        claimTimeSaver.load();
        for (Square square : claimTimes.keySet()) {
            if (Main.getPlugin().getEarth().getState(square) == null)
                remove(square);

        }
    }

    public void save() {
        ClaimTimeSaver claimTimeSaver = new ClaimTimeSaver();
        claimTimeSaver.save(claimTimes);
    }

    public boolean isRecent(Square square) {
        return isRecent(get(square));
    }

    private boolean isRecent(long claimTime) {
        return claimTime + GlobalFinals.TAKEOVER_MILLIS > Main.getPlugin().getClock().getTimeInMillis();
    }

    public long getSecuredTime(Square square) {
        return get(square) + GlobalFinals.TAKEOVER_MILLIS;
    }

    public MultiPolygon getMultipolygon() {
        return recent.getMultipolygon(MapFinals.MULTIPOLYGON_RECENT_CLAIMS_KEY);
    }

}
