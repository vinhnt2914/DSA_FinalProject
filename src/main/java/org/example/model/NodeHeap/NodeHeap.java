package org.example.model.NodeHeap;
import org.example.model.KDTree.Node;
import org.example.model.KDTree.NodeWithDistance;

/**
 * <p>
 *     This is a max heap, means that the root will be the furthest node compare to target
 * </p>
 * */
public class NodeHeap {
    private NodeWithDistance[] heap;
    private int n;
    private int furthestIndex;
    private final int SIZE = 50;
    private Node target;
    public NodeHeap(Node targetNode) {
        heap = new NodeWithDistance[SIZE+1];
        n = 0;
        furthestIndex = -1;
        target = targetNode;
    }
    public NodeHeap() {
    }

    private int parent(int k) {
        return k / 2;
    }

    private int leftChild(int k) {
        return (2 * k)  ;
    }

    private int rightChild(int k) {
        return (2 * k) + 1;
    }


    private void swap(int i, int j) {
        NodeWithDistance temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void downheapify(int k) {
        if (k >= (n / 2) && k <= n)
            return;

        if (heap[k].getDistance() > heap[leftChild(k)].getDistance() ||
                heap[k].getDistance() > heap[rightChild(k)].getDistance()) {

            if (heap[leftChild(k)].getDistance() < heap[rightChild(k)].getDistance()) {
                swap(k, leftChild(k));
                downheapify(leftChild(k));
            } else {
                swap(k, rightChild(k));
                downheapify(rightChild(k));
            }
        }
    }
    private void heapifyUp(int k) {
        NodeWithDistance temp = heap[k];
        while(k>0 && heap[parent(k)] != null && temp.getDistance() < heap[parent(k)].getDistance()){
            heap[k] = heap[parent(k)];
            k = parent(k);
        }
        heap[k] = temp;

    }


    public void insert(NodeWithDistance node) {
        // If the heap is full
        if (n == heap.length - 1) {
            // And the insert node is closer than furthest node
            if (node.getDistance() < heap[furthestIndex].getDistance()) {
                // swap and heapify upward
                heap[furthestIndex] = node;
                heapifyUp(furthestIndex);
            }
            return;
        }

        // Else heap is not full
        heap[++n] = node;
        // If first node insert
        if (n == 1) furthestIndex = n;
        // If insert node is further than furthest node
        else if (node.getDistance() > heap[furthestIndex].getDistance()) {
            // Set furthest index to new node
            furthestIndex = n;
        }

        int current = n;
        heapifyUp(current);
    }

    public NodeWithDistance poll() {
        NodeWithDistance max = heap[1];
        heap[1] = heap[n--];
        downheapify(1);
        return max;
    }

    public void displayClosetToFurthest() {
        for (int i = 1; i <= n; i++) {
            NodeWithDistance node = poll();
            System.out.println(i + ": " + node);
        }
    }

    public void setTarget(Node target) {
        this.target = target;
    }

    public int getN() {
        return n;
    }

    //    public static void main(String[] args) {
//        List<Node> nodes = new ArrayList<>();
//        Node target = new Node(new int[]{5,5});
//        Nodeheap nodeheap = new Nodeheap(target);
//        File file = new File("D:\\DSA_FinalProject\\src\\places_100.txt");
//        System.out.println(file.getAbsolutePath());
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Split the line by comma and remove parenthesis
//                String[] parts = line.split(",");
//                // Parse coordinates
//                int x = Integer.parseInt(parts[0]);
//                int y = Integer.parseInt(parts[1]);
//
//                Node node = new Node(new int[]{x,y});
//                nodes.add(node);
//                nodeheap.insert(node);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(nodes);
//    }
}
