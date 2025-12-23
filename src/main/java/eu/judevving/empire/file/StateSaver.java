package eu.judevving.empire.file;

import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.map.marker.Marker;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateColor;
import eu.judevving.empire.earth.storage.StateLevel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class StateSaver extends FolderSaver {

    public HashMap<String, State> loadStates() {
        load();
        HashMap<String, State> states = new HashMap<>();
        for (FileSaver fileSaver : fileSavers) {
            if (!fileSaver.getFileName().endsWith(GlobalFinals.FILES_DEFAULT_EXTENSION)) continue;
            if (fileSaver.getFileName().length() <= GlobalFinals.FILES_DEFAULT_EXTENSION.length()) continue;
            State state = State.create(fileSaver.getFileName().substring(0, fileSaver.getFileName().length() - GlobalFinals.FILES_DEFAULT_EXTENSION.length()), fileSaver);
            setStateValues(state, fileSaver);
            states.put(state.getStateId(), state);
        }
        return states;
    }

    public void saveStates(Collection<State> states) {
        fileSavers = new LinkedList<>();
        for (State state : states) {
            FileSaver fileSaver = state.getFileSaver();
            setFileSaverValues(fileSaver, state);
            fileSavers.add(fileSaver);
        }
        save();
    }

    public static void deleteStateFile(String stateId) {
        FileSaver fileSaver = new FileSaver(GlobalFinals.FILES_FOLDER_STATES + '/' + stateId + GlobalFinals.FILES_DEFAULT_EXTENSION);
        fileSaver.delete();
    }

    private void setFileSaverValues(FileSaver fileSaver, State state) {
        fileSaver.put("accessLevelAllies", state.getSettingsManager().getAccessLevelAllies().name());
        fileSaver.put("accessLevelEnemies", state.getSettingsManager().getAccessLevelEnemies().name());
        fileSaver.put("accessLevelNeutrals", state.getSettingsManager().getAccessLevelNeutrals().name());
        fileSaver.put("allies", state.getRelationManager().getAllies());
        if (state.getCapital() != null) fileSaver.put("capital", state.getCapital().toString());
        fileSaver.put("capitalName", state.getCapitalName());
        fileSaver.put("completedQuests", state.getQuestManager().getCompletedQuests());
        fileSaver.put("description", state.getDescription());
        fileSaver.put("enemies", state.getRelationManager().getEnemies());
        fileSaver.put("enemyWelcome", state.getEnemyWelcome());
        fileSaver.put("level", state.getStateLevel().name());
        fileSaver.put("levelProgress", state.getLevelProgress());
        for (int i = 1; i <= state.getMarkerManager().size(); i++) {
            Marker marker = state.getMarkerManager().getMarker(i);
            if (marker == null) continue;
            fileSaver.put("marker" + i, marker.toString());
        }
        fileSaver.put("name", state.getName());
        fileSaver.put("power", state.getPower());
        fileSaver.put("quests", state.getQuestManager().toStringList());
        fileSaver.put("stateColor", state.getStateColor().name());
        fileSaver.put("tag", state.getRawTag());
        for (Toggle toggle : Toggle.values())
            fileSaver.put(toggle.getKey(), state.getSettingsManager().getToggle(toggle));
        fileSaver.put("welcome", state.getWelcome());

        state.getSettingsManager().addStateAccessLevelsToFile();
    }

    private void setStateValues(State state, FileSaver fileSaver) {
        state.getSettingsManager().setAccessLevelAllies(getAccessLevel(fileSaver, "accessLevelAllies"));
        state.getSettingsManager().setAccessLevelEnemies(getAccessLevel(fileSaver, "accessLevelEnemies"));
        state.getSettingsManager().setAccessLevelNeutrals(getAccessLevel(fileSaver, "accessLevelNeutrals"));
        for (String id : fileSaver.getList("allies")) state.getRelationManager().addAlly(id);
        state.setCapital(Square.fromString(fileSaver.getString("capital")));
        state.setCapitalName(fileSaver.getString("capitalName"));
        state.getQuestManager().setCompletedQuests(fileSaver.getIntSafely("completedQuests", 0));
        state.setDescription(fileSaver.getString("description"));
        for (String id : fileSaver.getList("enemies")) state.getRelationManager().addEnemy(id);
        state.setEnemyWelcome(fileSaver.getString("enemyWelcome"));
        try {
            state.setStateLevel(StateLevel.valueOf(fileSaver.getString("level")));
        } catch (Exception e) {
            state.setStateLevel(StateLevel.LEVEL_1);
        }
        state.setLevelProgress(fileSaver.getIntSafely("levelProgress", 0));
        int i = 1;
        while (true) {
            Marker marker = Marker.fromString(fileSaver.getString("marker" + i));
            if (marker == null) break;
            state.getMarkerManager().putMarker(marker);
            i++;
        }
        state.setName(fileSaver.getString("name"));
        state.setPower(fileSaver.getInt("power"));
        state.getQuestManager().fillFromStringList(fileSaver.getList("quests"));
        try {
            state.setStateColor(StateColor.valueOf(fileSaver.getString("stateColor")));
        } catch (Exception e) {
            state.setStateColor(GlobalFinals.STATE_DEFAULT_COLOR);
        }
        state.setTag(fileSaver.getString("tag"));
        for (Toggle toggle : Toggle.values()) {
            state.getSettingsManager().setToggle(toggle, fileSaver.getBooleanSafely(toggle.getKey(), toggle.getDefaultValue()));
        }
        state.setWelcome(fileSaver.getString("welcome"));
    }

    private AccessLevel getAccessLevel(FileSaver fileSaver, String key) {
        String value = fileSaver.getString(key);
        if (value == null) return AccessLevel.NONE;
        try {
            return AccessLevel.valueOf(value);
        } catch (IllegalArgumentException e) {
            return AccessLevel.NONE;
        }
    }

    public StateSaver() {
        super(GlobalFinals.FILES_FOLDER_STATES);
    }
}
