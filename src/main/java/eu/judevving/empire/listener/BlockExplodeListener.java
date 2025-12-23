package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;

public class BlockExplodeListener implements Listener {

    @EventHandler
    public void onExplode(BlockExplodeEvent e) {
        if (Main.getPlugin().getEarth().isWorld(e.getBlock().getWorld())) e.setCancelled(true);
    }

}
