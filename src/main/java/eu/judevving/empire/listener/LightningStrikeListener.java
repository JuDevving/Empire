package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.listener.storage.EntityInteraction;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.earth.setting.AccessLevel;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;

public class LightningStrikeListener implements Listener {

    @EventHandler
    public void onStrike(LightningStrikeEvent e) {
        if (e.getCause() != LightningStrikeEvent.Cause.TRIDENT
                && e.getCause() != LightningStrikeEvent.Cause.WEATHER) return;
        State state = Main.getPlugin().getEarth().getState(e.getLightning().getLocation());
        if (state == null) return;
        if (e.getCause() == LightningStrikeEvent.Cause.WEATHER) {
            if (state.getSettingsManager().getToggle(Toggle.NATURAL_LIGHTNING_STRIKES)) return;
            e.setCancelled(true);
            return;
        }
        if (e.getLightning().getCausingEntity() == null) {
            e.setCancelled(true);
            return;
        }
        if (e.getLightning().getCausingPlayer() == null) {
            e.setCancelled(true);
            return;
        }
        if (e.getLightning().getCausingPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getLightning(), e.getLightning().getCausingPlayer(), EntityInteraction.ATTACK)) e.setCancelled(true);
    }

}
