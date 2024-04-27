package org.example;

import org.example.model.KDTree.KDTree;
import org.example.model.KDTree.Node;
import org.example.model.Utility.DataManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;


public class Main {

    public static void processPlace(String line, AtomicLong counter, KDTree kdTree) {

        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        Node node = new Node(id, new int[]{x,y});
        kdTree.insert(node);
        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename, int limit, KDTree kdTree) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> processPlace(line, counter, kdTree));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places");
        return endTime - startTime;
    }


    public static void main(String[] args) {
//        KDTree kdTree = new KDTree();
//        try {
//            long duration = readPlacesFromFile("src/places_with_id.txt", 1, kdTree);
//            System.out.println("Completed processing of places in " + duration + " ms.");
//        } catch (IOException e) {
//            System.err.println("Error reading the file: " + e.getMessage());
//            e.printStackTrace();
//        }

        DataManager dataManager = new DataManager();
        KDTree kdTree = dataManager.createKDTree();

        Node target = new Node(new int[]{512351,1234123});

        kdTree.kNearestNeighborsWithMap(target);
    }

    public static void addIdToFile() throws IOException {
        String inputFile = "src/data/places_with_id.txt";
        String outputFile = "src/data/places_with_id.txt"; // You can change the output filename
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
