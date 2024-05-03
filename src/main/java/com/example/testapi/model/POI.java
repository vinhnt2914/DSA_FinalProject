package com.example.testapi.model;

import com.example.testapi.dto.POIJson;
import com.example.testapi.model.Array.MyArray;
import java.util.Arrays;

public class POI {
    public final int[] coordinates;
    public MyArray<String> services;

    public POI( int x, int y, String... services) {
        this.coordinates = new int[]{x,y};
        this.services = new MyArray<>(10);
        for (String s : services) {
            this.services.insert(s);
        }
    }

    public POI(int x, int y, MyArray<String> services) {
        this.coordinates = new int[]{x,y};
        this.services = services;
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
        return new POIWithDistance(coordinates[0], coordinates[1], services, distance);
    }

    public POIJson mapToPOIJson() {
        return new POIJson(coordinates[0], coordinates[1], services.toArrayList());
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

    public void setServices(MyArray<String> services) {
        this.services = services;
    }
}
