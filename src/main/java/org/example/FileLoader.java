package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class FileLoader {

    public static long readPlacesFromFile(String filename, AtomicLong counter) throws IOException {
        Path path = Path.of(filename);
        long startTime = System.currentTimeMillis();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.forEach(line -> processPlace(line, counter));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Loaded " + counter.get() + " places.");
        return endTime - startTime; // Duration in milliseconds
    }

    private static void processPlace(String line, AtomicLong counter) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        String[] serviceArray = parts[3].split(";");
        Set<String> services = new HashSet<>();
        for (String service : serviceArray) {
            services.add(service);
        }
        Place place = new Place(id, x, y, services);
        counter.incrementAndGet();
    }
}
