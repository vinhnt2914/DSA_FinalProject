package com.example.testapi.model.KDTree;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.Arrays;
public class Node {
    int[] coordinates;
    MyArray<String> services;
    Node left;
    Node right;

    public Node(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public Node(int[] coordinates, MyArray<String> services) {
        this.coordinates = coordinates;
        this.services = services;
    }

    //    public MapNode mapToMapNode() {
//        return new MapNode(this.id, this.coordinates);
//    }

    public POI mapToPOI() {
        return new POI(coordinates, services);
    }

    public POIWithDistance mapToPOIWithDistance(double distance) {
        return new POIWithDistance(coordinates, services, distance);
    }
    public int[] getCoordinates() {
        return coordinates;
    }
    public double distance(Node target) {
        double dx = this.coordinates[0] - target.coordinates[0];
        double dy = this.coordinates[1] - target.coordinates[1];

        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "Node{" +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
