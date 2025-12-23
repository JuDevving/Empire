package eu.judevving.empire.sidefeature;

import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class WanderingTraderTrades {

    private static ArrayList<Material> sherds;
    private static Random random;

    public static void setTrades(WanderingTrader wanderingTrader) {
        if (sherds == null) return;
        wanderingTrader.setRecipes(getRecipes());
    }

    private static List<MerchantRecipe> getRecipes() {
        List<MerchantRecipe> recipes = new LinkedList<>();
        recipes.addLast(getRecipe(Material.PHANTOM_MEMBRANE, 4, 8));
        recipes.addLast(getRecipe(Material.ALLAY_SPAWN_EGG, 32, 1));
        recipes.addLast(getRecipe(Material.HEART_OF_THE_SEA, 48, 1));
        for (int i = 0; i < 5; i++) {
            recipes.addLast(getRecipe(sherds.get(random.nextInt(sherds.size())), 4, 4));
        }
        return recipes;
    }

    public static void init() {
        random = new Random();
        sherds = new ArrayList<>();
        for (Material material : Material.values()) {
            if (!material.name().endsWith("_POTTERY_SHERD")) continue;
            sherds.addLast(material);
        }
    }

    private static MerchantRecipe getRecipe(Material material, int price, int maxUses) {
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(material), maxUses);
        recipe.addIngredient(new ItemStack(Material.EMERALD, price));
        return recipe;
    }

}
