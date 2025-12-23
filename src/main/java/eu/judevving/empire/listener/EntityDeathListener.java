package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.inventory.CustomHead;
import eu.judevving.empire.quest.QuestCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.WARDEN) {
            e.getDrops().add(CustomHead.MOB_WARDEN.get());
            return;
        }
        if (e.getEntityType() == EntityType.WITHER) {
            e.getDrops().add(CustomHead.MOB_WITHER.get());
            return;
        }
        if (Main.getPlugin().getEarth().isWorld(e.getEntity().getWorld())) {
            e.getEntity().removePotionEffect(PotionEffectType.WEAVING);
        }
        if (e.getEntity().getKiller() != null) {
            Main.getPlugin().getEarth().progressQuests(e.getEntity().getKiller(), QuestCategory.KILL, e.getEntityType());
        }
    }

}
