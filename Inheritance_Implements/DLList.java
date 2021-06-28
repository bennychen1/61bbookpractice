public class DLList<T> implements List61b<T>{
    protected int size;
    protected IntNode sentinel; /* Only code in this file can access this variable*/
    private class IntNode<T> {
        T item;
        IntNode next;
        IntNode prev;

        public IntNode(T item, IntNode next, IntNode prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public DLList(T x) {
        sentinel = new IntNode(null, null, null);
        sentinel.next = new IntNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /**Constructor for an empty list */
    public DLList() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }


    /**Adds X to the front of the list*/
    @Override
    public void addFirst(T x) {
       IntNode<T> i = new IntNode(x, sentinel.next, sentinel);

       sentinel.next.prev = i;

       sentinel.next  = i;

       size += 1;

    }

    /**Get the first item of the list. The first item if it exists is at sentinel.next */
    @Override
    public T getFirst() {
        return (T) sentinel.next.item;
    }


    /**Return the size of the SLList */
    @Override
    public int size() {
        return size;
    }

    /**Return the size of an IntNode */
    public int size(IntNode n) {
        if (n.next == null) {
            return 1;
        }

        return 1 + size(n.next);
    }

    /** Return the item at the ith index */
    @Override
    public T get(int i) {
        IntNode p = sentinel.next;

        while (i != 0) {
            p = p.next;
            i -= 1;
        }

        return (T) p.item;
    }

    /** Add item x to the end of the list */
    @Override
    public void addLast(T x) {
       sentinel.prev.next = new IntNode(x, null, sentinel);
       sentinel.prev = sentinel.prev.next;
       size += 1;
    }

    /** Return the last item of the list */
    @Override
    public T getLast() {
        return (T) sentinel.prev.item;
    }

    /**Removes the last item of the list */
    @Override
    public T removeLast() {
        T toReturn = (T) sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        return toReturn;
    }

    /** Prints out the items in the list */
    @Override
    public void print() {
        for (IntNode p = sentinel.next; p != null; p = p.next) {
            System.out.print(p.item + " ");
        }

        System.out.println();
    }
}

/** Instead of having a user create a IntList new IntList(5, null)
 * use SLList(5) instead. User does not need to know of the null link.
 * Add item, IntList: set up x and set up y to be new IntList(y, x)
 * Make first private so that someone can't just go L (SLList) L.first.next = z
 * Marking private keeps a layer of abstraction - no need for a user to tinker with that variable/method
 * Mark a nested class static - it can't access members (variables, methods) of the outer class
 * size method: not a good idea to change what first points to.
 * Overloaded method: has another method of same name, different signature (argument type//arguments different)
 * Caching: size function - if there is a very large lists, would take a long time. Instead save the data
 * in a instance variable
 * Make an empty list because there's an intermediary between the pointer and the IntList.
 * Allows you to instantiate an object as an empty list and then add on later
 * Add a Sentinel node: in add last, need to account for a special case. minimize special case code.
 * Make all SLList the same structure, make first point to a Sentinel node
 * Invariant: something that will be true for a data structure
 * sentinel always points to a sentinel node.
 * If there was a remove last function, it would be slow because of iterating through to get to the second to last item.
 * Add a previous link to each IntNode
 * Problem: last could point to sentinel sometimes or actual node other times (get if statements)
 * Solve: 2 sentinels or
 * Have it be circular, no last pointer
 * Generics: <Generic Type Placeholder> after class at the top**/
