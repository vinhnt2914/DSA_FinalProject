package org.example.model.HashMap;

import java.util.Arrays;

public record POIWithDistance(int id, int[] coordinates, double distance) {
    @Override
    public String toString() {
        return String.format("POI{(%s, %s) - distance: %.2f}", coordinates[0], coordinates[1], distance);
    }
}
