package eu.judevving.empire.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalCreateListener implements Listener {

    @EventHandler
    public void onCreate(PortalCreateEvent e) {
        if (e.getEntity() == null) return;
        if (e.getEntity().getType() == EntityType.PLAYER) return;
        e.setCancelled(true);
    }

}
