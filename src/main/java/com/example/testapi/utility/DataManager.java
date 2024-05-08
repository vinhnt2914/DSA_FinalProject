package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.Map2D.Map2D;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class DataManager {
    private static DataManager single_DataManager = null;
    public static KDTree kdTree;
    public static Map2D poiHashMap;
    private DataManager() {
        kdTree = new KDTree();
        poiHashMap = new Map2D();
    }

    public static synchronized DataManager getInstance()
    {
        if (single_DataManager == null)
            single_DataManager = new DataManager();

        return single_DataManager;
    }

    // Could replace with const later
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places.txt";
    public KDTree createKDTreeAndMap(int limit) {

        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return kdTree;
    }

    private void populateNodeIntoTree() {
        long startTime = System.currentTimeMillis();


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
            String[] serviceArr = new String[parts.length - 2];
            for (int i = 2; i < parts.length; i++) {
                serviceArr[i-2] = parts[i];
            }

            MyArray<String> services = new MyArray<>(serviceArr.length);

            // for (String service : serviceArr) {
            //     services.insert(service.trim());
            // }

            POINode node = new POINode(x, y, services);
            kdTree.insert(node);
            poiHashMap.put(node);
            counter.incrementAndGet();
        } catch (Exception e) {
        }
    }

    public static long readPlacesFromFile(String filename, int limit) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);
    
        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> {
                processPlace(line, counter);
                if (counter.get() % 100000 == 0) {
                    System.out.println("Loaded " + counter.get() + " places");
                }
            });
        }
        long endTime = System.currentTimeMillis();
    
        System.out.println("Finished loading. Total places loaded: " + counter.get());
        return endTime - startTime;
    }
}
