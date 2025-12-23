package eu.judevving.empire.inventory.recipe;

import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SaddleRecipe {

    private static NamespacedKey key;

    public static void learn(Player p) {
        if (key == null) register();
        BannerPatternRecipe.learn(p, Material.SADDLE);
    }

    private static void register() {
        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.fromString(GlobalFinals.RECIPE_PREFIX + "saddle"),
                new ItemStack(Material.SADDLE));
        recipe.shape(" L ","LIL");
        recipe.setIngredient('I', new ItemStack(Material.IRON_INGOT));
        recipe.setIngredient('L', new ItemStack(Material.LEATHER));
        Main.getPlugin().getServer().addRecipe(recipe);
        key = recipe.getKey();
    }

}
