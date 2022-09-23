import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {

    private static class Node {
        private Point2D site;
        private Node next;
        private boolean isOpen;
    }

    Node[] nodes;
    int size;

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        nodes = new Node[N * N];
        size = N;
        int i = 0;
        for (Node node : nodes) {
            node = new Node();
            node.site = new Point2D(i / N, i % N);
            node.next = null;
            node.isOpen = false;
            i++;
        }
    }
    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        nodes[i * size + j].isOpen = true;
    }
    public boolean isOpen(int i, int j) { // is site (row i, column j) open?
        return nodes[i * size + j].isOpen;
    }
    public boolean isFull(int i, int j) { // is site (row i, column j) full?
        return false;

    }
    public boolean percolates() { // does the system percolate?
        return false;

    }
//    public Point2D[] PercolatedRegion() {
//        // return the array of the sites of the percolated region in order (using Point2D default compare.to)
//        // This function should always return the content of the percolated region AT THE MOMENT when percolation just happened.
//
//    }

    public static void main(String args[]) {

//        Percolation s = new Percolation(3);
//        s.open(1, 1);
//        System.out.println(s.isOpen(0, 1));
//        System.out.println(s.isOpen(1, 1));
//        s.open(0, 1);
//        System.out.println(s.isOpen(0, 1));

        Node[] nodes = new Node[9];
        nodes[0] = new Node();
        nodes[0].site = new Point2D(1, 1);
        System.out.println(nodes[0].site);
        System.out.println(nodes[0].next);
        System.out.println(nodes[0].isOpen);
    }

}