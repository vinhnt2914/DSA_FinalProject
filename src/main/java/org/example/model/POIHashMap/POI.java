package org.example.model.POIHashMap;

import org.example.model.Array.MyArray;
import org.example.model.KDTree.Node;

import java.util.Arrays;

public class POI {
    protected int id;
    protected final int[] coordinates;
    protected MyArray<String> services;


    public POI( int x, int y, String... services) {
        this.coordinates = new int[]{x,y};
        this.services = new MyArray<>(10);
        for (String s : services) {
            this.services.insert(s);
        }
    }


    public POI(int x, int y) {
        this.coordinates = new int[]{x,y};
        services = new MyArray<>(10);
    }

    public boolean containsService(String service) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).equalsIgnoreCase(service)) return true;
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
                "coordinates=" + Arrays.toString(coordinates) +
                ", services=" + services +
                '}';
    }

    public String removeService(String service) {
        // If service list is empty
        if (services.size() == 0) return null;
        // Loop over the POI's list of services
        for (int i = 0; i < services.size(); i++) {
            // If found
            if (services.get(i).equalsIgnoreCase(service)) {
                return services.remove(i);
            }
        }
        // Return null if input service does not exist
        return null;
    }

    public boolean addService(String service) {
        return services.insert(service);
    }
}
