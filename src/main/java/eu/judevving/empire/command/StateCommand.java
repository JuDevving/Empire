package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.command.storage.FormatTests;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

public class StateCommand implements CommandExecutor {

    private String colorList;

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get());
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        if (args.length == 0) {
            if (sendIfNoState(h)) return false;
            h.openMainMenu();
            return true;
        }
        args[0] = args[0].toLowerCase();
        State state = h.getState();
        String name;
        switch (args[0]) {
            case "accept":
                if (state != null) {
                    sender.sendMessage(Text.COMMAND_STATE_CREATE_ALREADY_IN_STATE.get(h, state.getColoredNameAndId()));
                    return false;
                }
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_ACCEPT.get(sender));
                    return false;
                }
                State targetState = Main.getPlugin().getEarth().getState(args[1]);
                if (targetState == null) {
                    sender.sendMessage(Text.COMMAND_STATE_NOT_FOUND.get(sender, args[1]));
                    return false;
                }
                if (targetState.hasMaxMembers()) {
                    sender.sendMessage(Text.COMMAND_STATE_INVITE_MAX_MEMBERS.get(sender, targetState.getColoredName()));
                    return false;
                }
                if (!targetState.isInvited(h)) {
                    sender.sendMessage(Text.COMMAND_STATE_ACCEPT_NOT_INVITED.get(sender, targetState.getColoredNameAndId()));
                    return false;
                }
                targetState.addMember(h);
                Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_STATE_JOINED, h.getName(), targetState.getColoredNameAndId());
                return true;
            case "capitalname":
                if (sendIfNoState(h)) return false;
                if (args.length < 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_CAPITAL_NAME.get(sender));
                    return false;
                }
                name = combineArgs(args, 1);
                if (!FormatTests.legalStateName(name)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_NAME_MIN_LENGTH + "", FormatTests.STATE_NAME_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_NAME));
                    return false;
                }
                state.setCapitalName(name);
                sender.sendMessage(Text.COMMAND_STATE_CAPITAL_NAME_SET.get(sender, name));
                return true;
            case "color":
                if (sendIfNoState(h)) return false;
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_COLOR.get(h, getColorList()));
                    return false;
                }
                args[1] = args[1].toLowerCase();
                StateColor stateColor = StateColor.fromName(args[1]);
                if (stateColor == null) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_COLOR.get(h, getColorList()));
                    return false;
                }
                h.getState().setStateColor(stateColor);
                sender.sendMessage(Text.COMMAND_STATE_COLOR_SET.get(h, stateColor.getColoredText(h.getLanguage())));
                return true;
            case "create":
                if (state != null) {
                    sender.sendMessage(Text.COMMAND_STATE_CREATE_ALREADY_IN_STATE.get(h, state.getColoredNameAndId()));
                    return false;
                }
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_CREATE.get(h));
                    sender.sendMessage(Text.INFO_STATE_ID_DESCRIPTION.get(h));
                    return false;
                }
                if (!FormatTests.validStateId(args[1])) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_ID_MIN_LENGTH + "", FormatTests.STATE_ID_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_STATE_ID));
                    return false;
                }
                state = Main.getPlugin().getEarth().getState(args[1]);
                if (state != null) {
                    sender.sendMessage(Text.COMMAND_STATE_CREATE_ID_ALREADY_IN_USE.get(h, args[1], state.getColoredName()));
                    return false;
                }
                state = Main.getPlugin().getEarth().addState(args[1]);
                state.addMember(h);
                Main.getPlugin().getEarth().sendMessage(Text.BROADCAST_NEW_STATE, h.getName());
                return true;
            case "description":
                if (sendIfNoState(h)) return false;
                if (args.length == 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_DESCRIPTION.get(sender));
                    return false;
                }
                String description = combineArgs(args, 1);
                if (!FormatTests.legalStateDescription(description)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_DESCRIPTION_MIN_LENGTH + "", FormatTests.STATE_DESCRIPTION_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_DESCRIPTION));
                    return false;
                }
                state.setDescription(description);
                sender.sendMessage(Text.COMMAND_STATE_DESCRIPTION_SET.get(h, description));
                return true;
            case "flag":
                if (sendIfNoState(h)) return false;
                if (args.length != 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_FLAG.get(h));
                    return false;
                }
                if (!isBanner(h.getPlayer().getInventory().getItemInMainHand())) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_FLAG_NO_BANNER.get(h));
                    return false;
                }
                state.setFlag(h.getPlayer().getInventory().getItemInMainHand().clone());
                sender.sendMessage(Text.COMMAND_STATE_FLAG_SET.get(h));
                return true;
            case "invite":
                if (sendIfNoState(h)) return false;
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_INVITE.get(sender));
                    return false;
                }
                Human target = Main.getPlugin().getEarth().getHuman(args[1]);
                if (state.hasMaxMembers()) {
                    sender.sendMessage(Text.COMMAND_STATE_INVITE_MAX_MEMBERS.get(sender, state.getColoredName()));
                    return false;
                }
                if (target == null) {
                    sender.sendMessage(Text.COMMAND_PLAYER_NOT_FOUND.get(sender, args[1]));
                    return false;
                }
                if (target.getState() != null) {
                    sender.sendMessage(Text.COMMAND_STATE_INVITE_ALREADY_IN_STATE.get(sender, target.getName(), target.getState().getColoredNameAndId()));
                    return false;
                }
                if (state.isInvited(target)) {
                    sender.sendMessage(Text.COMMAND_STATE_INVITE_ALREADY_INVITED.get(sender, target.getName()));
                    return false;
                }
                state.invite(target);
                sender.sendMessage(Text.COMMAND_STATE_INVITE_SENT.get(sender, target.getName()));
                target.sendMessage(Text.COMMAND_STATE_INVITE_GOTTEN.get(target, state.getColoredNameAndId(), state.getStateId()));
                return true;
            case "leave":
                if (sendIfNoState(h)) return false;
                if (args.length == 2 && args[1].equalsIgnoreCase("confirm")) {
                    state.leave(h);
                    return true;
                }
                if (state.getMemberCount() == 1) {
                    sender.sendMessage(Text.INFO_LEAVE_STATE_LAST.get(sender));
                } else sender.sendMessage(Text.INFO_LEAVE_STATE_LOSS.get(sender));
                sender.sendMessage(Text.COMMAND_USAGE_STATE_LEAVE.get(sender));
                return false;
            case "name":
                if (sendIfNoState(h)) return false;
                if (args.length == 1) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_NAME.get(sender));
                    return false;
                }
                name = combineArgs(args, 1);
                if (!FormatTests.legalStateName(name)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_NAME_MIN_LENGTH + "", FormatTests.STATE_NAME_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_NAME));
                    return false;
                }
                state.setName(name);
                sender.sendMessage(Text.COMMAND_STATE_NAME_SET.get(h, state.getColoredName()));
                return true;
            case "tag":
                if (sendIfNoState(h)) return false;
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_STATE_TAG.get(sender));
                    return false;
                }
                if (!FormatTests.legalStateTag(args[1])) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_TAG_MIN_LENGTH + "", FormatTests.STATE_TAG_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_TAG));
                    return false;
                }
                state.setTag(args[1]);
                sender.sendMessage(Text.COMMAND_STATE_TAG_SET.get(h, state.getColoredTag()));
                return true;
            case "welcome":
            case "enemywelcome":
                if (sendIfNoState(h)) return false;
                if (args.length == 1) {
                    Text welcomeUsage;
                    if (args[0].equals("welcome")) {
                        welcomeUsage = Text.COMMAND_USAGE_STATE_WELCOME;
                    } else welcomeUsage = Text.COMMAND_USAGE_STATE_ENEMY_WELCOME;
                    sender.sendMessage(welcomeUsage.get(sender));
                    return false;
                }
                String welcome = combineArgs(args, 1);
                if (welcome.equalsIgnoreCase(GlobalFinals.COMMAND_STATE_TEXT_FOR_EMPTY)) welcome = "";
                if (!FormatTests.legalStateWelcome(welcome)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_WELCOME_MIN_LENGTH + "", FormatTests.STATE_WELCOME_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_WELCOME));
                    return false;
                }
                if (args[0].equals("welcome")) {
                    state.setWelcome(welcome);
                    sender.sendMessage(Text.COMMAND_STATE_WELCOME_SET.get(h, welcome));
                } else {
                    state.setEnemyWelcome(welcome);
                    sender.sendMessage(Text.COMMAND_STATE_ENEMY_WELCOME_SET.get(h, welcome));
                }
                return true;
            default:
                sender.sendMessage(Text.COMMAND_USAGE_STATE.get(h));
                return false;
        }
    }

    private boolean isBanner(ItemStack itemStack) {
        if (itemStack == null) return false;
        return (itemStack.getItemMeta() instanceof BannerMeta);
    }

    private boolean sendIfNoState(Human h) {
        if (h.getState() != null) return false;
        h.sendMessage(Text.COMMAND_NO_STATE.get(h));
        h.sendMessage(Text.INFO_JOIN_STATE.get(h));
        return true;
    }

    private String getColorList() {
        if (colorList == null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (StateColor sc : StateColor.values()) {
                stringBuilder.append('/');
                stringBuilder.append(sc.getColoredName());
                stringBuilder.append("§7");
            }
            colorList = stringBuilder.substring(1);
        }
        return colorList;
    }

    public static String combineArgs(String[] args, int first) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = first; i < args.length - 1; i++) {
            stringBuilder.append(args[i]).append(' ');
        }
        stringBuilder.append(args[args.length - 1]);
        return stringBuilder.toString();
    }

}
