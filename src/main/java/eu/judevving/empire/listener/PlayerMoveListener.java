package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.hasChangedPosition()) {
            Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
            if (h.isWaitingForHomeTeleport()) {
                h.cancelHomeTeleport();
            }
        }
        if (!e.getPlayer().isGliding()) return;
        if (!Main.getPlugin().getEarth().isNether(e.getTo().getWorld())) return;
        if (e.getPlayer().getY() < GlobalFinals.NETHER_ELYTRA_MAX_Y) return;
        e.getPlayer().setGliding(false);



        /*
        if (!e.getPlayer().isRiptiding()) return;
        if (!Main.getPlugin().getEarth().isWorld(e.getPlayer().getWorld())) return;
        if (!e.hasChangedPosition()) return;
        if (e.getTo().getY() > e.getTo().getWorld().getMaxHeight()) {
            e.setCancelled(true);
            return;
        }
        State in = Main.getPlugin().getEarth().getState(e.getTo());
        if (in == null) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        if (h.getState() == in) return;
        if (in.getAccessLevel(h.getState()).getLevel() >= AccessLevel.BUILD.getLevel()) return;
        e.getTo().setX(e.getFrom().getX());
        e.getTo().setY(e.getFrom().getY());
        e.getTo().setZ(e.getFrom().getZ());
        */
    }

}
