package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromToListener implements Listener {

    @EventHandler
    public void onFlow(BlockFromToEvent e) {
        if (e.getBlock().getType() == Material.DRAGON_EGG) return;
        State state = Main.getPlugin().getEarth().getState(e.getToBlock().getLocation());
        if (state == null) return;
        State from = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
        if (state == from) return;
        e.setCancelled(true);
    }

}
