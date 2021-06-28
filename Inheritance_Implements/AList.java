public class AList <T> implements List61b<T> {
    private T[] items;
    private int size;
    public AList() {
        items = (T [])new Object[100];
        size = 0;
    }

    /** Add int x as last element of ITEMS */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            reSize(size * 2);
        }
        items[size] = x;
        size += 1;
    }


    /** Get the last element of array ITEMS */
    @Override
    public T getLast() {
        return items[size - 1];
    }

    /** Get the Xth element of array ITEMS */
    @Override
    public T get(int x) {
        return items[x];
    }

    /** Return the size (number of elements) of the array ITEMS */
    @Override
    public int size() {
        return size;
    }

    /** Remove the last item of the array ITEM*/
    @Override
    public T removeLast() {
        T toReturn = getLast();
        items[size - 1] = null; /* Have to null it out since it is an generic object, not just an int */
        size = size - 1;
        if (size / items.length < 0.25) {
            reSize(items.length / 2); /* size is how many elements are in the list */
        }
        return toReturn;
    }

    /** Once ITEMS is full (size == items.length) change items to an array with length capacity */
    public void reSize(int capacity) {
        T[] a = (T []) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size); /* number of elements = size */
        items = a;
    }

    /** Returns the first item of the array */
    @Override
    public T getFirst() {
        return this.get(0);
    }


    /** Add item X to the front of the array*/
    @Override
    public void addFirst(T x) {
        T[] a = (T[]) new Object[items.length + 1];
        System.arraycopy(items, 0, a, 1, size);
        items = a;
        items[0] = x;
    }
}

/** To make a new project, need to right click on a directory (arrays in this case) and select mark as source root.
 * Linked List / Doubly linked list (uses linked list as the base implementation)
 * get will be slow (linear time). Example: get the 100th item in a 200 item list.
 * Arrays - get is constant time
 * ADD LAST METHOD:
 *      at instantiation [0 0 0 0 0 0 0 0.....] size = 0
 *                      [0 1 2 3 4 5 6 7 ......]
 *       addLast        [1 0 0 0 0 0 0 0.....] size = 1
 *       addLast        [1 2 0 0 0 0 0 0 .....] size = 2
 *       Notice pattern: addLast, place element at the sizeth position of the array
 *Invariants: adding the last element means adding an element at the SIZE index
 * the number of elements in the array is SIZE
 * the last element is at the index SIZE - 1
 * abstract: [1 2 3 0 0 0 0 0 0 .....] what the user sees [1 2 3]
 * removeLast
 * [1 2 3 0 0 0 0 0 0 ......]  what the user sees [1 2]
 * for the invariant for adding last element to be true, size needs to go down by one when we remove last.
 * removing one element, size needs to go down by one so that the number of elements in array is size
 * removing one element, size needs to go down by one so that the last element is at size - 1
 * don't need to abstractly zero out the last element, because it won't be accessed
 * think about how to implement remove last, consider what needs to be changed while still keeping
 * invariants true.
 * [1, 2, 3, 4, 5] size = 5
 * 0   1  2  3  4
 * [1, 2, 3, 4, 5, 0] size = 6
 * Override - a subclass method has same signature as the superclass (name and argument and argument types the same)*/