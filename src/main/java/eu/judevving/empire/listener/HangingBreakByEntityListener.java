package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntityListener implements Listener {

    @EventHandler
    public void onBreak(HangingBreakByEntityEvent e) {
        if (EntityDamageByEntityListener.isEntityByMonster(e.getRemover())) {
            e.setCancelled(EntityDamageByEntityListener.isProtectedFromMonsters(e.getEntity()));
        }
        Player p = EntityDamageByEntityListener.getResponsiblePlayer(e.getRemover());
        if (p == null) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getEntity(), p, EntityInteraction.ATTACK)) e.setCancelled(true);
    }

}
