package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PlayerPortalListener implements Listener {

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        if (Main.getPlugin().getEarth().isNether(e.getTo().getWorld())) {
            Location newTo = e.getFrom().clone();
            newTo.setWorld(e.getTo().getWorld());
            newTo.setY(GlobalFinals.NETHER_PORTAL_Y_NETHER);
            e.setTo(newTo);
            h.setNetherPortalLocation(e.getFrom());
            return;
        }
        if (Main.getPlugin().getEarth().isWorld(e.getTo().getWorld())) {
            Location newTo = h.getNetherPortalLocation();
            if (newTo == null) {
                newTo = e.getFrom().clone();
                newTo.setWorld(e.getTo().getWorld());
                newTo.setY(GlobalFinals.NETHER_PORTAL_Y_OVERWORLD);
            }
            e.setTo(newTo);
            return;
        }
    }

}
