package org.example;

import org.example.model.KDTree;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {
    static KDTree kdTree = new KDTree();
    public static void processPlace(String line, AtomicLong counter) {

        String[] parts = line.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        String[] serviceArray = parts[2].split(";");
        Set<String> services = new HashSet<>();
        for (String service : serviceArray) {
            services.add(service);
        }
        Place place = new Place(x, y, services);
        int[] coordinates = new int[] {place.getX(), place.getY()};
        kdTree.insert(coordinates);

        System.out.println("Processed place at (" + place.getX() + ", " + place.getY() + ") with services: " + place.getServices());


        counter.incrementAndGet();
    }

    public static long readPlacesFromFile(String filename) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);

        long startTime = System.currentTimeMillis();

        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.limit(30)  // Limit to 30 lines
                    .forEach(line -> {
                        if (counter.get() < 30) {
                            processPlace(line, counter);
                        }
                    });
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places.");
        return endTime - startTime;
    }


    public static void main(String[] args) {
        try {
            long duration = readPlacesFromFile("src/places.txt");
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        kdTree.printTree();
    }
}
