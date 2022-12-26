import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

import java.util.List;
import java.util.ArrayList;

class Flood {
    public Flood() {};
    //return which village is the latest one flooded
    public int village(int villages, List<int[]> road) {
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(villages);
        for (int[] r : road) {
            G.addEdge(new DirectedEdge(r[0], r[1], r[2]));
        }

        DijkstraSP sp = new DijkstraSP(G, 0);
        int ans = 0;

        for (int i = 0; i < villages; i++) {
            if (sp.hasPathTo(i)) {
                if (sp.distTo(i) > sp.distTo(ans)) {
                    ans = i;
                }
            }
        }

        return ans;
    }

//    public static void main(String[] args) {
//        Flood solution = new Flood();
//        System.out.println(solution.village(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,3});
//            add(new int[]{0,2,6});
//            add(new int[]{1,2,2});
//            add(new int[]{2,1,3});
//            add(new int[]{2,3,3});
//            add(new int[]{3,1,1});
//        }}));
//        System.out.println(solution.village(6, new ArrayList<int[]>(){{
//            add(new int[]{0,1,5});
//            add(new int[]{0,4,3});
//            add(new int[]{1,2,1});
//            add(new int[]{1,3,3});
//            add(new int[]{1,5,2});
//            add(new int[]{2,3,4});
//            add(new int[]{3,2,1});
//            add(new int[]{4,0,2});
//            add(new int[]{4,1,4});
//            add(new int[]{5,0,3});
//        }}));
//    }
}