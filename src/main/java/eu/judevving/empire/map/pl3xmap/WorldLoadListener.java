package eu.judevving.empire.map.pl3xmap;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.world.WorldLoadedEvent;

public class WorldLoadListener implements EventListener {

    @EventHandler
    public void onLoad(WorldLoadedEvent e) {
        if (!e.getWorld().getName().equals(GlobalFinals.WORLD_OVERWORLD_NAME)) return;
        Main.getPlugin().getPl3xmapConnection().initOnEnable(e.getWorld());
    }

}
