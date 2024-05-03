package com.example.testapi.utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PlaceDataGenerator {

    private static final int MAP_SIZE = 10_000_000;
    private static final int REGION_SIZE = 100_000;
    private static final int NUM_REGIONS = MAP_SIZE / REGION_SIZE;
    private static final int NUM_PLACES = 100;
    private static final String[] SERVICES = {"ATM", "Restaurant", "Hospital", "Gas Station", "Coffee Shop", "Library"};
    private static final int NUM_THREADS = 10;

    public static void main(String[] args) {
        try {
            generatePlaces();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generatePlaces() throws IOException {
        String baseDir = "src/main/java/com/example/testapi/data/";
        Path path = Paths.get(baseDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Random random = new Random();
        Set<String> usedCoordinates = new HashSet<>();
        int attempts, maxAttempts = 10;
        int progressCounter = 0;
        int totalPlaces = NUM_PLACES / 100;

        for (int i = 0; i < NUM_PLACES; i++) {
            int x, y;
            String coordKey;
            attempts = 0;
            do {
                x = random.nextInt(MAP_SIZE);
                y = random.nextInt(MAP_SIZE);
                coordKey = x + "," + y;
                attempts++;
            } while (usedCoordinates.contains(coordKey) && attempts < maxAttempts);

            if (attempts >= maxAttempts) {
                continue;
            }

            usedCoordinates.add(coordKey);
            int regionX = x / REGION_SIZE;
            int regionY = y / REGION_SIZE;
            String filename = baseDir + "region_" + regionX + "_" + regionY + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                String services = generateServices(random);
                writer.write(i + "," + x + "," + y + "," + services + "\n");
            }

            progressCounter++;
            if (progressCounter % totalPlaces == 0) {
                System.out.println("Generated " + (progressCounter / totalPlaces) + "% of places...");
            }
        }
        System.out.println("Data generation completed.");
    }


    private static String generateServices(Random random) {
        int numServices = 1 + random.nextInt(3);
        StringBuilder servicesBuilder = new StringBuilder();
        for (int i = 0; i < numServices; i++) {
            if (i > 0) servicesBuilder.append(";");
            servicesBuilder.append(SERVICES[random.nextInt(SERVICES.length)]);
        }
        return servicesBuilder.toString();
    }
}
