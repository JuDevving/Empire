package eu.judevving.empire.inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ItemEditor {

    public static ItemStack removeToolTipDisplay(ItemStack itemStack) {
        try {
            System.out.println(getVersion());
            Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
            Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
            Object nmsItemStack = asNMSCopy.invoke(null, itemStack);

            // Get or create tag
            Method getTag = nmsItemStack.getClass().getMethod("j"); //getTags()
            Object tagCompound = getTag.invoke(nmsItemStack);

            Stream<Object> stream = (Stream<Object>) getTag.invoke(nmsItemStack);

            //itemStack.getItemMeta().getPersistentDataContain

            if (tagCompound != null) {
                // Remove the tag
                Method removeMethod = tagCompound.getClass().getMethod("remove", String.class);
                removeMethod.invoke(tagCompound, "minecraft:tooltip_display");

                // Re-set the tag on the item
                Method setTag = nmsItemStack.getClass().getMethod("setTag", tagCompound.getClass());
                setTag.invoke(nmsItemStack, tagCompound);
            }

            // Convert back to Bukkit ItemStack
            Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItemStack.getClass());
            return (ItemStack) asBukkitCopy.invoke(null, nmsItemStack);

        } catch (Exception e) {
            e.printStackTrace();
            return itemStack; // Return original if something fails
        }
    }

    private static String getVersion() {
        // e.g., "v1_20_R3"
        String name = org.bukkit.Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }

    public static ItemStack setNameAndLore(ItemStack itemStack, String name, String... lore) {
        LinkedList<Component> loreList = new LinkedList<>();
        for (String string : lore) {
            loreList.addLast(Component.text(string).decoration(TextDecoration.ITALIC, false));
        }
        return setNameAndLore(itemStack, name, loreList);
    }

    public static ItemStack setNameAndLore(ItemStack itemStack, String name, List<Component> lore) {
        if (itemStack == null) return null;
        if (name == null) return itemStack;
        setName(itemStack, name);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setName(ItemStack itemStack, String name) {
        if (itemStack == null) return itemStack;
        if (name == null) return itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
        itemMeta.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack makeShiny(ItemStack itemStack) {
        if (itemStack == null) return null;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.UNBREAKING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}
