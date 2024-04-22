package org.example;

import org.example.model.KDTree.KDTree;
import org.example.model.KDTree.Node;
import org.example.model.NNLinearSearch.NNLinearSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class Main {
    public static void processPlace(String line, AtomicLong counter, KDTree kdTree, List<Node> nodes) {

        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        Node node = new Node(new int[]{x,y});
        kdTree.insert(node.getCoordinates());
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


    public static void main(String[] args) {
        KDTree kdTree = new KDTree();
        List<Node> nodes = new ArrayList<>();
        try {
            long duration = readPlacesFromFile("src/places.txt", 1000000, kdTree, nodes);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        Node target = new Node(new int[]{1234122,4214142});

//        System.out.println(kdTree.nearestNeighbor(target));
//        System.out.println(kdTree.nearestNeighborIteration(target));
//        System.out.println(NNLinearSearch.nearestNeighbor(nodes, target));

//        kdTree.nearestNeighbors(target);
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
}
