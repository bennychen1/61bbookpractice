public interface List61b<T> {

    void addLast(T x);

    /** Get the last element of array ITEMS */
    T getLast();

    /** Get the Xth element of array ITEMS */
    T get(int x);

    /** Return the size (number of elements) of the array ITEMS */
    int size();

    /** Remove the last item of the array ITEM*/
    T removeLast();

    /**Returns the first item in the list */
    T getFirst();

    /**Add item X to the front of the list */
    void addFirst(T x);

    /** Prints the items of the list */
    default void print() {
        for (int i = 0; i < size(); i++) {
            System.out.print(get(i) + " ");
        }

        System.out.println();
    }
}

/** Issue with the print method: get is fast for AList but SList get is a loop, so for SLList, there is a loop inside a loop.
 * Override the print method in the SList subclass*/