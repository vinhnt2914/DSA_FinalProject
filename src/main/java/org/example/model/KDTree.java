package org.example.model;

import java.util.*;

public class KDTree {
    private Node root;
    private final int DIMENSION = 2;
    private int size;

    public KDTree() {
        size = 0;
    }

    public Node insert(int[] coordinates) {
        if (root == null) {
            root = new Node(coordinates);
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
                    node.left = new Node(coordinates);
                    size++;
                    return node.left;
                }
                node = node.left;
            // Else it's on right insertion
            } else {
                // If reach a leaf node
                if (node.right == null) {
                    node.right = new Node(coordinates);
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

    public void display() {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            System.out.println(curr);
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
    }

    public void print() {
        print(root);
    }

    private void print(Node root)
    {
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node> level = new ArrayList<>();
        List<Node> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (Node n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                        String aa = Arrays.toString(n.coordinates);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null) nn++;
                    if (n.right != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<Node> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int depth) {
        if (node == null) {
            return;
        }

        // Print right child first
        printTree(node.right, depth + 1);

        // Print current node
        for (int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
        System.out.println(Arrays.toString(node.coordinates));

        // Print left child
        printTree(node.left, depth + 1);
    }



    static class Node{
        int[] coordinates;
        Node left;
        Node right;
        public Node(int[] coordinates) {
            this.coordinates = coordinates;
        }

    }

}
