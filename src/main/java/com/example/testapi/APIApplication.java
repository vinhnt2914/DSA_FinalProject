package com.example.testapi;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.utility.APIDataManager;
import com.example.testapi.utility.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
        DataManager dataManager = new DataManager();
        APIDataManager apiDataManager = new APIDataManager();
        KDTree kdTree = dataManager.createKDTree(1000000);
        MyArray<POIWithDistance> nodeArray = kdTree.kNearestNeighborsWithMap(10000,10000,"School");
        apiDataManager.populatePOIWithDistance(nodeArray);
        nodeArray.display();
    }
}
