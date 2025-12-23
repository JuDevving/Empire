package eu.judevving.empire.listener;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerPostRespawnEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (Main.getPlugin().getEarth().getMiniatureEarth().isInside(e.getRespawnedLocation())) return;
        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, GlobalFinals.RESPAWN_RESISTANCE_TICKS, 4));
    }

}
