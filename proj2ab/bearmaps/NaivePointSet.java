package bearmaps;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
    }

    public NaivePointSet() {
        this(List.of(new Point(Math.random(), Math.random())));
    }

    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);

        Point bestPoint = points.get(0);
        double bestDistance = Double.MAX_VALUE;

        for (Point pt : this.points) {
            double curDistance = Point.distance(p, pt);
            if (curDistance < bestDistance) {
                bestPoint = pt;
                bestDistance = curDistance;
            }
        }

        return bestPoint;
    }
}
