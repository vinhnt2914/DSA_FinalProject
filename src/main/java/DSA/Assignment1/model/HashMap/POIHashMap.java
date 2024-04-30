package DSA.Assignment1.model.HashMap;

import DSA.Assignment1.model.List.ArrayList;

import java.util.List;

public class POIHashMap {
    int N;  // size of the array for the hash table
    private final int DEFAULT_SIZE = 100000000;
    // An array of arraylist of poi;
    // We use an array since we already know the size
    POIArrayList[] hashTable;

    public POIHashMap() {
        hashTable = new POIArrayList[DEFAULT_SIZE];
        N = DEFAULT_SIZE;
    }
    private int hash(int x) {
        return x % N;
    }

    public void populate(List<POI> POIs) {
        POIs.forEach(this::put);
    }

    public void put(POI poi) {
        // Hashing the poi X coordinate will return
        // the appropriate index
        int hash = hash(poi.getX());
        // If there is nothing at the index
        if (hashTable[hash] == null) {
            // Create a chain at the index
            hashTable[hash] = new POIArrayList();
        }
        // Insert POI at current index
        hashTable[hash].insert(poi);
    }

//    public POI get(int key) {
//        // Get the X coordinate
//        int hash = hash(key);
//        // If there is nothing at the index, return null
//        if (hashTable[hash] == null) {
//            return null;
//        }
//        // Else invoke get() on the chain
//        return hashTable[hash].get(hash);
//    }

    public void KNNSearchWithQuickSort(POI target, String service, int boundingSize) {
        long startTime = System.currentTimeMillis();

        int xStart = target.getX() - boundingSize/2;
        int xEnd = target.getX() + boundingSize/2;

        int yStart = target.getY() - boundingSize/2;
        int yEnd = target.getY() + boundingSize/2;

        ArrayList<POIWithDistance> pois = new ArrayList<>();

        for (int i = xStart; i <= xEnd; i++) {
            if (hashTable[i] != null) {
                for (int j = 0; j < hashTable[i].size(); j++) {
                    POI curr = hashTable[i].get(j);
                    if (curr.getY() >= yStart && curr.getY() <= yEnd && curr.containsService(service)) {
                        double distance = Math.sqrt(distanceSquared(curr, target));
                        pois.insert(curr.mapToPOIWithDistance(distance));
                    }
                }
            }
        }

        quickSort(pois, 0, pois.size() - 1, target);

        for (int i = 0; i < 50; i++) {
            if (pois.get(i) == null) break;
            System.out.println(pois.get(i));
        }

        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("POIHashMap KNN Search with quick sort done in: " + time + "ms");
    }

    // Stolen from Tri Dang
    private void quickSort(ArrayList<POIWithDistance> pois, int left, int right, POI target) {
        if (left < right) {
            int partition = partition(pois, left, right, target);
            quickSort(pois, left, partition, target);
            quickSort(pois, partition + 1, right, target);
        }
    }

    private int partition(ArrayList<POIWithDistance> pois, int left, int right, POI target) {
        POIWithDistance pivot = pois.get(left);
        int front = left;
        int back = right;
        while (true) {
            while (pois.get(front).distance() < pivot.distance()) {
                front++;
            }

            while (pois.get(back).distance() > pivot.distance()) {
                back--;
            }

            if (front >= back) {
                return back;
            }

            POIWithDistance temp = pois.get(front);
            pois.set(front, pois.get(back));
            pois.set(back, temp);
            front++;
            back--;
        }
    }

    // Testing purpose
//    public void display() {
//        for (int i = 0; i < 1000000; i++) {
//            System.out.print("At X = " + i + ": ");
//            if (hashTable[i] != null) hashTable[i].display();
//            System.out.println();
//        }
//    }

    // Calculate the squared distance between current POI and target
    private double distanceSquared(POI poi, POI target) {

        double dx = poi.coordinates[0] - target.coordinates[0];
        double dy = poi.coordinates[1] - target.coordinates[1];

        return dx * dx + dy * dy;
    }

}
