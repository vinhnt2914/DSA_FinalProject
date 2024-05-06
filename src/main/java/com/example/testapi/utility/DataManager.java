package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.Map2D.Map2D;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class DataManager {
    private static DataManager single_DataManager = null;
    public KDTree kdTree;
    public Map2D poiHashMap;
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
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places_with_id.txt";
    public KDTree createKDTreeAndMap(int limit) {
        List<POINode> nodes = new ArrayList<>();
        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit, nodes);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        populateNodeIntoTree(nodes);

        return kdTree;
    }

    private void populateNodeIntoTree(List<POINode> nodes) {
        long startTime = System.currentTimeMillis();

        kdTree.populate(nodes);
        poiHashMap.populateFromPOINode(nodes);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Completed create and populate kd-tree, hashmap in " + duration + " ms.");
    }


    public static void processPlace(String line, AtomicLong counter, List<POINode> nodes) {
        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        String[] serviceArr =  parts[3].split(";");
        MyArray<String> services = new MyArray<>(serviceArr);
//        System.out.println("X: " + x);
//        System.out.println("Y: " + x);
//        System.out.println("Services: " + services);

        POINode node = new POINode(x,y, services);
//        System.out.println("Created node: " + node);
        nodes.add(node);
        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename, int limit, List<POINode> nodes) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> processPlace(line, counter, nodes));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places");
        return endTime - startTime;
    }


}
