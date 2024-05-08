package com.example.testapi.dto;

import java.util.List;

// Just a JSON class for POIWithDistance
public record POIWithDistanceJson(int x, int y, String[] services, double distance) {
}
