package eu.judevving.empire.listener;

import com.destroystokyo.paper.event.entity.EntityKnockbackByEntityEvent;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityKnockbackByEntityListener implements Listener {

    @EventHandler
    public void onKnockback(EntityKnockbackByEntityEvent e) {
        /*if (e.getPushedBy().getType() == EntityType.CREEPER) {
            e.setCancelled(true);
            return;
        }*/
        Player p = EntityDamageByEntityListener.getResponsiblePlayer(e.getPushedBy());
        if (p == null) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getEntity(), p, EntityInteraction.DEFAULT)) {
            e.setCancelled(true);
            return;
        }
    }

}
