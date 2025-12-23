package eu.judevving.empire.command;

import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.GlobalFinals;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConvertCoordinatesCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length != 2) {
            sender.sendMessage(Text.COMMAND_USAGE_CONVERTCOORDINATES.get(sender));
            return false;
        }
        double northDeg, eastDeg;
        try {
            northDeg = Double.parseDouble(args[0]);
            eastDeg = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Text.COMMAND_USAGE_CONVERTCOORDINATES.get(sender));
            return false;
        }
        Location result = GlobalFinals.coordinatesToLocation(northDeg, eastDeg);
        if (result == null) {
            sender.sendMessage(Text.COMMAND_USAGE_CONVERTCOORDINATES.get(sender));
            return false;
        }
        sender.sendMessage(Text.COMMAND_CONVERTCOORDINATES_RESULT.get(sender,
                northDeg + "", eastDeg + "", result.getBlockX() + "", result.getBlockZ() + ""));
        return true;
    }

}
