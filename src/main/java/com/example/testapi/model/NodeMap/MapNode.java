package com.example.testapi.model.NodeMap;

import com.example.testapi.model.Array.MyArray;

import java.security.Provider;

public class MapNode {
    int id;
    int[] coordinates;
    MyArray<String> services;
    MapNode next;

    public MapNode(int id, int[] coordinates, MyArray<String> services) {
        this.id = id;
        this.coordinates = coordinates;
        this.services = services;
    }

    @Override
    public String toString() {
        return String.format("%s: (%s, %s)", id, coordinates[0], coordinates[1]);
    }
}
