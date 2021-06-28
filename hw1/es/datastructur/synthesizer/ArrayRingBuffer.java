package es.datastructur.synthesizer;
import java.util.Iterator;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    private class ArbIterable implements Iterator<T> {
        int cur = first;
        @Override
        public boolean hasNext() {
            return last != first;
        }

        @Override
        public T next() {
            T toReturn = rb[cur];
            if (cur + 1 >= capacity()) {
                cur = 0;
            } else {
                cur = cur + 1;
            }

            return toReturn;
        }
    }

    /**
     * Returns the size of RB array.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    /**
     * Returns the number of items in RB
     */
    public int fillCount() {
        return fillCount;
    }

    @Override
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.

        if (isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        if (!isFull()) {
            rb[last] = x;
            moveLast();
            fillCount += 1;
        }

        return;
    }

    @Override
    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        T toReturn = rb[first];

        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        } else {
            fillCount = fillCount - 1;
        }

        rb[first] = null;

        moveFirst();

        return toReturn;
    }

    @Override
    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        return rb[first];
    }

    /* Moves the LAST index to the next index of the array RB according to circular structure. */
    private void moveLast() {
        if (last + 1 >= capacity()) {
            last = 0;
        } else {
            last = last + 1;
        }
    }

    private void moveFirst() {
        if (first + 1 >= capacity()) {
            first = 0;
        } else {
            first = first + 1;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArbIterable();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o.getClass() != this.getClass()) {
            return false;
        }

        if (o == null) {
            return false;
        }

        ArrayRingBuffer<T> otherArb = (ArrayRingBuffer<T>) o;

        if (otherArb.capacity() != this.capacity()
                || otherArb.fillCount() != this.fillCount()) {
            return false;
        }

        for (int i = 0; i < this.fillCount(); i = i + 1) {
            T otherItem = otherArb.peek();
            T thisItem = this.peek();
            if (otherItem != thisItem) {
                return false;
            }

            otherArb.dequeue();
            otherArb.enqueue(otherItem);

            this.dequeue();
            this.enqueue(thisItem);
        }

        return true;

    }




    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.
