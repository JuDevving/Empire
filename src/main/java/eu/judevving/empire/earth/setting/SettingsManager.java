package eu.judevving.empire.earth.setting;

import eu.judevving.empire.earth.relation.Relation;
import eu.judevving.empire.file.FileSaver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class SettingsManager {

    private FileSaver stateFileSaver;

    private HashMap<String, AccessLevel> stateAccessLevels;

    private AccessLevel accessLevelAllies;
    private AccessLevel accessLevelEnemies;
    private AccessLevel accessLevelNeutrals;

    private final HashMap<Toggle, Boolean> toggles;

    public SettingsManager(FileSaver stateFileSaver) {
        this.stateFileSaver = stateFileSaver;
        this.accessLevelAllies = AccessLevel.NONE;
        this.accessLevelEnemies = AccessLevel.NONE;
        this.accessLevelNeutrals = AccessLevel.NONE;
        this.stateAccessLevels = new HashMap<>();
        toggles = new HashMap<>();
        for (Toggle toggle : Toggle.values()) {
            toggles.put(toggle, toggle.getDefaultValue());
        }
    }

    public void setStateAccessLevel(String stateId, AccessLevel accessLevel) {
        if (accessLevel == null) accessLevel = AccessLevel.DEFAULT;
        stateAccessLevels.put(stateId, accessLevel);
    }

    public @NotNull AccessLevel getStateAccessLevel(String stateId) {
        if (!stateAccessLevels.containsKey(stateId)) {
            if (stateFileSaver == null) {
                stateAccessLevels.put(stateId, AccessLevel.DEFAULT);
            } else {
                stateAccessLevels.put(stateId, getStateAccessLevelFromFile(stateId));
            }
        }
        return stateAccessLevels.get(stateId);
    }

    private AccessLevel getStateAccessLevelFromFile(String id) {
        String string = stateFileSaver.getString("customAccessLevel_" + id);
        if (string == null) return AccessLevel.DEFAULT;
        try {
            return AccessLevel.valueOf(string);
        } catch (Exception ignored) {
        }
        return AccessLevel.DEFAULT;
    }

    public void addStateAccessLevelsToFile() {
        for (String id : stateAccessLevels.keySet()) {
            if (stateAccessLevels.get(id) == AccessLevel.DEFAULT) {
                stateFileSaver.remove("customAccessLevel_" + id);
                continue;
            }
            stateFileSaver.put("customAccessLevel_" + id, stateAccessLevels.get(id).name());
        }
    }

    public void setAccessLevel(Relation relation, AccessLevel accessLevel) {
        switch (relation) {
            case ALLY -> setAccessLevelAllies(accessLevel);
            case ENEMY -> setAccessLevelEnemies(accessLevel);
            case NEUTRAL -> setAccessLevelNeutrals(accessLevel);
        }
    }

    public void setAccessLevelAllies(AccessLevel accessLevelAllies) {
        this.accessLevelAllies = accessLevelAllies;
    }

    public void setAccessLevelEnemies(AccessLevel accessLevelEnemies) {
        this.accessLevelEnemies = accessLevelEnemies;
    }

    public void setAccessLevelNeutrals(AccessLevel accessLevelNeutrals) {
        this.accessLevelNeutrals = accessLevelNeutrals;
    }

    public AccessLevel getAccessLevelAllies() {
        return accessLevelAllies;
    }

    public AccessLevel getAccessLevelEnemies() {
        return accessLevelEnemies;
    }

    public AccessLevel getAccessLevelNeutrals() {
        return accessLevelNeutrals;
    }

    public void toggle(Toggle toggle) {
        toggles.put(toggle, !toggles.get(toggle));
    }

    public boolean getToggle(Toggle toggle) {
        return toggles.get(toggle);
    }

    public void setToggle(Toggle toggle, boolean value) {
        toggles.put(toggle, value);
    }

}
