package com.example.testapi.utility;

import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class KNNLinearSearch {
    public static List<POIWithDistance> findKNearestNeighbors(int x, int y, List<POI> pois, int k) {
        List<POIWithDistance> nearestNeighbors = new ArrayList<>();

        // Iterate through all points to calculate distances and find nearest neighbors
        for (POI p : pois) {
            double distance = distance(x, y, p.getX(), p.getY());
            // If still under 50 pois
            if (nearestNeighbors.size() < k) {
                nearestNeighbors.add(p.mapToPOIWithDistance(distance));
            }
            // If more than 50 pois
            else {
                // Find the farthest neighbor in the current nearest neighbors
                double farthestDistance = 0;
                int farthestIndex = 0;
                for (int i = 0; i < nearestNeighbors.size(); i++) {
                    POIWithDistance curr = nearestNeighbors.get(i);
                    double d = distance(x, y, curr.x(), curr.y());
                    if (d > farthestDistance) {
                        farthestDistance = d;
                        farthestIndex = i;
                    }
                }
                // If the current point is closer than the farthest neighbor, replace it
                if (distance < farthestDistance) {
                    nearestNeighbors.set(farthestIndex, p.mapToPOIWithDistance(distance));
                }
            }
        }

        Collections.sort(nearestNeighbors, Comparator.comparingDouble(POIWithDistance::distance));
        return nearestNeighbors;
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }
}
