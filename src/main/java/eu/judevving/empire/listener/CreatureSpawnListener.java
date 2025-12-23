package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.sidefeature.WanderingTraderTrades;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getLocation().getWorld())) return;
        if (e.getEntityType() == EntityType.PLAYER) return;
        if (Main.getPlugin().getEarth().isOutOfBounds(e.getLocation())) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntityType() == EntityType.COPPER_GOLEM) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntityType() == EntityType.WITHER) {
            e.setCancelled(true);
            List<Entity> entities = e.getEntity().getNearbyEntities(GlobalFinals.SPAWN_WITHER_MESSAGE_RADIUS,
                    GlobalFinals.SPAWN_WITHER_MESSAGE_RADIUS, GlobalFinals.SPAWN_WITHER_MESSAGE_RADIUS);
            for (Entity entity : entities) {
                if (!(entity instanceof Player p)) continue;
                p.sendMessage(Text.INFO_WITHER_SPAWN.get(p));
            }
            return;
        }
        if (e.getEntityType() == EntityType.WANDERING_TRADER) {
            WanderingTraderTrades.setTrades((WanderingTrader) e.getEntity());
            return;
        }
        if (e.getEntity() instanceof Monster) {
            if (e.getSpawnReason()== CreatureSpawnEvent.SpawnReason.NATURAL) {
                if (inBufferZone(e.getLocation())) {
                    e.setCancelled(true);
                    return;
                }
                State in = Main.getPlugin().getEarth().getState(e.getLocation());
                if (in != null) {
                    if (!in.getSettingsManager().getToggle(Toggle.MONSTERS_SPAWN)) e.setCancelled(true);
                }
            }
        }
    }

    private boolean inBufferZone(Location loc) {
        if (loc.getY() < GlobalFinals.SPAWN_MONSTER_EDGE_BUFFER_MIN_Y) return false;
        if (loc.getX() < GlobalFinals.EARTH_BLOCK_MIN_X + GlobalFinals.SPAWN_MONSTER_EDGE_BUFFER) {
            return true;
        }
        if (loc.getX() > GlobalFinals.EARTH_BLOCK_MAX_X - GlobalFinals.SPAWN_MONSTER_EDGE_BUFFER) {
            return true;
        }
        if (loc.getZ() < GlobalFinals.EARTH_BLOCK_MIN_Z + GlobalFinals.SPAWN_MONSTER_EDGE_BUFFER) {
            return true;
        }
        if (loc.getZ() > GlobalFinals.EARTH_BLOCK_MAX_Z - GlobalFinals.SPAWN_MONSTER_EDGE_BUFFER) {
            return true;
        }
        return false;
    }

}
