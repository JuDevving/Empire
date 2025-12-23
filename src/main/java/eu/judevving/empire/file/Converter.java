package eu.judevving.empire.file;

import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static List<String> divideBracketList(String bracketList) {
        if (bracketList == null) return null;
        if (bracketList.isEmpty()) return null;
        if (bracketList.charAt(0) != '{') return null;
        if (bracketList.charAt(bracketList.length() - 1) != '}') return null;
        bracketList = bracketList.substring(1);
        List<String> list = new LinkedList<>();
        int co = bracketList.indexOf(';');
        while (co >= 0) {
            list.addLast(bracketList.substring(0, co));
            bracketList = bracketList.substring(co + 1);
            co = bracketList.indexOf(';');
        }
        list.addLast(bracketList.substring(0, bracketList.length() - 1));
        return list;
    }

    public static String toBracketList(Object... list) {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (int i = 0; i < list.length; i++) {
            stringBuilder.append(list[i]);
            if (i < list.length - 1) stringBuilder.append(';');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private static String arrayToString(Object[] list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : list) {
            stringBuilder.append(object + "").append(", ");
        }
        return stringBuilder.toString();
    }

    private static String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : list) {
            stringBuilder.append(string).append(", ");
        }
        return stringBuilder.toString();
    }

}
