package bearmaps;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class NaivePointSetTest {
    @Test
    public void testNaivePointSet() {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);
        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        assertEquals(3.3, ret.getX(), 0);
        assertEquals(4.4, ret.getY(), 0);

        Point p4 = new Point(0.5, 8.9);
        Point p5 = new Point(3.5, 0.1);
        Point p6 = new Point(-6.2, 1.4);
        Point p7 = new Point(1.5, 6.2);
        Point p8 = new Point(-3.5, 9.1);
        Point p9 = new Point(2.9, -12.2);
        Point p10 = new Point(0.9, 13.2);
        Point p11 = new Point(2.1, 9.1);
        NaivePointSet pp = new NaivePointSet(List.of(p4, p5, p6, p7, p8,
                p9, p10, p11));
        Point near = pp.nearest(2.3, 9.2);
        assertEquals(2.1, near.getX(), 0);
        assertEquals(9.1, near.getY(), 0);

    }
}
