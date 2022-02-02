package bearmaps;
import java.util.List;

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left, right;
        private double distance;
        Node(Point paramP, boolean orient) {
            p = paramP;
            orientation = orient;
        }
        private double distance(Point pt) {
            double distX = Math.pow((this.p.getX() - pt.getX()), 2);
            double distY = Math.pow((this.p.getY() - pt.getY()), 2);
            double dist = distX + distY;
            this.distance = dist;
            return this.distance;
        }
    }

    public KDTree(List<Point> points) {
        for (Point pt: points) {
            root = add(pt, root, HORIZONTAL);
        }
    }
    private Node add(Point p, Node n, boolean orient) {
        if (n == null) {
            return new Node(p, orient);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePts(p, n.p, orient);
        if (cmp < 0) {
            n.left = add(p, n.left, !orient);
        } else if (cmp >= 0) {
            n.right = add(p, n.right, !orient);
        }
        return n;
    }
    private int comparePts(Point a, Point b, boolean orient) {
        if (orient == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        }
        return Double.compare(a.getY(), b.getY());
    }
    @Override
    public Point nearest(double x, double y) {
        Point ptGoal = new Point(x, y);
        Node best = root;
        Node nearNode = nearest(root, ptGoal, best);
        return nearNode.p;
    }
    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (n.distance(goal) < best.distance(goal)) {
            best = n;
        }
        Node goodSide, badSide;
        int cmp = compare(n, goal, n.orientation);
        if (cmp < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        if (goodBadSide(n, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }
    private int compare(Node n, Point pt, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(pt.getX(), n.p.getX());
        } else {
            return Double.compare(pt.getY(), n.p.getY());
        }
    }
    private boolean goodBadSide(Node n, Point goal, Node best) {
        double bestDist = Point.distance(best.p, goal);
        double badDist;
        if (n.orientation == VERTICAL) {
            Point cmpPoint = new Point(goal.getX(), n.p.getY());
            badDist = Point.distance(cmpPoint, goal);
        } else {
            Point cmpPoint = new Point(n.p.getX(), goal.getY());
            badDist = Point.distance(cmpPoint, goal);
        }
        return bestDist > badDist;
    }

    /** private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (n.distance(goal) < best.distance) {
            best = n;
        }
        best = nearest(n.left, goal, best);
        best = nearest(n.right, goal, best);
        return best;
    } */
    /** @param pt point
     * @return
     * @param x
     * @param y
     */
    private Double nearDist(Point pt, double x, double y) {
        double distX = Math.pow((x - pt.getX()), 2);
        double distY = Math.pow((y - pt.getY()), 2);
        return Math.pow((distX + distY), 0.5);
    }
}
