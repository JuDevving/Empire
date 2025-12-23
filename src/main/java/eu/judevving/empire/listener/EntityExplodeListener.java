package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getEntityType() == EntityType.CREEPER) neutralizeExplosion(e);
    }

    private void neutralizeExplosion(EntityExplodeEvent e) {
        e.blockList().clear();
        //e.setCancelled(true);
        //e.getLocation().getWorld().playSound(e.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, GlobalFinals.SOUND_EXPLODE_VOLUME, GlobalFinals.SOUND_EXPLODE_PITCH);
        //e.getLocation().getWorld().createExplosion(e.getEntity(), e.getLocation(),e.getYield(),false, false);
    }

}
