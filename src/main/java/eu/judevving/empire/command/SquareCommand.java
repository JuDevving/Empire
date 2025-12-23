package eu.judevving.empire.command;

import eu.judevving.empire.clock.Clock;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SquareCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player) && args.length != 2) {
            sender.sendMessage(Text.COMMAND_USAGE_SQUARE.get(sender));
            return false;
        }
        Square square;
        if (args.length > 0) {
            if (args.length != 2) {
                sender.sendMessage(Text.COMMAND_USAGE_SQUARE.get(sender));
                return false;
            }
            int x,z;
            try {
                x = Integer.parseInt(args[0]);
                z = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(Text.COMMAND_USAGE_SQUARE.get(sender));
                return false;
            }
            square = Square.fromLocation(x,z);
        } else {
            square = Square.fromLocation(((Player) sender).getLocation());
        }
        if (square.isOutOfBounds()) {
            sender.sendMessage(Text.COMMAND_SQUARE_OUT_OF_BOUNDS.get(sender));
            return false;
        }
        if (sender instanceof Player) {
            if (!Main.getPlugin().getEarth().isWorld(((Player) sender).getWorld())) {
                sender.sendMessage(Text.COMMAND_SQUARE_OUT_OF_BOUNDS.get(sender));
                return false;
            }
        }
        State state = Main.getPlugin().getEarth().getState(square);
        String stateName;
        if (state != null) {
            stateName = state.getColoredNameAndId();
        } else stateName = Text.STATE_NONE.get(sender);
        sender.sendMessage(Text.COMMAND_SQUARE_INFO_HEADER.get(sender, square.toString()));
        if (state != null)
            sender.sendMessage(Text.COMMAND_SQUARE_INFO_CLAIM_TIME.get(sender,
                    Clock.millisToString(Main.getPlugin().getEarth().getClaimTimeManager().get(square))));
        if (state != null)
            sender.sendMessage(Text.COMMAND_SQUARE_INFO_TAKEOVER_POSSIBLE.get(sender,
                    Main.getPlugin().getEarth().isTakeableByAnyone(state, square) ?
                    Text.WORD_YES.get(sender) : Text.WORD_NO.get(sender)));
        sender.sendMessage(Text.COMMAND_SQUARE_INFO_POSITION.get(sender, square.getCenterBlockString()));
        sender.sendMessage(Text.COMMAND_SQUARE_INFO_STATE.get(sender, stateName));
        if (state != null)
            sender.sendMessage(Text.COMMAND_SQUARE_INFO_CAPITAL.get(sender, booleanToYesNo(square.equals(state.getCapital())).get(sender)));
        if (state == null)
            sender.sendMessage(Text.COMMAND_SQUARE_INFO_UNCLAIMABLE.get(sender, booleanToYesNo(Main.getPlugin().getEarth().isUnclaimable(square)).get(sender)));
        return true;
    }

    private static Text booleanToYesNo(boolean b) {
        return b ? Text.WORD_YES : Text.WORD_NO;
    }

}
