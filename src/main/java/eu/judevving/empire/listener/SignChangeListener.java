package eu.judevving.empire.listener;

import eu.judevving.empire.sidefeature.Shop;
import eu.judevving.empire.language.Text;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onChange(SignChangeEvent e) {
        Shop shop = Shop.getShop(e);
        if (shop == null) return;
        Player p = e.getPlayer();
        if (!shop.validChest()) {
            p.sendMessage(Text.SHOP_INVALID_CHEST.get(p));
            return;
        }
        if (!shop.validProduct()) {
            p.sendMessage(Text.SHOP_INVALID_PRODUCT.get(p));
            return;
        }
        if (!shop.validPrice()) {
            p.sendMessage(Text.SHOP_INVALID_PRICE.get(p));
            return;
        }
        if (!shop.validAmount()) {
            p.sendMessage(Text.SHOP_INVALID_AMOUNT.get(p));
            return;
        }
        p.sendMessage(Text.SHOP_CREATED.get(p));
    }

}
