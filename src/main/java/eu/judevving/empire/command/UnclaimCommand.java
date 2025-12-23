package eu.judevving.empire.command;

import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.storage.AutoClaimState;
import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.earth.storage.ClaimResult;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnclaimCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get());
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        if (h.getState() == null) {
            sender.sendMessage(Text.COMMAND_NO_STATE.get(h));
            return false;
        }
        if (args.length == 0) {
            tryUnclaim(h);
            return true;
        }
        args[0] = args[0].toLowerCase();
        switch (args[0]) {
            case "all":
                if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                    if (!h.getState().hasTerritory()) {
                        sender.sendMessage(Text.COMMAND_NO_TERRITORY.get(h));
                        return false;
                    }
                    Main.getPlugin().getEarth().unclaimAll(h.getState());
                    h.getState().sendMessage(Text.COMMAND_UNCLAIM_ALL_SUCCESS);
                    return true;
                }
                sender.sendMessage(Text.COMMAND_USAGE_UNCLAIM_ALL.get(h));
                return false;
            case "auto":
                if (args.length != 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_UNCLAIM_AUTO.get(h));
                    return false;
                }
                if (h.getAutoClaimState() == AutoClaimState.ON_UNCLAIM) {
                    h.setAutoClaimState(AutoClaimState.OFF);
                    sender.sendMessage(Text.COMMAND_UNCLAIM_AUTO_TURNED_OFF.get(h));
                    return true;
                }
                h.setAutoClaimState(AutoClaimState.ON_UNCLAIM);
                sender.sendMessage(Text.COMMAND_UNCLAIM_AUTO_TURNED_ON.get(h));
                return true;
            default:
                sender.sendMessage(Text.COMMAND_USAGE_UNCLAIM.get(h));
                return false;
        }
    }

    private void tryUnclaim(Human h) {
        Square square = Square.fromLocation(h.getLocation());
        State owner = Main.getPlugin().getEarth().getState(square);
        ClaimResult claimResult = Main.getPlugin().getEarth().tryUnclaim(h.getState(), square, h.getWorld());
        h.sendMessage(ClaimResult.getMessage(h, square, owner, claimResult));
    }

}
