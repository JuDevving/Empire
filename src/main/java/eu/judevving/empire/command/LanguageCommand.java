package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LanguageCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get());
            return false;
        }
        if (args.length == 0) {
            Human h = Main.getPlugin().getEarth().getHuman(p);
            h.openLanguageMenu();
            return true;
        }
        Language language = Main.getPlugin().getLanguages().fromStringIgnoreCase(args[0]);
        if (language == null) {
            p.sendMessage(Text.COMMAND_USAGE_LANGUAGE.get(p, getLanguageList()));
            return false;
        }
        Main.getPlugin().getEarth().getHuman(p).setLanguage(language);
        p.sendMessage(Text.COMMAND_LANGUAGE_SET.get(p, Text.LANGUAGE_NAME_NATIVE.get(p)));
        return true;
    }

    private String getLanguageList() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < Main.getPlugin().getLanguages().getLength(); i++) {
            if (i > 0) stringBuilder.append("/");
            stringBuilder.append(Main.getPlugin().getLanguages().get(i).getName());
        }
        return stringBuilder.toString();
    }
}
