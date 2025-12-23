package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getPlayer());
        h.addDeath();
        if (h.getState() != null) h.getState().memberDied(h);

        e.setKeepLevel(!GlobalFinals.DEATH_LEVEL_LOSS);
        if (e.getKeepLevel()) e.setDroppedExp(0);
        e.setKeepInventory(GlobalFinals.DEATH_ITEM_LOSS < 1);
        if (e.getKeepInventory()) {
            e.getDrops().clear();
            if (GlobalFinals.DEATH_ITEM_LOSS > 0) {
                PlayerInventory inv = e.getPlayer().getInventory();
                int loss = 0;
                ItemStack item;
                for (int i = 0; i < inv.getSize(); i++) {
                    item = inv.getItem(i);
                    if (item == null) continue;
                    if (item.getType().isAir()) continue;
                    if (item.getItemMeta().hasEnchant(Enchantment.VANISHING_CURSE)) {
                        inv.setItem(i, null);
                        continue;
                    }
                    if (Math.random() > GlobalFinals.DEATH_ITEM_LOSS) continue;
                    loss++;
                    e.getDrops().add(item);
                    inv.setItem(i, null);
                }
                for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                    if (equipmentSlot == EquipmentSlot.BODY ||
                            equipmentSlot == EquipmentSlot.HAND ||
                            equipmentSlot == EquipmentSlot.SADDLE) continue;
                    item = inv.getItem(equipmentSlot);
                    if (item.getType().isAir()) continue;
                    if (item.getItemMeta().hasEnchant(Enchantment.VANISHING_CURSE)) {
                        inv.setItem(equipmentSlot, null);
                        continue;
                    }
                    if (Math.random() > GlobalFinals.DEATH_ITEM_LOSS) continue;
                    loss++;
                    e.getDrops().add(item);
                    inv.setItem(equipmentSlot, null);
                }
                h.sendMessage(Text.DEATH_ITEM_LOSS, loss + "");
            }
        }
        if (GlobalFinals.DEATH_HEAD_DROP) {
            ItemStack head = Main.getPlugin().getPlayerHeads().getHead(e.getPlayer());
            if (head == null) head = new ItemStack(Material.PLAYER_HEAD);
            e.getDrops().add(head);
        }
    }

}
