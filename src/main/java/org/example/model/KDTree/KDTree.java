package org.example.model.KDTree;

import java.util.*;

public class KDTree {
    private Node root;
    private final int DIMENSION = 2;
    private int size;
    private final int MAX_NEIGHBORS = 50;

    public KDTree() {
        size = 0;
    }

    public Node insert(int[] coordinates) {
        if (root == null) {
            root = new Node(coordinates,0);
            size++;
            return root;
        }

        Node node = root;
        int depth = 0;
        while (node != null) {
            // Get the dividing axis (divide by x or divide by y => either 0 or 1)
            int axis = depth % DIMENSION;
            // Compare the value at the appropriate axis
            // If inserted value < current node value, it's on left insertion
            if (coordinates[axis] < node.coordinates[axis]) {
                // If reached a leaf node
                // Insert new node there
                if (node.left == null) {
                    node.left = new Node(coordinates, axis);
                    size++;
                    return node.left;
                }
                node = node.left;
            // Else it's on right insertion
            } else {
                // If reach a leaf node
                if (node.right == null) {
                    node.right = new Node(coordinates, axis);
                    size++;
                    return node.right;
                }
                node = node.right;
            }
            depth++;
        }
        // This never run
        return null;
    }

    public void populate(List<Node> nodes) {
        nodes.forEach(node -> insert(node.coordinates));
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

    private Node nearestNeighbors(Node root, Node target, int depth) {

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
        Node best = closerDistance(root, nearestNeighbor(nextBranch, target, depth+1), target);
        // Check if the bad side could potentially have a solution
        if (distanceSquared(target, best) > Math.pow(target.coordinates[axis] - root.coordinates[axis], 2)) {
            best = closerDistance(best, nearestNeighbor(otherBranch, target, depth+1), target);
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
        double dx = n1.coordinates[0] - n2.coordinates[0];
        double dy = n1.coordinates[1] - n2.coordinates[1];

        return dx * dx + dy * dy;
    }

    public String traversePreOrder(Node root) {

        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(root.coordinates));

        String pointerRight = "└──";
        String pointerLeft = (root.right != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, root.left, root.right != null);
        traverseNodes(sb, "", pointerRight, root.right, false);

        return sb.toString();
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, Node node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(Arrays.toString(node.coordinates));

            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.right != null) ? "├──" : "└──";

            if (node.left == null) {
                sb.append("\n");
                sb.append(paddingForBoth);
                sb.append(pointerLeft); // Print the branch indicating left node is null
                sb.append("NULL"); // Print a placeholder for the left node
            } else {
                traverseNodes(sb, paddingForBoth, pointerLeft, node.left, node.right != null);
            }

            traverseNodes(sb, paddingForBoth, pointerRight, node.right, false);
        }
    }


    public void print() {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }
        System.out.println(traversePreOrder(root));
    }


}
