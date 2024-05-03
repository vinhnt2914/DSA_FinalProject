package com.example.testapi.model;

import java.util.List;

public record POIWithDistanceJSON(int x, int y, List<String> services, double distance) {
}
