package com.example.testapi.model;

import com.example.testapi.dto.POIWithDistanceJson;
import com.example.testapi.model.Array.MyArray;

import java.util.Arrays;

// Record for attaching the distance to target into POI
public record POIWithDistance(int x, int y, MyArray<String> services, double distance) {
    public POIWithDistanceJson mapToJSON() {
        return new POIWithDistanceJson(x, y, services.toArrayList(), distance);
    }
}
