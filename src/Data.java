import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Data {
    private TreeMap<String, String> database;
    private TreeMap<String, String> history;

    Data() {
        database = new TreeMap<>();
        history = new TreeMap<>();
        importData();
    }

    private void importData() {
        File file = new File("database.txt");
        if (!file.exists())
            return;
        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(
                                new FileInputStream(file), StandardCharsets.UTF_8));) {
            String line;
            String[] keypair;
            while ((line = br.readLine()) != null) {
                keypair = line.split("\t");
                database.put(keypair[0], keypair[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void saveData() throws IOException {
        File file = new File("database.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8));) {
            for (Map.Entry<String, String> stringStringEntry : database.entrySet()) {
                bw.write(((Map.Entry) stringStringEntry).getKey() + "\t" + ((Map.Entry) stringStringEntry).getValue() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String[] keySet() {
        Set<String> key_set = database.keySet();
        return key_set.toArray(new String[database.size()]);
    }

    String[] keySetHistory() {
        Set<String> key_set = history.keySet();
        return key_set.toArray(new String[history.size()]);
    }

    String get(String key) {
        if (key == null) {
            return null;
        }
        return database.get(key);
    }

    void put(String key, String value) {
        if (database.containsKey(key))
            return;
        database.put(key, value);
    }

    void changeValue(String key, String value) {
        database.put(key, value);
    }

    void remove(String key) {
        database.remove(key);
    }

    boolean containsKey(String key) {
        return database.containsKey(key);
    }

    void writeHistory(String key) {
        if (key == null) {
            return;
        }
        history.put(key, database.get(key));
    }

    void export(String key) throws IOException {
        File file = new File("output1.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), StandardCharsets.UTF_8));) {
            bw.write(key + "\t" + database.get(key) + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}