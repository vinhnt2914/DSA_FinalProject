package com.example.testapi.model;

import java.util.List;

// Just a JSON class for POIWithDistance
public record POIWithDistanceJSON(int x, int y, List<String> services, double distance) {
}
