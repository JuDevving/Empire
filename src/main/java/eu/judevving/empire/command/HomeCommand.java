package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HomeCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get());
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        if (h.getState() == null) {
            sender.sendMessage(Text.COMMAND_NO_PERMISSION.get(h));
            return false;
        }
        if (h.getState().getLevel() < 55) {
            sender.sendMessage(Text.COMMAND_NO_PERMISSION.get(h));
            return false;
        }
        if (args.length != 0) {
            sender.sendMessage(Text.COMMAND_USAGE_HOME.get(h));
            return false;
        }
        h.startHomeTeleport();
        return true;
    }

}
