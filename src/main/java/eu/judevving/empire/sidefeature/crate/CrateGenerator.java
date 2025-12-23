package eu.judevving.empire.sidefeature.crate;

import eu.judevving.empire.inventory.CustomHead;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.OminousBottleMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CrateGenerator {

    private static final Random random = new Random();

    private static Material[] discs = new Material[]{Material.MUSIC_DISC_CREATOR,
            Material.MUSIC_DISC_CREATOR_MUSIC_BOX, Material.MUSIC_DISC_PRECIPICE, Material.DISC_FRAGMENT_5,
            Material.MUSIC_DISC_OTHERSIDE, Material.MUSIC_DISC_RELIC};

    public static LinkedList<ItemStack> randomLoot() {
        LinkedList<ItemStack> loot = new LinkedList<>();

        if (Math.random() < 0.5) loot.add(new ItemStack(Material.BEETROOT_SEEDS, 1 + random.nextInt(8)));
        if (Math.random() < 0.3333) loot.add(new ItemStack(Material.NAME_TAG, 1));
        if (Math.random() < 0.5) loot.add(new ItemStack(Material.DIAMOND, 1 + random.nextInt(4)));

        int r = random.nextInt(100);
        if (r < 40) {
            for (int i = 0; i < GlobalFinals.CRATE_HEADS; i++) loot.add(CustomHead.randomCrateHead());
            return loot;
        }
        if (r < 80) {
            add(loot, Material.RAIL, 2 * 64 + random.nextInt(64 * 4));
            add(loot, Material.POWERED_RAIL, 32 + random.nextInt(32));
            add(loot, Material.DETECTOR_RAIL, 1 + random.nextInt(16));
            add(loot, Material.ACTIVATOR_RAIL, 1 + random.nextInt(16));
            return loot;
        }
        do loot.add(randomRare());
        while (Math.random() < 0.5);
        return loot;
    }

    private static ItemStack randomRare() {
        int r = random.nextInt(100);
        if (r < 40) {
            ItemStack ominousBottle = new ItemStack(Material.OMINOUS_BOTTLE);
            OminousBottleMeta ominousBottleItemMeta = (OminousBottleMeta) ominousBottle.getItemMeta();
            ominousBottleItemMeta.setAmplifier(random.nextInt(5));
            ominousBottle.setItemMeta(ominousBottleItemMeta);
            return ominousBottle;
        }
        if (r < 80) {
            ItemStack disc = new ItemStack(discs[random.nextInt(discs.length)]);
            if (disc.getType() == Material.DISC_FRAGMENT_5) disc.setAmount(10);
            return disc;
        }
        if (r < 100) {
            return new ItemStack(Material.SNIFFER_EGG);
        }
        return new ItemStack(Material.DIRT);
    }

    private static void add(List<ItemStack> list, Material material, int amount) {
        while (amount > 0) {
            if (amount >= material.getMaxStackSize()) {
                list.add(new ItemStack(material, material.getMaxStackSize()));
                amount -= material.getMaxStackSize();
            } else {
                list.add(new ItemStack(material, amount));
                amount = 0;
            }
        }
    }

    public static Location randomLocation(World world) {
        return new Location(world,
                random.nextInt(GlobalFinals.EARTH_BLOCK_MIN_X + GlobalFinals.EARTH_SIZE_X),
                0, GlobalFinals.EARTH_BLOCK_MIN_Z + random.nextInt(GlobalFinals.EARTH_SIZE_Z));
    }

}
