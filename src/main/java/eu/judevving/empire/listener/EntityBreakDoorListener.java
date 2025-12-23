package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreakDoorEvent;

public class EntityBreakDoorListener implements Listener {

    @EventHandler
    public void onBreak(EntityBreakDoorEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        e.setCancelled(true);
    }

}
