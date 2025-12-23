package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Main.getPlugin().getPlayerHeads().refreshHead(e.getPlayer().getUniqueId());
        Main.getPlugin().getEarth().putHuman(e.getPlayer());
        Main.getPlugin().getEarth().updateMaxPlayers();
    }

}
