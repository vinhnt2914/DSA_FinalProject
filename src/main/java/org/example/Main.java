package org.example;

import org.example.model.HashMap.POI;
import org.example.model.HashMap.POIHashMap;
import org.example.model.KDTree.KDTree;
import org.example.model.KDTree.Node;
import org.example.Utility.DataManager;
import org.example.Utility.RandomPOI;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;



public class Main {

    public static void processPlace(String line, AtomicLong counter) {

        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        String[] serviceArray = parts[2].split(";");

        POI poi = new POI(x, y, serviceArray);
        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename, int limit) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> processPlace(line, counter));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places");
        return endTime - startTime;
    }


    public static void main(String[] args) {
//        KDTree kdTree = new KDTree();
//        try {
//            long duration = readPlacesFromFile("src/places_with_id.txt", 30000000);
//            System.out.println("Completed processing of places in " + duration + " ms.");
//        } catch (IOException e) {
//            System.err.println("Error reading the file: " + e.getMessage());
//            e.printStackTrace();
//        }

        DataManager dataManager = new DataManager();
//        KDTree kdTree = dataManager.createKDTree();
//        POIHashMap poiHashMap = dataManager.createPOIHashMap();
        POIHashMap poiHashMap = new POIHashMap();
        dataManager.populatePOIHashMap(poiHashMap, 10000000);
//        KDTree kdTree = dataManager.createKDTree();
        RandomPOI randomPOI = new RandomPOI();
        POI target = randomPOI.random();
        System.out.println("PERFORMING KNN SEARCH FOR TARGET: " + target);
        poiHashMap.KNNSearchWithQuickSort(target, "ParK", 100000);
//        kdTree.kNearestNeighborsWithMap(target.mapToNode());
//        for (int i = 0; i < 50; i++) {
//            POI target = randomPOI.random();
//            poiHashMap.KNNSearch(target, 100000);
//        }
//        poiHashMap.display();

//        kdTree.kNearestNeighborsWithMap(target);
    }

    public static void addIdToFile() throws IOException {
        String inputFile = "D:\\DSA_FinalProject\\src\\places.txt";
        String outputFile = "D:\\DSA_FinalProject\\src\\places_with_id.txt"; // You can change the output filename
        int startingId = 1; // Adjust this if you want a different starting ID

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        String line;
        int currentId = startingId;
        while ((line = reader.readLine()) != null) {
            writer.write(currentId + "," + line + "\n");
            currentId++;
        }

        reader.close();
        writer.close();

        System.out.println("Successfully added IDs to the file. Check " + outputFile);
    }
}
