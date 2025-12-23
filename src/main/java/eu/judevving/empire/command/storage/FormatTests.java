package eu.judevving.empire.command.storage;

import eu.judevving.empire.main.GlobalFinals;

public class FormatTests {

    public static final String LEGAL_SPECIAL_CHARACTERS_NAME = " äöüÄÖÜßáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙ-.'";
    public static final String LEGAL_SPECIAL_CHARACTERS_DESCRIPTION = ",:;!?&/_+*%$€()";

    private static final String NUMBERS = "0123456789";
    private static final String LETTERS_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String LETTERS = LETTERS_LOWERCASE + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LEGAL_CHARACTERS_NAME = NUMBERS + LETTERS + LEGAL_SPECIAL_CHARACTERS_NAME;
    public static final String LEGAL_CHARACTERS_DESCRIPTION = LEGAL_CHARACTERS_NAME + LEGAL_SPECIAL_CHARACTERS_DESCRIPTION;
    public static final String LEGAL_CHARACTERS_STATE_ID = LETTERS_LOWERCASE;
    public static final String LEGAL_CHARACTERS_TAG = NUMBERS + LETTERS;
    public static final String LEGAL_CHARACTERS_WELCOME = LEGAL_CHARACTERS_DESCRIPTION;

    public static final int STATE_DESCRIPTION_MIN_LENGTH = 0;
    public static final int STATE_DESCRIPTION_MAX_LENGTH = 100;
    public static final int STATE_ID_MIN_LENGTH = 2;
    public static final int STATE_ID_MAX_LENGTH = 6;
    public static final int STATE_NAME_MIN_LENGTH = 2;
    public static final int STATE_NAME_MAX_LENGTH = 30;
    public static final int STATE_TAG_MIN_LENGTH = 1;
    public static final int STATE_TAG_MAX_LENGTH = 6;
    public static final int STATE_WELCOME_MIN_LENGTH = 0;
    public static final int STATE_WELCOME_MAX_LENGTH = 50;
    
    public static boolean legalStateName(String string) {
        if (string.length() < STATE_NAME_MIN_LENGTH) return false;
        if (string.length() > STATE_NAME_MAX_LENGTH) return false;
        return containsOnly(string, LEGAL_CHARACTERS_NAME);
    }

    public static boolean legalStateDescription(String string) {
        if (string.length() < STATE_DESCRIPTION_MIN_LENGTH) return false;
        if (string.length() > STATE_DESCRIPTION_MAX_LENGTH) return false;
        return containsOnly(string, LEGAL_CHARACTERS_DESCRIPTION);
    }

    public static boolean legalStateTag(String string) {
        if (string.length() < STATE_TAG_MIN_LENGTH) return false;
        if (string.length() > STATE_TAG_MAX_LENGTH) return false;
        return containsOnly(string, LEGAL_CHARACTERS_TAG);
    }

    public static boolean legalStateWelcome(String string) {
        if (string.length() < STATE_WELCOME_MIN_LENGTH) return false;
        if (string.length() > STATE_WELCOME_MAX_LENGTH) return false;
        return containsOnly(string, LEGAL_CHARACTERS_WELCOME);
    }

    private static boolean containsOnly(String string, String chars) {
        for (int i = 0; i < string.length(); i++) {
            if (chars.indexOf(string.charAt(i)) < 0) return false;
        }
        return true;
    }

    public static boolean validStateId(String string) {
        if (string.equals(GlobalFinals.EARTH_UNCLAIMABLE_TERRITORY_NAME)) return false;
        if (string.length() < STATE_ID_MIN_LENGTH) return false;
        if (string.length() > STATE_ID_MAX_LENGTH) return false;
        return containsOnly(string, LEGAL_CHARACTERS_STATE_ID);
    }

}
