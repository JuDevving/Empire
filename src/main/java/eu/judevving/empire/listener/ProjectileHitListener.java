package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof WindCharge windCharge) {
            if (!(windCharge.getShooter() instanceof Player p)) return;
            if (p.getGameMode() == GameMode.CREATIVE) return;
            if (AccessLevel.deniesEntity(windCharge, p, EntityInteraction.ATTACK)) {
                windCharge.remove();
                windCharge.setYield(0);
                e.setCancelled(true);
            }
        }
    }

}
