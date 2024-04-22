package org.example;

import org.example.model.KDTree.KDTree;

public class TreeTest {
    public static void main(String[] args) {
        KDTree tree = new KDTree();

        // Inserting points into the KD tree
        tree.insert(new int[]{6,2});
        tree.insert(new int[]{7,1});
        tree.insert(new int[]{2,9});
        tree.insert(new int[]{3,6});
        tree.insert(new int[]{4,8});
        tree.insert(new int[]{8,4});
        tree.insert(new int[]{5,3});
        tree.insert(new int[]{1,5});
        tree.insert(new int[]{9,5});


        // Display the KD tree structure
        tree.print();
    }
}
