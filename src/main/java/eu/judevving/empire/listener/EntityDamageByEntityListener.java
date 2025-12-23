package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.listener.storage.EntityInteraction;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.earth.setting.AccessLevel;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.Nullable;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (isEntityByMonster(e.getDamager())) {
            e.setCancelled(isProtectedFromMonsters(e.getEntity()));
            return;
        }
        Player p = getResponsiblePlayer(e.getDamager());
        if (p == null) return;
        if (p.getGameMode() == GameMode.CREATIVE) return;
        if (AccessLevel.deniesEntity(e.getEntity(), p, EntityInteraction.ATTACK)) {
            e.setCancelled(true);
            return;
        }
    }

    public static boolean isEntityByMonster(Entity entity) {
        if (entity instanceof Monster) return true;
        if (entity instanceof Projectile projectile) {
            return projectile.getShooter() instanceof Monster;
        }
        return false;
    }

    public static Player getResponsiblePlayer(@Nullable Entity damager) {
        if (damager instanceof Player) return (Player) damager;
        if (damager instanceof Projectile projectile) {
            ProjectileSource source = projectile.getShooter();
            if (source != null) {
                if (source instanceof Player) return (Player) source;
            }
            if (projectile.getOwnerUniqueId() != null) {
                Player owner = Bukkit.getPlayer(projectile.getOwnerUniqueId());
                if (owner != null) return owner;
            }
        }
        return null;
    }

    public static boolean isProtectedFromMonsters(Entity entity) {
        if (entity == null) return false;
        if (entity.getType() == EntityType.PLAYER) return false;
        if (entity instanceof Monster) return false;
        if (entity.getType() == EntityType.IRON_GOLEM) return false;
        if (entity.getType() == EntityType.SNOW_GOLEM) return false;
        if (entity.getType() == EntityType.VILLAGER) return false;
        if (entity.getType() == EntityType.WOLF) return false;
        State state = Main.getPlugin().getEarth().getState(entity.getLocation());
        return state != null;
    }

}
