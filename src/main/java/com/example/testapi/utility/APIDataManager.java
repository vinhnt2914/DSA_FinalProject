package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.ArrayList;
import java.util.List;

public class APIDataManager {
    private static APIDataManager single_APIDataManager = null;
    public List<POI> poiList;
    public List<POIWithDistance> poiWithDistanceList;

    private APIDataManager() {
        this.poiList = new ArrayList<>();
        this.poiWithDistanceList = new ArrayList<>();
    }


    public void populatePOI(MyArray<POI> poiArr) {
        poiList = poiArr.toArrayList();
//        poiList.clear(); // Refresh the list
//        for (int i = 0; i < poiArr.size(); i++) {
//            poiList.add(poiArr.get(i));
//        }
    }

    public void populatePOIWithDistance(MyArray<POIWithDistance> poiWithDistanceArr) {
        poiWithDistanceList = poiWithDistanceArr.toArrayList();
//        poiWithDistanceList.clear(); // Refresh the list
//        for (int i = 0; i < poiWithDistanceArr.size(); i++) {
//            poiWithDistanceList.add(poiWithDistanceArr.get(i));
//        }
    }



    public static synchronized APIDataManager getInstance()
    {
        if (single_APIDataManager == null)
            single_APIDataManager = new APIDataManager();

        return single_APIDataManager;
    }

}
