package Generics_and_Autoboxing;

import java.util.List;

public interface Map61b<K, V> {
    /** Associates KEY with VALUE in the map */
    void put(K key, V value);

    /** Checks if Map containts KEY */
    boolean contains(K key);

    /** Returns the value of KEY, assuming map contains KEY */
    V get(K key);

    /** Returns a list of all keys */
    List<K> keys();

    /** Returns the number of keys */
    int size();

}
