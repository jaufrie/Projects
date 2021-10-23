package bearmaps;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Random;

public class KDTreeTest {
    private static Random r = new Random(500);
    private static KDTree lectureTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5, p6, p7));
        return kd;
    }
    @Test
    public void test15N() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 2);
        Point p4 = new Point(4, 5);
        Point p5 = new Point(3, 3);
        Point p6 = new Point(1, 5);
        Point p7 = new Point(4, 4);
        Point p8 = new Point(10, 9);
        Point p9 = new Point(-3, 0);
        Point p10 = new Point(7, 15);
        Point p11 = new Point(7, 15);
        Point p12 = new Point(-3, 10);
        Point p13 = new Point(20, -15);
        Point p14 = new Point(0, -1);
        Point p15 = new Point(2, 11);
        List<Point> points = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9,
                p10, p11, p12, p13, p14, p15);
        NaivePointSet nps = new NaivePointSet(points);
        KDTree kd = new KDTree(points);
        Point expected = nps.nearest(2, -5);
        Point actual = kd.nearest(2, -5);
        assertEquals(expected, actual);
    }

    private static void doubleTree() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);
        KDTree kd = new KDTree(List.of(p1, p2));
    }
    @Test
    public void testNearestDemo() {
        KDTree kd = lectureTree();
        Point actual = kd.nearest(0, 7);
        Point expected = new Point(1, 5);
        assertEquals(expected, actual);
    }
    private Point randomPt() {
        double x = -1000 + (1000 - (-1000)) * r.nextDouble();
        double y = -1000 + (1000 - (-1000)) * r.nextDouble();
        return new Point(x, y);
    }
    private List<Point> randomPts(int N) {
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

    @Test
    public void random1000PtTest() {
        testNPointsAndQQueries(15, 5);
    }
    @Test
    public void random10000PtTest() {
        testNPointsAndQQueries(10000, 2000);
    }
    @Test
    public void random20000PointTest() {
        testNPointsAndQQueries(20000, 5000);
    }
}
