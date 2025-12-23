package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class PlayerBucketListener implements Listener {

    @EventHandler
    public void onFill(PlayerBucketFillEvent e) {
        e.setCancelled(cancels(e.getBlock(), e.getPlayer(), e.getBucket()));
    }

    @EventHandler
    public void onEmpty(PlayerBucketEmptyEvent e) {
        e.setCancelled(cancels(e.getBlock(), e.getPlayer(), e.getBucket()));
    }

    private boolean cancels(Block block, Player p, Material bucket) {
        if (p.getGameMode() == GameMode.CREATIVE) return false;
        State in = Main.getPlugin().getEarth().getState(block.getLocation());
        if (in == null) return false;
        State state = Main.getPlugin().getEarth().getState(p);
        return in.getAccessLevel(state).deniesItem(bucket);
    }

}
