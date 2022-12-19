import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.PrimMST;

class Budget {
    public Budget() {
    };
    //return the total costs of the bridges
    public int plan(int island, List<int[]> bridge) {
        EdgeWeightedGraph graph = new EdgeWeightedGraph(island);

        for(int[] array : bridge) {
            graph.addEdge(new Edge(array[0], array[1], array[2]));
        }

        return (int)new PrimMST(graph).weight();
    }

    public static void main(String[] args) {
//        Budget solution = new Budget();
//        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,2});
//            add(new int[]{0,2,4});
//            add(new int[]{1,3,5});
//            add(new int[]{2,1,1});
//        }}));
//        System.out.println(solution.plan(4, new ArrayList<int[]>(){{
//            add(new int[]{0,1,0});
//            add(new int[]{0,2,4});
//            add(new int[]{0,3,4});
//            add(new int[]{1,2,1});
//            add(new int[]{1,3,4});
//            add(new int[]{2,3,2});
//        }}));
    }
}