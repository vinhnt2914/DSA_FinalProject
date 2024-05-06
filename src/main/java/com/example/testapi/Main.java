package com.example.testapi;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.Map2D.Map2D;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.utility.APIDataManager;
import com.example.testapi.utility.DataManager;
import com.example.testapi.utility.KNNLinearSearch;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        APIDataManager apiDataManager = APIDataManager.getInstance();

        // TEST KD TREE KNN SEARCH CORRECTNESS
//        int targetX = 1000;
//        int targetY = 1000;
//        String service = "hello";
//        int boundingSize = 100000;
//
//        int halfSide = boundingSize / 2;
//
//        int startX = targetX - halfSide;
//        int endX = targetX + halfSide;
//
//        int startY = targetY - halfSide;
//        int endY = targetY + halfSide;
//
//        if (startX < 0) startX = 0;
//        if (startY < 0) startY = 0;
//        if (endX < 0) endX = 0;
//        if (endY < 0) endY = 0;
//
//        List<POI> poiList = apiDataManager.poiHashMap.rangeQuery(startX, startY, endX, endY);
//        List<POIWithDistance> kdTreeKNNSearchResult = apiDataManager.kdTree.KNNSearch(targetX, targetY, service, boundingSize).toArrayList();
//        List<POIWithDistance> linearKNNSearchResult = KNNLinearSearch.findKNearestNeighbors(targetX, targetY, poiList, boundingSize);
//
//        System.out.println("KNN SEARCH WITH KDTREE");
//        kdTreeKNNSearchResult.forEach(System.out::println);
//
//        System.out.println("KNN SEARCH WITH LINEAR SEARCH");
//        linearKNNSearchResult.forEach(System.out::println);
//
//        System.out.println(kdTreeKNNSearchResult.size());
//        System.out.println(linearKNNSearchResult.size());
//
//        System.out.println(KNNLinearSearch.areListsSimilar(kdTreeKNNSearchResult, linearKNNSearchResult));


        // TEST DUPLICATE INSERTION ON KD-TREE -> OVERWRITE SERVICES ON KD-TREE
//        KDTree kdTree = apiDataManager.kdTree;
//        POI poi = new POI(1,1, "PUSSY", "PUSSY", "PUSSY");
//        kdTree.insert(poi.mapToPOINode());
//        System.out.println("FIRST INSERTION: " + kdTree.search(1,1));
//        POI anotherPOI = new POI(1,1,"DICK", "DICK", "DICK");
//        kdTree.insert(anotherPOI.mapToPOINode());
//        System.out.println("SECOND INSERTION: " + kdTree.search(1,1));
//        POI anotherAnotherPOI = new POI(1,1,"HELLO", "CAK", "CAK");
//        kdTree.insert(anotherAnotherPOI.mapToPOINode());
//        System.out.println("THIRD INSERTION: " + kdTree.search(1,1));

        // TEST DUPLICATION INSERTION ON MAP2D
//        Map2D poiHashMap = apiDataManager.poiHashMap;
//        poiHashMap.put(new POI(1,1, "PUSSY", "PUSSY", "PUSSY"));
//        System.out.println("FIRST INSERTION: " + poiHashMap.find(1,1));
//        poiHashMap.put(new POI(1,1,"DICK", "DICK", "DICK"));
//        System.out.println("SECOND INSERTION: " + poiHashMap.find(1,1));
//        poiHashMap.put(new POI(1,1,"HELLO", "CAK", "CAK"));
//        System.out.println("THIRD INSERTION: " + poiHashMap.find(1,1));

        // TEST MINI KD-TREE
        Map2D poiHashmap = apiDataManager.poiHashMap;
        KDTree kdTree = poiHashmap.createKDTreeFromRange(10000, 10000, 100000);


    }
}
