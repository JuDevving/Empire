package eu.judevving.empire.command.tabcompleter;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.map.marker.MarkerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class MarkerTabCompleter implements TabCompleter {

    private List<String> list;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) return getList();
        if (args.length == 2) {
            if (!(sender instanceof Player p)) return null;
            State state = Main.getPlugin().getEarth().getState(p);
            if (state == null) return new LinkedList<>();
            return state.getMarkerManager().getMarkerArgs();
        }
        if (args.length == 3) {
            if (!args[0].equalsIgnoreCase("icon")) return new LinkedList<>();
            return MarkerType.getNames();
        }
        return new LinkedList<>();
    }

    private List<String> getList() {
        if (list == null) {
            list = new LinkedList<>();
            list.addLast("description");
            list.addLast("icon");
            list.addLast("info");
            list.addLast("name");
            list.addLast("place");
            list.addLast("remove");
        }
        return list;
    }

}
