package org.example.model.Utility;

import org.example.model.KDTree.KDTree;
import org.example.model.KDTree.Node;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class DataManager {
    public KDTree createKDTree() {
        KDTree kdTree = new KDTree();
        List<Node> nodes = new ArrayList<>();
        try {
            long duration = readPlacesFromFile("be/src/data/places_with_id.txt", 30000000, nodes);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        populateNodeIntoTree(kdTree, nodes);

        return kdTree;
    }

    private void populateNodeIntoTree(KDTree kdTree, List<Node> nodes) {
        long startTime = System.currentTimeMillis();

        kdTree.populate(nodes);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Completed create and populate kd-tree in " + duration + " ms.");
    }


    public static void processPlace(String line, AtomicLong counter, List<Node> nodes) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        Node node = new Node(id, new int[]{x,y});
        nodes.add(node);
        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename, int limit, List<Node> nodes) throws IOException {
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
