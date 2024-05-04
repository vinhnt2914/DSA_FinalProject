package com.example.testapi;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.utility.APIDataManager;
import com.example.testapi.utility.DataManager;
import com.example.testapi.utility.KNNLinearSearch;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        APIDataManager apiDataManager = APIDataManager.getInstance();
        int targetX = 1000;
        int targetY = 1000;
        String service = "hello";
        int boundingSize = 100000;

        int halfSide = boundingSize / 2;

        int startX = targetX - halfSide;
        int endX = targetX + halfSide;

        int startY = targetY - halfSide;
        int endY = targetY + halfSide;

        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;
        if (endX < 0) endX = 0;
        if (endY < 0) endY = 0;


        List<POI> poiList = apiDataManager.poiHashMap.rangeQuery(startX, startY, endX, endY);
        List<POIWithDistance> kdTreeKNNSearchResult = apiDataManager.kdTree.KNNSearch(targetX, targetY, service, boundingSize).toArrayList();
        List<POIWithDistance> linearKNNSearchResult = KNNLinearSearch.findKNearestNeighbors(targetX, targetY, poiList, boundingSize);


        System.out.println("KNN SEARCH WITH KDTREE");
        kdTreeKNNSearchResult.forEach(System.out::println);

        System.out.println("KNN SEARCH WITH LINEAR SEARCH");
        linearKNNSearchResult.forEach(System.out::println);
    }
}
