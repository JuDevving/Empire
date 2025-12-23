package eu.judevving.empire.sidefeature;

import com.destroystokyo.paper.MaterialTags;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Shop {

    private int price;
    private Material product;
    private int amount;
    private Chest chest;

    public static boolean tryPurchase(Player p, Block sign) {
        Shop shop = getShop(sign);
        if (shop == null) return false;
        if (!shop.isValid()) return false;
        Human h = Main.getPlugin().getEarth().getHuman(p);
        if (!h.canShop()) return true;
        h.shopUsed();
        if (!shop.isStocked()) {
            h.sendMessage(Text.SHOP_OUT_OF_STOCK);
            return true;
        }
        if (!h.tryPurchase(GlobalFinals.SHOP_MONEY, shop.price)) {
            h.sendMessage(Text.SHOP_NOT_ENOUGH_MONEY);
            return true;
        }
        h.playSound(GlobalFinals.SOUND_SHOP, GlobalFinals.SOUND_SHOP_VOLUME, GlobalFinals.SOUND_SHOP_PITCH);
        HashMap<Integer, ItemStack> pOverflow = p.getInventory().addItem(shop.removePurchase());
        HashMap<Integer, ItemStack> cOverflow = new HashMap<>();
        if (shop.price > 0) cOverflow = shop.chest.getInventory().addItem(new ItemStack(GlobalFinals.SHOP_MONEY, shop.price));
        if (pOverflow.isEmpty() && cOverflow.isEmpty()) return true;
        Location chestTop = shop.chest.getLocation().clone().add(0.5, 1.1, 0.5);
        for (ItemStack itemStack : pOverflow.values()) {
            chestTop.getWorld().dropItemNaturally(chestTop, itemStack);
        }
        for (ItemStack itemStack : cOverflow.values()) {
            chestTop.getWorld().dropItemNaturally(chestTop, itemStack);
        }
        return true;
    }

    private Shop() {
        this.price = -1;
    }

    public static Shop getShop(Block sign) {
        return getShop(sign, null);
    }

    public static Shop getShop(SignChangeEvent event) {
        return getShop(event.getBlock(), event);
    }

    private static Shop getShop(Block sign, SignChangeEvent event) {
        if (sign == null) return null;
        SignSide front = null;
        if (event == null) {
            if (!MaterialTags.SIGNS.isTagged(sign)) return null;
            Sign signState = (Sign) sign.getState();
            front = signState.getSide(Side.FRONT);
        }
        if (!line(front, event, 0).equalsIgnoreCase(GlobalFinals.SHOP_LINE_0)) return null;
        Block chest = sign.getLocation().add(0, -1, 0).getBlock();
        Shop shop = new Shop();
        if (chest.getType() != Material.CHEST) return shop;
        if (!(chest.getState() instanceof Chest)) return shop;
        shop.chest = (Chest) chest.getState();
        if (shop.chest.getInventory() instanceof DoubleChestInventory) {
            shop.chest = null;
            return shop;
        }
        String productName = line(front, event, 1) + line(front, event, 2);
        productName = productName.toUpperCase();
        try {
            shop.product = Material.valueOf(productName);
        } catch (Exception e) {
            return shop;
        }
        String line3 = line(front, event, 3);
        int i = 0;
        while (i < line3.length() && Character.isDigit(line3.charAt(i))) i++;
        int j = i + GlobalFinals.SHOP_LINE_3_MIDDLE.length();
        if (line3.length() <= j) return shop;
        if (!line3.substring(i, j).equals(GlobalFinals.SHOP_LINE_3_MIDDLE)) return shop;
        try {
            shop.price = Integer.parseInt(line3.substring(0, i));
            shop.amount = Integer.parseInt(line3.substring(j));
        } catch (NumberFormatException e) {
            return shop;
        }
        return shop;
    }

    private static String line(SignSide front, SignChangeEvent event, int i) {
        if (front != null) return ((TextComponent) front.line(i)).content();
        return ((TextComponent) event.line(i)).content();
    }

    public ItemStack[] removePurchase() {
        int r = amount;
        List<ItemStack> purchase = new LinkedList<>();
        for (int i = chest.getInventory().getSize() - 1; i >= 0; i--) {
            ItemStack item = chest.getInventory().getItem(i);
            if (item == null) continue;
            if (item.getType() != product) continue;
            r -= item.getAmount();
            if (r < 0) {
                ItemStack last = item.clone();
                last.setAmount(item.getAmount() + r);
                purchase.add(last);
                item.setAmount(-r);
                chest.getInventory().setItem(i, item);
            } else {
                purchase.add(item);
                chest.getInventory().setItem(i, null);
            }
            if (r <= 0) break;
        }
        return purchase.toArray(new ItemStack[0]);
    }

    public boolean isStocked() {
        return chest.getInventory().contains(product, amount);
    }

    public boolean isValid() {
        return validChest() && validProduct() && validPrice() && validAmount();
    }

    public boolean validChest() {
        return chest != null;
    }

    public boolean validProduct() {
        if (product == null) return false;
        return !product.isAir();
    }

    public boolean validAmount() {
        return amount > 0;
    }

    public boolean validPrice() {
        return price >= 0;
    }

}
