package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerToggleFlightListener implements Listener {

    @EventHandler
    public void onToggle(PlayerToggleFlightEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (!e.isFlying()) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        Square square = Square.fromLocation(e.getPlayer().getLocation());
        if (Main.getPlugin().getEarth().getState(square) != h.getState()) return;
        if (Main.getPlugin().getEarth().getClaimTimeManager().isRecent(square)) return;
        e.setCancelled(true);
        e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection());
    }

}
