package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        State state = Main.getPlugin().getEarth().getState(e.getBlockPlaced().getLocation());
        if (state == null) return;
        State pState = Main.getPlugin().getEarth().getState(e.getPlayer());
        if (state.getAccessLevel(pState).deniesItem(e.getBlockPlaced().getType())) e.setCancelled(true);
    }

}
