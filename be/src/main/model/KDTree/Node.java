package org.example.model.KDTree;

import org.example.model.Map2D.MapNode;

import java.util.Arrays;

public class Node {
    int id;
    int[] coordinates;
    Node left;
    Node right;

    public Node(int id, int[] coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public Node(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public MapNode mapToMapNode() {
        return new MapNode(this.id, this.coordinates);
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
    public double distance(Node target) {
        double dx = this.coordinates[0] - target.coordinates[0];
        double dy = this.coordinates[1] - target.coordinates[1];

        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", coordinates=" + Arrays.toString(coordinates) +
                '}';
    }
}
