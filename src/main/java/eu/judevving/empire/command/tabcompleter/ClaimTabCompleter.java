package eu.judevving.empire.command.tabcompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class ClaimTabCompleter implements TabCompleter {

    private List<String> list;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length > 1) return new LinkedList<>();
        return getList();
    }

    private List<String> getList() {
        if (list == null) {
            list = new LinkedList<>();
            list.addLast("auto");
            list.addLast("capital");
        }
        return list;
    }

}
