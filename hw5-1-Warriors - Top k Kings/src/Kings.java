import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MaxPQ;


//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

class King implements Comparable<King> {
    // optional, for reference only
    int Strength;
    int Range;
    int Index;
    boolean IsKing;
    King(int str,int rng, int i){
        Strength=str;
        Range=rng;
        Index=i;
        IsKing = true;
    }

    @Override
    public int compareTo(King o) {
        if (this.Strength == o.Strength) {
            return o.Index - this.Index;
        }
        return this.Strength - o.Strength;
    }
}

class Kings {
    MaxPQ<King> finalKings = new MaxPQ<King>();
    public Kings(int[] strength, int[] range) {
        // Given the attributes of each warrior
        King[] kings = new King[strength.length];
        for (int i = 0; i < strength.length; i++) {
            kings[i] = new King(strength[i], range[i], i);
        }

        Stack<King> leftToRightStack = new Stack<King>();
        Stack<King> rightToLeftStack = new Stack<King>();

        for (int i = 0; i < kings.length; i++) {
            while (!leftToRightStack.isEmpty() && leftToRightStack.peek().Strength < kings[i].Strength) {
                if (kings[i].Range >= (kings[i].Index - leftToRightStack.peek().Index)) {
                    leftToRightStack.peek().IsKing = false;
                }
                leftToRightStack.pop();
            }
            leftToRightStack.push(kings[i]);

            while (!rightToLeftStack.isEmpty() && rightToLeftStack.peek().Strength < kings[kings.length - 1 - i].Strength) {
                if (kings[kings.length - 1 - i].Range >= (rightToLeftStack.peek().Index - kings[kings.length - 1 - i].Index)) {
                    rightToLeftStack.peek().IsKing = false;
                }
                rightToLeftStack.pop();
            }
            rightToLeftStack.push(kings[kings.length - 1 - i]);
        }

        for (int i = 0; i < kings.length; i++) {
            if (kings[i].IsKing) {
                finalKings.insert(kings[i]);
            }
        }
    }

    public int[] topKKings(int k) {
        if (k > finalKings.size()) {
            k = finalKings.size();
        }

        int[] answer = new int[k];

        for (int i = 0; i < k; i++) {
                answer[i] = finalKings.max().Index;
                finalKings.delMax();
        }

        return answer;
    }

    public static void main(String[] args) {
//        test t = new test(args);
//        Kings sol = new Kings(new int[] {1, 2, 2, 1}
//                , new int[] { 0, 0, 0, 0});
//
//        System.out.println(Arrays.toString(sol.topKKings(3)));
    }
}

//class test {
//    public test(String[] args) {
//        Kings sol;
//        JSONParser jsonParser = new JSONParser();
//        try (FileReader reader = new FileReader(args[0])) {
//            JSONArray all = (JSONArray) jsonParser.parse(reader);
//            for (Object CaseInList : all) {
//                JSONArray a = (JSONArray) CaseInList;
//                int q_cnt = 0, wa = 0, ac = 0;
//                for (Object o : a) {
//                    q_cnt++;
//                    JSONObject person = (JSONObject) o;
//                    JSONArray arg_str = (JSONArray) person.get("strength");
//                    JSONArray arg_rng = (JSONArray) person.get("attack_range");
//                    Long arg_k = (Long) person.get("k");
//                    JSONArray arg_ans = (JSONArray) person.get("answer");
//                    int STH[] = new int[arg_str.size()];
//                    int RNG[] = new int[arg_str.size()];
//                    int k = Integer.parseInt(arg_k.toString());
//
//                    int Answer[] = new int[arg_ans.size()];
//                    int Answer_W[] = new int[arg_ans.size()];
//                    for (int i = 0; i < arg_ans.size(); i++) {
//                        Answer[i] = (Integer.parseInt(arg_ans.get(i).toString()));
//                    }
//                    for (int i = 0; i < arg_str.size(); i++) {
//                        STH[i] = (Integer.parseInt(arg_str.get(i).toString()));
//                        RNG[i] = (Integer.parseInt(arg_rng.get(i).toString()));
//                    }
//                    sol = new Kings(STH, RNG);
//                    Answer_W = sol.topKKings(k);
//                    for (int i = 0; i < arg_ans.size(); i++) {
//                        if (Answer_W[i] == Answer[i]) {
//                            if (i == arg_ans.size() - 1) {
//                                System.out.println(q_cnt + ": AC");
//                            }
//                        } else {
//                            wa++;
//                            System.out.println(q_cnt + ": WA");
//                            break;
//                        }
//                    }
//
//                }
//                System.out.println("Score: " + (q_cnt - wa) + "/" + q_cnt);
//
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
