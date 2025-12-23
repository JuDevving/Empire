package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplyCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get(sender));
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        if (h.getState() == null) {
            sender.sendMessage(Text.COMMAND_NO_STATE.get(sender));
            return false;
        }
        if (h.getState().getLastSender() == null) {
            sender.sendMessage(Text.COMMAND_REPLY_NO_LAST_SENDER.get(sender));
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage(Text.COMMAND_USAGE_REPLY.get(sender));
            return false;
        }
        if (!h.getState().getLastSender().isOnline()) {
            sender.sendMessage(Text.COMMAND_MESSAGE_NONE_ONLINE.get(sender, h.getState().getLastSender().getColoredNameAndId()));
            return false;
        }
        String message = StateCommand.combineArgs(args, 0);
        MessageCommand.sendStateMessage(h.getState(), h.getState().getLastSender(), message);
        return true;
    }

}
