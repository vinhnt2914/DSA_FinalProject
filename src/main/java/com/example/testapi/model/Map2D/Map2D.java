package com.example.testapi.model.Map2D;

import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.POI;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     A Hashmap to store POI, using the X coordinates as Key.
 *     Sorted by X coordinates, not Y
 * </p>
 * */
public class Map2D {
    int N;  // size of the array for the hash table
    private final int DEFAULT_SIZE = 100000000;
    // An array of arraylist of poi;
    // We use an array since we already know the size
    POIArrayList[] hashTable;

    public Map2D(int size) {
        hashTable = new POIArrayList[size];
        N = size;
    }
    public Map2D() {
        hashTable = new POIArrayList[DEFAULT_SIZE];
        N = DEFAULT_SIZE;
    }

    private int hash(int x) {
        return x % N;
    }

    public void put(POI poi) {
        // Hashing the poi X coordinate will return
        // the appropriate index
        int hash = hash(poi.getX());
        // If there is nothing at the index
        if (hashTable[hash] == null) {
            // Create a chain at the index
            hashTable[hash] = new POIArrayList();
        }
        // Insert POI at current index
        hashTable[hash].insert(poi);
    }

    // Bulk putting
    public void put(POI... pois) {
        for (POI p : pois) {
            put(p);
        }
    }

    public void populateFromPOINode(List<POINode> nodes) {
        for (POINode node : nodes) {
            put(node.mapToPOI());
        }
    }

    public boolean contains(int x, int y) {
        return find(x, y) == null;
    }

    // Find the poi with the input X and Y
    public POI find(int x, int y) {
        // Get the X coordinate
        int hash = hash(x);
        // If there is nothing at the index, return null
        if (hashTable[hash] == null) {
            return null;
        }
        // Else invoke find() on the chain
        return hashTable[hash].find(y);
    }

    // Remove method with x and y passed as params
    public POI remove(int x, int y) {
        int hash = hash(x);
        POI removed = null;
        // If there is nothing at the index, return null
        if (hashTable[hash] == null) {
            return null;
        } else {
            removed = hashTable[hash].remove(y);
        }
        return removed;
    }

    // Remove method with POI passed as params
    public POI remove(POI poi) {
        int hash = hash(poi.getX());
        POI removed = null;
        // If there is nothing at the index, return null
        if (hashTable[hash] == null) {
            return null;
        } else {
            removed = hashTable[hash].remove(poi.getY());
        }
        return removed;
    }

    public String removeServiceFromPOI(int x, int y, String service) {
        int hash = hash(x);
        String removed = null;

        // If there is no such POI with input x and y
        if (hashTable[hash] == null) {
            return removed;
        } else {
            // Get the POI from the chain
            POI poi = hashTable[hash].find(y);
            removed = poi.removeService(service);
        }
        return removed;
    }

    public boolean addServiceToPOI(int x, int y, String service) {
        int hash = hash(x);

        // If there is no such POI with input x and y
        if (hashTable[hash] == null) {
            return false;
        } else {
            // Get the POI from the chain
            POI poi = hashTable[hash].find(y);
            return poi.addService(service);
        }
    }

    // Only purpose is for testing
    // Ignore this, not an API
    public void display(int range) {
        for (int i = 0; i < range; i++) {
            if (hashTable[i] != null) {
                System.out.print("At X = " + i + ": ");
                hashTable[i].display();
                System.out.println();
            }
        }
    }

    public List<POI> rangeQuery(int startX, int startY, int endX, int endY) {
        List<POI> poiList = new ArrayList<>();
        for (int i = startX; i <= endX; i++) {
            // If there is a chain
            if (hashTable[i] != null) {
                for (int j = 0; j < hashTable[i].size(); j++) {
                    POI curr = hashTable[i].get(j);
                    if (curr.getY() >= startY && curr.getY() <= endY) {
                        poiList.add(curr);
                    }
                }
            }
        }
        return poiList;
    }

//    public KDTree createKDTreeFromRange(int x, int y, int boundingWidth) {
//        double halfSide = boundingWidth / 2.0;
//
//        double startX = Math.floor(x - halfSide);
//        double endX = Math.floor(x + halfSide);
//
//        double startY = y - halfSide;
//        double endY = y + halfSide;
//
//        for (int i = startX; i <= endX; i++) {
//
//        }
//
//
//        KDTree kdTree = new KDTree();
//
//        return kdTree;
//    }

}
