package eu.judevving.empire.map;

import eu.judevving.empire.earth.storage.StateColor;
import eu.judevving.empire.main.GlobalFinals;

import java.awt.*;

public class MapFinals {

    public static final Color COLOR_RAILWAY = new Color(0, 0, 0, 255);
    public static final int COLOR_RECENT_CLAIM_EDGE = StateColor.BLACK.getMapEdgeColor();
    public static final int COLOR_RECENT_CLAIM_FILL = StateColor.BLACK.getMapFillColor();
    public static final Color COLOR_TRANSPARENT = new Color(0, 0, 0, 0);

    public static final String ICON_EXTENSION = ".png";
    public static final String ICON_KEY_PREFIX = "icon_";
    public static final String ICON_FOLDER = "images/icon";
    public static final String ICON_CAPITAL_FILE = ICON_FOLDER + "/capital" + ICON_EXTENSION;
    public static final String ICON_CAPITAL_KEY = ICON_KEY_PREFIX + "capital";
    public static final String ICON_CAPITAL_KEY_PREFIX = ICON_CAPITAL_KEY + "_";
    public static final String ICON_CRATE_FILE = ICON_FOLDER + "/crate" + ICON_EXTENSION;
    public static final String ICON_CRATE_KEY = ICON_KEY_PREFIX + "crate";
    public static final String ICON_POI_CUSTOM_FILE = ICON_FOLDER + "/poi_custom" + ICON_EXTENSION;
    public static final String ICON_POI_CUSTOM_KEY = ICON_KEY_PREFIX + "poi_custom";
    public static final double ICON_SIZE = 32;

    public static final boolean LAYER_CLAIM_RADIUS_DEFAULT_HIDDEN = true;
    public static final String LAYER_CLAIM_RADIUS_KEY = "empire_layer_claim_radius";
    public static final String LAYER_CLAIM_RADIUS_NAME = "Claim Radii";
    public static final int LAYER_CLAIM_RADIUS_PRIORITY = 30;
    public static final int LAYER_CLAIM_RADIUS_UPDATE_INTERVAL = 5;
    public static final int LAYER_CLAIM_RADIUS_Z_INDEX = 90;

    public static final boolean LAYER_MARKER_DEFAULT_HIDDEN = false;
    public static final String LAYER_MARKER_KEY = "empire_layer_marker";
    public static final String LAYER_MARKER_NAME = "Markers";
    public static final int LAYER_MARKER_PRIORITY = 6;
    public static final int LAYER_MARKER_UPDATE_INTERVAL = 10;
    public static final int LAYER_MARKER_Z_INDEX = 110;

    public static final boolean LAYER_POI_DEFAULT_HIDDEN = false;
    public static final String LAYER_POI_KEY = "empire_layer_poi";
    public static final String LAYER_POI_KEY_PREFIX = "empire_poi_";
    public static final String LAYER_POI_NAME = "POIs";
    public static final int LAYER_POI_PRIORITY = 5;
    public static final int LAYER_POI_UPDATE_INTERVAL = 10;
    public static final int LAYER_POI_Z_INDEX = 120;

    public static final boolean LAYER_RAILWAY_DEFAULT_HIDDEN = true;
    public static final String LAYER_RAILWAY_KEY = "empire_layer_railway";
    public static final String LAYER_RAILWAY_NAME = "Public Railways";
    public static final int LAYER_RAILWAY_PRIORITY = 40;
    public static final int LAYER_RAILWAY_UPDATE_INTERVAL = 10;
    public static final int LAYER_RAILWAY_Z_INDEX = 80;

    public static final boolean LAYER_RECENT_CLAIM_DEFAULT_HIDDEN = true;
    public static final String LAYER_RECENT_CLAIM_KEY = "empire_layer_recent_claim";
    public static final String LAYER_RECENT_CLAIM_NAME = "Recent Claims (" + GlobalFinals.TAKEOVER_HOURS + "h)";
    public static final int LAYER_RECENT_CLAIM_PRIORITY = 20;
    public static final int LAYER_RECENT_CLAIM_UPDATE_INTERVAL = 30;
    public static final int LAYER_RECENT_CLAIM_Z_INDEX = 130;

    public static final boolean LAYER_STATE_DEFAULT_HIDDEN = false;
    public static final String LAYER_STATE_KEY = "empire_layer_state";
    public static final String LAYER_STATE_NAME = "States";
    public static final int LAYER_STATE_PRIORITY = 8;
    public static final int LAYER_STATE_UPDATE_INTERVAL = 5;
    public static final int LAYER_STATE_Z_INDEX = 100;

    public static final String CIRCLE_CLAIM_RADIUS_KEY_PREFIX = "empire_state_claim_radius_";

    public static final String MULTIPOLYGON_RECENT_CLAIMS_KEY = "empire_state_multipolygon_recent_claims";
    public static final int MULTIPOLYGON_STATE_FILL_ALPHA = 48;
    public static final String MULTIPOLYGON_STATE_KEY_PREFIX = "empire_state_multipolygon_";
    public static final String MULTIPOLYGON_STATE_KEY_SUFFIX_BUFFER = "_";

    public static final String MULTIPOLYLINE_RAILWAY_KEY = "empire_railway_multipolyline";

    public static final String POLYLINE_RAILWAY_PREFIX = "empire_railway_";

    public static final int ALLY_LIST_MAX_LENGTH = 5;
    public static final int ENEMY_LIST_MAX_LENGTH = 5;

    public static final String CAPITAL_POPUP_TEXT = """
            <div class="text-container-capital">
                {name}
            </div>
            """;

    public static final String MARKER_POPUP_TEXT = """
            <div class="text-container-marker">
                <h1 class="headline-marker">{name}</h1>
                {description}
             </div>
            """;

    public static final String MULTIPOLYGON_STATE_POPUP_TEXT = """
            <div class="text-container-state">
                <h1 class="headline-state">{name}</h1>
                Description: {description}<br>
                ID: {id}<br>
                Tag: {tag}<br>
                Rank: {rank}<br>
                Power: {power}<br>
                Size: {size} / {maxSize}<br>
                Level: {level}<br>
                Members: {memberList}<br>
                Allies: {allyList}<br>
                Enemies: {enemyList}
             </div>
            """;

    public static final String LAYER_MARKERS_CSS = """
            .text-container-capital {
                font-size: 18px;
            }
            .text-container-marker {
                line-height: 1.0;
            }
            .headline-marker {
                font-size: 18px;
                font-weight: normal;
                margin-top: 0px;
                margin-bottom: 2px;
            }
            """;

    public static final String LAYER_STATES_CSS = """
            .text-container-state {
                line-height: 1.0;
            }
            .headline-state {
                font-weight: bold;
                font-size: 24px;
            }
            """;

    /*public static final String MARKER_POPUP_TEXT = """
            <div class="big-text">
            {name}
            </div>
            """;*/

    /*public static final String LAYER_MARKERS_CSS = """
            .big-text {
            line-height: 1.0;
            font-size: 18px;
            }
            """;*/

}
