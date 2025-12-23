package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;

public class TNTPrimeListener implements Listener {

    @EventHandler
    public void onPrime(TNTPrimeEvent e) {
        if (e.getCause() != TNTPrimeEvent.PrimeCause.PROJECTILE) return;
        State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
        if (state == null) return;
        Player igniter = EntityDamageByEntityListener.getResponsiblePlayer(e.getPrimingEntity());
        if (igniter != null) {
            if (igniter.getGameMode() == GameMode.CREATIVE) return;
        }
        State igniterState = Main.getPlugin().getEarth().getState(igniter);
        if (igniterState == null) {
            e.setCancelled(!state.getSettingsManager().getToggle(Toggle.OWNERLESS_TNT_EXPLODES));
            return;
        }
        e.setCancelled(state.getAccessLevel(igniterState).getLevel() < AccessLevel.BUILD.getLevel());
    }

}
