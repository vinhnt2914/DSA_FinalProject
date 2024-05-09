package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.Map2D.Map2D;
import com.example.testapi.services.ServiceMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


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
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/NewPlaces.txt";
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

    // private void populateNodeIntoTree(List<POINode> nodes) {
    //     long startTime = System.currentTimeMillis();

    //     kdTree.populate(nodes);

    //     long endTime = System.currentTimeMillis();
    //     long duration = endTime - startTime;
    //     System.out.println("Completed create and populate kd-tree, hashmap in " + duration + " ms.");
    // }

    public static void processPlace(String line, AtomicLong counter) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                return; 
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            byte[] serviceIndexes = new byte[parts.length - 2];
            for (int i = 2; i < parts.length; i++) {
                serviceIndexes[i-2] = ServiceMapper.getInstance().getIndex(parts[i]);
            }

            POINode node = new POINode(x, y, serviceIndexes);
            synchronized (kdTree) {
                kdTree.insert(node);
            }
            counter.incrementAndGet();
        } catch (Exception e) {
        }
    }

    public static long readPlacesFromFile(String filename, int limit) throws IOException {
    AtomicLong counter = new AtomicLong(0);
    Path path = Path.of(filename);

    long startTime = System.currentTimeMillis();
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    try {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && counter.get() < limit) {
                final String finalLine = line;
                executor.execute(() -> processPlace(finalLine, counter));
                long count = counter.incrementAndGet();
                if (count % 100000 == 0) {
                    System.out.println("Loaded " + count + " places");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading lines: " + e.getMessage());
        }
    } finally {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    long endTime = System.currentTimeMillis();

    System.out.println("Finished loading. Total places loaded: " + counter.get());
    return endTime - startTime;
}
}
