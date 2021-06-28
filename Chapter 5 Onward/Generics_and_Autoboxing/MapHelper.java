package Generics_and_Autoboxing;

import java.util.List;
import java.util.ArrayList;
import static org.junit.Assert.*;
public class MapHelper {
    public static <K,V> V get(Map61b<K, V> m, K key) {
        if (m.contains(key)) {
            return m.get(key);
        }

        return null;
    }

    public static <K,V extends Comparable<V>> K maxKey(Map61b<K,V> m) {
        List<K> mKeys = m.keys();
        K currentMax = mKeys.get(0);
        for (K key : mKeys) {
            if (m.get(key).compareTo(m.get(currentMax)) > 0) {
                currentMax = key;
            }
        }

        return currentMax;
    }

    public static void test() {
        ArrayMap<String, Integer> am = new ArrayMap<>();
        am.put("Dog", 5);
        am.put("Cat", 2);
        am.put("Bear", 6);

        assertEquals((Integer) 5, MapHelper.get(am, "Dog")); /* (Object, Object)*/
        assertNull(MapHelper.get(am, "Zebra"));

        assertEquals("Bear", MapHelper.maxKey(am));
    }

    public static void main(String[] args) {
        test();
    }
}

/** Examples of generic methods
 * > only works for primitives - use comparables
 * Need Comparable<V> because Comparable has a generic and we want V to be comparable to other V's
 * Type upper bound - whatver class V is extends Comparable<V> is saying the class of V must be a subclass of Comparable
 *      extends in this context is imposing a restriction on what class V can be -
 *          must be a subclass of Comparable
 *      extends in Dog extends Animal is saying Dog is a subclass of animal AND it gives the Dog
 *          class all the methods of animal
 *      with type upper bound, Dog extends Animal is only saying Dog is a subclass of Animal**/
