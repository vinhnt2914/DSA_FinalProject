package org.example.model.KDTree;
import org.example.model.Map2D.Map2D;

import java.util.List;

public class KDTree {
    private Node root;
    // 2-d tree
    private final int DIMENSION = 2;
    private int size;
    private Map2D nodeMap;
    public KDTree() {
        size = 0;
        nodeMap = new Map2D();
    }

    public void populate(List<Node> nodes) {
        nodes.forEach(this::insert);
    }

    public void insert(Node node) {
        if (root == null) {
            root = node;
            size++;
            return;
        }

        Node temp = root;
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
    public void kNearestNeighborsWithMap(Node target) {
        long startTime = System.currentTimeMillis();

        // Run K-NN search 50 times
        for (int i = 1; i <= 50; i++) {
            // Break in case bounding rectangle has less than 50 points
            if (i > this.size) break;
            Node ans = kNearestNeighborsWithMap(root, target, 0);
            System.out.printf("%s: (%s, %s); distance = %.2f\n",
                    i, ans.getX(), ans.getY(), ans.distance(target));
            nodeMap.put(ans.mapToMapNode());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("KD TREE K-NN SEARCH WITH MAP - START AT: " + startTime);
        System.out.println("KD TREE K-NN SEARCH WITH MAP - END AT: " + endTime);
        System.out.println("KD TREE K-NN SEARCH WITH MAP RUN FOR: " + (endTime - startTime) + "ms");
    }

    private Node kNearestNeighborsWithMap(Node root, Node target, int depth) {
        // When reach a leaf node, the recursion stop
        // In case of the best node, not being the leaf node
        // The recursion will be terminated early, which is incorrect
        if (root == null) return null;

        // Get the split axis
        int axis = depth % DIMENSION;

        Node nextBranch, otherBranch;

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
        Node best = closerDistance(root, kNearestNeighborsWithMap(nextBranch, target, depth+1), target);
        // Check if the bad side could potentially have a solution
        if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - root.coordinates[axis], 2)) {
            best = closerDistance(best, kNearestNeighborsWithMap(otherBranch, target, depth+1), target);
        }

        return best;
    }

    // Compare the distance between n1 to target and n2 to target
    // Return the closer one.
    private Node closerDistance(Node n1, Node n2, Node target) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;

        double d1 = distanceSquared(n1, target);
        double d2 = distanceSquared(n2, target);

        if (d1 < d2) return n1;
        else return n2;
    }

    // Return Euclidean distance, but it's not sqrt
    private double distanceSquared(Node n1, Node n2) {

        // Return INF if the nearest node is already found
        if (nodeMap.contains(n1.id)) return Double.MAX_VALUE;

        double dx = n1.coordinates[0] - n2.coordinates[0];
        double dy = n1.coordinates[1] - n2.coordinates[1];

        return dx * dx + dy * dy;
    }



}
