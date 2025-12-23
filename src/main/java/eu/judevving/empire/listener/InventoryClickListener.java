package eu.judevving.empire.listener;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.inventory.menu.MenuFinals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getView().getTopInventory().getSize() != MenuFinals.SIZE) return;
        e.setCancelled(true);
        if (e.getClickedInventory().getSize() != MenuFinals.SIZE) return;
        if (e.getClick() != ClickType.LEFT) return;
        Human h = Main.getPlugin().getEarth().getHuman(e.getWhoClicked().getUniqueId());
        if (h == null) return;
        if (e.getSlot() < 0) return;
        h.getInventoryMenu().click(e.getSlot());
    }

}
