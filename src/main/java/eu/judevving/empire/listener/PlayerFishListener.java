package eu.judevving.empire.listener;

import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishListener implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        if (e.getState() != PlayerFishEvent.State.CAUGHT_ENTITY) return;
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (!Main.getPlugin().getEarth().isWorld(e.getPlayer().getWorld())) return;
        if (AccessLevel.deniesEntity(e.getHook(), e.getPlayer(), EntityInteraction.DEFAULT)) {
            e.setCancelled(true);
            e.getHook().setHookedEntity(null);
        }
    }

}
