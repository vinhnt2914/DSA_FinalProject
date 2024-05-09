package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.Map2D.Map2D;

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
    public static Map2D poiHashMap;
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places.txt";

    private DataManager() {
        kdTree = new KDTree();
        poiHashMap = new Map2D();
    }

    public static synchronized DataManager getInstance() {
        if (single_DataManager == null) {
            single_DataManager = new DataManager();
        }
        return single_DataManager;
    }

    public KDTree createKDTreeAndMap(int limit) {
        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return kdTree;
    }

    public static void processPlace(String line, AtomicLong counter) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                return;
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            MyArray<String> services = new MyArray<>(parts.length - 2);
            for (int i = 2; i < parts.length; i++) {
                services.insert(parts[i].trim());
            }

            POINode node = new POINode(x, y, services);
            synchronized (kdTree) {
                kdTree.insert(node);
            }
            synchronized (poiHashMap) {
                poiHashMap.put(node);
            }
            counter.incrementAndGet();
        } catch (Exception e) {
            System.err.println("Error processing place: " + e.getMessage());
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
