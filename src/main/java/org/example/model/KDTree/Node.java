package org.example.model.KDTree;

import java.util.Arrays;

public class Node {
    int[] coordinates;
    Node left;
    Node right;
    int axis; // Keep track of the split axis
    public Node(int[] coordinates, int axis) {
        this.coordinates = coordinates;
        this.axis = axis;
    }

    public Node(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public int[] getCoordinates() {
        return coordinates;
    }
    public int getX() {
        return getCoordinates()[0];
    }
    public int getY() {
        return getCoordinates()[1];
    }
    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public int getAxis() {
        return axis;
    }

    @Override
    public String toString() {
        return "Node{" +
                "coordinates=" + Arrays.toString(coordinates) +
                '}';
    }

}
