package org.example.model.Map2D;

import java.util.Arrays;

public class MapNode {
    int id;
    int[] coordinates;
    MapNode next;

    public MapNode(int id, int[] coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return String.format("%s: (%s, %s)", id, coordinates[0], coordinates[1]);
    }
}
