package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.raid.RaidTriggerEvent;

public class RaidTriggerListener implements Listener {

    @EventHandler
    public void onTrigger(RaidTriggerEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        State state = Main.getPlugin().getEarth().getState(e.getRaid().getLocation());
        if (state == null) return;
        if (state.getAccessLevel(Main.getPlugin().getEarth().getState(e.getPlayer())).getLevel() >= AccessLevel.BUILD.getLevel())
            return;
        if (state.equals(Main.getPlugin().getEarth().getState(e.getPlayer()))) return;
        e.setCancelled(true);
    }

}
