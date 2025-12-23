package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.List;

public class BlockPistonListener implements Listener {

    @EventHandler
    public void onExtend(BlockPistonExtendEvent e) {
        e.setCancelled(cancels(e.getBlock(), e.getBlocks(), e.getDirection(), false));
    }

    @EventHandler
    public void onRetract(BlockPistonRetractEvent e) {
        e.setCancelled(cancels(e.getBlock(), e.getBlocks(), e.getDirection(), true));
    }

    private boolean cancels(Block piston, List<Block> blocks, BlockFace direction, boolean retract) {
        if (!Main.getPlugin().getEarth().isWorld(piston.getWorld())) return false;
        State state = Main.getPlugin().getEarth().getState(piston.getLocation());
        if (!retract) {
            if (Main.getPlugin().getEarth().getState(piston.getRelative(direction).getLocation()) != state) return true;
        }
        for (Block block : blocks) {
            if (Main.getPlugin().getEarth().getState(block.getLocation()) != state) return true;
            if (Main.getPlugin().getEarth().getState(block.getRelative(direction).getLocation()) != state)
                return true;
        }
        return false;
    }

}
