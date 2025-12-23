package eu.judevving.empire.command;

import eu.judevving.empire.earth.Human;
import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.earth.railway.Railway;
import eu.judevving.empire.file.BackupCreator;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateLevel;
import net.pl3x.map.core.markers.Point;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmpireAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!sender.hasPermission(GlobalFinals.PERMISSION_PREFIX + "command.empireadmin")) {
            sender.sendMessage(Text.COMMAND_NO_PERMISSION.get(sender));
            return false;
        }
        if (args.length == 0) return false;
        args[0] = args[0].toLowerCase();
        State state = null;
        if (args.length >= 2) state = Main.getPlugin().getEarth().getState(args[1]);
        Player p = null;
        if (sender instanceof Player) p = (Player) sender;
        int x, z;
        switch (args[0]) {
            case "backup":
                if (!BackupCreator.createBackup(true)) {
                    sender.sendMessage(Text.COMMAND_EMPIREADMIN_BACKUP_FAILED.get(sender));
                    return false;
                }
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_BACKUP_CREATED.get(sender));
                return true;
            case "capital":
                if (state == null) return false;
                if (p == null) return false;
                Square square = Square.fromLocation(p.getLocation());
                if (!state.isTerritory(square)) return false;
                state.setCapital(square);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_CAPITAL_SET.get(sender));
                return true;
            case "claim":
                if (p == null) return false;
                if (!Main.getPlugin().getEarth().forceClaim(Square.fromLocation(p.getLocation()), args[1]))
                    return false;
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_CLAIM_CLAIMED.get(sender));
                return true;
            case "defaultstrings":
                if (state == null) return false;
                state.setDefaultStrings();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_DEFAULT_STRINGS_SET.get(sender));
                return true;
            case "delete":
                if (state == null) return false;
                state.delete();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_DELETE_DELETED.get(sender));
                return true;
            case "donater":
                if (args.length != 2) return false;
                Human target = Main.getPlugin().getEarth().getHuman(args[1]);
                if (target == null) return false;
                target.setDonater(!target.isDonater(), true);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_DONATER_SET.get(sender, target.isDonater() + ""));
                return true;
            case "level":
                if (args.length < 3) return false;
                if (state == null) return false;
                StateLevel stateLevel;
                try {
                    stateLevel = StateLevel.fromInt(Integer.parseInt(args[2]));
                } catch (NumberFormatException e) {
                    stateLevel = null;
                }
                if (stateLevel == null) return false;
                state.setStateLevel(stateLevel);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_LEVEL_SET.get(sender));
                return true;
            case "newquests":
                if (state == null) return false;
                state.getQuestManager().allNewRandom();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_QUESTS_SET.get(sender));
                return true;
            case "poi":
                if (args.length < 3) return false;
                try {
                    x = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    return false;
                }
                Main.getPlugin().getEarth().getCustomPOIManager().addCustomPOI(x, z);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_POI_ADDED.get(sender));
                return true;
            case "power":
                if (args.length < 3) return false;
                if (state == null) return false;
                int power;
                try {
                    power = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    return false;
                }
                state.setPower(power);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_POWER_SET.get(sender));
                return true;
            case "railwayend":
                if (!Main.getPlugin().getEarth().getRailwayManager().createRailway()) return false;
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_RAILWAY_END_SUCCESS.get(sender));
                return true;
            case "railwayinfo":
                if (args.length != 2) return false;
                int id0 = 0;
                try {
                    id0 = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {
                    return false;
                }
                Railway railway = Main.getPlugin().getEarth().getRailwayManager().getRailway(id0);
                if (railway == null) return false;
                List<Point> points = railway.getPolyline().getPoints();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_RAILWAY_INFO_INFO.get(sender, id0 + "",
                        points.getFirst().x() + "", points.getFirst().z() + "",
                        points.getLast().x() + "", points.getLast().z() + "",
                        ((int) railway.getLength()) + ""));
                return true;
            case "railwaypoint":
                if (p == null) return false;
                Main.getPlugin().getEarth().getRailwayManager().queuePoint
                        (new Point(p.getLocation().getBlockX(), p.getLocation().getBlockZ()));
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_RAILWAY_POINT_ADDED.get(sender));
                return true;
            case "railwayremove":
                if (args.length != 2) return false;
                int id1 = 0;
                try {
                    id1 = Integer.parseInt(args[1]);
                } catch (NumberFormatException ignored) {
                    return false;
                }
                if (!Main.getPlugin().getEarth().getRailwayManager().removeRailway(id1)) return false;
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_RAILWAY_REMOVE_REMOVED.get(sender));
                return true;
            case "railwaystart":
                Main.getPlugin().getEarth().getRailwayManager().startQueue();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_RAILWAY_START_SET.get(sender));
                return true;
            case "removemember":
                if (state == null) return false;
                if (args.length < 3) return false;
                Human member = state.getMember(args[2]);
                if (member == null) return false;
                state.removeMember(member);
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_REMOVE_MEMBER_REMOVED.get(sender));
                return true;
            case "removepoi":
                if (args.length < 3) return false;
                try {
                    x = Integer.parseInt(args[1]);
                    z = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    return false;
                }
                if (Main.getPlugin().getEarth().getCustomPOIManager().removeClosestCustomPOI(x, z)) {
                    sender.sendMessage(Text.COMMAND_EMPIREADMIN_POI_REMOVED.get(sender));
                    return true;
                }
                return false;
            case "shrink":
                if (state == null) return false;
                state.shrinkTerritoryToMax();
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_SHRINK_SHRUNK.get(sender));
                return true;
            case "spawncrate":
                Main.getPlugin().getEarth().getCrateManager().spawnCrate(true);
                return true;
            case "unclaim":
                if (p == null) return false;
                Main.getPlugin().getEarth().forceUnclaim(Square.fromLocation(p.getLocation()));
                sender.sendMessage(Text.COMMAND_EMPIREADMIN_UNCLAIM_UNCLAIMED.get(sender));
                return true;
            default:
                return false;
        }
    }

}
