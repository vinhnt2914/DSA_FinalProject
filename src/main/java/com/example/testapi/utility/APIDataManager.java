package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.ArrayList;
import java.util.List;

public class APIDataManager {
    private List<POI> poiList;
    private List<POIWithDistance> poiWithDistanceList;

    public APIDataManager() {
        this.poiList = new ArrayList<>();
        this.poiWithDistanceList = new ArrayList<>();
    }


    public void populatePOI(MyArray<POI> poiArr) {
        poiList.clear(); // Refresh the list
        for (int i = 0; i < poiArr.size(); i++) {
            poiList.add(poiArr.get(i));
        }
    }

    public void populatePOIWithDistance(MyArray<POIWithDistance> poiWithDistanceArr) {
        poiWithDistanceList.clear(); // Refresh the list
        for (int i = 0; i < poiWithDistanceArr.size(); i++) {
            poiWithDistanceList.add(poiWithDistanceArr.get(i));
        }
    }
}
