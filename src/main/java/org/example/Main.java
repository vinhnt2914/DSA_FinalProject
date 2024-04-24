package org.example;

import org.example.model.KDTree.KDTree;
import org.example.model.KDTree.Node;
import org.example.model.NNLinearSearch.NNLinearSearch;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Main {
    public static void processPlace(String line, AtomicLong counter, KDTree kdTree, List<Node> nodes) {

        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        Node node = new Node(id, new int[]{x,y});
        kdTree.insert(node);
        nodes.add(node);
        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename, int limit, KDTree kdTree, List<Node> nodes) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> processPlace(line, counter, kdTree, nodes));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places");
        return endTime - startTime;
    }


    public static void main(String[] args) throws IOException {
        KDTree kdTree = new KDTree();
        List<Node> nodes = new ArrayList<>();
        try {
            long duration = readPlacesFromFile("src/places_with_id.txt", 5000000, kdTree, nodes);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

//        List<Node> nodes100 = read100Places();
//        kdTree.populate(nodes100);

        Node target = new Node(new int[]{512351,1234123});

//        System.out.println(kdTree.nearestNeighbor(target));
//        System.out.println(kdTree.nearestNeighborIteration(target));
//        System.out.println(NNLinearSearch.nearestNeighbor(nodes, target));

//        kdTree.kNearestNeighbors(target);
        kdTree.kNearestNeighborsWithMap(target);
//        addIdToFile();
    }
    public static List<Node> read100Places() {
        List<Node> nodes = new ArrayList<>();
        File file = new File("D:\\DSA_FinalProject\\src\\places_100.txt");
        System.out.println(file.getAbsolutePath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && nodes.size() < 100) {
                // Split the line by comma and remove parenthesis
                String[] parts = line.split(",");
                // Parse coordinates
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);

                Node node = new Node(new int[]{x,y});
                nodes.add(node);
            }
            return nodes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
