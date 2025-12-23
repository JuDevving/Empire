package eu.judevving.empire.listener;

import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.minecart.RideableMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

public class VehicleEnterListener implements Listener {

    @EventHandler
    public void onEnter(VehicleEnterEvent e) {
        //if (!Main.getPlugin().getEarth().isWorld(e.getVehicle().getWorld())) return;
        if (!(e.getVehicle() instanceof RideableMinecart)) return;
        ((Minecart) e.getVehicle()).setMaxSpeed(GlobalFinals.MINECART_SPEED_MAX);
    }

}
