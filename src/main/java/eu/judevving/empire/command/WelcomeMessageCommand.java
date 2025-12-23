package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.storage.WelcomeMessage;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WelcomeMessageCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get(sender));
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(Text.COMMAND_USAGE_WELCOMEMESSAGE.get(sender));
            return false;
        }
        WelcomeMessage welcomeMessage = WelcomeMessage.fromNameIgnoreCase(args[0]);
        if (welcomeMessage == null) {
            sender.sendMessage(Text.COMMAND_USAGE_WELCOMEMESSAGE.get(sender));
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        h.setWelcomeMessage(welcomeMessage);
        sender.sendMessage(Text.COMMAND_WELCOMEMESSAGE_SET.get(sender, welcomeMessage.getName()));
        return true;
    }
}
