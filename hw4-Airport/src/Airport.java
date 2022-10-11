import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.GrahamScan;


class Airport { // Output the smallest average distance with optimal selection of airport location.
    public double airport(List<int[]> houses) {
        Point2D[] housePoints = new Point2D[houses.size()];
        ArrayList<Point2D> housePointsOnConvexHull = new ArrayList<Point2D>();
        double totalDistance = 0;
        double smallestTotalDistance = 1e10;
        double avgDistance = 0;
        int index = 0;

        // Convert arraylist of integer array of houses to array of Point2D
        for (int[] house: houses) {
            housePoints[index] = new Point2D(house[0], house[1]);
            index++;
        }

        // Store all house point2Ds into an ArrayList
        for (Point2D point2D : new GrahamScan(housePoints).hull()) {
            housePointsOnConvexHull.add(point2D);
        }

        System.out.println(housePointsOnConvexHull);

        for (int i = 0; i < housePointsOnConvexHull.size(); i++) {
            totalDistance = 0;
            double x1, x2, y1, y2;
            if (i == housePointsOnConvexHull.size() - 1) {
                System.out.println(i + "up");
                x1 = housePointsOnConvexHull.get(i).x();
                y1 = housePointsOnConvexHull.get(i).y();
                x2 = housePointsOnConvexHull.get(0).x();
                y2 = housePointsOnConvexHull.get(0).y();
            } else {
                System.out.println(i + "down");
                x1 = housePointsOnConvexHull.get(i).x();
                y1 = housePointsOnConvexHull.get(i).y();
                x2 = housePointsOnConvexHull.get(i+1).x();
                y2 = housePointsOnConvexHull.get(i+1).y();
            }

            for (Point2D housePoint:housePoints) {
                double x0 = housePoint.x();
                double y0 = housePoint.y();
                totalDistance += Math.abs((x2-x1)*(y1-y0)-(x1-x0)*(y2-y1))/Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
            }
            if (totalDistance < smallestTotalDistance) {
                smallestTotalDistance = totalDistance;
            }
        }

        avgDistance = smallestTotalDistance / houses.size();

        return avgDistance;
    }

//    public static void main(String[] args) {
//
//        System.out.println(new Airport().airport(new ArrayList<int[]>(){{
//            add(new int[]{0,0});
//            add(new int[]{1,0});
//            add(new int[]{0,1});
//        }}));
//    }
}
