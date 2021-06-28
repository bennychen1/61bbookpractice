package Generics_and_Autoboxing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ArrayMap<K, V> implements Map61b<K, V> {
    private K[] keys; /* Using array as the core data structure to implement the Map61b interface */
    private V[] values;
    private int size;

    public ArrayMap() {
        keys = (K[]) new Object[10];
        values = (V[]) new Object[10];
    }

    private int getKeyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void put(K k, V v) {
        if (size == keys.length) {
            resize();
        }

        int keyIndex = getKeyIndex(k);

        if (keyIndex > -1) {
            values[keyIndex] = v;
        } else {
            keys[size] = k;
            values[size] = v;
            size += 1;
        }

    }

    @Override
    public boolean contains(K k) {

        return getKeyIndex(k) > -1;
    }

    @Override
    public V get(K key) {
        if (contains(key)) {
            return values[getKeyIndex(key)];
        }

        return null;
    }

    @Override
    public List<K> keys() {
        List<K> key_list = new ArrayList<>(){};

        for(int i = 0; i < size; i++) {
            key_list.add(keys[i]);
        }

        return key_list;
    }

    @Override
    public int size() {
        return size;
    }

    private void resize() {
        K[] new_keys = (K[]) new Object[size * 2];
        V[] new_values = (V[]) new Object[size * 2];

        System.arraycopy(keys, 0, new_keys, 0, keys.length);
        System.arraycopy(values, 0, new_values, 0, values.length);

        keys = new_keys;
        values = new_values;
    }

    private static void test() {
        ArrayMap<String, Integer> am = new ArrayMap<>();

        am.put("Dog", 5);
        am.put("Cat", 1);

        int e = 1;

        /*
        assertEquals(e, am.get("Cat"));
        no assertEquals for (int, Integer) so compiler chooses between (long, long) and (Object, Object)

        assertEquals((long) e, (long) am.get("Cat")); widen e to long, am.get("Cat") is implicitly unboxed
            then widened to a long

        For (Object, Object), just cast e to (Integer) and am.get("Cat") is already an Integer
        */
        assertEquals(e, am.get("Cat").longValue());
    }

    public static void main(String[] args) {
        test();
    }
}
