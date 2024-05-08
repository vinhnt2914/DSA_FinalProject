package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.services.ServiceMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class DataManager {
    private static DataManager single_DataManager = null;
    public static KDTree kdTree;
    private DataManager() {
        kdTree = new KDTree();
    }

    public static synchronized DataManager getInstance()
    {
        if (single_DataManager == null)
            single_DataManager = new DataManager();

        return single_DataManager;
    }

    // Could replace with const later
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places.txt";
    public KDTree createKDTree(int limit) {
        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return kdTree;
    }

    private void populateNodeIntoTree(List<POINode> nodes) {
        long startTime = System.currentTimeMillis();

        kdTree.populate(nodes);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Completed create and populate kd-tree, hashmap in " + duration + " ms.");
    }

    public static void processPlace(String line, AtomicLong counter) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                return; 
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            String[] serviceArr = Arrays.copyOfRange(parts, 2, parts.length);
            MyArray<Byte> serviceIndexes = new MyArray<>(10);

            for (int i = 0; i < serviceArr.length; i++) {
                serviceIndexes.insert(ServiceMapper.getInstance().getIndex(serviceArr[i]));
            }

            kdTree.insert(new POINode(x,y,serviceIndexes));

            counter.incrementAndGet();


        } catch (Exception e) {
        }
    }

    public static long readPlacesFromFile(String filename, int limit) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && counter.get() < limit) {
                processPlace(line, counter);
                if (counter.get() % 1000 == 0) {  
                    System.out.println("Processed " + counter.get() + " lines");
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Loaded " + counter.get() + " places in " + (endTime - startTime) + " ms");
        return endTime - startTime;
    }


}
