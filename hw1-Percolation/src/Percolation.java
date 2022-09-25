import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;

class Percolation {
    private static WeightedQuickUnionUF uf;
    private static WeightedQuickUnionUF ufTopConnected;
    private static WeightedQuickUnionUF ufTopAndBottomConnected;
    int size;
    boolean percolated;
    int[] firsts;
    int[] lasts;
    Node[] nodes;
    ArrayList<Point2D> percolatedRegions = new ArrayList<Point2D>();

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
        nodes = new Node[N * N];

        for (int i = 0; i < size; i++) {
            nodes[i] = new Node();
            nodes[i].site = new Point2D(i / N, i % N);
            nodes[i].isOpen = false;
            firsts[i] = i;
            lasts[i] = i;
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
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index - size)]];  // index node last -> new node first
            int newLast = lasts[uf.find(index - size)]; // new node last is the new last

            uf.union(index, index - size);

            firsts[uf.find(index)] = index;
            lasts[uf.find(index)] = newLast;

            ufTopConnected.union(index, index - size);
            ufTopAndBottomConnected.union(index, index - size);
        }
        if (index + size < size * size && nodes[index + size].isOpen && !uf.connected(index, index + size)) { // down
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index + size)]];  // index node last -> new node first
            int newLast = lasts[uf.find(index + size)]; // new node last is the new last

            uf.union(index, index + size);

            firsts[uf.find(index)] = index;
            lasts[uf.find(index)] = newLast;

            ufTopConnected.union(index, index + size);
            ufTopAndBottomConnected.union(index, index + size);
        }
        if (index % size != 0 && nodes[index - 1].isOpen && !uf.connected(index, index - 1)) { // left
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index - 1)]];  // index node last -> new node first
            int newLast = lasts[uf.find(index - 1)]; // new node last is the new last

            uf.union(index, index - 1);

            firsts[uf.find(index)] = index;
            lasts[uf.find(index)] = newLast;

            ufTopConnected.union(index, index - 1);
            ufTopAndBottomConnected.union(index, index - 1);
        }
        if (index % size != size - 1 && nodes[index + 1].isOpen && !uf.connected(index, index + 1)) { // right
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index + 1)]];  // index node last -> new node first
            int newLast = lasts[uf.find(index + 1)]; // new node last is the new last

            uf.union(index, index + 1);

            firsts[uf.find(index)] = index;
            lasts[uf.find(index)] = newLast;

            ufTopConnected.union(index, index + 1);
            ufTopAndBottomConnected.union(index, index + 1);
        }

        if (percolates() && !percolated) {
            percolated = true;
            Node node = nodes[index];

            while (node != null) {
                percolatedRegions.add(node.site);
                node = node.next;
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

        Point2D[] newPercolatedRegions = new Point2D[percolatedRegions.size()];
        newPercolatedRegions = percolatedRegions.toArray(newPercolatedRegions);

        Merge.sort(newPercolatedRegions);

        return newPercolatedRegions;
    }
}


