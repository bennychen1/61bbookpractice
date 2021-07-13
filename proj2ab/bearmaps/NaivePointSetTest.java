package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class NaivePointSetTest {

    @Test
    public void testNaivePointSet() {
        Point A = new Point(1, 1);
        Point B = new Point(-5, -6);
        Point C = new Point(5, 6);

        ArrayList<Point> points = new ArrayList<>();
        points.add(A);
        points.add(B);
        points.add(C);

        NaivePointSet n = new NaivePointSet(points);

        assertEquals(A, n.nearest(1, 1));
        assertEquals(C, n.nearest(1, 6));
        assertEquals(B, n.nearest(-15, -10));
    }
}
