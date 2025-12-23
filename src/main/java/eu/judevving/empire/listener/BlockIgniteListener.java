package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener implements Listener {

    @EventHandler
    public void onIgnite(BlockIgniteEvent e) {
        State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
        if (state == null) return;
        switch (e.getCause()) {
            case ENDER_CRYSTAL:
            case FIREBALL:
            case FLINT_AND_STEEL:
                return;
            case EXPLOSION:
            case LAVA:
            case LIGHTNING:
            case SPREAD:
                if (state.getSettingsManager().getToggle(Toggle.FIRE_SPREADS)) return;
                e.setCancelled(true);
                return;
            case ARROW:
                Player igniter = EntityDamageByEntityListener.getResponsiblePlayer(e.getIgnitingEntity());
                if (igniter != null) {
                    if (igniter.getGameMode() == GameMode.CREATIVE) return;
                }
                State igniterState = Main.getPlugin().getEarth().getState(igniter);
                if (igniterState == null) {
                    e.setCancelled(true);
                    return;
                }
                e.setCancelled(state.getAccessLevel(igniterState).getLevel() < AccessLevel.BUILD.getLevel());
                return;
        }
    }

}
