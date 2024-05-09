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
            return searchRec(current.right, coords, depth + 1);
        }
    }

    // K-NN search using hash map
    public MyArray<POIWithDistance> KNNSearch(int x, int y, String service, int boundingSize) {
        MyArray<POIWithDistance> results = new MyArray<>(50);
        POINode target = new POINode(x, y);
        searchWithService(root, target, 0, service, results, boundingSize);
        return results;
    }

    private void searchWithService(POINode current, POINode target, int depth, String service, MyArray<POIWithDistance> results, int boundingSize) {
        if (current == null || results.size() >= 50) {
            return;
        }

        int axis = depth % DIMENSION;
        double dist = current.distance(target);
        if (dist <= boundingSize && current.containsService(service)) {
            results.insert(new POIWithDistance(current.getX(), current.getY(), current.services, dist));
        }

        POINode next = (target.coordinates[axis] < current.coordinates[axis]) ? current.left : current.right;
        POINode opposite = (target.coordinates[axis] < current.coordinates[axis]) ? current.right : current.left;

        searchWithService(next, target, depth + 1, service, results, boundingSize);

        if (Math.pow((target.coordinates[axis] - current.coordinates[axis]), 2) < boundingSize) {
            searchWithService(opposite, target, depth + 1, service, results, boundingSize);
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
