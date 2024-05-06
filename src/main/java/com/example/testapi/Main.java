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
        // TEST KD TREE ADJUSTABLE BOUNDING
        KDTree kdTree = apiDataManager.kdTree;
        // Adjust the boundingSize param to see
        MyArray<POIWithDistance> res = kdTree.KNNSearch(100000,100000, "Hello", 50000);
        System.out.println("RESULT: ");
        res.display();




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

////         TEST DUPLICATE INSERTION ON KD-TREE -> OVERWRITE SERVICES ON KD-TREE
//        KDTree kdTree = apiDataManager.kdTree;
//        POI poi = new POI(1,1, "PUSSY", "PUSSY", "PUSSY");
//        kdTree.insert(poi.mapToPOINode());
//        System.out.println("KD TREE FIRST INSERTION: " + kdTree.search(1,1));
//        POI anotherPOI = new POI(1,1,"DICK", "DICK", "DICK");
//        kdTree.insert(anotherPOI.mapToPOINode());
//        System.out.println("KD TREE SECOND INSERTION: " + kdTree.search(1,1));
//        POI anotherAnotherPOI = new POI(1,1,"HELLO", "CAK", "CAK");
//        kdTree.insert(anotherAnotherPOI.mapToPOINode());
//        System.out.println("KD TREE THIRD INSERTION: " + kdTree.search(1,1));
//
////         TEST DUPLICATION INSERTION ON MAP2D
//        Map2D poiHashMap = apiDataManager.poiHashMap;
//        poiHashMap.put(poi);
//        System.out.println("MAP 2D FIRST INSERTION: " + poiHashMap.find(1,1));
//        poiHashMap.put(anotherPOI);
//        System.out.println("MAP 2D SECOND INSERTION: " + poiHashMap.find(1,1));
//        poiHashMap.put(anotherAnotherPOI);
//        System.out.println("MAP 2D THIRD INSERTION: " + poiHashMap.find(1,1));

//        // TEST MINI KD-TREE KNN CORRECTNESS WITH LINEAR KNN SEARCH
//        // TEST BIG KD-TREE KNN CORRECTNESS WITH LINEAR KNN SEARCH
//        int centerX = 4000;
//        int centerY = 4000;
//        int boundingSize = 100000;
//        int halfSize = boundingSize / 2;
//        int startX = centerX - halfSize;
//        int endX = centerX + halfSize;
//        int startY = centerY - halfSize;
//        int endY = centerY + halfSize;
//
//        if (startX < 0) startX = 0;
//        if (startY < 0) startY = 0;
//        if (endX < 0) endX = 0;
//        if (endY < 0) endY = 0;
//
//        Map2D poiHashmap = apiDataManager.poiHashMap;
//        KDTree kdTree1 = poiHashmap.createKDTreeFromRange(centerX, centerY, boundingSize);
//        MyArray<POIWithDistance> res1 = kdTree1.KNNSearch(centerX,centerY,"hello",boundingSize);
//
////        MyArray<POIWithDistance> res2 = apiDataManager.kdTree.KNNSearch(100000,100000,"hello",100000);
//        List<POI> poiList = apiDataManager.poiHashMap.getPOIsFromRange(startX, startY, endX, endY);
//        MyArray<POIWithDistance> res3 = KNNLinearSearch.findKNearestNeighbors(centerX, centerY, poiList, boundingSize);
////        res1.display();
////
////        System.out.println("LINEAR KNN SEARCH - STATUS");
////        System.out.println("START X: " + startX);
////        System.out.println("END X: " + endX);
////        System.out.println("START Y: " + startY);
////        System.out.println("END Y: " + endY);
////        res3.display();
////        findDifferences(res1, res3);
//
//        kdTree1.containsAllPOI(poiList);
    }

    public static void findDifferences(MyArray<POIWithDistance> dataset1, MyArray<POIWithDistance> dataset2) {
        boolean clear = true;

        if (dataset1.size() != dataset2.size()) {
            System.out.println("SIZE NOT EQUAL!");
        }

        for (int i = 0; i < dataset1.size(); i++) {
            boolean similar = true;
            POIWithDistance entry1 = dataset1.get(i);
            POIWithDistance entry2 = dataset2.get(i);
            // If there is a mismatch in either coordinates
            if (entry1.x() != entry2.x() || entry1.y() != entry2.y()) {
                similar = false;
                clear = false;
            }

            if (!similar) {
                System.out.println("Difference found at index " + i + ": " + entry1);
            }
        }
        if (clear) System.out.println("NO DIFFERENCE!");

        System.out.println("THIS THING ACTUALLY RUN");
    }
}
