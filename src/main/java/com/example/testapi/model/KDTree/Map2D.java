package com.example.testapi.model.KDTree;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POIWithDistance;

import java.util.List;

public class Map2D {
    private POINode root;
    // 2-d tree
    private final int DIMENSION = 2;
    private int size;
    private MyArray<POINode> nodeArray;
    public Map2D() {
        size = 0;
    }

    public void populate(List<POINode> nodes) {
        nodes.forEach(this::insert);
    }

    public void insert(POINode node) {
        root = insertRec(root, node, 0);
    }

    private POINode insertRec(POINode current, POINode node, int depth) {
        if (current == null) {
            size++;
            return node;
        }

        int axis = depth % DIMENSION;
        if (node.coordinates[axis] < current.coordinates[axis]) {
            current.left = insertRec(current.left, node, depth + 1);
        } else if (node.coordinates[axis] > current.coordinates[axis]) {
            current.right = insertRec(current.right, node, depth + 1);
        } else {
            if (node.coordinates[(axis + 1) % DIMENSION] != current.coordinates[(axis + 1) % DIMENSION]) {
                current.right = insertRec(current.right, node, depth + 1);
            } else {
                current.setServices(node.services);
            }
        }
        return current;
    }

    public POINode search(int x, int y) {
        return searchRec(root, new int[]{x, y}, 0);
    }

    private POINode searchRec(POINode current, int[] coords, int depth) {
        if (current == null) {
            return null;
        }

        int axis = depth % DIMENSION;
        if (coords[axis] < current.coordinates[axis]) {
            return searchRec(current.left, coords, depth + 1);
        } else if (coords[axis] > current.coordinates[axis]) {
            return searchRec(current.right, coords, depth + 1);
        } else {
            if (coords[(axis + 1) % DIMENSION] == current.coordinates[(axis + 1) % DIMENSION]) {
                return current;
            }
            POINode found = searchRec(current.left, coords, depth + 1);
            if (found == null) {
                found = searchRec(current.right, coords, depth + 1);
            }
            return found;
        }
    }

    public MyArray<POIWithDistance> KNNSearch(int x, int y, String service, int boundingSize) {
        long startTime = System.currentTimeMillis();
    
        int halfSize = boundingSize / 2;
        int startX = x - halfSize;
        int endX = x + halfSize;
        int startY = y - halfSize;
        int endY = y + halfSize;
    
        MyArray<POIWithDistance> results = new MyArray<>(50);
        POINode target = new POINode(x, y);
        MyArray<POINode> visitedNodes = new MyArray<>(50);
    
        searchAndFilter(root, target, 0, startX, endX, startY, endY, service, results, visitedNodes);
    
        long endTime = System.currentTimeMillis();
        System.out.println("Search completed in " + (endTime - startTime) + " ms with " + results.size() + " results found.");
        return results;
    }
    
    private void searchAndFilter(POINode current, POINode target, int depth, int startX, int endX, int startY, int endY, String service, MyArray<POIWithDistance> results, MyArray<POINode> visitedNodes) {
        if (current == null || results.size() >= 50) return;
    
        int axis = depth % DIMENSION;
        boolean inRange = current.getX() >= startX && current.getX() <= endX && current.getY() >= startY && current.getY() <= endY;
        boolean hasService = current.containsService(service);
    
        if (inRange && hasService) {
            double distance = current.distance(target);
            results.insert(new POIWithDistance(current.getX(), current.getY(), current.services, distance));
        }
    
        if (target.coordinates[axis] < current.coordinates[axis]) {
            searchAndFilter(current.left, target, depth + 1, startX, endX, startY, endY, service, results, visitedNodes);
            if (crossBoundaryCheck(target, current, axis, endX, endY)) {
                searchAndFilter(current.right, target, depth + 1, startX, endX, startY, endY, service, results, visitedNodes);
            }
        } else {
            searchAndFilter(current.right, target, depth + 1, startX, endX, startY, endY, service, results, visitedNodes);
            if (crossBoundaryCheck(target, current, axis, startX, startY)) {
                searchAndFilter(current.left, target, depth + 1, startX, endX, startY, endY, service, results, visitedNodes);
            }
        }
    }
    
    private boolean crossBoundaryCheck(POINode target, POINode current, int axis, int boundaryX, int boundaryY) {
        return (axis == 0 && Math.abs(target.getX() - current.getX()) <= boundaryX) ||
               (axis == 1 && Math.abs(target.getY() - current.getY()) <= boundaryY);
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
//        if (nodeArray.contains(n1)) return Double.MAX_VALUE;

        double dx = n1.coordinates[0] - n2.coordinates[0];
        double dy = n1.coordinates[1] - n2.coordinates[1];

        return dx * dx + dy * dy;
    }

    public void update(POINode node) {
        updateRec(root, node, 0);
    }

    private void updateRec(POINode current, POINode node, int depth) {
        if (current == null) {
            return;
        }
        int axis = depth % DIMENSION;
        if (node.coordinates[0] == current.coordinates[0] && node.coordinates[1] == current.coordinates[1]) {
            current.setServices(node.services);
            return;
        }
        if (node.coordinates[axis] < current.coordinates[axis]) {
            updateRec(current.left, node, depth + 1);
        } else {
            updateRec(current.right, node, depth + 1);
        }
    }

    public boolean remove(POINode node) {
        if (root == null) {
            return false;
        }
        root = removeRec(root, node, 0);
        return root != null;
    }

    private POINode removeRec(POINode current, POINode node, int depth) {
        if (current == null) {
            return null;
        }

        int axis = depth % DIMENSION;
        if (node.coordinates[0] == current.coordinates[0] && node.coordinates[1] == current.coordinates[1]) {
            if (current.right != null) {
                POINode min = findMin(current.right, depth);
                POINode newNode = new POINode(min.getX(), min.getY(), min.services);
                newNode.left = current.left;
                newNode.right = removeRec(current.right, min, depth);
                return newNode;
            } else if (current.left != null) {
                return current.left;
            } else {
                return null;
            }
        } else if (node.coordinates[axis] < current.coordinates[axis]) {
            current.left = removeRec(current.left, node, depth);
        } else {
            current.right = removeRec(current.right, node, depth);
        }
        return current;
    }

    private POINode findMin(POINode current, int depth) {
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

}
