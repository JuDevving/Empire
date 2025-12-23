package eu.judevving.empire.listener;

import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;

public class VehicleCreateListener implements Listener {

    @EventHandler
    public void onPlace(VehicleCreateEvent e) {
        //if (!Main.getPlugin().getEarth().isWorld(e.getVehicle().getWorld())) return;
        if (e.getVehicle().getType() == EntityType.MINECART) {
            Minecart minecart = (Minecart) e.getVehicle();
            minecart.setMaxSpeed(GlobalFinals.MINECART_SPEED_MAX);
            return;
        }
    }

}
