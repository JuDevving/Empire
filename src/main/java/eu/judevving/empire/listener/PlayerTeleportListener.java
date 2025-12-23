package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getCause() != PlayerTeleportEvent.TeleportCause.CONSUMABLE_EFFECT &&
                e.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!e.getFrom().getWorld().getUID().equals(e.getTo().getWorld().getUID())) {
            e.setTo(e.getFrom());
            return;
        }
        if (e.getFrom().distance(e.getTo()) > GlobalFinals.PEARL_MAX_DISTANCE) {
            e.setTo(e.getFrom());
            return;
        }
        State state = Main.getPlugin().getEarth().getState(e.getTo());
        if (state == null) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        if (state.getAccessLevel(h.getState()).getLevel() < AccessLevel.BUILD.getLevel()) return;
        e.setTo(e.getFrom());
    }

}
