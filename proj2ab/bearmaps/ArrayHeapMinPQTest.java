package bearmaps;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Stopwatch;

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
    public void testRemoveSmallestSpped() {
        ArrayHeapMinPQ<Integer> a100 = createArrayMinHeapPQ(100);
        ArrayHeapMinPQ<Integer> a1000 = createArrayMinHeapPQ(1000);

        Stopwatch s1 = new Stopwatch();

        for (int i = 0; i < 100; i = i + 1) {
            a100.removeSmallest();
        }

        double elapsed100 = s1.elapsedTime();

        Stopwatch s2 = new Stopwatch();

        for (int i = 0; i < 1000; i = i + 1) {
            a1000.removeSmallest();
        }

        double elapsed1000 = s2.elapsedTime();

        assertTrue(String.format("Time for 100 items was %f, while time for 1000 items was %f",
                elapsed100, elapsed1000), elapsed1000/elapsed100 <= 15);
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
}
