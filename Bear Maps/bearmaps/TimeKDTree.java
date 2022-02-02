package bearmaps;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.Stopwatch;

import static org.junit.Assert.assertEquals;

public class TimeKDTree {
    private static Random r = new Random(500);
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeKdNearest();
    }
    private static Point randomPt() {
        double x = -1000 + (1000 - (-1000)) * r.nextDouble();
        double y = -1000 + (1000 - (-1000)) * r.nextDouble();
        return new Point(x, y);
    }
    private static List<Point> randomPts(int N) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            points.add(randomPt());
        }
        return points;
    }
    private void testNPointsAndQQueries(int pointCount, int queryCount) {
        List<Point> points = randomPts(pointCount);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);

        List<Point> queries = randomPts(queryCount);
        for (Point p: queries) {
            Point expected = nps.nearest(p.getX(), p.getY());
            Point actual = kd.nearest(p.getX(), p.getY());
            assertEquals(expected, actual);
        }
    }
    public static void timeKDTree() {
        int[] nums = new int[]{31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for (int num: nums) {
            Ns.add(num);
            int swOps = num;
            List<Point> points = randomPts(num);
            Stopwatch sw = new Stopwatch();
            KDTree kd = new KDTree(points);
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(swOps);
        }
        printTimingTable(Ns, times, opCounts);
    }
    public static void timeNaiveNearest() {
        int[] nums = new int[]{125, 250, 500, 1000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for (int num: nums) {
            Ns.add(num);
            NaivePointSet nps = new NaivePointSet(randomPts(num));
            List<Point> queries = randomPts(1000000);
            Stopwatch sw = new Stopwatch();
            for (Point p: queries) {
                Point expected = nps.nearest(p.getX(), p.getY());
            }
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(1000000);
        }
        printTimingTable(Ns, times, opCounts);
    }
    public static void timeKdNearest() {
        int[] nums = new int[]{31250, 62500, 125000, 250000, 500000, 1000000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();
        for (int num: nums) {
            Ns.add(num);
            KDTree kd = new KDTree(randomPts(num));
            List<Point> queries = randomPts(1000000);
            Stopwatch sw = new Stopwatch();
            for (Point p: queries) {
                Point expected = kd.nearest(p.getX(), p.getY());
            }
            double timeInSeconds = sw.elapsedTime();
            times.add(timeInSeconds);
            opCounts.add(1000000);
        }
        printTimingTable(Ns, times, opCounts);
    }
}
