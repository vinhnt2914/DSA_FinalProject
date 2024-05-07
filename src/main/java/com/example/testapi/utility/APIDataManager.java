package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.Map2D.Map2D;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

import java.util.ArrayList;
import java.util.List;

public class APIDataManager {
    private static APIDataManager single_APIDataManager = null;
    public List<POI> poiList;
    public List<POIWithDistance> poiWithDistanceList;
    public KDTree kdTree;
    public Map2D poiHashMap;

    private APIDataManager() {
        this.poiList = new ArrayList<>();
        this.poiWithDistanceList = new ArrayList<>();
        DataManager.getInstance().createKDTreeAndMap(100000000);
        kdTree = DataManager.getInstance().kdTree;
        poiHashMap = DataManager.getInstance().poiHashMap;
    }


    public void populatePOI(MyArray<POI> poiArr) {
        poiList = poiArr.toArrayList();
    }

    public void populatePOIWithDistance(MyArray<POIWithDistance> poiWithDistanceArr) {
        poiWithDistanceList = poiWithDistanceArr.toArrayList();
    }

    public static synchronized APIDataManager getInstance()
    {
        if (single_APIDataManager == null)
            single_APIDataManager = new APIDataManager();

        return single_APIDataManager;
    }



}
