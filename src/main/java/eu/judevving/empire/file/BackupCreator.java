package eu.judevving.empire.file;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BackupCreator {

    public static void save() {
        Main.getPlugin().getLogger().info("Saving...");
        Main.getPlugin().getEarth().save();
        Main.getPlugin().getPlayerHeads().save();
    }

    public static boolean createBackup(boolean save) {
        if (save) save();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String folderName = calendar.get(Calendar.YEAR)
                + "_" + (calendar.get(Calendar.MONTH) + 1)
                + "_" + calendar.get(Calendar.DAY_OF_MONTH)
                + "_" + calendar.get(Calendar.HOUR_OF_DAY)
                + "_" + calendar.get(Calendar.MINUTE)
                + "_" + calendar.get(Calendar.SECOND);
        File backupFolder = new File(GlobalFinals.FILES_FOLDER_BACKUPS + "/" + folderName);
        File folder = new File(GlobalFinals.FILES_FOLDER);
        backupFolder.mkdirs();
        folder.mkdirs();
        try {
            FileUtils.copyDirectory(folder, new File(backupFolder, folder.getName()));
            Main.getPlugin().getLogger().info("Backup created");
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
