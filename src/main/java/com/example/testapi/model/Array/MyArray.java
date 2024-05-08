package com.example.testapi.model.Array;

/**
 * <p>
 *     Custom array class that return the actually nums of occupied index cell
 * </p>
 * */
public class MyArray<T> {
    private static final int DEFAULT_SIZE = 10;
    private T[] items;
    private int ptr; // Hold the value for insertion index
    private final int size;

    public MyArray() {
        this.size = DEFAULT_SIZE;
        items = (T[])new Object[DEFAULT_SIZE];
    }

    public MyArray(int size) {
        this.size = size;
        items = (T[])new Object[size];
    }

    public MyArray(T[] arr) {
        this.size = DEFAULT_SIZE;
        items = (T[])new Object[DEFAULT_SIZE];
        for (int i = 0; i < arr.length; i++) {
            this.insert(arr[i]);
        }
    }

    public boolean insert(T item) {
        // If the array if full
        if (ptr == size) {
            return false;
        }
        items[ptr] = item;
        ptr++;
        return true;
    }

    public T remove(int index) {
        // The chain is empty or we remove from an index that has nothing
        if (ptr == 0 || index > ptr) return null;

        T removedItem = items[index];

        shiftLeft(index + 1);
        return removedItem;
    }

    private void shiftLeft(int start) {
        for (int i = start; i < ptr; i++) {
            items[i-1] = items[i];
        }
        // Since we shift left,
        // One item will be removed
        // As a shift left cannot start from 0 index;
        ptr--;
    }

    public T get(int index) {
        return items[index];
    }

    public int size() {
        return ptr;
    }
    public T[] getItems() {
        return items;
    }

    public boolean contains(T item) {
        for (int i = 0; i < ptr; i++) {
            if (items[i] == item) return true;
        }
        return false;
    }

    public void display() {
        for (int i = 0; i < ptr; i++) {
            System.out.println(String.format("%s: %s", i, items[i]));
        }
    }

    public T[] toArray() {
       T[] res = (T[])new Object[items.length];
        for (int i = 0; i < items.length; i++) {
            res[i] = items[i];
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ptr; i++) {
            if (i == ptr - 1) {
                sb.append(items[i]);

            } else {
                sb.append(items[i]).append(", ");
            }
        }
        return sb.toString();
    }
}
