package eu.judevving.empire.inventory.recipe;

import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.LinkedList;
import java.util.List;

public enum BannerPatternRecipe {

    CREEPER(Material.CREEPER_BANNER_PATTERN, Material.LIME_DYE, "creeper"),
    FLOW(Material.FLOW_BANNER_PATTERN, Material.STRING, "flow"),
    GLOBE(Material.GLOBE_BANNER_PATTERN, Material.DIRT, "globe"),
    GUSTER(Material.GUSTER_BANNER_PATTERN, Material.FEATHER, "guster"),
    SKULL(Material.SKULL_BANNER_PATTERN, Material.BONE, "skull"),
    SNOUT(Material.PIGLIN_BANNER_PATTERN, Material.PORKCHOP, "snout"),
    THING(Material.MOJANG_BANNER_PATTERN, Material.APPLE, "thing");

    public static final Material PAPER = Material.PAPER;

    private final Material bannerPattern, ingredient;
    private final NamespacedKey key;

    BannerPatternRecipe(Material bannerPattern, Material ingredient, String name) {
        this.bannerPattern = bannerPattern;
        this.ingredient = ingredient;
        this.key = NamespacedKey.fromString(GlobalFinals.RECIPE_PREFIX + name);
    }

    public static void learnAll(Player p) {
        LinkedList<NamespacedKey> recipes = new LinkedList<>();
        for (BannerPatternRecipe bannerPatternRecipe : values()) {
            recipes.add(bannerPatternRecipe.key);
        }
        p.discoverRecipes(recipes);
        learn(p, Material.BORDURE_INDENTED_BANNER_PATTERN);
        learn(p, Material.FIELD_MASONED_BANNER_PATTERN);
        learn(p, Material.FLOWER_BANNER_PATTERN);
    }

    public static void learn(Player p, Material material) {
        List<Recipe> recipes = Main.getPlugin().getServer().getRecipesFor(new ItemStack(material));
        if (recipes.isEmpty()) return;
        for (Recipe recipe : recipes) {
            if (!(recipe instanceof Keyed)) return;
            p.discoverRecipe(((Keyed) recipe).getKey());
        }
    }

    public static void registerAll() {
        for (BannerPatternRecipe bannerPatternRecipe : values()) {
            bannerPatternRecipe.register();
        }
    }

    private void register() {
        ShapelessRecipe recipe = new ShapelessRecipe(key, new ItemStack(bannerPattern));
        recipe.addIngredient(PAPER);
        recipe.addIngredient(ingredient);
        Main.getPlugin().getServer().addRecipe(recipe);
    }

    public Material getBannerPattern() {
        return bannerPattern;
    }

    public Material getIngredient() {
        return ingredient;
    }
}
