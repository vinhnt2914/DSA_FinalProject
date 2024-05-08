package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.Map2D.Map2D;
import com.example.testapi.model.POI;
import com.example.testapi.services.ServiceMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class Map2DGenerator {
    private static Map2DGenerator single_Map2DGenerator = null;
    public static Map2D poiHashMap;
    private Map2DGenerator() {
        poiHashMap = new Map2D();
    }

    public static synchronized Map2DGenerator getInstance()
    {
        if (single_Map2DGenerator == null)
            single_Map2DGenerator = new Map2DGenerator();

        return single_Map2DGenerator;
    }

    // Could replace with const later
    private final String PLACES_WITH_ID_PATH = "src/main/java/com/example/testapi/data/places.txt";
    public Map2D createMap2D(int limit) {
        try {
            long duration = readPlacesFromFile(PLACES_WITH_ID_PATH, limit);
            System.out.println("Completed processing of places in " + duration + " ms.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return poiHashMap;
    }

    public static void processPlace(String line, AtomicLong counter) {
        try {
            String[] parts = line.split(",");
            if (parts.length < 3) {
                return;
            }
            int x = Integer.parseInt(parts[0].trim());
            int y = Integer.parseInt(parts[1].trim());
            String[] serviceArr = Arrays.copyOfRange(parts, 2, parts.length);
            Byte[] serviceIndexes = new Byte[serviceArr.length];
            for (int i = 0; i < serviceArr.length; i++) {
                serviceIndexes[i] = ServiceMapper.getInstance().getIndex(serviceArr[i]);
            }
//            MyArray<Byte> serviceIndexes = new MyArray<>();
//
//            for (int i = 0; i < serviceArr.length; i++) {
//                serviceIndexes.insert(ServiceMapper.getInstance().getIndex(serviceArr[i]));
//            }

            poiHashMap.put(new POI(x,y,serviceIndexes));

            counter.incrementAndGet();


        } catch (Exception e) {
        }
    }

    public static long readPlacesFromFile(String filename, int limit) throws IOException {
        AtomicLong counter = new AtomicLong(0);
        Path path = Path.of(filename);
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && counter.get() < limit) {
                processPlace(line, counter);
                if (counter.get() % 1000 == 0) {
                    System.out.println("Processed " + counter.get() + " lines");
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Loaded " + counter.get() + " places in " + (endTime - startTime) + " ms");
        return endTime - startTime;
    }


}
