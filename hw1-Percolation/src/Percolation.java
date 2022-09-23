import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {
    Node[] nodes;
    WeightedQuickUnionUF uf;
    int size;

    private static class Node {
        private Point2D site;
        private Node next;
        private boolean isOpen;
    }

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        nodes = new Node[N * N + 2];
        uf = new WeightedQuickUnionUF(N * N);
        size = N;

        int i = 0;
        for (Node node : nodes) {
            node = new Node();
            node.site = new Point2D(i / N, i % N);
            node.isOpen = false;
            nodes[i] = node;
            i++;
        }

        for (int x = 0; x <= N; x++) {

        }


    }
    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        int index = i * size + j;
        nodes[index].isOpen = true;

        if (index - size >= 0  && nodes[index - size].isOpen) { // up
            uf.union(index, index - size);
        } else if (index + size <=  size * size - 1 && nodes[index + size].isOpen) { // down
            uf.union(index, index + size);
        } else if (index % size == 0 && nodes[index - 1].isOpen) { // left
            uf.union(index, index - 1);
        } else if (index % size == size - 1 && nodes[index + 1].isOpen) { // right
            uf.union(index, index + 1);
        }
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

        Percolation s = new Percolation(3);
        for (Node node : s.nodes) {
            System.out.println(node.site);
        }



        s.open(1, 1);
        System.out.println(s.isOpen(0, 1));
        System.out.println(s.isOpen(1, 1));
        s.open(0, 1);
        System.out.println(s.isOpen(0, 1));

//        Node[] nodes = new Node[9];
//        nodes[0] = new Node();
//        nodes[0].site = new Point2D(1, 1);
//        System.out.println(nodes[0].site);
//        System.out.println(nodes[0].next);
//        System.out.println(nodes[0].isOpen);
    }

}