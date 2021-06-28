package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer<Integer> arbInt = new ArrayRingBuffer<>(10);

        arbInt.enqueue(3);
        arbInt.enqueue(5);
        arbInt.enqueue(7);

        assertEquals(10, arbInt.capacity());

        /* Integer.valueOf returns Integer so assertEquals(Object, Object) can be used (both Integers) */
        assertEquals(Integer.valueOf(3),arbInt.peek());
        /* Both are int */
        assertEquals(3, arbInt.fillCount());

        assertEquals(Integer.valueOf(3), arbInt.dequeue());
        assertEquals(2, arbInt.fillCount());
        assertEquals(10, arbInt.capacity());

        arbInt.enqueue(12);

        assertEquals(Integer.valueOf(5), arbInt.peek());

        for (int i = 0; i < 3; i = i + 1) {
            arbInt.dequeue();
        }

        assertNull(arbInt.dequeue());
        assertNull(arbInt.peek());

        for (int i = 0; i < 10; i = i + 1) {
            arbInt.enqueue(i);
        }


        assertTrue(arbInt.isFull());

        arbInt.enqueue(20);

        assertEquals(10, arbInt.fillCount());

        for (int i = 0; i < 9; i = i + 1){
            arbInt.dequeue();
        }

        assertEquals(Integer.valueOf(9), arbInt.peek());
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);
        Iterator<Integer> arbIt = arb.iterator();

        assertFalse(arbIt.hasNext());

        arb.enqueue(12);
        arb.enqueue(20);
        arb.enqueue(25);

        assertEquals(Integer.valueOf(12), arbIt.next());
    }

    @Test (expected = RuntimeException.class)
    public void testEnqueueException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(5);

        for (int i = 0; i < arb.capacity(); i = i + 1) {
            arb.enqueue((int) Math.random());
        }

        arb.enqueue(10);
    }

    @Test (expected = RuntimeException.class)
    public void testDequeueException() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(3);

        arb.enqueue(1);
        arb.enqueue(2);
        arb.enqueue(3);

        for (int i = 0; i < 3; i = i + 1) {
            arb.dequeue();
        }

        arb.dequeue();
    }

    @Test
    public void testEquals() {
        ArrayRingBuffer<Integer> arb1 = new ArrayRingBuffer<>(5);
        ArrayRingBuffer<Integer> arb2 = new ArrayRingBuffer<>(5);
        ArrayRingBuffer<Integer> arb3 = new ArrayRingBuffer<>(10);


        fillArb(arb1);
        fillArb(arb2);
        fillArb(arb3);

        Integer firstItem = arb1.peek();

        assertTrue(arb1.equals(arb2));
        assertFalse(arb3.equals(arb2));

        assertEquals(firstItem, arb1.peek());

        arb1.dequeue();
        arb1.enqueue(10);

        assertFalse(arb1.equals(arb2));


    }

    private <T> void fillArb(ArrayRingBuffer<T> a) {
        for (int i = 0; i < a.capacity(); i = i + 1) {
            a.enqueue((T) Integer.valueOf(i));
        }
    }
}
