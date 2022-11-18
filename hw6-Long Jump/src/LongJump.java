//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;

class BST {
    private Node root;             // root of BST
    private int floorSubtract, ceilSubtract = 0;

    private class Node {
        private Integer key;           // sorted by key
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(Integer key, int size) {
            this.key = key;
            this.size = size;
        }
    }

    public BST() {
    }

    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public void put(Integer key) {
        root = put(root, key);
    }

    private Node put(Node x, Integer key) {
        if (x == null) return new Node(key, key);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key);
        else if (cmp > 0) x.right = put(x.right, key);
        else              x.key   = key;
        x.size = x.key + size(x.left) + size(x.right);
        return x;
    }

    private Node floor(Node x, Integer key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            floorSubtract += size(x.left);
            return x;
        }
        if (cmp <  0) return floor(x.left, key);
        Node t = floor(x.right, key);
        floorSubtract += size(x.left) + x.key;
        if (t != null) return t;
        else return x;
    }

    private Node ceiling(Node x, Integer key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            floorSubtract += size(x.right);
            return x;
        }
        if (cmp < 0) {
            ceilSubtract += size(x.right) + x.key;
            Node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }
        return ceiling(x.right, key);
    }

    public int winnerDistances(int lo, int hi) {
        Node start = root;
        while(start.key < lo || start.key > hi) {
            if (start.key < lo) start = start.right;
            else start = start.left;
            if (start == null) return 0;
        }

        ceilSubtract = 0;
        floorSubtract = 0;
        floor(start, lo);
        ceiling(start, hi);

        return size(start) - ceilSubtract - floorSubtract;
    }

}

class LongJump {
    BST bst = new BST();
    public LongJump(int[] playerList){
        for (int i = 0; i < playerList.length; i++) {
            bst.put(playerList[i]);
        }
    }

    // Add new player in the competition with different distance
    public void addPlayer(int distant) {
        bst.put(distant);
    }

    // return the winners total distances in range[from, to]
    public int winnerDistances(int from, int to) {
        return bst.winnerDistances(from, to);
    }

//    public static void main(String[] args) {

//        test t = new test(args);
//        LongJump solution = new LongJump(new int[]{3,5,9});
//        System.out.println(solution.bst.size());
//        System.out.println(solution.winnerDistances(3,9));
//        solution.addPlayer(100);
//        solution.addPlayer(2);
//        System.out.println(solution.winnerDistances(3,10));

//    }
}

//class test{
//    public test(String[] args) {
//        LongJump g;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])) {
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            int count = 0;
//            for (Object CaseInList : all) {
//                count++;
//                JSONArray a = (JSONArray) CaseInList;
//                int testSize = 0;
//                int waSize = 0;
//                System.out.print("Case ");
//                System.out.println(count);
//                //Board Setup
//                JSONObject argsSetting = (JSONObject) a.get(0);
//                a.remove(0);
//
//                JSONArray argSettingArr = (JSONArray) argsSetting.get("args");
//
//                int[] arr=new int[argSettingArr.size()];
//                for(int k=0;k<argSettingArr.size();k++) {
//                    arr[k] = (int)(long) argSettingArr.get(k);
//                }
//                g = new LongJump(arr);
//
//                for (Object o : a) {
//                    JSONObject person = (JSONObject) o;
//
//                    String func = person.get("func").toString();
//                    JSONArray arg = (JSONArray) person.get("args");
//
//                    switch (func) {
//                        case "addPlayer" -> g.addPlayer(Integer.parseInt(arg.get(0).toString()));
//                        case "winnerDistances" -> {
//                            testSize++;
//                            Integer t_ans = (int)(long)person.get("answer");
//                            Integer r_ans = g.winnerDistances(Integer.parseInt(arg.get(0).toString()),
//                                    Integer.parseInt(arg.get(1).toString()));
//                            if (t_ans.equals(r_ans)) {
//                                System.out.println("winnerDistances : AC");
//                            } else {
//                                waSize++;
//                                System.out.println("winnerDistances : WA");
//                                System.out.println("Your answer : "+r_ans);
//                                System.out.println("True answer : "+t_ans);
//                            }
//                        }
//                    }
//
//                }
//                System.out.println("Score: " + (testSize - waSize) + " / " + testSize + " ");
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//}