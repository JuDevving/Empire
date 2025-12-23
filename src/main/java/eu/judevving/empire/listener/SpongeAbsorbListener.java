package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SpongeAbsorbEvent;

public class SpongeAbsorbListener implements Listener {

    @EventHandler
    public void onAbsorb(SpongeAbsorbEvent e) {
        e.setCancelled(Main.getPlugin().getEarth().isBorderRegion(e.getBlock().getLocation()));
    }

}
