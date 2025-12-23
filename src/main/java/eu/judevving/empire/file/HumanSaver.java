package eu.judevving.empire.file;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.storage.WelcomeMessage;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.clock.Day;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

public class HumanSaver extends FolderSaver {

    public HashMap<UUID, Human> loadHumans() {
        load();
        HashMap<UUID, Human> humans = new HashMap<>();
        for (FileSaver fileSaver : fileSavers) {
            if (!fileSaver.getFileName().endsWith(GlobalFinals.FILES_DEFAULT_EXTENSION)) continue;
            if (fileSaver.getFileName().length() <= GlobalFinals.FILES_DEFAULT_EXTENSION.length()) continue;
            UUID uuid;
            try {
                uuid = UUID.fromString(fileSaver.getFileName().substring(0, fileSaver.getFileName().length() - GlobalFinals.FILES_DEFAULT_EXTENSION.length()));
            } catch (IllegalArgumentException e) {
                continue;
            }
            Human h = Human.create(uuid);
            setHumanValues(h, fileSaver);
            humans.put(uuid, h);
        }
        return humans;
    }

    public void saveHumans(Collection<Human> humans) {
        fileSavers = new LinkedList<>();
        for (Human h : humans) {
            FileSaver fileSaver = new FileSaver(folder.getPath() + '/' + h.getUniqueId().toString() + GlobalFinals.FILES_DEFAULT_EXTENSION);
            setFileSaverValues(fileSaver, h);
            fileSavers.add(fileSaver);
        }
        save();
    }

    private void setFileSaverValues(FileSaver fileSaver, Human h) {
        fileSaver.put("dailyRewardStreak", h.getDailyRewardStreak());
        fileSaver.put("deaths", h.getDeaths());
        fileSaver.put("donater", h.isDonater());
        fileSaver.put("doubleJump", h.getDoubleJumpEnabled());
        if (h.getLanguage() != null) fileSaver.put("language", h.getLanguage().getName());
        fileSaver.put("lastOnline", h.getLastOnline());
        fileSaver.put("lastTakenDailyReward", h.getLastTakenDailyReward().asInt());
        fileSaver.put("name", h.getName());
        if (h.getNetherPortalLocation() != null) {
            fileSaver.put("netherPortalLocationX", h.getNetherPortalLocation().getBlockX());
            fileSaver.put("netherPortalLocationY", h.getNetherPortalLocation().getBlockY());
            fileSaver.put("netherPortalLocationZ", h.getNetherPortalLocation().getBlockZ());
        }
        if (h.getState() != null) fileSaver.put("state", h.getState().getStateId());
        fileSaver.put("welcomeMessage", h.getWelcomeMessage().name());
    }

    private void setHumanValues(Human h, FileSaver fileSaver) {
        h.setDailyRewardStreak(fileSaver.getIntSafely("dailyRewardStreak", 0));
        h.setDeaths(fileSaver.getIntSafely("deaths", -1));
        h.setDonater(fileSaver.getBooleanSafely("donater", false), false);
        h.setDoubleJumpEnabled(fileSaver.getBooleanSafely("doubleJump", false));
        h.setLanguage(Main.getPlugin().getLanguages().fromString(fileSaver.getString("language")));
        h.setLastOnline(fileSaver.getLongSafely("lastOnline", -1));
        h.setLastTakenDailyReward(Day.fromInt(fileSaver.getIntSafely("lastTakenDailyReward", 0)));
        h.setName(fileSaver.getString("name"));
        try {
            Location portalLocation = new Location(Main.getPlugin().getEarth().getWorld(),
                    fileSaver.getInt("netherPortalLocationX"),
                    fileSaver.getInt("netherPortalLocationY"),
                    fileSaver.getInt("netherPortalLocationZ"));
            h.setNetherPortalLocation(portalLocation);
        } catch (Exception e) {
            h.setNetherPortalLocation(null);
        }
        State state = Main.getPlugin().getEarth().getState(fileSaver.getString("state"));
        if (state != null) state.addMember(h);
        try {
            h.setWelcomeMessage(WelcomeMessage.valueOf(fileSaver.getString("welcomeMessage")));
        } catch (Exception e) {
            h.setWelcomeMessage(GlobalFinals.PLAYER_WELCOME_MESSAGE_DEFAULT);
        }
    }

    public HumanSaver() {
        super(GlobalFinals.FILES_FOLDER_HUMANS);
    }
}
