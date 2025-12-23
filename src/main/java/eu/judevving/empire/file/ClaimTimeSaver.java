package eu.judevving.empire.file;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;

import java.util.TreeMap;

public class ClaimTimeSaver {

    private FileSaver fileSaver;

    public void load() {
        fileSaver.load();
        for (String key : fileSaver.getKeys()) {
            Square square = Square.fromString(key);
            if (square == null) continue;
            long time = fileSaver.getLongSafely(key, -1);
            if (time <= 0) continue;
            Main.getPlugin().getEarth().getClaimTimeManager().set(square, time);
        }
    }

    public void save(TreeMap<Square, Long> claimTimes) {
        for (Square square : claimTimes.keySet()) {
            if (claimTimes.get(square) <= 0) continue;
            fileSaver.put(square.toString(), claimTimes.get(square));
        }
        fileSaver.save();
    }

    public ClaimTimeSaver() {
        fileSaver = new FileSaver(GlobalFinals.FILES_FILE_CLAIM_TIMES);
    }

}
