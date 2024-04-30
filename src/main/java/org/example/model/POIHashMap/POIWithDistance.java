package org.example.model.POIHashMap;

// Record for attaching the distance to target into POI
public record POIWithDistance(int id, int[] coordinates, double distance) {
    @Override
    public String toString() {
        return String.format("POI{(%s, %s) - distance: %.2f}", coordinates[0], coordinates[1], distance);
    }
}
