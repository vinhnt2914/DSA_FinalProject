package com.example.testapi.model.Map2D;

import com.example.testapi.model.POI;

public class POIArrayList {
    private static final int DEFAULT_SIZE = 10;
    public POI[] items;
    private int ptr; // Hold the value for insertion index
    private int size;

    public POIArrayList() {
        this(DEFAULT_SIZE);
    }

    public POIArrayList(int size) {
        this.size = size;
        items = new POI[size];
        ptr = 0;
    }

    // Utilize insertion sort, using the array list to store
    // POI with same x coordinate but different y coordinate
    // We can maintain a sorted order of points of a particular x coordinates
    // Imagine a straight line for each x coordinate
    public void insert(POI poi) {
        // If the array if full
        if (ptr == size) {
            // double the size
            if (size == 0) size = 1;
            else size *= 2;

            resize(size);
        }

        // Insert first POI
        if (ptr == 0) {
            items[ptr] = poi;
        } else {
            int i = ptr - 1;
            // Find the insertion position using a while loop
            while (i >= 0 && items[i].getY() > poi.getY()) {
                i--;
            }
            // Shift elements one position to the right starting from the next position
            for (int j = ptr; j > i + 1; j--) {
                items[j] = items[j - 1];
            }
            // Insert the POI at the correct position (i + 1)
            items[i + 1] = poi;
        }
        ptr++;
    }

    private void resize(int size) {
        POI[] newItems = new POI[size];

        for (int i = 0; i < ptr; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    public int size() {
        return ptr;
    }

    public boolean isEmpty() {
        return ptr == 0;
    }

    // Get a poi at the input index
    public POI get(int index) {
        return items[index];
    }

    // Get a poi with the input Y coordinate
    public POI find(int y) {
        if (ptr == 0) return null;

        POI foundPOI = null;

        // Loop over the array and find the poi if exists
        for (int i = 0; i < items.length; i++) {
            // If found the target
            if (items[i].getY() == y) {
                foundPOI = items[i];
                break;
            }
        }

        return foundPOI;
    }

    public POI remove(int y) {
        // The chain is empty
        if (ptr == 0) return null;

        int removedIndex = -1;
        POI removedPOI = null;
        // Loop over the list of items
        for (int i = 0; i < items.length; i++) {
            // If found the target
            if (items[i].getY() == y) {
                removedIndex = i;
                removedPOI = items[i];
                break;
            }
        }
        shiftLeft(removedIndex + 1);
        return removedPOI;
    }

    private void shiftLeft(int start) {
        for (int i = start; i < ptr; i++) {
            items[i-1] = items[i];
        }
        // Since we shift left,
        // One item will be removed
        // As a shift left cannot start from index 0;
        ptr--;
    }

    public int display() {
        int count = 0;
        for (int i = 0; i < ptr; i++) {
            System.out.print(items[i] + ", ");
            count++;
        }
        System.out.print("END");
        return count;
    }

    public static void main(String[] args) {
        POIArrayList poiArrayList = new POIArrayList();
        POI p1 = new POI(1,3);
        POI p2 = new POI(1,5);
        POI p3 = new POI(1,7);
        POI p4 = new POI(1,2);
        POI p5 = new POI(1,1);
        POI p6 = new POI(1,6);

        poiArrayList.insert(p1);
        poiArrayList.insert(p2);
        poiArrayList.insert(p3);
        poiArrayList.insert(p4);
        poiArrayList.insert(p5);
        poiArrayList.insert(p6);

        poiArrayList.display();
    }


}
