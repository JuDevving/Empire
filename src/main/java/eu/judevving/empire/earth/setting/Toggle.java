package eu.judevving.empire.earth.setting;

import eu.judevving.empire.language.Text;
import org.bukkit.Material;

public enum Toggle {

    FARMLAND_CAN_BE_TRAMPLED(2 * 9 + 6, Material.FARMLAND, Text.MENU_NAME_SETTINGS_TOGGLE_FARMLAND_CAN_BE_TRAMPLED, true, 23),
    FIRE_SPREADS(5, Material.BLAZE_POWDER, Text.MENU_NAME_SETTINGS_TOGGLE_FIRE_SPREAD, false),
    FRIENDLY_FIRE(2, Material.IRON_SWORD, Text.MENU_NAME_SETTINGS_TOGGLE_FRIENDLY_FIRE, false),
    MONSTERS_SPAWN(2 * 9 + 7, Material.CREEPER_HEAD, Text.MENU_NAME_SETTINGS_TOGGLE_MONSTERS_SPAWN, true, 50),
    NATURAL_LIGHTNING_STRIKES(2 * 9 + 1, Material.LIGHTNING_ROD, Text.MENU_NAME_SETTINGS_TOGGLE_NATURAL_LIGHTNING, false),
    OWNERLESS_TNT_EXPLODES(6, Material.TNT, Text.MENU_NAME_SETTINGS_TOGGLE_OWNERLESS_TNT, false),
    PUBLIC_BOATS(4, Material.OAK_BOAT, Text.MENU_NAME_SETTINGS_TOGGLE_PUBLIC_BOATS, false),
    PUBLIC_MINECARTS(3, Material.MINECART, Text.MENU_NAME_SETTINGS_TOGGLE_PUBLIC_MINECARTS, false),
    SNOW_GOLEMS_CREATE_SNOW(2 * 9 + 2, Material.CARVED_PUMPKIN, Text.MENU_NAME_SETTINGS_TOGGLE_SNOW_GOLEM_SNOW, false),
    VILLAGERS_CAN_HARVEST_CROPS(2 * 9 + 4, Material.WHEAT, Text.MENU_NAME_SETTINGS_TOGGLE_VILLAGER_CROPS, false),
    VILLAGERS_CAN_OPEN_DOORS(2 * 9 + 3, Material.OAK_DOOR, Text.MENU_NAME_SETTINGS_TOGGLE_VILLAGER_DOORS, false);

    private final int slot;
    private final Material material;
    private final Text itemName;
    private final String key;
    private final boolean def;
    private final int minLevel;

    Toggle(int slot, Material material, Text itemName, boolean defaultValue, int minLevel) {
        this.slot = slot;
        this.material = material;
        this.itemName = itemName;
        this.key = "toggle_" + name();
        this.minLevel = minLevel;
        this.def = defaultValue;
    }

    Toggle(int slot, Material material, Text itemName, boolean defaultValue) {
        this(slot, material, itemName, defaultValue, 0);
    }

    public boolean getDefaultValue() {
        return def;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public String getKey() {
        return key;
    }

    public int getSlot() {
        return slot;
    }

    public Material getMaterial() {
        return material;
    }

    public Text getItemName() {
        return itemName;
    }
}
