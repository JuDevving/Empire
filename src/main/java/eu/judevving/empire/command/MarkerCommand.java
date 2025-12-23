package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.map.marker.Marker;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.command.storage.FormatTests;
import eu.judevving.empire.map.marker.MarkerType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MarkerCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Text.COMMAND_FOR_PLAYERS.get());
            return false;
        }
        Human h = Main.getPlugin().getEarth().getHuman((Player) sender);
        if (h.getState() == null) {
            sender.sendMessage(Text.COMMAND_NO_STATE.get(sender));
            return false;
        }
        if (h.getState().getStateLevel().getMarkers() == 0) {
            sender.sendMessage(Text.COMMAND_MARKER_NONE_AVAILABLE.get(sender));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(Text.COMMAND_USAGE_MARKER.get(sender));
            return false;
        }
        args[0] = args[0].toLowerCase();
        args[1] = args[1].toLowerCase();
        int id = Integer.MAX_VALUE;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException ignored) {
        }
        if (id < 1 || id > h.getState().getStateLevel().getMarkers()) {
            sender.sendMessage(Text.COMMAND_USAGE_MARKER_MARKER_NOT_FOUND.get(sender, getAvailableMarkers(h.getState(), false)));
            return false;
        }
        Marker marker = h.getState().getMarkerManager().getMarker(id);
        if (!args[0].equals("place") && marker == null) {
            sender.sendMessage(Text.COMMAND_MARKER_NOT_PLACED.get(sender, args[1]));
            return false;
        }
        switch (args[0]) {
            case "description":
                if (args.length < 3) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_DESCRIPTION.get(sender));
                    return false;
                }
                String description = StateCommand.combineArgs(args, 2);
                if (!FormatTests.legalStateDescription(description)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_DESCRIPTION_MIN_LENGTH + "", FormatTests.STATE_DESCRIPTION_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_DESCRIPTION));
                    return false;
                }
                marker.setDescription(description);
                sender.sendMessage(Text.COMMAND_MARKER_DESCRIPTION_SET.get(sender, args[1], description));
                return true;
            case "icon":
                if (args.length != 3) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_ICON.get(sender, getUsageIconList()));
                    return false;
                }
                MarkerType markerType = MarkerType.fromName(args[2]);
                if (markerType == null) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_ICON.get(sender, getUsageIconList()));
                    return false;
                }
                marker.setMarkerIcon(markerType);
                sender.sendMessage(Text.COMMAND_MARKER_ICON_SET.get(sender, marker.getId() + "", markerType.getName()));
                return true;
            case "info":
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_INFO.get(sender));
                    return false;
                }
                sender.sendMessage(Text.COMMAND_MARKER_INFO_HEADER.get(sender, args[1]));
                sender.sendMessage(Text.COMMAND_MARKER_INFO_NAME.get(sender, marker.getName()));
                sender.sendMessage(Text.COMMAND_MARKER_INFO_ICON.get(sender, marker.getMarkerIcon().getName()));
                sender.sendMessage(Text.COMMAND_MARKER_INFO_POSITION.get(sender, marker.getLocationString()));
                return true;
            case "name":
                if (args.length < 3) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_NAME.get(sender));
                    return false;
                }
                String name = StateCommand.combineArgs(args, 2);
                if (!FormatTests.legalStateName(name)) {
                    sender.sendMessage(Text.INFO_FORMAT_ILLEGAL.get(h));
                    sender.sendMessage(Text.INFO_FORMAT_LENGTH.get(sender, FormatTests.STATE_NAME_MIN_LENGTH + "", FormatTests.STATE_NAME_MAX_LENGTH + ""));
                    sender.sendMessage(Text.INFO_FORMAT_LEGAL_CHARACTERS.get(sender, FormatTests.LEGAL_CHARACTERS_NAME));
                    return false;
                }
                marker.setName(name);
                sender.sendMessage(Text.COMMAND_MARKER_NAME_SET.get(sender, args[1], name));
                return true;
            case "place":
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_PLACE.get(sender));
                    return false;
                }
                State state = Main.getPlugin().getEarth().getState(h.getLocation());
                if (!h.getState().equals(state)) {
                    sender.sendMessage(Text.COMMAND_MARKER_PLACE_OUTSIDE_TERRITORY.get(sender));
                    return false;
                }
                Square square = Square.fromLocation(h.getLocation());
                if (state.getCapital() != null) {
                    if (state.getCapital().equals(square)) {
                        h.sendMessage(Text.COMMAND_MARKER_PLACE_CAPITAL);
                        return false;
                    }
                }
                Marker marker1 = state.getMarkerManager().getMarker(square);
                if (marker1 != null) {
                    h.sendMessage(Text.COMMAND_MARKER_PLACE_OCCUPIED, marker1.getId() + "");
                    return false;
                }
                Marker originalMarker = h.getState().getMarkerManager().getMarker(id);
                if (originalMarker != null) marker = originalMarker;
                if (marker == null) {
                    marker = new Marker(id, square);
                    h.getState().getMarkerManager().putMarker(marker);
                } else marker.setSquare(square);
                sender.sendMessage(Text.COMMAND_MARKER_PLACE_PLACED.get(sender, args[1], marker.getLocationString()));
                return true;
            case "remove":
                if (args.length != 2) {
                    sender.sendMessage(Text.COMMAND_USAGE_MARKER_REMOVE.get(sender));
                    return false;
                }
                h.getState().getMarkerManager().removeMarker(marker.getId());
                sender.sendMessage(Text.COMMAND_MARKER_REMOVE_REMOVED.get(sender, args[1]));
                return true;
            default:
                sender.sendMessage(Text.COMMAND_USAGE_MARKER.get(sender));
                return false;
        }
    }

    private static String getUsageIconList() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (MarkerType markerType : MarkerType.values()) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append('/');
            }
            stringBuilder.append(markerType.getName());
        }
        return stringBuilder.toString();
    }

    private static String getAvailableMarkers(State state, boolean slash) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (String string : state.getMarkerManager().getMarkerArgs()) {
            if (first) {
                first = false;
            } else {
                if (slash) {
                    stringBuilder.append('/');
                } else stringBuilder.append(", ");
            }
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

}
