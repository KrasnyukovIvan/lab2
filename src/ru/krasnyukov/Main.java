package ru.krasnyukov;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static final int numberOfElementHasTable = 2000;
    public static final int maxIterForFindEmptyNode = 15;
    public static final int stepForFindEmptyNode = 3;

    public static void main(String[] args) {

        String[] table = new String[numberOfElementHasTable];

        Set<String> set = generatedKeys("ццББцц");

        Iterator<String> iter = set.iterator();
        for(int i = 0; i < numberOfElementHasTable; i++) {
            add(table, iter.next());
        }

        saveHashTable(table, "out.txt");

    }


    public static Set<String> generatedKeys(String formatKey) {
        Set<String> set = new HashSet<>();
        try {
            FileWriter fileWriter = new FileWriter("keys.txt", false);

            StringBuilder key = new StringBuilder();
            Random random = new Random();

            for(int count = 0; count < 4000; count++) {
                for (int i = 0; i < formatKey.length(); i++) {
                    if (formatKey.charAt(i) == 'ц') {
                        key.append(random.nextInt(11));
                    } else {
                        key.append((char) (random.nextInt(26) + 65));
                    }
                }
                set.add(key.toString());
                key.setLength(0);
            }

            for(String str : set){
                fileWriter.write(str);
                fileWriter.append('\n');
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static int hash(String key){
        int rez = 0;
        for (int i = 0; i < key.length(); i++) {
            rez += Math.pow((int)key.charAt(i), 2);
        }
        rez %= numberOfElementHasTable;
        return rez;
    }

    public static void saveHashTable(String[] table, String file){
        try {
            FileWriter fileWriter = new FileWriter(file, false);

            for(int i = 0; i < numberOfElementHasTable; i++) {
                fileWriter.write(i + " : " + table[i] + "\n");
            }

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean add(String[] table, String key){
        int id = hash(key);
        for(int i = 0; i < maxIterForFindEmptyNode; i++) {
            id = id + i * stepForFindEmptyNode;
            if (id >= numberOfElementHasTable) {
                return false;
            }
            if (table[id] != "") {
                table[id] = key;
                return true;
            }
        }

        return false;
    }
}
