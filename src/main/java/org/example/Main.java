package org.example;

import org.example.model.Map2D.Map2DUsingX;
import org.example.model.POIHashMap.POI;
import org.example.model.POIHashMap.POIHashMap;
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

//        DataManager dataManager = new DataManager();
////        KDTree kdTree = dataManager.createKDTree();
////        POIHashMap poiHashMap = dataManager.createPOIHashMap();
//        POIHashMap poiHashMap = new POIHashMap();
//        dataManager.populatePOIHashMap(poiHashMap, 10000000);
////        KDTree kdTree = dataManager.createKDTree();
//        RandomPOI randomPOI = new RandomPOI();
//        POI target = randomPOI.random();
//        System.out.println("PERFORMING KNN SEARCH FOR TARGET: " + target);
//        poiHashMap.KNNSearchWithQuickSort(target, "ParK", 100000);
//        kdTree.kNearestNeighborsWithMap(target.mapToNode());
//        for (int i = 0; i < 50; i++) {
//            POI target = randomPOI.random();
//            poiHashMap.KNNSearch(target, 100000);
//        }
//        poiHashMap.display();

//        kdTree.kNearestNeighborsWithMap(target);

        Map2DUsingX map2DUsingX = new Map2DUsingX();
        POI p1 = new POI(1,2,"Coffee");
        POI p2 = new POI(1,3,"Coffee");
        POI p3 = new POI(1,4,"School","Mall","Mall","Mall","Mall","Mall","Mall","Mall","Mall");
        POI p4 = new POI(2,2,"Restaurant");
        POI p5 = new POI(4,2,"Restaurant", "Coffee");
        POI p6 = new POI(6,2,"School");
        POI p7 = new POI(9,2,"Hospital");
        map2DUsingX.put(p1, p2, p3, p4, p5,p6, p7);

        map2DUsingX.display(10);
//
//        map2DUsingX.removeServiceFromPOI(1,4,"Mall");
//
//        System.out.println("----------------AFTER REMOVED----------------");
//        map2DUsingX.display(10);

        System.out.println("----------------AFTER ADDED SERVICE----------------");
        System.out.println(map2DUsingX.addServiceToPOI(1,4,"Coffee"));
        map2DUsingX.display(10);

        System.out.println(map2DUsingX.addServiceToPOI(1,4,"Bank"));


//        System.out.println(map2DUsingX.find(1,2));
//        System.out.println(map2DUsingX.find(1,4));
//        System.out.println(map2DUsingX.find(6,2));
//        System.out.println("Removed: " + map2DUsingX.remove(6,2));
//        System.out.println(map2DUsingX.find(6,2));
//        System.out.println(map2DUsingX.find(9,2));
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
