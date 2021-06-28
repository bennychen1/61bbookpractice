package Chapter_6;

import java.lang.reflect.Array;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ArraySet<T> implements Iterable<T>{
    private T[] items;
    private int size;

    ArraySet() {
        items = (T[]) new Object[10];
    }

    private class ArraySetIterator implements Iterator<T> {
        private int pointer;

        ArraySetIterator() {
            pointer = 0;
        }

        public boolean hasNext() {
            return pointer < size;
        }

        public T next() {
            T toReturn = items[pointer];
            pointer += 1;
            return toReturn;
        }
    }

    /** Add VALUE to the set if it is not already in the set
     * Throws an IllegalArgmuentException is VALUE is null */
    void add(T value) {
        /*if (value == null) {
            throw new IllegalArgumentException("Null can not be added to an ArraySet");
        } */

        if (value == null) {
            return;
        }
       if (!contains(value)) {
           items[size] = value;
           size += 1;
       }
    }

    /** Returns TRUE if this contains VALUE */
    boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (items[i].equals(value)) {
                return true;
            }
        }

        return false;
    }

    /** Returns the number of items in the set */
    int size() {
        return size;
    }

    /** Returns an Iterator to iterate through this */
    public Iterator<T> iterator() {
        return new ArraySetIterator();
    }

    public static <G> ArraySet<G> of(G...values) {
        ArraySet<G> toReturnSet = new ArraySet<>();

        for (G item : values) {
            toReturnSet.add(item);
        }

        return toReturnSet;
    }

    @Override
    public String toString() {

        /*
        Strings are immutable, so concatenating requires creating a new String.
        Use StringBuilder instead.
        String s = "[";

        for (int i = 0; i < size - 1; i = i + 1) {
                s = s + items[i].toString() + ", ";
        }

        s = s + items[size - 1].toString();

        return s + "]";
         */
        /*
        StringBuilder s = new StringBuilder("{");

        for (int i = 0; i < size - 1; i = i + 1) {
            s.append(items[i].toString());
            s.append(", ");
        }

        s.append(items[size - 1]);
        s.append("}");

        return s.toString(); */

        /*List<String> itemsList = new ArrayList<>(); */

        ArraySet<String> itemsList = new ArraySet<>();

        for (T i : this) {
            itemsList.add(i.toString());
        }

        return "{" + String.join(", ", itemsList) + "}";

    }

    @Override
    /** This equals ArraySet A if A has the number of elements and
     * same elements as this.*/
    public boolean equals(Object a) {
        if (a == this) {
            return true;
        }

        if (a == null) {
            return false;
        }

        if (a.getClass() != this.getClass()) {
            return false;
        }

        ArraySet<T> aSet = (ArraySet<T>) a;
        if (aSet.size != this.size) {
            return false;
        }
        for (int i = 0; i < size; i = i + 1) {
            if (!aSet.contains(items[i])) {
                return false;
            }
        }

        return true;
    }


    private static void test() {
        ArraySet<String> s = new ArraySet<>();

        s.add("Apple");
        s.add("Grape");
        s.add("Apple");

        assertEquals(2, s.size());
        assertTrue(s.contains("Apple"));
        assertFalse(s.contains("Banana"));

        s.add(null);
        s.add("Pear");

        HashSet<String> h = new HashSet<>();
        h.add(null);
        h.add("Green");
        System.out.println(h.contains(null));

        ArraySet<String> s2 = new ArraySet<>();

        s2.add("Apple");
        s2.add("Pear");
        s2.add("Grape");

        Iterator<String> s2Iterator = s2.iterator();

        while (s2Iterator.hasNext()) {
            System.out.println(s2Iterator.next());
        }

        for (String word : s2) {
            System.out.println(word);
        }

        System.out.println(s2);

        ArraySet<String> s3 = new ArraySet<>();

        s3.add("Banana");
        s3.add("Orange");

        assertNotEquals(s2, s3);

        ArraySet<Integer> integerSet = new ArraySet<>();

        integerSet.add(3);
        integerSet.add(5);
        integerSet.add(6);

        assertNotEquals(s2, integerSet);

        ArraySet<String> sCopy = new ArraySet<>();

        for (String word : s2) {
            sCopy.add(word);
        }

        assertEquals(s2, sCopy); /* This calls s2.equals(sCopy) */

        ArraySet<String> aSetOf = ArraySet.of("Apple", "Pear", "Grape");
        assertEquals(aSetOf, s2);
    }

    public static void main(String[] args) {
        test();
    }

}

/** Throw excpetion to give more control relative to implicit exception (code just crashes)
 *  Can give a more helpful message
 *  Alternative - make code null safe so code doesn't crash
 *          add (if value == null) to the add method
 *          change contains (if items[i] == null) return value == null
 *          make sure to put in documentation "can't add null values" so users know
 *          what to expect
 * Java HashSet allows for null values
 * Iterator
 *      implement an iterator so that ArraySet can be used in an enhanced for loop
 *      have a method that returns at Iterator in ArraySet
 * Nested class with a generic that's same as outer class, don't need generic for nested
 *      ArraySetIterator instead of ArraySetIterator<T>
 * To support an enahcned for loop, the object must have an iterator method
 *      have class implement Iterable, which gurantees to the compiler the class
 *          has an iterator method
 *  System.out.println(some_list), implicitly is saying to
 *          String s = some_list.toString()
 *          System.out.println(s)
 *  All classes are subclasses of Object class so they inherit Object methods
 *  Static methods can't access a generic variable
 *      static methods are called by ArraySet.method - no instantiation is needed
 *  Packages
 *      org.junit - compiler looks in org/junit file path**/
