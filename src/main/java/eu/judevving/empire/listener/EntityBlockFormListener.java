package eu.judevving.empire.listener;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.setting.Toggle;
import eu.judevving.empire.main.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockFormListener implements Listener {

    @EventHandler
    public void onForm(EntityBlockFormEvent e) {
        if (!Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) return;
        if (e.getEntity().getType() != EntityType.SNOW_GOLEM) return;
        State state = Main.getPlugin().getEarth().getState(e.getBlock().getLocation());
        if (state == null) return;
        if (state.getSettingsManager().getToggle(Toggle.SNOW_GOLEMS_CREATE_SNOW)) return;
        e.setCancelled(true);
    }

}
