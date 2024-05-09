package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.services.ServiceMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;


public class DataManagerUsingScanner {
    private static DataManagerUsingScanner single_DataManager = null;
    public static KDTree kdTree;
    private DataManagerUsingScanner() {
        kdTree = new KDTree();
    }

    public static synchronized DataManagerUsingScanner getInstance()
    {
        if (single_DataManager == null)
            single_DataManager = new DataManagerUsingScanner();

        return single_DataManager;
    }

    // Could replace with const later
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places.txt";
    public KDTree createKDTree(int limit) {
        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return kdTree;
    }

    public static void processPlace(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                return;
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            byte[] serviceIndexes = new byte[parts.length - 2];
            for (int i = 2; i < parts.length; i++) {
                serviceIndexes[i-2] = ServiceMapper.getInstance().getIndex(parts[i]);
            }

            kdTree.insert(new POINode(x,y,serviceIndexes));


        } catch (Exception e) {
        }
    }

    public static long readPlacesFromFile(String fileName, int limit) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        long startTime = System.currentTimeMillis();

        File file = new File(fileName);
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String line;
        while((line = br.readLine()) != null) {
            processPlace(line);
            if (counter.get() % 1000 == 0) {
                System.out.println("Processed " + counter.get() + " lines");
            }
            counter.getAndIncrement();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Loaded " + counter.get() + " places in " + (endTime - startTime) + " ms");
        return endTime - startTime;
    }


}
