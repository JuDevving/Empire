package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class VehicleDamageListener implements Listener {

    @EventHandler
    public void onDamage(VehicleDamageEvent e) {
        if (e.getAttacker() != null) {
            if (EntityDamageByEntityListener.isEntityByMonster(e.getAttacker())) {
                e.setCancelled(EntityDamageByEntityListener.isProtectedFromMonsters(e.getVehicle()));
                return;
            }
        }
        Player p = EntityDamageByEntityListener.getResponsiblePlayer(e.getAttacker());
        if (p == null) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getVehicle(), p, EntityInteraction.ATTACK)) e.setCancelled(true);
    }

}
