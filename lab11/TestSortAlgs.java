import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {

    }

    @Test
    public void testMergeSort() {
        Queue<String> q = new Queue<>();
        q.enqueue("Blue");
        q.enqueue("Green");
        q.enqueue("Apple");

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
        Queue<Integer> q = new Queue<>();

        for (int i = 0; i < 100; i = i + 1) {
            q.enqueue(10 + (int)(Math.random() + ((1000 - 10) + 1)));
        }

        Queue<Integer> sortedQ = MergeSort.mergeSort(q);

        assertTrue(isSorted(sortedQ));
    }

    @Test
    public void testMergeSortSpeed() {

        int[] numItems = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] time = new double[numItems.length];

        for (int i = 0; i < numItems.length; i = i + 1) {
            int currentNumItems = numItems[i];

            Queue<Integer> q = new Queue<>();

            for (int j = 0; j < currentNumItems; j = j + 1) {
                q.enqueue(1 + (int)(Math.random() * ((100 - 1) + 1)));
            }

            Stopwatch s = new Stopwatch();
            MergeSort.mergeSort(q);
            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));

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
