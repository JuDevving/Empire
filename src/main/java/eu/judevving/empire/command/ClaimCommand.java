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

public class ClaimCommand implements CommandExecutor {

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
            tryClaim(h, false);
            return true;
        }
        args[0] = args[0].toLowerCase();
        switch (args[0]) {
            case "auto":
                if (args.length != 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_CLAIM_AUTO.get(h));
                    return false;
                }
                if (h.getAutoClaimState() == AutoClaimState.ON_CLAIM) {
                    h.setAutoClaimState(AutoClaimState.OFF);
                    sender.sendMessage(Text.COMMAND_CLAIM_AUTO_TURNED_OFF.get(h));
                    return true;
                }
                h.setAutoClaimState(AutoClaimState.ON_CLAIM);
                sender.sendMessage(Text.COMMAND_CLAIM_AUTO_TURNED_ON.get(h));
                return true;
            case "capital":
                if (args.length != 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_CLAIM_CAPITAL.get(h));
                    return false;
                }
                tryClaim(h, true);
                return true;
            default:
                sender.sendMessage(Text.COMMAND_USAGE_CLAIM.get(h));
                return false;
        }
    }

    private void tryClaim(Human h, boolean capital) {
        Square square = Square.fromLocation(h.getLocation());
        State owner = Main.getPlugin().getEarth().getState(square);
        ClaimResult claimResult = Main.getPlugin().getEarth().tryClaim(h.getState(), square, h.getWorld(), capital);
        h.sendMessage(ClaimResult.getMessage(h,square,owner,claimResult));
        if (claimResult != ClaimResult.NO_CAPITAL) return;
        h.sendMessage(Text.INFO_CAPITAL_DESCRIPTION);
    }

}
