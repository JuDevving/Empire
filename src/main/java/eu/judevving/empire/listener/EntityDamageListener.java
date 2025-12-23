package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) return;
        if (Main.getPlugin().getEarth().getMiniatureEarth().isInside(e.getEntity().getLocation())) {
            if (e.getCause() == EntityDamageEvent.DamageCause.CUSTOM) return;
            if (e.getCause() == EntityDamageEvent.DamageCause.KILL) return;
            if (e.getCause() == EntityDamageEvent.DamageCause.SUICIDE) return;
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) return;
            e.setCancelled(true);
            if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
            if (((Player) e.getEntity()).getGameMode() == GameMode.CREATIVE) return;
            Main.getPlugin().getEarth().getMiniatureEarth().teleportAccordingly((Player) e.getEntity());
            return;
        }
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        ItemStack chest = ((Player)  e.getEntity()).getInventory().getItem(EquipmentSlot.CHEST);
        State state = Main.getPlugin().getEarth().getState((Player) e.getEntity());
        if (state != null) {
            if (state.getLevel() >= 52) {
                Square square = Square.fromLocation(e.getEntity().getLocation());
                if (Main.getPlugin().getEarth().getState(square) == state) {
                    if (!Main.getPlugin().getEarth().getClaimTimeManager().isRecent(square)) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if (chest.getType() != Material.ELYTRA) return;
        Damageable elytra = (Damageable) chest.getItemMeta();
        if (elytra.hasMaxDamage()) return;
        e.setCancelled(true);
    }

}
