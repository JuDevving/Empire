package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.listener.storage.Griefing;
import eu.judevving.empire.main.Main;
import io.papermc.paper.event.block.BlockPreDispenseEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockPreDispenseListener implements Listener {

    @EventHandler
    public void onDispense(BlockPreDispenseEvent e) {
        if (e.getBlock().getType() != Material.DISPENSER) return;
        if (!Main.getPlugin().getEarth().isWorld(e.getBlock().getWorld())) return;
        if (!Griefing.isOnDispenserBlacklist(e.getItemStack().getType())) return;
        if (!(e.getBlock().getBlockData() instanceof Directional directional)) return;
        if (directional.getFacing() == BlockFace.UP || directional.getFacing() == BlockFace.DOWN) return;
        Block target  = e.getBlock().getRelative(directional.getFacing());
        State targetState = Main.getPlugin().getEarth().getState(target.getLocation());
        if (targetState == null) return;
        if (Main.getPlugin().getEarth().getState(e.getBlock().getLocation()) == targetState) return;
        e.setCancelled(true);
    }

}
