package com.example.testapi;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.utility.DataManager;

public class Main {
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        KDTree kdTree = dataManager.createKDTree(1000000);
        MyArray<POIWithDistance> nodeArray = kdTree.kNearestNeighborsWithMap(10000,10000,"School");
        nodeArray.display();
    }
}
