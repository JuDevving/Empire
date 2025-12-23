package eu.judevving.empire.listener;

import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;

public class VehicleEntityCollisionListener implements Listener {

    @EventHandler
    public void onCollision(VehicleEntityCollisionEvent e) {
        if (e.getVehicle().getType() != EntityType.MINECART) return;
        if (e.getVehicle().getVelocity().length() < GlobalFinals.MINECART_SPEED_REDUCED_COLLISIONS) return;
        if (!(e.getEntity() instanceof Monster) && !(e.getEntity() instanceof Animals)) return;
        e.setCancelled(true);
    }

}
