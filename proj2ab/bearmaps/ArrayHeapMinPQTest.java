package bearmaps;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;


public class ArrayHeapMinPQTest {

    @Test
    public void testBasicAdd() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Blue", 1);
        a.add("Red", 10);
        a.add("Orange", 2);

        assertEquals("Blue", a.getSmallest());

        a.add("Purple", 0.5);

        assertEquals("Purple", a.getSmallest());
    }

    @Test
    public void testBasic() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        assertTrue(a.contains("Green"));
        assertFalse(a.contains("Blue"));
        assertEquals(2, a.size());
        assertEquals("Green", a.getSmallest());
        assertEquals(2, a.size());
        assertTrue(a.contains("Green"));

        String smallest = a.removeSmallest();

        assertEquals("Green", smallest);
        assertEquals(1, a.size());
        assertFalse(a.contains("Green"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddSameItem() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Green", 6);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Blue", 15);

        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Blue", 1);
        String r1 = a.removeSmallest();

        a.add("Red", 10);
        a.add("Silver", 6);

        String r2 = a.removeSmallest();

        assertEquals("Blue", r1);
        assertEquals("Green", r2);

        a.add("Peach", 9);

        String r3 = a.removeSmallest();

        assertEquals("Yellow", r3);
        assertEquals("Silver", a.getSmallest());

    }

    @Test
    public void testRemoveSmallestTwoItems() {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        a.add(3, 3);
        a.add(1, 1);

        assertEquals(1, (int) a.removeSmallest());
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetEmpty() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Blue", 15);

        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.getSmallest();
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Blue", 10);

        a.changePriority("Blue", 1);

        assertEquals("Blue", a.getSmallest());

        a.add("Silver", 11);
        a.add("White", 5);
        a.add("Red", 3);
        a.add("Peach", 20);
        a.add("Purple", 9);
        a.add("Brown", 3);

        a.changePriority("Yellow", 1);

        a.removeSmallest();
        String r1 = a.removeSmallest();

        assertEquals("Yellow", r1);

        a.changePriority("Silver", 1);

        String r2 = a.removeSmallest();

        assertEquals("Silver", r2);

        a.changePriority("Green", 25);

        String r3 = a.removeSmallest();

        assertEquals("Brown", r3);
    }


    @Test(expected = NoSuchElementException.class)
    public void testChangePriorityException() {
        ArrayHeapMinPQ<String> a = createArrayMinHeapPQ();

        a.add("Silver", 3);

        a.removeSmallest();
        a.removeSmallest();

        a.changePriority("Silver", 1);
    }

    @Test
    public void testChangePrioritySpeed() {

        int[] numPoints = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] runTimes = new double[6];
        double[] runTimesNaive = new double[6];

        for (int i = 0; i < numPoints.length; i = i + 1) {
            int thisManyPoints = numPoints[i];
            ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
            NaiveMinPQ<Integer> npq = new NaiveMinPQ<>();
            ArrayList<Integer> randomInputs = new ArrayList<>();

            for (int j = 0; j < thisManyPoints; j = j + 1) {
                a.add(j, Math.random() * 1000);
                npq.add(j, Math.random() * 1000);
                randomInputs.add(j);
            }

            Stopwatch sAPQ = new Stopwatch();
            for (int k = 0; k < 1000000; k = k + 1) {
                // Random int between a and b = (int) (Math.random() * ((b - a) + 1))
                int randomIndex = (int)(Math.random() * thisManyPoints);
                a.changePriority(randomInputs.get(randomIndex), Math.random() * 1000);
            }

            runTimes[i] = sAPQ.elapsedTime();

            Stopwatch sNPQ = new Stopwatch();
            for (int k = 0; k < 1000000; k = k + 1) {
                // Random int between a and b = (int) (Math.random() * ((b - a) + 1))
                int randomIndex = (int)(Math.random() * thisManyPoints);
                npq.changePriority(randomInputs.get(randomIndex), Math.random() * 1000);
            }

            runTimesNaive[i] = sNPQ.elapsedTime();

        }

        System.out.println("Run times for" + Arrays.toString(numPoints) + "are: " + Arrays.toString(runTimes));
        System.out.println("Run times for" + Arrays.toString(numPoints) + "are: " + Arrays.toString(runTimesNaive));

    }

    @Test
    public void testAddSpeed() {

        int[] inputs = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] runTimes = new double[6];

        for (int i = 0; i < 6; i = i + 1) {
            Stopwatch s = new Stopwatch();
            createArrayMinHeapPQ(inputs[i]);
            double elapsedTime = s.elapsedTime();
            runTimes[i] = elapsedTime;
        }

        double[] runTimesNaive = new double[6];

        for (int i = 0; i < 6; i = i + 1) {
            Stopwatch s = new Stopwatch();
            createNaiveMinHeapPQ(inputs[i]);
            double elapsedTime = s.elapsedTime();
            runTimesNaive[i] = elapsedTime;
        }


        System.out.println("Run times for" + Arrays.toString(inputs) + "are: " + Arrays.toString(runTimes));
        System.out.println("Run times for" + Arrays.toString(inputs) + "are: " + Arrays.toString(runTimesNaive));

    }

    @Test
    public void testRemoveSmallestSpeed() {
        int[] inputs = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] runTimes = new double[6];

        for (int i = 0; i < 6; i = i + 1) {
            ExtrinsicMinPQ<Integer> a = createArrayMinHeapPQ(inputs[i]);
            Stopwatch s = new Stopwatch();
            removeMinHeap(a);
            double elapsedTime = s.elapsedTime();
            runTimes[i] = elapsedTime;
        }

        double[] runTimesNaive = new double[6];

        for (int i = 0; i < 4; i = i + 1) {
            ExtrinsicMinPQ<Integer> a = createNaiveMinHeapPQ(inputs[i]);
            Stopwatch s = new Stopwatch();
            removeMinHeap(a);
            double elapsedTime = s.elapsedTime();
            runTimesNaive[i] = elapsedTime;
        }


        System.out.println("Run times for" + Arrays.toString(inputs) + "are: " + Arrays.toString(runTimes));
        System.out.println("Run times for" + Arrays.toString(inputs) + "are: " + Arrays.toString(runTimesNaive));
    }

    @Test
    public void testRandomized() {
        int numberOfNodes = (int) (1000 + Math.random() * 9000);
        int numberOfRemoveSmallest = (int) (5 + Math.random() * (numberOfNodes - 5));

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> n = new NaiveMinPQ<>();

        for (int i = 0; i < numberOfNodes; i = i + 1) {
            double randomValue = Math.random() * 100;
            a.add(i, randomValue);
            n.add(i, randomValue);
        }

        Integer aSmallest = null;
        Integer nSmallest = null;

        for (int j = 0; j < numberOfRemoveSmallest; j = j + 1) {
            aSmallest = a.removeSmallest();
            nSmallest = n.removeSmallest();
        }

        assertEquals(aSmallest, nSmallest);

    }

    @Test
    public void testRandomizedChangePriorities(){

        int numberOfNodes = (int) (1000 + Math.random() * 9000);
        int numberOfRemoveSmallest = (int) (5 + Math.random() * (numberOfNodes - 5));
        int numberChangePriorities = (int) (10 + Math.random() * 30);

        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();
        NaiveMinPQ<Integer> n = new NaiveMinPQ<>();

        for (int i = 0; i < numberOfNodes; i = i + 1) {
            double randomValue = Math.random() * 100;
            a.add(i, randomValue);
            n.add(i, randomValue);
        }

        Integer aSmallest = null;
        Integer nSmallest = null;

        for (int i = 0; i < numberChangePriorities; i = i + 1) {
            double randomValue = Math.random() * 100;
            int nodeToChange = (int) (Math.random() * (numberOfNodes));
            a.changePriority(nodeToChange, randomValue);
            n.changePriority(nodeToChange, randomValue);
        }

        for (int j = 0; j < numberOfRemoveSmallest; j = j + 1) {
            aSmallest = a.removeSmallest();
            nSmallest = n.removeSmallest();
        }

        assertEquals(aSmallest, nSmallest);
    }

    private ArrayHeapMinPQ<String> createArrayMinHeapPQ() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();

        a.add("Green", 2);
        a.add("Yellow", 5);

        return a;
    }

    private ArrayHeapMinPQ<Integer> createArrayMinHeapPQ(int size) {
        ArrayHeapMinPQ<Integer> a = new ArrayHeapMinPQ<>();

        for (int i = 0; i < size; i = i + 1) {
            a.add(i, (double) Math.random() * (1000 - 0));
        }

        return a;
    }

    private NaiveMinPQ<Integer> createNaiveMinHeapPQ(int size) {
        NaiveMinPQ<Integer> a = new NaiveMinPQ<>();

        for (int i = 0; i < size; i = i + 1) {
            a.add(i, (double) Math.random() * (1000 - 0));
        }

        return a;
    }

    private <T> void removeMinHeap(ExtrinsicMinPQ<T> e) {
        for (int i = 0; i < e.size(); i = i + 1) {
            e.removeSmallest();
        }
    }
}
