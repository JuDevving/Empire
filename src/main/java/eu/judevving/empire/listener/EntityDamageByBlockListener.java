package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByBlockListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent e) {
        if (e.getCause() != EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return;
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getLocation().getWorld())) return;
        e.setCancelled(true);
    }

}
