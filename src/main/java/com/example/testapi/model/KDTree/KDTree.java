package com.example.testapi.model.KDTree;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;


public class KDTree {
    private POINode root;
    private final int DIMENSION = 2;
    private int size;
    private MyArray<POINode> nodeArray;
    public KDTree() {
        size = 0;
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
        long startTime = System.currentTimeMillis();

        int halfSize = boundingSize / 2;

        int startX = x - halfSize;
        int endX = x + halfSize;
        int startY = y - halfSize;
        int endY = y + halfSize;

        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;
        if (endX < 0) endX = 0;
        if (endY < 0) endY = 0;

        System.out.println("PERFORMING KNN SEARCH WITH KDTREE - STATUS: ");
        System.out.println("TARGET: (" + x + ", " + y + ")");
        System.out.println("START X: " + startX);
        System.out.println("END X: " + endX);
        System.out.println("START Y: " + startY);
        System.out.println("END Y: " + endY);

        MyArray<POIWithDistance> res = new MyArray<>(50);
        POINode target = new POINode(x,y);
        nodeArray = new MyArray<>(50);

        // Run K-NN search 50 times
        for (int i = 1; i <= 50; i++) {
            // Break in case bounding rectangle has less than 50 points
            if (i > this.size) break;
            POINode ans = KNNSearch(root, target, 0);

            // If the poi is out of bound, then all poi returned after this will obviously be out of bound as well
            // We can break here
            if (ans.getX() < startX || ans.getX() > endX || ans.getY() < startY || ans.getY() > endY) {
                System.out.println("FOUND AN OUT OF BOUND POI");
                System.out.println(ans);
                return res;
            }

            POIWithDistance poiWithDistance = ans.mapToPOIWithDistance(ans.distance(target));
            res.insert(poiWithDistance);

            nodeArray.insert(ans);

        }

        long endTime = System.currentTimeMillis();
        System.out.println("KD TREE K-NN SEARCH WITH MAP - START AT: " + startTime);
        System.out.println("KD TREE K-NN SEARCH WITH MAP - END AT: " + endTime);
        System.out.println("KD TREE K-NN SEARCH WITH MAP RUN FOR: " + (endTime - startTime) + "ms");
        return res;
    }

    private POINode KNNSearch(POINode root, POINode target, int depth) {
        // When reach a leaf node, the recursion stop
        // In case of the best node, not being the leaf node
        // The recursion will be terminated early, which is incorrect
        if (root == null) return null;

        // Get the split axis
        int axis = depth % DIMENSION;

        POINode nextBranch, otherBranch;

        // Decide good side and bad side
        if (target.coordinates[axis] < root.coordinates[axis]) {
            nextBranch = root.left;
            otherBranch = root.right;
        } else {
            nextBranch = root.right;
            otherBranch = root.left;
        }

        // Get the best node using recursion
        // It keeps going until reach a leaf node then compare it to find the best node
        // Then traverse backward and compare with parent node to see if the parent is better
        POINode best = closerDistance(root, KNNSearch(nextBranch, target, depth + 1), target);
        // Check if the bad side could potentially have a solution
        if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - root.coordinates[axis], 2)) {
            best = closerDistance(best, KNNSearch(otherBranch, target, depth + 1), target);
        }
        return best;
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
