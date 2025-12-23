package eu.judevving.empire.language;

public class Translation {

    private final String rawTranslation;
    private final String[] segments;
    private final int[] gapArgIds;

    public String get(String... args) {
        StringBuilder re = new StringBuilder(segments[0]);
        for (int i = 1; i < segments.length; i++) {
            re.append(getArg(args, gapArgIds[i - 1]));
            re.append(segments[i]);
        }
        return re.toString();
    }

    Translation(String rawTranslation) {
        this.rawTranslation = rawTranslation;
        if (rawTranslation == null) rawTranslation = Text.MISSING_TRANSLATION;
        String remaining = rawTranslation;
        int gapCount = countChar(rawTranslation, '{');
        segments = new String[gapCount + 1];
        gapArgIds = new int[gapCount];
        int i = -1;
        while (!remaining.isEmpty()) {
            i++;
            int o = remaining.indexOf('{');
            int c = remaining.indexOf('}');
            if (o == -1 || c == -1) {
                segments[i] = remaining;
                break;
            }
            segments[i] = remaining.substring(0, o);
            try {
                gapArgIds[i] = Integer.parseInt(remaining.substring(o + 1, c));
            } catch (NumberFormatException e) {
                gapArgIds[i] = -1;
            }
            remaining = remaining.substring(c + 1);
        }
        i++;
        while (i < segments.length) {
            segments[i] = "";
            i++;
        }
    }

    private String getArg(String[] args, int id) {
        if (id < 0 || id >= args.length) return Text.MISSING_ARGUMENT;
        return args[id];
    }

    private int countChar(String string, char c) {
        int re = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == c) re++;
        }
        return re;
    }

    public String getRawTranslation() {
        return rawTranslation;
    }
}
