package org.example.model.List;

import org.example.model.HashMap.POI;

public class ArrayList<T> {
    private static final int DEFAULT_SIZE = 10;
    public T[] items;
    private int ptr;
    private int size;

    public ArrayList() {
        this(DEFAULT_SIZE);
    }

    public ArrayList(int size) {
        this.size = size;
        items = (T[])new Object[DEFAULT_SIZE];
        ptr = 0;
    }

    // Utilize insertion sort, using the array list to store
    // POI with same x coordinate but different y coordinate
    // We can maintain a sorted order of points of a particular x coordinates
    // Imagine a straight line for each x coordinate
    public void insert(T item) {
        // If the array if full
        if (ptr == size) {
            // double the size
            if (size == 0) size = 1;
            else size *= 2;

            resize(size);
        }
        items[ptr] = item;
        ptr++;
    }

    private void resize(int size) {
        T[] newItems = (T[])new Object[size];

        for (int i = 0; i < ptr; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    public void set(int index, T item) {
        items[index] = item;
    }

    public boolean contains(T item) {
        for (int i = 0; i < ptr; i++) {
            if (items[i] == item) return true;
        }
        return false;
    }

    public int size() {
        return ptr;
    }

    public boolean isEmpty() {
        return ptr == 0;
    }

    public T get(int index) {
        return items[index];
    }
}
