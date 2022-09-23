import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;


class Percolation {
    private static WeightedQuickUnionUF uf;
    private static WeightedQuickUnionUF ufWithVirtualNode;
    int size;
    boolean percolated;
    Node[] nodes;
    int[] firsts;
    int[] lasts;
    Point2D[] percolatedRegions;

    private static class Node {
        private Point2D site;
        private Node next;
        private boolean isOpen;
    }

    public Percolation(int N) { // create N-by-N grid, with all sites blocked
        nodes = new Node[N * N];
        uf = new WeightedQuickUnionUF(N * N);
        ufWithVirtualNode = new WeightedQuickUnionUF(N * N);
        size = N;
        firsts = new int[N * N]; // store first node of each connected component (linked list)
        lasts = new int[N * N]; // store last node of each connected component (linked list)
        percolatedRegions = new Point2D[N * N];

        int i = 0;
        for (Node node : nodes) {
            firsts[i] = i;
            lasts[i] = i;
            node = new Node();
            node.site = new Point2D(i / N, i % N);
            node.isOpen = false;
            nodes[i] = node;
            i++;
        }

        for (int x = 1; x < size; x++) {
            ufWithVirtualNode.union(0, x); // union the first row
            ufWithVirtualNode.union(size * size - 1, size * size - 1 - x); // union the last row
        }
    }

    public void open(int i, int j) { // open site (row i, column j) if it is not open already
        int index = i * size + j;
        nodes[index].isOpen = true;

        if (index - size >= 0 && nodes[index - size].isOpen) { // up
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index - size)]];  // index node last -> new node first
            int newFirst = firsts[uf.find(index)];  // index node first is the new first
            int newLast = lasts[uf.find(index - size)]; // new node last is the new last

            uf.union(index, index - size);
            ufWithVirtualNode.union(index, index - size);

            firsts[uf.find(index)] = newFirst;
            lasts[uf.find(index)] = newLast;
        }
        if (index + size < size * size && nodes[index + size].isOpen) { // down
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index + size)]];  // index node last -> new node first
            int newFirst = firsts[uf.find(index)];  // index node first is the new first
            int newLast = lasts[uf.find(index + size)]; // new node last is the new last

            uf.union(index, index + size);
            ufWithVirtualNode.union(index, index + size);

            firsts[uf.find(index)] = newFirst;
            lasts[uf.find(index)] = newLast;
        }
        if (index % size != 0 && nodes[index - 1].isOpen) { // left
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index - 1)]];  // index node last -> new node first
            int newFirst = firsts[uf.find(index)];  // index node first is the new first
            int newLast = lasts[uf.find(index - 1)]; // new node last is the new last

            uf.union(index, index - 1);
            ufWithVirtualNode.union(index, index - 1);

            firsts[uf.find(index)] = newFirst;
            lasts[uf.find(index)] = newLast;
        }
        if (index % size != size - 1 && nodes[index + 1].isOpen) { // right
            nodes[lasts[uf.find(index)]].next = nodes[firsts[uf.find(index + 1)]];  // index node last -> new node first
            int newFirst = firsts[uf.find(index)];  // index node first is the new first
            int newLast = lasts[uf.find(index + 1)]; // new node last is the new last

            uf.union(index, index + 1);
            ufWithVirtualNode.union(index, index + 1);

            firsts[uf.find(index)] = newFirst;
            lasts[uf.find(index)] = newLast;
        }

        if (percolates() && !percolated) {
            percolated = true;

            int x = 0;
            Node node = nodes[firsts[uf.find(index)]];
            while (node.next != null) {
                percolatedRegions[x] = node.site;
                x++;
                node = node.next;
            }

            System.out.println("After:");
            for (int y = 0; y < size * size; y++) {
                System.out.println(percolatedRegions[y]);
            }
            System.out.println("End!!!!!!!!!!!!!!!!!!!!!");
        }

    }

    public boolean isOpen(int i, int j) { // is site (row i, column j) open?
        return nodes[i * size + j].isOpen;
    }

    public boolean isFull(int i, int j) { // is site (row i, column j) full?
        for (int x = 0; x < size; x++) {
            if (uf.connected(x, i * size + j))
                return true;
        }
        return false;

    }

    public boolean percolates() { // does the system percolate?
        return ufWithVirtualNode.connected(0, size * size - 1);
    }

    public Point2D[] PercolatedRegion() {
        // return the array of the sites of the percolated region in order (using Point2D default compare.to)
        // This function should always return the content of the percolated region AT THE MOMENT when percolation just happened.

        int i = 0;
        int count = 0;
        System.out.println("This!!!!!!!!!!!!!!!!!!!!!!");
        while (percolatedRegions[i] != null) {
            System.out.println(percolatedRegions[i]);
            count++;
            i++;
        }

        Point2D[] newPercolatedRegions = new Point2D[count];

        System.out.println("New Percolated Regions");
        for (i = 0; i < count; i++) {
            newPercolatedRegions[i] = percolatedRegions[i];
            System.out.println(newPercolatedRegions[i]);
        }

        Merge.sort(newPercolatedRegions);

        System.out.println("After sort !!!!!!!!!!!!!!!!!!!!!　");
        for (i = 0; i < count; i++) {
            System.out.println(newPercolatedRegions[i]);
        }


        return newPercolatedRegions;
    }

//    public static void test(String[] args){
//        Percolation g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])){
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int count = 0;
//            for(Object CaseInList : all){
//                count++;
//                JSONArray a = (JSONArray) CaseInList;
//                int testSize = 0; int waSize = 0;
//                System.out.print("Case ");
//                System.out.println(count);
//                //Board Setup
//                JSONObject argsSeting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSeting.get("args");
//                g = new Percolation(
//                        Integer.parseInt(argSettingArr.get(0).toString()));
//
//                for (Object o : a)
//                {
//                    JSONObject person = (JSONObject) o;
//
//                    String func =  person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch(func){
//                        case "open":
//                            g.open(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            break;
//                        case "isOpen":
//                            testSize++;
//                            String true_isop = (Boolean)person.get("answer")?"1":"0";
//                            String ans_isop = g.isOpen(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
//                            if(true_isop.equals(ans_isop)){
//                                System.out.println("isOpen : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("isOpen : WA");
//                            }
//                            break;
//                        case "isFull":
//                            testSize++;
//                            String true_isfu =  (Boolean)person.get("answer")?"1":"0";
//                            String ans_isfu = g.isFull(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()))?"1":"0";
//                            if(true_isfu.equals(ans_isfu)){
//                                System.out.println("isFull : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("isFull : WA");
//                            }
//                            break;
//                        case "percolates":
//                            testSize++;
//                            String true_per = (Boolean)person.get("answer")?"1":"0";
//                            String ans_per = g.percolates()?"1":"0";
//                            if(true_per.equals(ans_per)){
//                                System.out.println("percolates : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("percolates : WA");
//                            }
//                            break;
//                        case "PercolatedRegion":
//                            testSize++;
//                            String true_reg = person.get("args").toString();
//                            String reg = "[";
//                            Point2D[] pr = g.PercolatedRegion();
//                            for (int i = 0; i < pr.length; i++) {
//                                reg = reg + ((int)pr[i].x() + "," + (int)pr[i].y());
//                                if(i != pr.length - 1){
//                                    reg =reg + ",";
//                                }
//                            }
//                            reg = reg +"]";
//                            if(true_reg.equals(reg)){
//                                System.out.println("PercolatedRegion : AC");
//                            }else{
//                                waSize++;
//                                System.out.println("PercolatedRegion : WA");
//                            }
//                            break;
//                    }
//
//                }
//                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");
//            }
//        }catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String args[]) {
//        test(args);
        Percolation s = new Percolation(3);
        s.open(1, 1);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.percolates());
        s.open(0, 1);
        s.open(2, 0);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.isFull(0, 1));
        System.out.println(s.isFull(2, 0));
        System.out.println(s.percolates());
        s.open(2, 1);
        System.out.println(s.isFull(1, 1));
        System.out.println(s.isFull(0, 1));
        System.out.println(s.isFull(2, 0));
        System.out.println(s.isFull(2, 1));
        System.out.println(s.percolates());

        s.PercolatedRegion();


    }
}