package com.example.testapi;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.utility.APIDataManager;
import com.example.testapi.utility.DataManager;
import com.example.testapi.utility.DataManagerUsingScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class APIApplication {
    public static void main(String[] args) {
//        APIDataManager apiDataManager = APIDataManager.getInstance();
        KDTree kdTree = DataManagerUsingScanner.getInstance().createKDTree(100000000);
        SpringApplication.run(APIApplication.class, args);

    }
}
