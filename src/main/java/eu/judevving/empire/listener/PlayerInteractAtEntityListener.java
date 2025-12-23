package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getRightClicked(), e.getPlayer(), EntityInteraction.DEFAULT)) e.setCancelled(true);
    }

}
