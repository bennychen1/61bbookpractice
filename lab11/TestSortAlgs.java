import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestSortAlgs {

    private final  int[] numItems = new int[]{10, 100, 1000, 10000, 100000, 1000000};

    @Test
    public void testQuickSort() {
        Queue<String> q = createBasicQueue();
        Queue<String> sortedQueue = QuickSort.quickSort(q);
        assertTrue(isSorted(sortedQueue));
    }

    @Test
    public void testQuickSortRandom() {
        Queue<Integer> q = randomQueue(100);
        Queue<Integer> sortedQueue = QuickSort.quickSort(q);
        assertTrue(isSorted(sortedQueue));
    }

    @Test
    public void testQuickSortSpeed() {
        double[] time = new double[numItems.length];

        for (int i = 0; i < numItems.length; i = i + 1) {
            int currentNumItems = numItems[i];

            Queue<Integer> q = randomQueue(currentNumItems);

            Stopwatch s = new Stopwatch();
            QuickSort.quickSort(q);
            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));
    }

    @Test
    public void testMergeSort() {
        Queue<String> q = createBasicQueue();

        Queue<String> sortedQueue = MergeSort.mergeSort(q);

        assertTrue(isSorted(sortedQueue));

        Queue<String> twoItemQueue = new Queue<>();
        twoItemQueue.enqueue("Green");
        twoItemQueue.enqueue("Apple");
        Queue<String> sortedTwoItemQueue = MergeSort.mergeSort(twoItemQueue);
        assertTrue(isSorted(sortedTwoItemQueue));
    }

    @Test
    public void testRandomIntsMergeSort() {
        Queue<Integer> q = randomQueue(100);

        Queue<Integer> sortedQ = MergeSort.mergeSort(q);

        assertTrue(isSorted(sortedQ));
    }

    @Test
    public void testMergeSortSpeed() {

        double[] time = new double[numItems.length];

        for (int i = 0; i < numItems.length; i = i + 1) {
            int currentNumItems = numItems[i];

            Queue<Integer> q = randomQueue(currentNumItems);

            Stopwatch s = new Stopwatch();
            MergeSort.mergeSort(q);
            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));

    }

    /** Returns a queue with 3 strings. */
    public Queue<String> createBasicQueue() {
        Queue<String> q = new Queue<>();
        q.enqueue("Blue");
        q.enqueue("Green");
        q.enqueue("Apple");
        return q;
    }

    /**
     * Returns a queue of random ints
     *
     * @param size The number of random ints to put in the queue.
     * @returns    A queue of random ints of size SIZE
     */
    private Queue<Integer> randomQueue(int size) {
        Queue<Integer> q = new Queue<>();

        for (int j = 0; j < size; j = j + 1) {
            q.enqueue(1 + (int)(Math.random() * ((100 - 1) + 1)));
        }

        return q;
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
