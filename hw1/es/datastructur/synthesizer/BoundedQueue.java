package es.datastructur.synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    /* Returns the size of the buffer. */
    int capacity();

    /* Returns the number of items currently in the buffer. */
    int fillCount();

    /* Add item X to the end of the queue */
    void enqueue(T x);

    /* Return and remove the first item from the queue. */
    T dequeue();

    /* Return but does not remove the first item from the queue. */
    T peek();

    /* Returns true if the buffer has no items */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /* Returns true if buffer is full. */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
