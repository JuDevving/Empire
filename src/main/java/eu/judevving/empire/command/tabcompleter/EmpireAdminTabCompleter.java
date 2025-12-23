package eu.judevving.empire.command.tabcompleter;

import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class EmpireAdminTabCompleter implements TabCompleter {

    private List<String> list;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!sender.hasPermission(GlobalFinals.PERMISSION_PREFIX + "command.empireadmin")) {
            return new LinkedList<>();
        }
        if (args.length == 1) return getList();
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("donater")) {
                return Main.getPlugin().getEarth().getOnlinePlayerNames(false);
            } else {
                return Main.getPlugin().getEarth().getStateIds(true);
            }
        }
        return null;
    }

    private List<String> getList() {
        if (list == null) {
            list = new LinkedList<>();
            list.addLast("backup");
            list.addLast("capital");
            list.addLast("claim");
            list.addLast("defaultstrings");
            list.addLast("delete");
            list.addLast("donater");
            list.addLast("level");
            list.addLast("newquests");
            list.addLast("poi");
            list.addLast("power");
            list.addLast("railwayend");
            list.addLast("railwayinfo");
            list.addLast("railwaypoint");
            list.addLast("railwayremove");
            list.addLast("railwaystart");
            list.addLast("removemember");
            list.addLast("removepoi");
            list.addLast("shrink");
            list.addLast("spawncrate");
            list.addLast("unclaim");
        }
        return list;
    }

}
