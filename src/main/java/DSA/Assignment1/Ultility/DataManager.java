package DSA.Assignment1.Ultility;

import DSA.Assignment1.model.HashMap.POI;
import DSA.Assignment1.model.HashMap.POIHashMap;
import DSA.Assignment1.model.KDTree.KDTree;
import DSA.Assignment1.model.KDTree.Node;

import java.io.BufferedReader;
import java.io.FileReader;
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
            long duration = readPlacesFromFile("src/places_with_id.txt", 1000000, nodes);
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

    public POIHashMap createPOIHashMap() {
        POIHashMap hashMap = new POIHashMap();
//        List<POI> pois = new ArrayList<>();
        try {
            long duration = readPOIsFromFile("src/places_with_id.txt", 100000, hashMap);
            System.out.println("Completed processing of POIs in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

//        populatePOIIntoMap(hashMap, pois);

        return hashMap;
    }

    // Testing things
    public void populatePOIHashMap(POIHashMap hashMap, int limit) {
        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\DSA_FinalProject\\src\\places_with_id.txt"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count <= limit) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[1]);
                int y = Integer.parseInt(parts[2]);
                String[] services = parts[3].split(";");

                POI poi = new POI(x, y, services);
                hashMap.put(poi);

                count++;


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Completed reading " + limit + " POIs from file in " + duration + " ms.");
    }

//    private void populatePOIIntoMap(POIHashMap hashMap, List<POI> pois) {
//        long startTime = System.currentTimeMillis();
//
////        hashMap.populate(pois);
//        long endTime = System.currentTimeMillis();
//        long duration = endTime - startTime;
//        System.out.println("Completed create and populate POI HashMap in " + duration + " ms.");
//    }

    public static void processPlace(String line, AtomicLong counter, List<Node> nodes) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        Node node = new Node(id, new int[]{x,y});
        nodes.add(node);
        counter.incrementAndGet();
    }

    public static void processPOIs(String line, AtomicLong counter, POIHashMap hashMap) {
        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        String[] services = parts[3].split(";");

        POI poi = new POI(x, y, services);
        hashMap.put(poi);
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

    public static long readPOIsFromFile(String filename, int limit, POIHashMap hashMap) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(limit).forEach(line -> processPOIs(line, counter, hashMap));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " POIs");
        return endTime - startTime;
    }

}
