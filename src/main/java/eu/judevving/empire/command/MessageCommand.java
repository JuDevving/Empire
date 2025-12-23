package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MessageCommand implements CommandExecutor {

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
        if (args.length < 2) {
            sender.sendMessage(Text.COMMAND_USAGE_MESSAGE.get(sender));
            return false;
        }
        State target = Main.getPlugin().getEarth().getState(args[0]);
        if (target == null) {
            sender.sendMessage(Text.COMMAND_STATE_NOT_FOUND.get(sender, args[0]));
            return false;
        }
        if (!target.isOnline()) {
            sender.sendMessage(Text.COMMAND_MESSAGE_NONE_ONLINE.get(sender, target.getColoredNameAndId()));
            return false;
        }
        /*if (target.equals(h.getState())) {
            sender.sendMessage(Text.COMMAND_MESSAGE_SELF.get(sender));
            return false;
        }*/
        String message = StateCommand.combineArgs(args, 1);
        sendStateMessage(h.getState(), target, message);
        return true;
    }

    public static void sendStateMessage(State from, State to, String message) {
        from.sendMessage(Text.STATE_MESSAGE, from.getColoredTag(), to.getColoredTagAndId(), message);
        to.sendMessage(Text.STATE_MESSAGE, from.getColoredTagAndId(), to.getColoredTag(), message);
        to.setLastSender(from);
    }

}
