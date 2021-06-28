public class VengefulSLList<T> extends SLList<T>{
    SLList<T> removedItems;

    VengefulSLList() {
        removedItems = new SLList<>();
    }

    /** Constructor if list is initialized with an ITEM */
    VengefulSLList(T item) {
        super(item); /* Calls SLList(item). If this is not specified, super() is called (no parameters) */
    }

    public void printLostItems() {
        removedItems.print();
    }

    @Override
    public T removeLast() {

        T toReturn = super.removeLast(); /* IntNode is set to private, so use super */

        removedItems.addLast(toReturn);

        return toReturn;

    }

    public static void main(String[] args) {
        VengefulSLList<String> s = new VengefulSLList<>();
        s.addFirst("Apple");
        s.addLast("Orange");
        s.addLast("Grape");
        s.removeLast();
        s.addLast("Mango");
        s.printLostItems();
        s.removeLast();
        s.printLostItems();
    }

}

/** Need to override the removeLast of SList
 * Need to initialize any instance variables before using them - either through constructor or setting them
 * Item x = initialized_value;*/
