package eu.judevving.empire.file;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;

import java.util.TreeMap;

public class SquareSaver {

    private FileSaver fileSaver;

    public void load() {
        fileSaver.load();
        for (String key : fileSaver.getKeys()) {
            Square square = Square.fromString(key);
            if (square == null) continue;
            Main.getPlugin().getEarth().forceClaim(square, fileSaver.getString(key));
        }
    }

    public void save(TreeMap<Square, String> squares) {
        for (Square square : squares.keySet()) {
            fileSaver.put(square.toString(), squares.get(square));
        }
        fileSaver.save();
    }

    public SquareSaver() {
        fileSaver = new FileSaver(GlobalFinals.FILES_FILE_SQUARES);
    }

}
