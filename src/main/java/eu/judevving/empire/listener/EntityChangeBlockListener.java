package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.main.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {

    @EventHandler
    public void onChange(EntityChangeBlockEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getEntity() instanceof Vehicle) {
            State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
            if (state != null) {
                e.setCancelled(true);
            }
            return;
        }
        State state;
        switch (e.getEntityType()) {
            case ENDERMAN:
            case RABBIT:
            case RAVAGER:
            case SILVERFISH:
            case SNOW_GOLEM:
                e.setCancelled(true);
                return;
            case VILLAGER:
                if (e.getTo() == Material.DIRT) {
                    e.setCancelled(true);
                    return;
                }
                state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
                if (state == null) return;
                if (state.getSettingsManager().getToggle(Toggle.VILLAGERS_CAN_HARVEST_CROPS)) return;
                e.setCancelled(true);
                return;
            case SHEEP:
                break;
            default:
                if (e.getTo() == Material.DIRT) {
                    if (e.getEntity() instanceof Player) {
                        state = Main.getPlugin().getEarth().getState((Player) e.getEntity());
                        State in = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
                        if (in == null) return;
                        if (in.getAccessLevel(state).allowsBlock(Material.DIRT_PATH)) return;
                    }
                    e.setCancelled(true);
                    return;
                }
                break;
        }
        if (e.getEntity() instanceof Projectile) {
            e.setCancelled(true);
            return;
        }
    }

}
