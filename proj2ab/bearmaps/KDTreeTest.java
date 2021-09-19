package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.Arrays;

public class KDTreeTest {

    Point[] pArray = new Point[]{new Point(0, 1), new Point(0, 5),
            new Point(-3, -2), new Point(2, -2), new Point(3, 2)};

    @Test
    public void testKDTree() {

        ArrayList<Point> pL = new ArrayList<>();

        for (Point p : pArray) {
            pL.add(p);
        }

        KDTree k = new KDTree(pL);

        assertEquals(new Point(0, 1), k.nearest(0, 2));
        assertEquals(new Point(-3, -2), k.nearest(-7, -5));
        assertEquals(new Point(0, 5), k.nearest(0, 5));
    }

    @Test
    public void testKDTreeRandom() {
        Random r = new Random(10);

        ArrayList<Point> points = new ArrayList<>();
        ArrayList<Point> pointsToTest = new ArrayList<>();

        for (int i = 0; i < 1000; i = i + 1) {
            points.add(new Point(r.nextInt(), r.nextInt()));
            pointsToTest.add(new Point(r.nextInt(), r.nextInt()));
        }

        NaivePointSet n = new NaivePointSet(points);
        KDTree k = new KDTree(points);

        for (Point p : pointsToTest) {
            assertEquals(k.nearest(p.getX(), p.getY()), n.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void testKDTreeSpeed() {
        int[] numPoints = new int[]{10, 100, 1000, 10000, 100000};
        double[] timeKDTree = new double[numPoints.length];
        double[] timeNaivePointSet = new double[numPoints.length];

        ArrayList<Point> callsToNearest = new ArrayList<>();

        Random pointsToCall = new Random();

        for (int i = 0; i < 10000000; i = i + 1) {
            callsToNearest.add(new Point(pointsToCall.nextInt(), pointsToCall.nextInt()));
        }

        for (int i = 0; i < numPoints.length; i = i + 1) {
            int thisManyPoints = numPoints[i];
            ArrayList<Point> points = new ArrayList<>();
            for (int j = 0; j < thisManyPoints; j = j + 1) {
                points.add(new Point(pointsToCall.nextInt(), pointsToCall.nextInt()));
            }

            KDTree k = new KDTree(points);
            NaivePointSet nPS = new NaivePointSet(points);

            Stopwatch s1 = new Stopwatch();
            for (Point p : points) {
                k.nearest(p.getX(), p.getY());
            }

            timeKDTree[i] = s1.elapsedTime();

            Stopwatch s2 = new Stopwatch();
            for (Point p : points) {
                nPS.nearest(p.getX(), p.getY());
            }

            timeNaivePointSet[i] = s2.elapsedTime();

        }

        System.out.println("Time to run KDTree nearest for " + Arrays.toString(numPoints) +
                " " + Arrays.toString(timeKDTree));
        System.out.println("Time to run NaivePointSet nearest for " + Arrays.toString(numPoints) +
                " " + Arrays.toString(timeNaivePointSet));
    }
}


