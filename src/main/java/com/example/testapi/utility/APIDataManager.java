package com.example.testapi.utility;

import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.KDTree;
import com.example.testapi.model.Map2D.Map2D;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;

public class APIDataManager {
    private static APIDataManager single_APIDataManager = null;
    public KDTree kdTree;
    public Map2D poiHashMap;

    private APIDataManager() {
        DataManager.getInstance().createKDTreeAndMap(1000000);
        kdTree = DataManager.getInstance().kdTree;
    }

    public static synchronized APIDataManager getInstance()
    {
        if (single_APIDataManager == null)
            single_APIDataManager = new APIDataManager();

        return single_APIDataManager;
    }



}
