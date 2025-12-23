package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.listener.storage.Griefing;
import io.papermc.paper.event.entity.EntityMoveEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityMoveListener implements Listener {

    @EventHandler
    public void onMove(EntityMoveEvent e) {
        if (e.getEntityType() != EntityType.WARDEN) return;
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getTo().getY() > Griefing.WARDEN_MAX_Y) e.getTo().setY(Griefing.WARDEN_MAX_Y);
    }

}
