package com.example.testapi.model.Map2D;

public class POILinkedList {
    POIListNode head;
    int size;

    public POILinkedList() {
        head = null;
        size = 0;
    }

    // insert a poi node at the end
    // because we have to check for duplicated poi Id
    public boolean insert(POIListNode poi) {
        if (size == 0) {
            head = poi;
            size = 1;
            return true;
        }
        POIListNode prev = null;
        POIListNode curr = head;
        while (curr != null) {
            if (curr.getY() > poi.getY()) {
                break;
            }
            prev = curr;
            curr = curr.next;
        }

        // Insert at head
        if (prev == null) {
            poi.next = head;
            head = poi;
        } else {
            poi.next = curr;
            prev.next = poi;
        }
        size++;
        return true;
    }

//    // return a poi with the provided id
//    public POIListNode get(String poiId) {
//        POIListNode node = head;
//        while (node != null) {
//            if (node.data.poiId.equals(poiId)) {
//                return node.data;
//            }
//            node = node.next;
//        }
//        return null;
//    }
//
//    // remove a poi with a provided poi id
//    // return true or false if the remove is successful or not
//    public boolean remove(String poiId) {
//        if (size == 0) {
//            return false;
//        }
//        POIListNode parent = null;
//        POIListNode current = head;
//        while (current != null) {
//            if (current.data.poiId.equals(poiId)) {
//                if (current == head) {
//                    // remove head => need to update head
//                    head = head.next;
//                    size--;
//                    return true;
//                }
//                parent.next = current.next;
//                size--;
//                return true;
//            }
//            parent = current;
//            current = current.next;
//        }
//        return false;
//    }
}
