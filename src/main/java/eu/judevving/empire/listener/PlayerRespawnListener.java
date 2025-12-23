package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        if (!Main.getPlugin().getEarth().isWorld(e.getRespawnLocation().getWorld())) return; // respawn anchor
        if (!Main.getPlugin().getEarth().isOutOfBounds(e.getRespawnLocation())) {
            State state = Main.getPlugin().getEarth().getState(e.getRespawnLocation());
            if (state == null || state.equals(h.getState())) {
                return; // no man's land or own state
            }
        }
        if (e.getRespawnReason() == PlayerRespawnEvent.RespawnReason.END_PORTAL) {
            if (h.getState() != null) {
                if (h.getState().getCapital() != null) {
                    e.setRespawnLocation(h.getState().getCapitalRespawnLocation());
                    return;
                }
            }
        }
        e.setRespawnLocation(Main.getPlugin().getEarth().getMiniatureEarth().getSpawn());
        h.setCollides(false);
    }

}
