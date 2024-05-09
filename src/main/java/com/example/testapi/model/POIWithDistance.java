package com.example.testapi.model;

import com.example.testapi.dto.POIWithDistanceJson;
import com.example.testapi.model.Array.MyArray;
import com.example.testapi.services.ServiceMapper;

import java.util.Arrays;

// Record for attaching the distance to target into POI
public record POIWithDistance(int x, int y, byte[] services, double distance) {
    public POIWithDistanceJson mapToJSON() {
        return new POIWithDistanceJson(x, y, services, distance);
    }

    private String[] getServiceString() {
        String[] res = new String[services.length];
        for (int i = 0; i < services.length; i++) {
            res[i] = ServiceMapper.getInstance().getService(i);
        }
        return res;
    }
}
