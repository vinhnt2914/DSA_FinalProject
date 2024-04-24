package org.example.model.KDTree;

// Hold the node and it's distance to target
public class NodeWithDistance {
    Node node;
    double distance;

    public NodeWithDistance(Node node, double distance) {
        this.node = node;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s) = %.2f", node.getX(), node.getY(), distance);
    }
}
