package eu.judevving.empire.file;

import java.io.File;
import java.util.LinkedList;

public abstract class FolderSaver {

    protected File folder;

    protected LinkedList<FileSaver> fileSavers;

    public FolderSaver(String folderPath) {
        folder = new File(folderPath);
        folder.mkdirs();
    }

    protected void save() {
        for (FileSaver fileSaver : fileSavers) {
            fileSaver.save();
        }
    }

    protected void load() {
        fileSavers = new LinkedList<>();
        File[] files = folder.listFiles();
        for (File file : files) {
            FileSaver fileSaver = new FileSaver(file.getPath());
            fileSaver.load();
            fileSavers.add(fileSaver);
        }
    }

}
