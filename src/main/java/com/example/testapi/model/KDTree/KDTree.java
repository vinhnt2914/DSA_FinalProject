package com.example.testapi.model.KDTree;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POIWithDistance;

import java.util.List;

public class KDTree {
    private POINode root;
    // 2-d tree
    private final int DIMENSION = 2;
    private int size;
    private MyArray<POINode> nodeArray;
    public KDTree() {
        size = 0;
    }

    public void populate(List<POINode> nodes) {
        nodes.forEach(this::insert);
    }

    public void insert(POINode node) {
        if (root == null) {
            root = node;
            size++;
            return;
        }

        POINode temp = root;
        int depth = 0;
        while (temp != null) {
            // Get the dividing axis (divide by x or divide by y => either 0 or 1)
            int axis = depth % DIMENSION;
            // Compare the value at the appropriate axis
            // If inserted value < current node value, it's on left insertion
            if (node.coordinates[axis] < temp.coordinates[axis]) {
                // If reached a leaf node
                // Insert new node there
                if (temp.left == null) {
                    temp.left = node;
                    size++;
                    return;
                }
                temp = temp.left;
                // Else it's on right insertion
            } else if (node.coordinates[axis] > temp.coordinates[axis]) {
                // If reach a leaf node
                if (temp.right == null) {
                    temp.right = node;
                    size++;
                    return;
                }
                temp = temp.right;
            }
            // If the coordinate of the current axis is equal,
            // This could potentially be a duplicate
            else {
                // We check if the other axis is also equal as well
                int otherAxis = (depth + 1) % DIMENSION;
                // If duplicate
                if (node.coordinates[otherAxis] == temp.coordinates[otherAxis]) {
                    // Overwrite the services
                    temp.setServices(node.services);
                    // Break here
                    return;
                }
            }
            depth++;
        }
        // This never run
    }

    public POINode search(int x, int y) {
        POINode temp = root;
        POINode target = new POINode(x,y);
        int depth = 0;
        while (target != null) {
            int axis = depth % DIMENSION;
            if (target.coordinates[axis] < temp.coordinates[axis]) {
                // If reached a leaf node
                // Then there is no such node
                if (temp.left == null) {
                    temp.left = target;
                    size++;
                    return null;
                }
                temp = temp.left;
                // Else it's on right side
            } else if (target.coordinates[axis] > temp.coordinates[axis]) {
                // If reach a leaf node
                // Then there is no such node
                if (temp.right == null) {
                    temp.right = target;
                    size++;
                    return null;
                }
                temp = temp.right;
            }
            // If the coordinate of the current axis is equal
            // This could potentially be what we need to find
            else {
                // We check if the other axis is also equal as well
                int otherAxis = (depth + 1) % DIMENSION;
                // If equal
                if (target.coordinates[otherAxis] == temp.coordinates[otherAxis]) {
                    // Return target
                    return temp;
                }
            }
            depth++;
        }
        return null;
    }

    // K-NN search using hash map
    public MyArray<POIWithDistance> KNNSearch(int x, int y, String service, int boundingSize) {
        long startTime = System.currentTimeMillis();

        POINode target = new POINode(x, y);
        MyArray<POIWithDistance> results = new MyArray<>(50);

        findKNN(root, target, 0, boundingSize, service, results);

        long endTime = System.currentTimeMillis();
        System.out.println("KNN Search completed in " + (endTime - startTime) + " ms with " + results.size() + " results found.");
        return results;
    }

    private void findKNN(POINode current, POINode target, int depth, int boundingSize, String service, MyArray<POIWithDistance> results) {
        if (current == null || results.size() >= 50) return;

        int axis = depth % DIMENSION;
        POINode nearerSubtree = (target.coordinates[axis] < current.coordinates[axis]) ? current.left : current.right;
        POINode fartherSubtree = (target.coordinates[axis] < current.coordinates[axis]) ? current.right : current.left;

        findKNN(nearerSubtree, target, depth + 1, boundingSize, service, results);

        if (current.containsService(service) && distanceSquared(current, target) <= boundingSize) {
            results.insert(new POIWithDistance(current.getX(), current.getY(), current.services, current.distance(target)));
        }

        if ((target.coordinates[axis] - current.coordinates[axis]) * (target.coordinates[axis] - current.coordinates[axis]) <= boundingSize) {
            findKNN(fartherSubtree, target, depth + 1, boundingSize, service, results);
        }
    }

    // Compare the distance between n1 to target and n2 to target
    // Return the closer one.
    private POINode closerDistance(POINode n1, POINode n2, POINode target) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;

        double d1 = distanceSquared(n1, target);
        double d2 = distanceSquared(n2, target);

        if (d1 < d2) return n1;
        else return n2;
    }

    // Return Euclidean distance, but it's not sqrt
    private double distanceSquared(POINode n1, POINode n2) {
        // Return INF if the nearest node is already found
        if (nodeArray.contains(n1)) return Double.MAX_VALUE;

        double dx = n1.coordinates[0] - n2.coordinates[0];
        double dy = n1.coordinates[1] - n2.coordinates[1];

        return dx * dx + dy * dy;
    }

}
