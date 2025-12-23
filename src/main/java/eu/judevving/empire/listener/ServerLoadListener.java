package eu.judevving.empire.listener;

import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadListener implements Listener {

    @EventHandler
    public void onLoad(ServerLoadEvent e) {
        Main.getPlugin().getEarth().setWorld(Bukkit.getWorlds().get(GlobalFinals.WORLD_OVERWORLD_ID));
        Main.getPlugin().getEarth().setNether(Bukkit.getWorlds().get(GlobalFinals.WORLD_NETHER_ID));
        Main.getPlugin().getEarth().postLoadInit();
    }

}
