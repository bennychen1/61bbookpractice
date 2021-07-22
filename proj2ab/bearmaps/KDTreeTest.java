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
            points.add(new Point(r.nextInt(), r.nextInt()));
        }

        NaivePointSet n = new NaivePointSet(points);
        KDTree k = new KDTree(points);

        for (Point p : pointsToTest) {
            assertEquals(k.nearest(p.getX(), p.getY()), n.nearest(p.getX(), p.getY()));
        }
    }

    @Test
    public void testKDTreeSpeed() {
        ArrayList<Point> points = new ArrayList<>();

        Random r = new Random();

        for (int i = 0; i < 10000; i = i + 1) {
            points.add(new Point(r.nextInt(), r.nextInt()));
        }

        KDTree k = new KDTree(points);
        NaivePointSet n = new NaivePointSet(points);

        int[] numberOfNearest = new int[]{10, 100, 1000, 10000, 100000, 1000000};

        double[] timeKDTree = new double[6];
        double[] timeNaivePointSet = new double[6];

        for (int i = 0; i < 6; i = i + 1) {
            Stopwatch s1 = new Stopwatch();
            for (int j = 0; j < numberOfNearest[i]; j = j + 1) {
                k.nearest(r.nextInt(), r.nextInt());
            }
            timeKDTree[i] = s1.elapsedTime();

            Stopwatch s2 = new Stopwatch();
            for (int j = 0; j < numberOfNearest[i]; j = j + 1) {
                n.nearest(r.nextInt(), r.nextInt());
            }
             timeNaivePointSet[i] = s2.elapsedTime();
        }

        System.out.println("Time to run KDTree nearest for " + Arrays.toString(numberOfNearest) +
                " " + Arrays.toString(timeKDTree));
        System.out.println("Time to run NaivePointSet nearest for " + Arrays.toString(numberOfNearest) +
                " " + Arrays.toString(timeNaivePointSet));
    }
}


