import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
//import java.util.ArrayList;

class Percolation {
    private static WeightedQuickUnionUF uf;
    private static WeightedQuickUnionUF ufTopConnected;
    private static WeightedQuickUnionUF ufTopAndBottomConnected;
    int size;
    boolean percolated;
    int[] firsts;
    int[] lasts;
    int[] lengths;
    Node[] nodes;
    Point2D[] percolatedRegions;
//    ArrayList<Point2D> percolatedRegions = new ArrayList<Point2D>();


    private static class Node {
        private Point2D site;
        private Node next;
        private boolean isOpen;
    }

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        uf = new WeightedQuickUnionUF(N * N);
        ufTopConnected = new WeightedQuickUnionUF(N * N);
        ufTopAndBottomConnected = new WeightedQuickUnionUF(N * N);
        size = N;
        firsts = new int[N * N]; // store first node of each connected component (linked list)
        lasts = new int[N * N]; // store last node of each connected component (linked list)
        lengths = new int[N * N];
        nodes = new Node[N * N];

        for (int i = 0; i < size * size; i++) {
            nodes[i] = new Node();
            nodes[i].site = new Point2D(i / N, i % N);
            firsts[i] = i;
            lasts[i] = i;
            lengths[i] = 1;
        }

        for (int x = 1; x < size; x++) {
            ufTopConnected.union(0, x); // union the first row
            ufTopAndBottomConnected.union(0, x);
            ufTopAndBottomConnected.union(size * size - 1, size * size - 1 - x); // union the last row
        }
    }

    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        int index = i * size + j;
        nodes[index].isOpen = true;

        if (index - size >= 0 && nodes[index - size].isOpen) { // up
            int findIndex = uf.find(index);
            int findOtherIndex = uf.find(index - size);

            nodes[lasts[findIndex]].next = nodes[firsts[findOtherIndex]];  // index node last -> new node first
            int newLast = lasts[findOtherIndex]; // new node last is the new last
            int oldLengthOne = lengths[findIndex];
            int oldLengthTwo = lengths[findOtherIndex];

            uf.union(index, index - size);
            findIndex = uf.find(index);

            firsts[findIndex] = index;
            lasts[findIndex] = newLast;
            lengths[findIndex] = oldLengthOne + oldLengthTwo;

            ufTopConnected.union(index, index - size);
            ufTopAndBottomConnected.union(index, index - size);
        }
        if (index + size < size * size && nodes[index + size].isOpen && !uf.connected(index, index + size)) { // down
            int findIndex = uf.find(index);
            int findOtherIndex = uf.find(index + size);

            nodes[lasts[findIndex]].next = nodes[firsts[findOtherIndex]];  // index node last -> new node first
            int newLast = lasts[findOtherIndex]; // new node last is the new last
            int oldLengthOne = lengths[findIndex];
            int oldLengthTwo = lengths[findOtherIndex];

            uf.union(index, index + size);
            findIndex = uf.find(index);

            firsts[findIndex] = index;
            lasts[findIndex] = newLast;
            lengths[findIndex] = oldLengthOne + oldLengthTwo;

            ufTopConnected.union(index, index + size);
            ufTopAndBottomConnected.union(index, index + size);
        }
        if (index % size != 0 && nodes[index - 1].isOpen && !uf.connected(index, index - 1)) { // left
            int findIndex = uf.find(index);
            int findOtherIndex = uf.find(index - 1);

            nodes[lasts[findIndex]].next = nodes[firsts[findOtherIndex]];  // index node last -> new node first
            int newLast = lasts[findOtherIndex]; // new node last is the new last
            int oldLengthOne = lengths[findIndex];
            int oldLengthTwo = lengths[findOtherIndex];

            uf.union(index, index - 1);
            findIndex = uf.find(index);

            firsts[findIndex] = index;
            lasts[findIndex] = newLast;
            lengths[findIndex] = oldLengthOne + oldLengthTwo;

            ufTopConnected.union(index, index - 1);
            ufTopAndBottomConnected.union(index, index - 1);
        }
        if (index % size != size - 1 && nodes[index + 1].isOpen && !uf.connected(index, index + 1)) { // right
            int findIndex = uf.find(index);
            int findOtherIndex = uf.find(index + 1);

            nodes[lasts[findIndex]].next = nodes[firsts[findOtherIndex]];  // index node last -> new node first
            int newLast = lasts[findOtherIndex]; // new node last is the new last
            int oldLengthOne = lengths[findIndex];
            int oldLengthTwo = lengths[findOtherIndex];

            uf.union(index, index + 1);
            findIndex = uf.find(index);

            firsts[findIndex] = index;
            lasts[findIndex] = newLast;
            lengths[findIndex] = oldLengthOne + oldLengthTwo;

            ufTopConnected.union(index, index + 1);
            ufTopAndBottomConnected.union(index, index + 1);
        }

        if (!percolated && percolates()) {
            percolated = true;
            Node node = nodes[index];
            percolatedRegions = new Point2D[lengths[uf.find(index)]];

            int x = 0;
            while (node != null) {
//                percolatedRegions.add(node.site);
                percolatedRegions[x] = node.site;
                node = node.next;
                x++;
            }
        }
    }

    public boolean isOpen(int i, int j) { // is site (row i, column j) open?
        return nodes[i * size + j].isOpen;
    }

    public boolean isFull(int i, int j) { // is site (row i, column j) full?
        return ufTopConnected.connected(0, i * size + j);
    }

    public boolean percolates() { // does the system percolate?
        if (size == 1) {
            return isOpen(0,0);
        }
        return ufTopAndBottomConnected.connected(0, size * size - 1);
    }

    public Point2D[] PercolatedRegion() {
        // return the array of the sites of the percolated region in order (using Point2D default compare.to)
        // This function should always return the content of the percolated region AT THE MOMENT when percolation just happened.

//        Point2D[] newPercolatedRegions = new Point2D[percolatedRegions.size()];
//        newPercolatedRegions = percolatedRegions.toArray(newPercolatedRegions);

        Merge.sort(percolatedRegions);

        return percolatedRegions;
    }
}


