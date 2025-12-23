package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.AccessLevel;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.listener.storage.EntityInteraction;
import eu.judevving.empire.listener.storage.Griefing;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class ExplosionPrimeListener implements Listener {

    @EventHandler
    public void onPrime(ExplosionPrimeEvent e) {
        if (explosionShouldCancel(e.getEntity())) e.setCancelled(true);
        if (e.getEntity() instanceof ExplosiveMinecart explosiveMinecart) {
            if (explosiveMinecart.isIgnited()) explosiveMinecart.remove();
        }
    }

    public static boolean explosionShouldCancel(Entity exploder) {
        if (exploder == null) return false;
        if (!Main.getPlugin().getEarth().isWorld(exploder.getWorld())) return false;
        if (exploder.getType() == EntityType.CREEPER) return false;
        if (Griefing.isOnExplosionBlacklist(exploder.getType())) return true;
        Player p = null;
        if (exploder.getType() == EntityType.TNT) {
            if (((TNTPrimed) exploder).getSource() instanceof Player)
                p = (Player) ((TNTPrimed) exploder).getSource();
        }
        if (exploder.getType() == EntityType.WIND_CHARGE) {
            if (((WindCharge) exploder).getShooter() instanceof Player)
                p = (Player) ((WindCharge) exploder).getShooter();
        }
        if (p != null) {
            if (p.getGameMode() == GameMode.CREATIVE) return false;
            return AccessLevel.deniesEntity(exploder, p, EntityInteraction.EXPLODE);
        }
        if (isTnt(exploder)) {
            if (Main.getPlugin().getEarth().isBorderRegion(exploder.getLocation())) {
                State in = Main.getPlugin().getEarth().getBorderRegionStateIfSole(exploder.getLocation());
                if (in == null) return true;
                return !in.getSettingsManager().getToggle(Toggle.OWNERLESS_TNT_EXPLODES);
            }
            State state = Main.getPlugin().getEarth().getState(exploder.getLocation());
            if (state == null) return false;
            return !state.getSettingsManager().getToggle(Toggle.OWNERLESS_TNT_EXPLODES);
        }
        return Main.getPlugin().getEarth().getState(exploder.getLocation()) != null;
    }

    private static boolean isTnt(Entity entity) {
        return entity.getType() == EntityType.TNT || entity.getType() == EntityType.TNT_MINECART;
    }

}
