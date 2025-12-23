package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;

public class HangingBreakListener implements Listener {

    @EventHandler
    public void onBreak(HangingBreakEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getCause() != HangingBreakEvent.RemoveCause.EXPLOSION) return;
        State state = Main.getPlugin().getEarth().getState(e.getEntity().getLocation());
        if (state == null) return;
        e.setCancelled(true);
    }

}
