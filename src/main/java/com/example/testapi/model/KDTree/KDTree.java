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
            if (node.getCoordinates()[axis] < temp.coordinates[axis]) {
                // If reached a leaf node
                // Insert new node there
                if (temp.left == null) {
                    temp.left = node;
                    size++;
                    return;
                }
                temp = temp.left;
                // Else it's on right insertion
            } else {
                // If reach a leaf node
                if (temp.right == null) {
                    temp.right = node;
                    size++;
                    return;
                }
                temp = temp.right;
            }
            depth++;
        }
        // This never run
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

        System.out.println("PERFORMING KNN SEARCH - STATUS: ");
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
}
