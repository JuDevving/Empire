package eu.judevving.empire.language;

import eu.judevving.empire.main.Main;
import eu.judevving.empire.main.GlobalFinals;

import java.io.*;
import java.util.LinkedList;
import java.util.TreeMap;

public class Languages {

    private static final String FOLDER_NAME = "languages";
    private static final String FILE_EXTENSION = ".txt";
    private static final String TEMPLATE_FILE_NAME = "template" + FILE_EXTENSION;

    private Language[] languages; // Sorted by name
    private String folderPath;

    public Languages() {
        folderPath = GlobalFinals.FILES_FOLDER + "/" + FOLDER_NAME;
        make();
        saveTemplateFile();
        loadLanguages();
    }

    private void make() {
        File folder = new File(folderPath);
        folder.mkdirs();
        File templateFile = new File(folderPath + "/" + TEMPLATE_FILE_NAME);
        try {
            templateFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadLanguages() {
        languages = new Language[0];
        reloadLanguages();
    }

    public void reloadLanguages() {
        TreeMap<String, Language> languageSet = new TreeMap<>();
        if (languages != null) {
            for (Language language : languages) {
                languageSet.put(language.getName(), language);
            }
        }
        try {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            if (files == null) return;
            for (File file : files) {
                if (file.getName().equals(TEMPLATE_FILE_NAME)) continue;
                if (!file.getName().endsWith(FILE_EXTENSION)) continue;
                if (file.getName().indexOf(' ') != -1) {
                    Main.getPlugin().getLogger().info("Language in " + file.getName() + " not loaded because file name contains space");
                    continue;
                }
                if (file.getName().length() == FILE_EXTENSION.length()) continue;
                String name = file.getName().substring(0, file.getName().length() - FILE_EXTENSION.length()); // remove file extension from name
                Language language = null;
                if (languageSet.containsKey(name)) language = languageSet.get(name);
                if (language == null) {
                    language = new Language(name);
                    languageSet.put(name, language);
                }
                loadLanguageFile(language, file);
            }
            languages = languageSet.values().toArray(languages);
            Main.getPlugin().getLogger().info("Loaded " + languages.length + " language(s)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLanguageFile(Language language, File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        Text text;
        while (bufferedReader.ready()) {
            line = bufferedReader.readLine();
            int i = line.indexOf('=');
            if (i <= 0) continue;
            try {
                text = Text.valueOf(line.substring(0, i));
            } catch (IllegalArgumentException e) {
                text = null;
            }
            if (text == null) {
                Main.getPlugin().getLogger().info("Invalid translation in " + file.getName() + ": " + line.substring(0, i));
                continue;
            }
            language.putTranslation(text, new Translation(line.substring(i + 1)));
        }
        fileReader.close();
    }

    private void saveTemplateFile() {
        File file = new File(folderPath + "/" + TEMPLATE_FILE_NAME);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (Text text : Text.values()) {
                writer.write(text.name() + "=" + text.getDefaultTranslationRaw());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<String> getLanguageNames() {
        LinkedList<String> list = new LinkedList<>();
        for (int i = 0; i < languages.length; i++) {
            list.addLast(languages[i].getName());
        }
        return list;
    }

    public Language fromStringIgnoreCase(String name) {
        for (Language language : languages) {
            if (name.equalsIgnoreCase(language.getName())) return language;
        }
        return null;
    }

    public Language fromString(String name) {
        if (name == null) return null;
        for (Language language : languages) {
            if (name.equals(language.getName())) return language;
        }
        return null;
    }

    public Language get(int i) {
        return languages[i];
    }

    public int getLength() {
        return languages.length;
    }

}
