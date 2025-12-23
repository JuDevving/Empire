package eu.judevving.empire.command.tabcompleter;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;

public class StateTabCompleter implements TabCompleter {

    private List<String> arg0List;
    private List<String> welcomeList;

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) return getArg0List();
        if (args.length > 2) return new LinkedList<>();
        args[0] = args[0].toLowerCase();
        switch (args[0]) {
            case "accept":
                return Main.getPlugin().getEarth().getStateIds(false);
            case "color":
                return StateColor.getColorNames();
            case "invite":
                return Main.getPlugin().getEarth().getOnlinePlayerNames(true);
            case "welcome":
            case "enemywelcome":
                return getWelcomeList();
            default:
                return new LinkedList<>();
        }
    }

    private List<String> getWelcomeList() {
        if (welcomeList == null) {
            welcomeList = new LinkedList<>();
            welcomeList.addLast(GlobalFinals.COMMAND_STATE_TEXT_FOR_EMPTY);
        }
        return welcomeList;
    }

    private List<String> getArg0List() {
        if (arg0List == null) {
            arg0List = new LinkedList<>();
            arg0List.addLast("accept");
            arg0List.addLast("capitalname");
            arg0List.addLast("color");
            arg0List.addLast("create");
            arg0List.addLast("description");
            arg0List.addLast("enemywelcome");
            arg0List.addLast("flag");
            arg0List.addLast("invite");
            arg0List.addLast("leave");
            arg0List.addLast("name");
            arg0List.addLast("tag");
            arg0List.addLast("welcome");
        }
        return arg0List;
    }

}
