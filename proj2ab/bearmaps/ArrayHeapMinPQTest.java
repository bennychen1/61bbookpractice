package bearmaps;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Stopwatch;

public class ArrayHeapMinPQTest {

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
        Stopwatch s = new Stopwatch();

        createArrayMinHeapPQ(100);

        double elapsed100 = s.elapsedTime();

        Stopwatch s2 = new Stopwatch();

        createArrayMinHeapPQ(1000);

        double elapsed1000 = s2.elapsedTime();

        assertTrue(String.format("Time for 100 items was %f, while time for 1000 items was %f",
                elapsed100, elapsed1000), elapsed1000/elapsed100 <= 15);
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
}
