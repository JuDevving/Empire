package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class EntityPortalListener implements Listener {

    @EventHandler
    public void onPortal(EntityPortalEvent e) {
        if (e.getTo() == null) {
            e.setCancelled(true);
            return;
        }
        if (e.getFrom().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
            return;
        }
        if (e.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            e.setCancelled(true);
            return;
        }
        e.setCanCreatePortal(false);
        Location newTo = e.getFrom().clone();
        newTo.setWorld(e.getTo().getWorld());
        if (Main.getPlugin().getEarth().isNether(e.getTo().getWorld())) {
            newTo.setY(GlobalFinals.NETHER_PORTAL_Y_NETHER);
        } else newTo.setY(GlobalFinals.NETHER_PORTAL_Y_OVERWORLD);
        e.setTo(newTo);
    }

}
