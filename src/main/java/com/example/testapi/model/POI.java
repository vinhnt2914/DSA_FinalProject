package com.example.testapi.model;

import com.example.testapi.dto.POIJson;
import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.services.ServiceMapper;

import java.util.Arrays;

public class POI {
    public final int[] coordinates;
    public MyArray<Byte> services;

    public POI( int x, int y, Byte... services) {
        this.coordinates = new int[]{x,y};
        this.services = new MyArray<>(10);
        for (Byte s : services) {
            this.services.insert(s);
        }
    }

    public POI(int x, int y, MyArray<Byte> services) {
        this.coordinates = new int[]{x,y};
        this.services = services;
    }

    public POI(int x, int y) {
        this.coordinates = new int[]{x,y};
        services = new MyArray<>(10);
    }

    public boolean containsService(Byte serviceIndex) {
        for (int i = 0; i < services.size(); i++) {
            if (services.contains(serviceIndex)) return true;
        }
        return false;
    }

    public POIWithDistance mapToPOIWithDistance(double distance) {
        return new POIWithDistance(coordinates[0], coordinates[1], services, distance);
    }

    public POIJson mapToPOIJson() {
        return new POIJson(coordinates[0], coordinates[1], services.toArrayList());
    }

    public POINode mapToPOINode() {
        return new POINode(coordinates[0], coordinates[1], services);
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

    public Byte removeService(Byte serviceIndex) {
        // If service list is empty
        if (services.size() == 0) return null;
        // Loop over the POI's list of services
        for (int i = 0; i < services.size(); i++) {
            // If found
            if (services.get(i) == serviceIndex) {
                return services.remove(i);
            }
        }
        // Return null if input service does not exist
        return null;
    }

    public boolean addService(Byte serviceIndex) {
        return services.insert(serviceIndex);
    }

    public void setServices(MyArray<Byte> services) {
        this.services = services;
    }
}
