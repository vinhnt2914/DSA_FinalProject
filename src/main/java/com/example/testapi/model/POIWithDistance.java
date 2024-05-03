package com.example.testapi.model;

import com.example.testapi.model.Array.MyArray;

import java.util.Arrays;

// Record for attaching the distance to target into POI
public record POIWithDistance(int[] coordinates, MyArray<String> services, double distance) {

    @Override
    public String toString() {
        return "coordinates=" + Arrays.toString(coordinates) +
                ", services=" + services +
                ", distance=" + distance;
    }
}
