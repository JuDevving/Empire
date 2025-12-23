package eu.judevving.empire.listener;

import com.destroystokyo.paper.MaterialTags;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.listener.storage.MaterialTags2;
import eu.judevving.empire.sidefeature.Shop;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.FluidCollisionMode;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getHand() == EquipmentSlot.HAND) {
            if (e.getPlayer().isSneaking()) {
                if (Shop.tryPurchase(e.getPlayer(), e.getClickedBlock())) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (Main.getPlugin().getEarth().isOutOfBounds(e.getPlayer().getLocation())) {
            e.setUseInteractedBlock(Event.Result.DENY);
            e.setUseItemInHand(Event.Result.DENY);
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) { // make railway buttons pressable
            if (e.getClickedBlock() != null) {
                if (MaterialTags2.BUTTONS.isTagged(e.getClickedBlock().getType())) {
                    State in = Main.getPlugin().getEarth().getState(e.getClickedBlock().getLocation());
                    if (in != null) {
                        if (in.getSettingsManager().getToggle(Toggle.PUBLIC_MINECARTS)) {
                            Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
                            if (!in.getRelationManager().isEnemy(h.getState())) {
                                if (neighbors(e.getClickedBlock(), Material.POWERED_RAIL)) {
                                    e.setUseInteractedBlock(Event.Result.DEFAULT);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) { // prevent spawn egg on spawner use
            if (e.getClickedBlock() != null) {
                if (e.getClickedBlock().getType() == Material.SPAWNER || e.getClickedBlock().getType() == Material.TRIAL_SPAWNER) {
                    if (e.getItem() != null) {
                        if (MaterialTags.SPAWN_EGGS.isTagged(e.getItem().getType()))
                            e.setUseItemInHand(Event.Result.DENY);
                    }
                }
            }
        }
        State state;
        if (e.getClickedBlock() != null) {
            state = Main.getPlugin().getEarth().getState(e.getClickedBlock().getLocation());
            if (e.getClickedBlock().getType() == GlobalFinals.CRATE_MATERIAL) {
                Main.getPlugin().getEarth().getCrateManager().popCrate(e.getPlayer(), e.getClickedBlock().getLocation());
            }
        } else state = Main.getPlugin().getEarth().getState(e.getPlayer().getLocation());
        if (state == null) return;
        State pState = Main.getPlugin().getEarth().getState(e.getPlayer());
        if (e.getAction() == Action.PHYSICAL) {
            if (e.getClickedBlock().getType() == Material.FARMLAND) {
                if (!state.getSettingsManager().getToggle(Toggle.FARMLAND_CAN_BE_TRAMPLED)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
        if (e.getClickedBlock() != null) {
            if (state.getAccessLevel(pState).deniesBlock(e.getClickedBlock().getType()))
                e.setUseInteractedBlock(Event.Result.DENY);
        }
        if (!state.getRelationManager().isEnemy(pState)) {
            if (e.getMaterial() == Material.MINECART) {
                if (e.getClickedBlock() != null) {
                    if (MaterialTags.RAILS.isTagged(e.getClickedBlock().getType())) {
                        if (state.getSettingsManager().getToggle(Toggle.PUBLIC_MINECARTS)) {
                            e.setUseInteractedBlock(Event.Result.DEFAULT);
                            return;
                        }
                    }
                }
            }
            if (MaterialTags2.BOATS_NO_CHEST.isTagged(e.getMaterial())) {
                Block target = e.getPlayer().getTargetBlockExact(3, FluidCollisionMode.SOURCE_ONLY);
                if (target != null && target.getType() == Material.WATER) {
                    if (state.getSettingsManager().getToggle(Toggle.PUBLIC_BOATS)) {
                        e.setUseInteractedBlock(Event.Result.DEFAULT);
                        return;
                    }
                }
            }
        }
        if (state.getAccessLevel(pState).deniesItem(e.getMaterial())) e.setUseItemInHand(Event.Result.DENY);
    }

    private static boolean neighbors(Block block, Material material) {
        Location location = block.getLocation().clone();
        for (int x = block.getX() - 1; x <= block.getX() + 1; x++) {
            location.setX(x);
            for (int y = block.getY() - 1; y <= block.getY() + 1; y++) {
                location.setY(y);
                for (int z = block.getZ() - 1; z <= block.getZ() + 1; z++) {
                    location.setZ(z);
                    Block neighbor = location.getBlock();
                    if (neighbor.getType().isAir()) continue;
                    if (neighbor.getType() == material) return true;
                }
            }
        }
        return false;
    }

}
