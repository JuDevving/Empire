package eu.judevving.empire.listener;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.quest.QuestCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Main.getPlugin().getEarth().progressQuests(e.getPlayer(), QuestCategory.MINE, e.getBlock().getType());
    }

}
