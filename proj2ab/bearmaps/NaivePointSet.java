package bearmaps;
import java.util.List;

public class NaivePointSet implements PointSet {
    private List<Point> pts;
    public NaivePointSet(List<Point> points) {
        pts = points;
    }
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        Point resultPt = pts.get(0);
        Double nearestDist = Point.distance(resultPt, goal);
        for (Point pt: pts) {
            Double compareDist = Point.distance(pt, goal);
            if (compareDist < nearestDist) {
                nearestDist = compareDist;
                resultPt = pt;
            }
        }
        return resultPt;
    }
    private Double nearDist(Point pt, double x, double y) {
        double distX = Math.pow((x - pt.getX()), 2);
        double distY = Math.pow((y - pt.getY()), 2);
        return Math.pow((distX + distY), 0.5);
    }
}
