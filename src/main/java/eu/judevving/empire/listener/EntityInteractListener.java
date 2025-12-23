package eu.judevving.empire.listener;

import com.destroystokyo.paper.MaterialTags;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityInteractEvent;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onInteract(EntityInteractEvent e) {
        if (e.getEntity() instanceof Projectile) {
            Human h = Main.getPlugin().getEarth().getHuman(EntityDamageByEntityListener.getResponsiblePlayer(e.getEntity()));
            if (h == null) {
                e.setCancelled(true);
                return;
            }
            State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
            if (state == null) return;
            e.setCancelled(state.getAccessLevel(h.getState()).deniesBlock(e.getBlock().getType()));
            return;
        }
        if (!MaterialTags.DOORS.isTagged(e.getBlock().getType())) return;
        if (e.getEntityType() == EntityType.VINDICATOR || e.getEntityType() == EntityType.EVOKER) {
            e.setCancelled(true);
            return;
        }
        if (e.getBlock().getType() == Material.TURTLE_EGG) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntityType() != EntityType.VILLAGER) return;
        State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
        if (state == null) return;
        e.setCancelled(!state.getSettingsManager().getToggle(Toggle.VILLAGERS_CAN_OPEN_DOORS));
    }

}
