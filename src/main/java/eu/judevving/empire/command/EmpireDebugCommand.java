package eu.judevving.empire.command;

import eu.judevving.empire.earth.Square;
import eu.judevving.empire.earth.State;
import eu.judevving.empire.inventory.ItemEditor;
import eu.judevving.empire.language.Language;
import eu.judevving.empire.language.Languages;
import eu.judevving.empire.language.Text;
import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import eu.judevving.empire.earth.storage.StateColor;
import eu.judevving.empire.earth.storage.StateLevel;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class EmpireDebugCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!sender.hasPermission(GlobalFinals.PERMISSION_PREFIX + "command.empiredebug")) {
            sender.sendMessage(Text.COMMAND_NO_PERMISSION.get(sender));
            return false;
        }
        if (args.length == 0) {
            args = new String[1];
            args[0] = "";
        }
        args[0] = args[0].toLowerCase();
        Languages debugLanguages;
        Language debugLanguage;
        switch (args[0]) {
            case "1":
                if (sender instanceof Player p) {
                    if (p.getVehicle() != null) {
                        p.sendMessage(p.getVehicle().getVelocity().length() * 20 + "m/s");
                    }
                }
                return true;
            case "2":
                State state = Main.getPlugin().getEarth().addState("debugstate");
                state.setPower(1000069);
                state.setStateLevel(StateLevel.LEVEL_100);
                state.setStateColor(StateColor.GRAY);
                state.setName("Debugging State");
                Main.getPlugin().getEarth().tryClaim(state, new Square(1, 0), Main.getPlugin().getEarth().getWorld(), true);
                Main.getPlugin().getEarth().tryClaim(state, new Square(1, 1), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(0, 2), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(1, 2), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(2, 2), Main.getPlugin().getEarth().getWorld());

                Main.getPlugin().getEarth().tryClaim(state, new Square(10, 0), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(10, 1), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(10, 2), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(11, 0), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(11, 2), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(12, 0), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(12, 1), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(12, 2), Main.getPlugin().getEarth().getWorld());
                Main.getPlugin().getEarth().tryClaim(state, new Square(13, 3), Main.getPlugin().getEarth().getWorld());
                return true;
            case "3":
                Location cl = Main.getPlugin().getEarth().getCrateManager().getACrateLocation();
                if (cl == null) return false;
                if (sender instanceof Player p) p.teleport(cl);
                return true;
            case "4":
                sender.sendMessage("day: " + Main.getPlugin().getClock().getDay().getDay());
                sender.sendMessage("last online day: " + Main.getPlugin().getEarth().getLastOnlineDay().getDay());
                return true;
            case "5":
                if (!(sender instanceof Player p)) return true;
                File file = new File("plugins/test.yml");
                YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
                for (int i = 0; i < 9; i++) {
                    if (i == 0) {
                        ItemMeta meta = p.getInventory().getItem(0).getItemMeta();
                        for (NamespacedKey key : meta.getPersistentDataContainer().getKeys())
                            sender.sendMessage(key.asString());
                    }
                    yml.set("item." + i, p.getInventory().getItem(i));
                }
                try {
                    yml.save(file);
                } catch (IOException e) {
                }
                sender.sendMessage("saved");
                return true;
            case "6":
                if (!(sender instanceof Player p)) return true;
                p.getInventory().setItemInMainHand(ItemEditor.removeToolTipDisplay(p.getInventory().getItemInMainHand()));
                return true;
            case "heads":
                if (sender instanceof Player p) {
                    Main.getPlugin().getPlayerHeads().dropAllHeads(p.getLocation().clone().add(0, 30, 0));
                }
                return true;
            case "lang":
                debugLanguages = Main.getPlugin().getLanguages();
                for (int i = 0; i < debugLanguages.getLength(); i++) {
                    debugLanguage = debugLanguages.get(i);
                    sender.sendMessage("");
                    sender.sendMessage(debugLanguage.getName() + ":");
                    for (Text text : Text.values()) {
                        sender.sendMessage(text.name() + ":");
                        sender.sendMessage(text.get(debugLanguages.get(i), "1", "2", "3", "4", "5"));
                    }
                }
                return true;
            case "langrl":
                Main.getPlugin().getLanguages().reloadLanguages();
                sender.sendMessage(Text.COMMAND_EMPIREDEBUG_LANUGAGES_RELOADED.get(sender));
                return true;
            case "missingtranslations":
            case "mt":
                debugLanguages = Main.getPlugin().getLanguages();
                for (int i = 0; i < debugLanguages.getLength(); i++) {
                    debugLanguage = debugLanguages.get(i);
                    if (debugLanguage.getName().equals("english")) continue;
                    for (Text text : Text.values()) {
                        if (text.hasTranslation(debugLanguage)) continue;
                        sender.sendMessage(debugLanguage.getName() + ":" + text.name());
                    }
                }
                return true;
            case "randomcoordinates":
            case "rc":
                Random random = new Random();
                sender.sendMessage((GlobalFinals.EARTH_BLOCK_MIN_X + random.nextInt(GlobalFinals.EARTH_SIZE_X)) +
                        "   " +
                        (GlobalFinals.EARTH_BLOCK_MIN_Z + random.nextInt(GlobalFinals.EARTH_SIZE_Z)));
                return true;
            default:
                sender.sendMessage(Text.COMMAND_USAGE_EMPIREDEBUG.get(sender));
                return false;
        }
    }
}
