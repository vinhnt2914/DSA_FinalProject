package com.example.testapi.dto;

import com.example.testapi.services.ServiceMapper;

import java.util.List;

// Just a JSON class for POIWithDistance
public class POIWithDistanceJson {
    private int x;
    private int y;
    private String[] services;
    private double distance;
    public POIWithDistanceJson(int x, int y, byte[] services, double distance) {
        this.x = x;
        this.y = y;
        this.services = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            this.services[i] = ServiceMapper.getInstance().getService(i);
        }
    }
}
