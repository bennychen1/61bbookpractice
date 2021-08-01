package bearmaps;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

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
                this.left.add(addPoint);
            }
        }
    }

    /** Returns true if point containPoint is in KDTree. For testing add. **/
    private boolean contains(Point containPoint) {
        if (this.p.equals(containPoint)) {
            return true;
        } else {

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
        return null;
    }
}
