import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {

    /** A set that contains all the keys added to the HashMap. **/
    private Set<K> keys;

    /** Buckets of the HashMap for values. **/
    private ArrayList<ArrayList<V>> valueBuckets;

    /** Buckets of the HashMap for the keys. **/
    private ArrayList<ArrayList<K>> keyBuckets;

    /** The load factor - once size of HashMap exceeds current capacity * load factor, increase capacity.**/
    private double loadFactor;

    /** The current size (number of entries). **/
    private int size;

    /** The current capacity, which is the number of buckets. **/
    private int curCapacity;

    /** The initial size of the HashMap. Does not change. */
    private int initialSize;

    public MyHashMap(int initialSize, double loadFactor) {

        valueBuckets = new ArrayList<>();
        keyBuckets = new ArrayList<>();

        for (int i = 0; i < initialSize; i = i + 1) {
            valueBuckets.add(new ArrayList<>());
            keyBuckets.add(new ArrayList<>());
        }

        this.loadFactor = loadFactor;
        this.size = 0;
        this.curCapacity = initialSize;
        this.initialSize = initialSize;
        keys = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);

    }

    public MyHashMap() {
        this(16, 0.75);
    }

    /*
    private class HashMapIterator implements Iterator<K> {
        public boolean hasNext() {
            return false;
        }

        public K next() {
            return null;
        }
    } */

    @Override
    /**
     * Remove all mappings from the HashMap.
     */
    public void clear() {

        valueBuckets = new ArrayList<>();
        keyBuckets = new ArrayList<>();

        for (int i = 0; i < initialSize; i = i + 1) {
            valueBuckets.add(new ArrayList<>());
            keyBuckets.add(new ArrayList<>());
        }

        this.curCapacity = initialSize;
        keys = new HashSet<>();
        this.size = 0;


    };

    @Override
    /**
     * Returns true if the map contains KEY.
     */
    public boolean containsKey(K key){
        return keys.contains(key);
    }

    @Override
    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (!keys.contains(key)) {
            return null;
        }

        int b = Math.abs(key.hashCode() % curCapacity);

        int keyIndex = keyBuckets.get(b).indexOf(key);

        return valueBuckets.get(b).get(keyIndex);
    }

    @Override
    /** Returns the number of key-value mappings in this map. */
    public int size() {
        return this.size;
    }

    @Override
    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value){
        int b = Math.abs(key.hashCode() % this.curCapacity);

        if (this.containsKey(key)) {
            int keyIndex = keyBuckets.get(b).indexOf(key);
            valueBuckets.get(b).set(keyIndex, value);
        } else {
            keys.add(key);
            valueBuckets.get(b).add(value);
            keyBuckets.get(b).add(key);
            this.size += 1;

            if (this.size / this.curCapacity > this.loadFactor) {
                this.resize();
            }
        }

    }

    @Override
    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
        return this.keys;
    }

    @Override
    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key) {

        if (!this.containsKey(key)) {
            return null;
        }

        int bucket = Math.abs(key.hashCode() % curCapacity);
        int keyIndex = keyBuckets.get(bucket).indexOf(key);
        V toReturn = this.get(key);
        keyBuckets.get(bucket).remove(key);
        valueBuckets.get(bucket).remove(keyIndex);

        size = size - 1;

        keys.remove(key);

        return toReturn;
    }

    @Override
    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value){
        if (!this.containsKey(key) || this.get(key) != value) {
            return null;
        } else {
            return remove(key);
        }
    }

    public Iterator<K> iterator() {
        return keys.iterator();
    }

    /** Resizes the array once size is greater than current capacity multiplied by the load factor. */
    private void resize() {

        int newSize = this.curCapacity * 2;

        for (int i = 0; i < this.curCapacity; i = i + 1) {
            keyBuckets.add(new ArrayList<>());
            valueBuckets.add(new ArrayList<>());
        }

        this.curCapacity = newSize;

        HashSet<K> keysCopy = new HashSet<>();
        keysCopy.containsAll(this.keys);


        for (K key : keysCopy) {
            V value = this.remove(key);
            this.put(key, this.remove(key));
        }

    }

    /** Returns the current capacity, which is the number of buckets. */
    int getCurCapacity() {
        return this.curCapacity;
    }

    /** Returns the load factor for this HashMap. */
    double getLoadFactor() {
        return this.loadFactor;
    }

    /** A helper function that creates and initializes an ArrayList of ArrayLists with
     * a SIZE number of ArrayLists */
    private ArrayList createArrayList(int size) {
        ArrayList a = new ArrayList<>();
        for (int i = 0; i < size; i = i + 1) {
            a.add(new ArrayList<>());
        }
        return a;
    }


}
