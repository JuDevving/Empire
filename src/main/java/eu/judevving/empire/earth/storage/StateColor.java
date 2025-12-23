package eu.judevving.empire.earth.storage;

import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.map.MapFinals;

import java.awt.*;
import java.util.LinkedList;

public enum StateColor {

    AQUA("aqua", "§b", eC(0, 255, 255), fC(0, 255, 255), Text.COLOR_AQUA),
    BLACK("black", "§0", eC(0, 0, 0), fC(0, 0, 0), Text.COLOR_BLACK),
    BLUE("blue", "§9", eC(0, 0, 255), fC(0, 0, 255), Text.COLOR_BLUE),
    CYAN("cyan", "§3", eC(0, 128, 128), fC(0, 128, 128), Text.COLOR_CYAN),
    DARK_BLUE("dark_blue", "§1", eC(0, 0, 192), fC(0, 0, 192), Text.COLOR_DARK_BLUE),
    DARK_RED("dark_red","§4",eC(128,0,0),fC(128,0,0),Text.COLOR_DARK_RED),
    GRAY("gray", "§8", eC(64, 64, 64), fC(64, 64, 64), Text.COLOR_GRAY),
    GREEN("green", "§2", eC(0, 128, 0), fC(0, 128, 0), Text.COLOR_GREEN),
    LIGHT_GRAY("light_gray", "§7", eC(160, 160, 160), fC(160, 160, 160), Text.COLOR_LIGHT_GRAY),
    LIME("lime", "§a", eC(0, 255, 0), fC(0, 255, 0), Text.COLOR_LIME),
    ORANGE("orange", "§6", eC(255, 128, 0), fC(255, 128, 0), Text.COLOR_ORANGE),
    PINK("pink", "§d", eC(255, 0, 192), fC(255, 0, 192), Text.COLOR_PINK),
    PURPLE("purple", "§5", eC(128, 0, 255), fC(128, 0, 255), Text.COLOR_PURPLE),
    RED("red", "§c", eC(255, 0, 0), fC(255, 0, 0), Text.COLOR_RED),
    WHITE("white", "§f", eC(255, 255, 255), fC(255, 255, 255), Text.COLOR_WHITE),
    YELLOW("yellow", "§e", eC(255, 255, 0), fC(255, 255, 0), Text.COLOR_YELLOW);

    private String name, mcCode;
    private Text text;
    private int mapEdgeColor, mapFillColor;

    StateColor(String name, String mcCode, int mapEdgeColor, int mapFillColor, Text text) {
        this.name = name;
        this.mcCode = mcCode;
        this.mapEdgeColor = mapEdgeColor;
        this.mapFillColor = mapFillColor;
        this.text = text;
    }

    public static LinkedList<String> getColorNames() {
        LinkedList<String> list = new LinkedList<>();
        for (StateColor stateColor : values()) {
            list.addLast(stateColor.getName());
        }
        return list;
    }

    public static StateColor fromName(String name) {
        for (StateColor stateColor : values()) {
            if (!stateColor.getName().equals(name)) continue;
            return stateColor;
        }
        return null;
    }

    private static int eC(int r, int g, int b) {
        return (new Color(r, g, b, 255)).getRGB();
    }

    private static int fC(int r, int g, int b) {
        return (new Color(r, g, b, MapFinals.MULTIPOLYGON_STATE_FILL_ALPHA)).getRGB();
    }

    public String getColoredText(Language language) {
        return getMcCode() + text.get(language);
    }

    public String getColoredName() {
        return getMcCode() + getName();
    }

    public String getName() {
        return name;
    }

    public int getMapEdgeColor() {
        return mapEdgeColor;
    }

    public int getMapFillColor() {
        return mapFillColor;
    }

    public String getMcCode() {
        return mcCode;
    }

    public Text getText() {
        return text;
    }
}
