package eu.judevving.empire.file;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class FileSaver {

    private TreeMap<String, String> values;
    private File file;

    public void delete() {
        try {
            file.delete();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        values.clear();
        if (!file.exists()) return;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while (bufferedReader.ready()) {
                line = bufferedReader.readLine();
                if (line == null) continue;
                int i = line.indexOf(':');
                if (i <= 0) continue;
                values.put(line.substring(0, i), line.substring(i + 1));
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String key : values.keySet()) {
                writer.write(key + ':' + values.get(key));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileSaver(File file) {
        this.file = file;
        File folder = file.getParentFile();
        folder.mkdirs();
        values = new TreeMap<>();
    }

    public Collection<String> getKeys() {
        return values.keySet();
    }

    public FileSaver(String filePath) {
        this(new File(filePath));
    }

    public String getFileName() {
        return file.getName();
    }

    public void put(String key, List<String> list) {
        if (list == null) return;
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : list) {
            stringBuilder.append(string);
            stringBuilder.append(',');
        }
        values.put(key, stringBuilder.toString());
    }

    public LinkedList<String> getList(String key) {
        LinkedList<String> list = new LinkedList<>();
        String value = values.get(key);
        if (value == null) return list;
        while (!value.isEmpty()) {
            int i = value.indexOf(',');
            if (i == -1) return list;
            list.addLast(value.substring(0, i));
            value = value.substring(i + 1);
        }
        return list;
    }

    public void remove(String key) {
        values.remove(key);
    }

    public void put(String key, boolean b) {
        values.put(key, b + "");
    }

    public void put(String key, int a) {
        values.put(key, a + "");
    }

    public void put(String key, long a) {
        values.put(key, a + "");
    }

    public void put(String key, String string) {
        values.put(key, string);
    }

    public boolean getBooleanSafely(String key, boolean d) {
        String value = values.get(key);
        if (value == null) return d;
        if (value.equals("true")) return true;
        if (value.equals("false")) return false;
        return d;
    }

    public int getIntSafely(String key, int d) {
        try {
            return Integer.parseInt(values.get(key));
        } catch (NumberFormatException e) {
            return d;
        }
    }

    public long getLongSafely(String key, long d) {
        try {
            return Long.parseLong(values.get(key));
        } catch (NumberFormatException e) {
            return d;
        }
    }

    public int getInt(String key) {
        return Integer.parseInt(values.get(key));
    }

    public String getString(String key) {
        return values.get(key);
    }

}
