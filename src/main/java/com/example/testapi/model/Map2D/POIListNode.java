package com.example.testapi.model.Map2D;

import com.example.testapi.model.POI;

public class POIListNode extends POI {
    public POIListNode next;
    public POIListNode(int x, int y, byte[] services) {
        super(x, y, services);
        next = null;
    }
}
