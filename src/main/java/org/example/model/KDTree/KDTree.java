package org.example.model.KDTree;

import org.example.model.Map2D.Map2D;
import org.example.model.NodeHeap.NodeHeap;

import java.util.*;

public class KDTree {
    private Node root;
    private final int DIMENSION = 2;
    private int size;
    private final int MAX_NEIGHBORS = 50;
    private NodeHeap nodeHeap;
    private Map2D nodeMap;
    public KDTree() {
        size = 0;
        nodeMap = new Map2D();
    }

    public Node insert(Node node) {
        if (root == null) {
            root = node;
            size++;
            return root;
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
                    return temp.left;
                }
                temp = temp.left;
            // Else it's on right insertion
            } else {
                // If reach a leaf node
                if (temp.right == null) {
                    temp.right = node;
                    size++;
                    return temp.right;
                }
                temp = temp.right;
            }
            depth++;
        }
        // This never run
        return null;
    }

    public void populate(List<Node> nodes) {
        nodes.forEach(this::insert);
    }

    public Node nearestNeighbor(Node target) {
        long startTime = System.currentTimeMillis();
        Node ans = nearestNeighbor(root, target, 0);

        long endTime = System.currentTimeMillis();
        System.out.println("KD TREE NN SEARCH - START AT: " + startTime);
        System.out.println("KD TREE NN SEARCH - END AT: " + endTime);
        System.out.println("KD TREE NN SEARCH USING RECURSION RUN FOR: " + (endTime - startTime) + "ms");

        return ans;
    }

    private Node nearestNeighbor(Node root, Node target, int depth) {
        if (root == null) return null;

//        System.out.println("Current node: " + root);

        // Get the split axis
        int axis = depth % DIMENSION;

        Node nextBranch, otherBranch;

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
        Node best = closerDistance(root, nearestNeighbor(nextBranch, target, depth+1), target);
        // Check if the bad side could potentially have a solution
        if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - root.coordinates[axis], 2)) {
            best = closerDistance(best, nearestNeighbor(otherBranch, target, depth+1), target);
        }

        return best;
    }

    // Working but bad time complexity, logic is wrong
    public Node nearestNeighborIteration(Node target) {
        long startTime = System.currentTimeMillis();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        Node best = root;
        int depth = 0;

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            int axis = depth % DIMENSION;

            Node nextBranch, otherBranch;
            if (target.coordinates[axis] < node.coordinates[axis]) {
                nextBranch = node.left;
                otherBranch = node.right;
            } else {
                nextBranch = node.right;
                otherBranch = node.left;
            }

            // Compare the distance from the current best node to target
            // With the distance from current node to target
            best = closerDistance(best, node, target);
            depth++;

            // If there could potentially be better points on the bad side
            // Push the bad side if it's not null
            if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - node.coordinates[axis], 2)) {
                if (otherBranch != null) {
                    stack.push(otherBranch);
                }
            }

            // Move onto next branch
            if (nextBranch != null) {
                stack.push(nextBranch);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("KD TREE NN SEARCH - START AT: " + startTime);
        System.out.println("KD TREE NN SEARCH - END AT: " + endTime);
        System.out.println("KD TREE NN SEARCH USING ITERATION RUN FOR: " + (endTime - startTime) + "ms");
        return best;
    }

    public void kNearestNeighbors(Node target) {
        long startTime = System.currentTimeMillis();

        nodeHeap = new NodeHeap(target);
        kNearestNeighbors(root, target, 0);

        long endTime = System.currentTimeMillis();
        System.out.println("KD TREE K-NN SEARCH - START AT: " + startTime);
        System.out.println("KD TREE K-NN SEARCH - END AT: " + endTime);
        System.out.println("KD TREE K-NN SEARCH USING ITERATION RUN FOR: " + (endTime - startTime) + "ms");

//        System.out.println(nodeHeap.size());

        nodeHeap.displayClosetToFurthest();
        System.out.println(nodeHeap.getN());
    }

    private Node kNearestNeighbors(Node root, Node target, int depth) {
        if (root == null) return null;

//        System.out.println("Current node: " + root);

        // Get the split axis
        int axis = depth % DIMENSION;

        Node nextBranch, otherBranch;

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
        Node best = closerDistance(root, kNearestNeighbors(nextBranch, target, depth+1), target);
        // Check if the bad side could potentially have a solution
        if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - root.coordinates[axis], 2)) {
            best = closerDistance(best, kNearestNeighbors(otherBranch, target, depth+1), target);
        }

        NodeWithDistance nodeWithDistance = new NodeWithDistance(root, Math.sqrt(distanceSquared(root, target)));

        nodeHeap.insert(nodeWithDistance);

        return best;
    }

    public void kNearestNeighborsWithMap(Node target) {
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= 50; i++) {
            if (i > this.size) break;
            Node ans = kNearestNeighborsWithMap(root, target, 0);
//            System.out.println(ans);
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

    private Node closerDistance(Node n1, Node n2, Node target) {
        if (n1 == null) return n2;
        if (n2 == null) return n1;

        double d1 = distanceSquared(n1, target);
        double d2 = distanceSquared(n2, target);

        if (d1 < d2) return n1;
        else return n2;
    }

    private double distanceSquared(Node n1, Node n2) {

        // Return INF if the nearest node is already found
        if (nodeMap.contains(n1.id)) return Double.MAX_VALUE;

        double dx = n1.coordinates[0] - n2.coordinates[0];
        double dy = n1.coordinates[1] - n2.coordinates[1];

        return dx * dx + dy * dy;
    }



}
