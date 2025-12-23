package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getEntityType() != EntityType.SMALL_FIREBALL) return;
        Fireball fireball = (Fireball) e.getEntity();
        fireball.setIsIncendiary(false);
    }

}
