package org.example.model.NNLinearSearch;

import org.example.Place;
import org.example.model.KDTree.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class NNLinearSearch {

    public static Node nearestNeighbor(List<Node> nodes, Node target) {
        long startTime = System.currentTimeMillis();

        Node bestNode = null;
        double bestDistance = Double.MAX_VALUE;

        for (Node node: nodes) {
            double currentDistance = distance(node, target);

            if (currentDistance < bestDistance) {
                bestDistance = currentDistance;
                bestNode = node;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("LINEAR NN SEARCH - START AT: " + startTime);
        System.out.println("LINEAR NN SEARCH - END AT: " + endTime);
        System.out.println("LINEAR NN SEARCH USING ITERATION RUN FOR: " + (endTime - startTime) + "ms");

        return bestNode;


    }

    // Calculate distance between 2 points using Euclidean distance
    private static double distance(Node node, Node target) {
        double x1 = node.getX();
        double y1 = node.getY();
        double x2 = target.getX();
        double y2 = target.getY();

        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx*dx + dy*dy);
    }
}
