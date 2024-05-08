package com.example.testapi.model;

import com.example.testapi.dto.POIJson;
import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.services.ServiceMapper;

import java.util.Arrays;

public class POI {
    public final int[] coordinates;
    public Byte[] services;

    public POI( int x, int y, String[] serviceArr) {
        this.coordinates = new int[]{x,y};
        this.services = new Byte[serviceArr.length];
        for (int i = 0; i < serviceArr.length; i++) {
            services[i] = ServiceMapper.getInstance().getIndex(serviceArr[i]);
        }
    }

    public POI(int x, int y, Byte[] services) {
        this.coordinates = new int[]{x,y};
        this.services = services;
    }

    public POI(int x, int y) {
        this.coordinates = new int[]{x,y};
        services = new Byte[10];
    }

    public boolean containsService(Byte serviceIndex) {
        for (int i = 0; i < services.length; i++) {
            if (services[i].equals(serviceIndex)) return true;
        }
        return false;
    }

    public POIWithDistance mapToPOIWithDistance(double distance) {
        return new POIWithDistance(coordinates[0], coordinates[1], services, distance);
    }

    public POIJson mapToPOIJson() {
        return new POIJson(coordinates[0], coordinates[1], services);
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
        if (services.length == 0) return null;
        // Loop over the POI's list of services
        for (int i = 0; i < services.length; i++) {
            // If found
            if (services[i] == serviceIndex) {
                Byte removed = services[i];
                shiftLeft(i, services.length);
                return removed;
            }
        }
        // Return null if input service does not exist
        return null;
    }

    private void shiftLeft(int start, int end) {
        for (int i = start; i < end; i++) {
            services[i-1] = services[i];
        }
    }

    public boolean addService(Byte service) {
        // If the poi already have max services
        if (services.length >= 10) return false;
        // Set new length to 10 at max to prevent adding more than 1 service
        int newLength = services.length * 2;
        if (newLength > 10) newLength = 10;
        Byte[] newServices = new Byte[newLength];

        int i = 0;
        while (i < services.length) {
            newServices[i] = services[i];
            i++;
        }

        services = newServices;
        services[i] = service;
        return true;
    }

    public void setServices(Byte[] services) {
        this.services = services;
    }
}
