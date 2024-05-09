package com.example.testapi.dto;


import com.example.testapi.services.ServiceMapper;

import java.util.Arrays;

public class POIJson {
    private int x;
    private int y;
    private String[] services;
    public POIJson(int x, int y, byte[] services) {
        this.x = x;
        this.y = y;
        this.services = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            this.services[i] = ServiceMapper.getInstance().getService(i);
        }
    }

    @Override
    public String toString() {
        return "POIJson{" +
                "x=" + x +
                ", y=" + y +
                ", services=" + Arrays.toString(services) +
                '}';
    }
}
