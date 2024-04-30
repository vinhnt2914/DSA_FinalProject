package org.example.Utility;

import org.example.model.POIHashMap.POI;

import java.util.Random;

public class RandomPOI {
    public POI random() {
        Random random = new Random();
        int x = random.nextInt(10000001);
        int y = random.nextInt(10000001);
        return new POI(x, y);
    }
}
