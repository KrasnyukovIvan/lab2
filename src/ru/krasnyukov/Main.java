package ru.krasnyukov;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    public static int numberOfElementHasTable = 2000;
    public static final int maxIterForFindEmptyCell = 15;
    public static final int stepForFindEmptyCell = 3;
    public static final String valueRemoved = "******";
    public static final String emptyCell = "";

    public static void main(String[] args) {

        String[] table = new String[numberOfElementHasTable];

        Arrays.fill(table, emptyCell);

        Set<String> keys = generatedKeys("ццББцц");

        Iterator<String> iter = keys.iterator();
        for(int i = 0; i < numberOfElementHasTable; i++) {
            add(table, iter.next());
        }

        saveHashTable(table, "outHashTable.txt");
        collisionDefinitions(keys, "collision.txt");

        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();

        System.out.println(findPosition(table, value));

        deleteKey(table, value);

        System.out.println(findPosition(table, value));
    }


    public static Set<String> generatedKeys(String formatKey) {
        Set<String> set = new HashSet<>();
        try {
            FileWriter fileWriter = new FileWriter("keys.txt", false);

            StringBuilder key = new StringBuilder();
            Random random = new Random();

            for(int count = 0; count < 40000; count++) {
                for (int i = 0; i < formatKey.length(); i++) {
                    if (formatKey.charAt(i) == 'ц') {
                        key.append(random.nextInt(10));
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
        for(int i = 0; i < maxIterForFindEmptyCell; i++) {
            id = id + i * stepForFindEmptyCell;
            if (id >= numberOfElementHasTable) {
                return false;
            }
            if (table[id].equals(emptyCell)) {
                table[id] = key;
                return true;
            }
        }

        return false;
    }

    public static void collisionDefinitions(Set<String> keys, String file){
        try {
            FileWriter fileWriter = new FileWriter(file, false);

            int[] massiveCollision = new int[numberOfElementHasTable];

            Iterator<String> iter = keys.iterator();
            for(int i = 0; i < keys.size(); i++) {
                massiveCollision[hash(iter.next())] += 1;
            }

            for(int elem : massiveCollision) {
                fileWriter.write(elem + " \n");
            }

            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int findPosition(String[] table, String key) {
        int id = hash(key);
        for(int i = 0; i < maxIterForFindEmptyCell; i++) {
            id = id + i * stepForFindEmptyCell;
            if (id >=numberOfElementHasTable) {
                return -1;
            }
            if (table[id].equals(key)) {
                return id;
            }
            if (table[id].equals(emptyCell)) {
                return -1;
            }
        }

        return -1;
    }

    public static boolean deleteKey(String[] table, String key){
        int id = findPosition(table, key);
        if (id < 0) {
            return false;
        }
        table[id] = valueRemoved;
        return true;
    }

}
