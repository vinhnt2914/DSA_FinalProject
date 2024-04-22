package org.example;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    public static void main(String[] args) {
        try {
            // Step 1: Generate Data
            System.out.println("Generating data...");
            PlaceDataGenerator.generatePlaces();

            // Step 2: Read Generated Data
            System.out.println("Reading generated data...");
            AtomicLong counter = new AtomicLong();
            long duration = FileLoader.readPlacesFromFile("src/places.txt", counter);
            System.out.println("Completed processing of places in " + duration + " ms.");
            System.out.println("Total places processed: " + counter.get());
        } catch (Exception e) {
            System.err.println("Error during file processing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}