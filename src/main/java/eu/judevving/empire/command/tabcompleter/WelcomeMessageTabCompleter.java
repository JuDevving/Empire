package eu.judevving.empire.command.tabcompleter;

import eu.judevving.empire.earth.storage.WelcomeMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class WelcomeMessageTabCompleter implements TabCompleter {

    private List<String> list;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length > 1) return new LinkedList<>();
        return getList();
    }

    private List<String> getList() {
        if (list == null) {
            list = new LinkedList<>();
            for (WelcomeMessage welcomeMessage : WelcomeMessage.values()) {
                list.addLast(welcomeMessage.getName());
            }
        }
        return list;
    }

}
