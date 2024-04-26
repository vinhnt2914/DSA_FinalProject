package org.example.model.HashMap;

import org.example.model.KDTree.Node;

import java.util.Arrays;

public class POI {
    protected int id;
    protected final int[] coordinates;
    protected String[] services;

    public POI( int x, int y, String[] services) {
        this.coordinates = new int[]{x,y};
        this.services = services;
    }

    public POI(int x, int y) {
        this.coordinates = new int[]{x,y};
    }

    public boolean containsService(String service) {
        for (int i = 0; i < services.length; i++) {
            if (services[i].equalsIgnoreCase(service)) return true;
        }
        return false;
    }

    public POIWithDistance mapToPOIWithDistance(double distance) {
        return new POIWithDistance(id, coordinates, distance);
    }

    public Node mapToNode() {
        return new Node(id, coordinates);
    }

    public int getX() {
        return coordinates[0];
    }
    public int getY() {
        return coordinates[1];
    }


    @Override
    public String toString() {
        return "POI{" +
                "id=" + id +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
