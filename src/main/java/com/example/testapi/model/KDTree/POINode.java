package com.example.testapi.model.KDTree;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.Arrays;
public class POINode extends POI{
    POINode left;
    POINode right;

    public POINode(int x, int y, Byte[] services) {
        super(x, y, services);
    }

    public POINode(int x, int y) {
        super(x, y);
    }

//    public POINode(int[] coordinates) {
//        this.coordinates = coordinates;
//    }
//
//    public POINode(int[] coordinates, MyArray<String> services) {
//        this.coordinates = coordinates;
//        this.services = services;
//    }

    //    public MapNode mapToMapNode() {
//        return new MapNode(this.id, this.coordinates);
//    }

    public POI mapToPOI() {
        return new POI(coordinates[0], coordinates[1], services);
    }

    public POIWithDistance mapToPOIWithDistance(double distance) {
        return new POIWithDistance(coordinates[0], coordinates[1], services, distance);
    }
    public int[] coordinates() {
        return coordinates;
    }
    public double distance(POINode target) {
        double dx = this.coordinates[0] - target.coordinates[0];
        double dy = this.coordinates[1] - target.coordinates[1];

        return Math.sqrt(dx * dx + dy * dy);
    }
}
