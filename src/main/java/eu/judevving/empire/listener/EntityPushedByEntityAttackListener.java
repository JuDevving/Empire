package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import io.papermc.paper.event.entity.EntityPushedByEntityAttackEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityPushedByEntityAttackListener implements Listener {

    @EventHandler
    public void onPush(EntityPushedByEntityAttackEvent e) {
        if (!(e.getPushedBy() instanceof Player p)) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getEntity(), p, EntityInteraction.ATTACK)) {
            e.setCancelled(true);
        }
    }

}
