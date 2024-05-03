package com.example.testapi.model.NodeMap;
public class Map2D {
    int N;  // size of the array for the hash table
    private final int DEFAULT_SIZE = 1000000;
    MapNodeList[] hashTable;

    public Map2D() {
        N = DEFAULT_SIZE;
        hashTable = new MapNodeList[N];
    }

    private int hash(int id) {
        return id % N;
    }
    public void put(MapNode node) {
        int hash = hash(node.id);
        // no list at this location?
        if (hashTable[hash] == null) {
            hashTable[hash] = new MapNodeList();
        }
        hashTable[hash].insert(node);
    }

    public MapNode get(int id) {
        int hash = hash(id);
        if (hashTable[hash] == null) {
            return null;
        }
        return hashTable[hash].get(id);
    }

    public boolean remove(int id) {
        int hash = hash(id);
        if (hashTable[hash] == null) {
            return false;
        }
        return hashTable[hash].remove(id);
    }

    public boolean contains(int id) {
        return get(id) != null;
    }
}

// Note: the LinkedList class developed in week 3
// can be used instead of constructing a new class
class MapNodeList {
    MapNode head;
    int size;

    public MapNodeList() {
        head = null;
        size = 0;
    }

    // insert a student MapNode at the end
    // because we have to check for duplicated student Id
    public void insert(MapNode node) {
        if (size == 0) {
            head = node;
            size = 1;
            return;
        }
        MapNode parent = null;
        MapNode current = head;
        while (current != null) {
            if (current.id == node.id) {
                // duplicated id
                return;
            }
            parent = current;
            current = current.next;
        }
        parent.next = node;
        size++;
    }

    // return a student with the provided id
    public MapNode get(int id) {
        MapNode node = head;
        while (node != null) {
            if (node.id == id) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    // remove a student with a provided student id
    // return true or false if the remove is successful or not
    public boolean remove(int id) {
        if (size == 0) {
            return false;
        }
        MapNode parent = null;
        MapNode current = head;
        while (current != null) {
            if (current.id == id) {
                if (current == head) {
                    // remove head => need to update head
                    head = head.next;
                    size--;
                    return true;
                }
                parent.next = current.next;
                size--;
                return true;
            }
            parent = current;
            current = current.next;
        }
        return false;
    }


}
