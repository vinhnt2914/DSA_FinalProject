package org.example.model.KDTree;

// Hold the node and it's distance to target
public class NodeWithDistance {
    Node node;
    double distance;

    public NodeWithDistance(Node node, double distance) {
        this.node = node;
        this.distance = distance;
    }
}
