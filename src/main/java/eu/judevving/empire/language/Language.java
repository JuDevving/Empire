package eu.judevving.empire.language;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Language implements Comparable<Language> {

    private final String name;
    private final HashMap<Text, Translation> translations;

    Language(String name) {
        this.name = name;
        translations = new HashMap<>();
    }

    public Translation getTranslation(Text text) {
        return translations.get(text);
    }

    void putTranslation(Text text, Translation translation) {
        translations.put(text, translation);
    }

    public int compareTo(@NotNull Language language) {
        return name.compareTo(language.getName());
    }

    public String getName() {
        return name;
    }
}
