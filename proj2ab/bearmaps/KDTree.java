package bearmaps;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import static org.junit.Assert.assertTrue;

public class KDTree implements PointSet {

    Point p;
    KDTree left;
    KDTree right;
    ComparePoints<Point> compareTo; /* could be a lambda function */

    public KDTree(List<Point> points) {
        this(points, new CompareX());
    }

    private KDTree(List<Point> points, ComparePoints<Point> compareTo) {
        this.p = points.get(0);
        this.compareTo = new CompareX();

        for (Point pointToAdd : points) {
            this.add(pointToAdd);
        }
    }

    /** Add a point to the KD tree. **/
    private void add(Point addPoint) {
        if (this.p.equals(addPoint)) {
            return;
        }

        ArrayList<Point> pointSet = new ArrayList<>();
        pointSet.add(addPoint);

        KDTree toAdd = new KDTree(pointSet,  this.compareTo.nextComparator());

        if (this.compareTo.compare(addPoint, this.p) == 0) {
            if (this.left == null) {
                this.left = toAdd;
            } else {
                this.left.add(addPoint);
            }
        } else {
            if (this.right == null) {
                this.right = toAdd;
            } else {
                this.right.add(addPoint);
            }
        }
    }

    /** Returns true if point containPoint is in KDTree. For testing add. **/
    private boolean contains(Point containPoint) {
        if (this == null) {
            return false;
        }
        if (this.p.equals(containPoint)) {
            return true;
        } else {
            if (this.compareTo.compare(containPoint, this.p) == 0) {
                if (this.left == null) {
                    return false;
                } else {
                    return this.left.contains(containPoint);
                }
            } else {
                if (this.right == null) {
                    return false;
                } else {
                    return this.right.contains(containPoint);
                }
            }
        }
    }

    private static class CompareX implements ComparePoints<Point> {

        public int compare(Point p1, Point p2) {
            if (Double.compare(p1.getX(), p2.getX()) <= 0) {
                return 0;
            } else {
                return 1;
            }
        }

        public ComparePoints<Point> nextComparator() {
            return new CompareY();
        }

    }

    private static class CompareY implements ComparePoints<Point> {

        public int compare(Point p1, Point p2) {
            if (Double.compare(p1.getY(), p2.getY()) <= 0) {
                return 0;
            } else {
                return 1;
            }
        }

        public ComparePoints<Point> nextComparator() {
            return new CompareX();
        }
    }



    public Point nearest(double x, double y) {

        Point[] bestPoint = new Point[]{this.p};
        double[] bestDistance = new double[] {Point.distance(this.p, new Point(x, y))};

        return nearestHelper(new Point(x, y), bestDistance, bestPoint);
    }

    private Point nearestHelper(Point newPoint, double[] bestDistance, Point[] curBestPoint) {

        if (Double.compare(Point.distance(this.p, newPoint), bestDistance[0]) < 0) {
            bestDistance[0] = Point.distance(this.p, newPoint);
            curBestPoint[0] = this.p;
        }

        KDTree good;
        KDTree bad;

        if (this.compareTo.compare(newPoint, this.p) <= 0) {
            good = this.left;
            bad = this.right;
        } else {
            good = this.right;
            bad = this.left;
        }

        if (good != null) {

            good.nearestHelper(newPoint, bestDistance, curBestPoint);
        }

        Point bestPointB;

        if (this.compareTo.getClass() == CompareX.class) {
            bestPointB = new Point(this.p.getX(), newPoint.getY());

        } else {
            bestPointB = new Point(newPoint.getX(), this.p.getY());
        }

        if (Double.compare(bestDistance[0], Point.distance(bestPointB, newPoint)) >= 0) {
            if (bad != null) {
                bad.nearestHelper(newPoint, bestDistance, curBestPoint);
            }
        }

        return curBestPoint[0];
    }

    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(15, -2);
        Point p5 = new Point(-6, -3);
        Point p6 = new Point(2, 1);

        ArrayList<Point> pointSet = new ArrayList<>();

        pointSet.add(p1);
        pointSet.add(p2);
        pointSet.add(p3);
        pointSet.add(p4);
        pointSet.add(p5);
        pointSet.add(p6);



        KDTree k = new KDTree(pointSet);

        assertTrue(k.contains(p1));
        assertTrue(k.contains(p6));
    }
}
