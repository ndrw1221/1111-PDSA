import java.util.List;
import java.util.ArrayList;
import edu.princeton.cs.algs4.Point2D;


//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

class Cluster {
    public List<double[]> cluster(List<int[]> points, int cluster_num) {

        ArrayList<Point2D> p = new ArrayList<Point2D>();
        ArrayList<Integer> weight = new ArrayList<Integer>();


        for (int[] i : points) {
            p.add(new Point2D(i[0], i[1]));
            weight.add(1);
        }




        while (p.size() > cluster_num) {
            double minDistance = 1e15;
            int[] minPair = new int[2];
            for (int i = 0; i < p.size(); i++) {
                for (int j = i + 1; j < p.size(); j++) {
                    double distance = p.get(i).distanceTo(p.get(j));
                    if (distance < minDistance) {
                        minDistance = distance;
                        minPair[0] = i;
                        minPair[1] = j;
                    }
                }
            }

            double x = ((p.get(minPair[0]).x() * weight.get(minPair[0]) + p.get(minPair[1]).x() * weight.get(minPair[1])) / (weight.get(minPair[0]) + weight.get(minPair[1])));
            double y = ((p.get(minPair[0]).y() * weight.get(minPair[0]) + p.get(minPair[1]).y() * weight.get(minPair[1])) / (weight.get(minPair[0]) + weight.get(minPair[1])));
            int newWeight = weight.get(minPair[0]) + weight.get(minPair[1]);

            p.remove(minPair[1]);
            p.remove(minPair[0]);
            weight.remove(minPair[1]);
            weight.remove(minPair[0]);

            Point2D newPoint = new Point2D(x, y);
            p.add(newPoint);
            weight.add(newWeight);
        }


        ArrayList<double[]> ans = new ArrayList<double[]>();

        for (Point2D point : p) {
            double[] ahoy = {point.x(), point.y()};
            System.out.printf("(%f, %f) ", point.x(), point.y());
            ans.add(ahoy);
        }
        System.out.println(' ');

        ans.sort((point1, point2)-> {
            if (point1[0] > point2[0]) return 1;
            if (point1[0] < point2[0]) return -1;
            if (point1[1] > point2[1]) return 1;
            if (point1[1] < point2[1]) return -1;
            return 0;
        });

        return ans;
    }

    public static void main(String[] args) {
//        test t = new test(args);
//        List<double[]> out = new Cluster().cluster(new ArrayList<int[]>(){{
//            add(new int[]{0,1});
//            add(new int[]{0,2});
//            add(new int[]{3,1});
//            add(new int[]{3,2});
//        }}, 2);
//        for(double[] o: out)
//            System.out.println(Arrays.toString(o));
//    }
    }
}

//    class test {
//        public test(String[] args) {
//            Cluster sol = new Cluster();
//            JSONParser jsonParser = new JSONParser();
//            try (FileReader reader = new FileReader(args[0])) {
//                JSONArray all = (JSONArray) jsonParser.parse(reader);
//                for (Object CaseInList : all) {
//                    JSONArray a = (JSONArray) CaseInList;
//                    int q_cnt = 0, wa = 0, ac = 0;
//                    for (Object o : a) {
//                        q_cnt++;
//                        JSONObject person = (JSONObject) o;
//                        JSONArray point = (JSONArray) person.get("points");
//                        Long clusterNumber = (Long) person.get("cluster_num");
//                        JSONArray arg_ans = (JSONArray) person.get("answer");
//                        int points_x[] = new int[point.size()];
//                        int points_y[] = new int[point.size()];
//                        double Answer_x[] = new double[arg_ans.size()];
//                        double Answer_y[] = new double[arg_ans.size()];
//                        List<double[]> ansClus = new ArrayList<double[]>();
//                        ArrayList<int[]> pointList = new ArrayList<int[]>();
//                        for (int i = 0; i < clusterNumber; i++) {
//                            String ansStr = arg_ans.get(i).toString();
//                            ansStr = ansStr.replace("[", "");
//                            ansStr = ansStr.replace("]", "");
//                            String[] parts = ansStr.split(",");
//                            Answer_x[i] = Double.parseDouble(parts[0]);
//                            Answer_y[i] = Double.parseDouble(parts[1]);
//                        }
//                        for (int i = 0; i < point.size(); i++) {
//                            String ansStr = point.get(i).toString();
//                            ansStr = ansStr.replace("[", "");
//                            ansStr = ansStr.replace("]", "");
//                            String[] parts = ansStr.split(",");
//                            pointList.add(new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])});
//                        }
//                        ansClus = sol.cluster(pointList, Integer.parseInt(clusterNumber.toString()));
//                        if (ansClus.size() != clusterNumber) {
//                            wa++;
//                            System.out.println(q_cnt + ": WA");
//                            break;
//                        } else {
//                            for (int i = 0; i < clusterNumber; i++) {
//                                if (ansClus.get(i)[0] != Answer_x[i] || ansClus.get(i)[1] != Answer_y[i]) {
//                                    wa++;
//                                    System.out.println(q_cnt + ": WA");
//                                    break;
//                                }
//                            }
//                            System.out.println(q_cnt + ": AC");
//                        }
//                    }
//                    System.out.println("Score: " + (q_cnt - wa) + "/" + q_cnt);
//
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//    }