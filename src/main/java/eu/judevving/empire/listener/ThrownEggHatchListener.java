package eu.judevving.empire.listener;

import com.destroystokyo.paper.event.entity.ThrownEggHatchEvent;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.listener.storage.EntityInteraction;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ThrownEggHatchListener implements Listener {

    @EventHandler
    public void onHatch(ThrownEggHatchEvent e) {
        if (!(e.getEgg().getShooter() instanceof Player p)) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getEgg(), p, EntityInteraction.DEFAULT)) e.setHatching(false);
    }

}
