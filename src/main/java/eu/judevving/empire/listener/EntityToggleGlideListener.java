package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.util.Vector;

public class EntityToggleGlideListener implements Listener {

    @EventHandler
    public void onToggle(EntityToggleGlideEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) return;
        if (!e.isGliding()) return;
        Player p = (Player) e.getEntity();
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (p.getWorld().getEnvironment() == World.Environment.THE_END) return;
        //p.sendMessage(Text.INFO_ELYTRAS_DISABLED.get(p));
        State state = Main.getPlugin().getEarth().getState((Player) e.getEntity());
        if (Main.getPlugin().getEarth().isNether(e.getEntity().getWorld())) {
            if (state != null) {
                if (state.getLevel() >= 54) {
                    if (p.getY() < GlobalFinals.NETHER_ELYTRA_MAX_Y) {
                        return;
                    }
                }
            }
        }
        Vector velocity = p.getVelocity();
        velocity.setX(0);
        velocity.setZ(0);
        p.setVelocity(velocity);
        e.setCancelled(true);
    }

}
