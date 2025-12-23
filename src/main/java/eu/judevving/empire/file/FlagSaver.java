package eu.judevving.empire.file;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class FlagSaver {

    private HashMap<String, ItemStack> flags;
    private File file;
    private YamlConfiguration cfg;

    public FlagSaver() {
        file = new File(GlobalFinals.FILES_FILE_FLAGS);
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        flags = new HashMap<>();
        Set<String> keys = cfg.getKeys(false);
        for (String key : keys) {
            flags.put(key, cfg.getItemStack(key));
        }
    }

    public void save(Collection<State> states) {
        try {
            for (State state : states) {
                cfg.set(state.getStateId(), state.getFlag());
            }
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ItemStack getFlag(String stateId) {
        ItemStack flag = flags.get(stateId);
        if (flag == null) return GlobalFinals.STATE_DEFAULT_FLAG;
        return flag;
    }

}
