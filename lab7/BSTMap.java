import edu.princeton.cs.algs4.BST;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {

    /** The key for this node. */
    private K key;

    /** The value of this key. */
    private V value;

    /** The left branch of this BSTMap. */
    private BSTMap<K, V> left;

    /** The right branch of this BSTMap. */
    private BSTMap<K, V> right;

    /** The number of mappings in this BSTMap. */
    private int size;

    /** ArrayList of keys of this BSTMap. */
    ArrayList<K> keyArray;

    /** An iterable for a BSTMap. **/
    private class BSTMapIterator implements Iterator<K> {
        int pointer;

        BSTMapIterator(int size, BSTMap<K, V> b) {
            keyArray = new ArrayList<>(size);
            fillKeyArray(keyArray, b);
        }

        @Override
        public boolean hasNext() {
            return pointer < keyArray.size();
        }

        @Override
        public K next() {
            K toReturn = keyArray.get(pointer);
            pointer = pointer + 1;
            return toReturn;
        }


    }

    /** Create a BSTMap with root with KEY and VALUE. */
    public BSTMap(K key, V value) {
        this.key = key;
        this.value = value;
        this.size = 1;
    }

    public BSTMap() {
        this(null, null);
        this.size = 0;
    }

    /** Removes all mappings from this BSTMap. **/
    @Override
    public void clear() {
        this.key = null;
        this.value = null;
        this.left = null;
        this.right = null;
        this.size = 0;
    }

    /** Returns true if this BSTMap contains a mapping for KEY. */
    @Override
    public boolean containsKey(K key) {
        if (this.size == 0) {
            return false;
        }
        return containsKeyHelper(key, this);
    }

    /** Helper function for containsKey. **/
    private boolean containsKeyHelper(K key, BSTMap<K, V> b) {
        if (b == null) {
            return false;
        }
        if (b.key.compareTo(key) == 0) {
            return true;
        } else if (b.key.compareTo(key) < 0) {
            return containsKeyHelper(key, b.right);
        } else {
            return containsKeyHelper(key, b.left);
        }

    }

    /** Returns the value to which KEY is mapped.
     * Returns null if this BSTMap does not contain KEY. */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }

        return getHelper(key, this);
    }

    /** Helper method for get. **/
    private V getHelper(K key, BSTMap<K, V> b) {
        if (b == null) {
            return null;
        } else if (b.key.equals(key)) {
            return b.value;
            /* this.value - problem occurs when you finish recursion and return to original method call */
        } else if (b.key.compareTo(key) < 0) {
            return getHelper(key, b.right);
        } else {
            return getHelper(key, b.left);
        }
    }

    /** Associates KEY and VALUE in this BSTMap.
     * If KEY is not in this BSTMap, add the key-value pair.
     * Change the value of KEY to VALUE if KEY is already in this BSTMap. */
    @Override
    public void put(K key, V value) {
        if (this.key == null) {
            this.key = key;
            this.value = value;
        } else {
            putHelper(key, value, this);
        }

        this.size += 1;
        updateKeyArray();
    }

    /** Helper function for put. **/
    private BSTMap<K, V> putHelper(K key, V value, BSTMap<K, V> b) {
        if (b == null) {
            b = new BSTMap<>(key, value);
        } else if (b.key.compareTo(key) == 0) {
            b.value = value;
        } else if (b.key.compareTo(key) < 0) {
            b.right = putHelper(key, value, b.right);
        } else {
            b.left = putHelper(key, value, b.left);
        }
        return b;
    }

    /** Returns a set view of the keys in this BSTMap. */
    @Override
    public Set keySet() {
        Set<K> setOfKeys = new HashSet<>();
        keySetHelper(setOfKeys, this);
        return setOfKeys;
    }

    /** Helper function of keySet. */
    private void keySetHelper(Set<K> s, BSTMap<K, V> b) {
        if (b != null) {
            s.add(b.key);
            keySetHelper(s, b.left);
            keySetHelper(s, b.right);
        }
    }

    /** Remove KEY from this BSTMap if present. */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }

        if (size > 0) {
            size = size - 1;
        }
        V toReturn = removeHelper(key, this);
        updateKeyArray();
        return toReturn;
    }

    /** Helper function for remove. */
    private V removeHelper(K key, BSTMap<K, V> b) {
        if (b == null) {
            return null;
        }
        if (b.key.compareTo(key) == 0) {
            V toReturn = b.value;
            K newKey;
            if (b.left == null) {
                newKey = findLeft(b.right);
                b.value = removeHelper(newKey, b.right);
            } else {
                newKey = findRight(b.left);
                b.value = removeHelper(newKey, b.left);
            }
            b.key = newKey;
            return toReturn;

        } else if (b.key.compareTo(key) < 0) {
            return removeHelper(key, b.right);
        } else {
            return removeHelper(key, b.left);
        }
    }

    /** Finds the right-most node of the tree.
     * Returns the key
     */
    private K findRight(BSTMap<K, V> b) {
        if (b == null) {
            return null;
        }
        if (b.right == null) {
            return b.key;
        } else {
            return findRight(b.right);
        }
    }

    /** Finds the left-most node of the tree.
     * Returns the key
     */
    private K findLeft(BSTMap<K, V> b) {
        if (b == null) {
            return null;
        }
        if (b.left == null) {
            return b.key;
        } else {
            return findLeft(b.left);
        }
    }

    /** Removes KEY from this BSTMap if the key-value pair of (KEY, VALUE) is present. */
    @Override
    public V remove(K key, V value) {
        if (!containsKey(key) || this.get(key) != value) {
            return null;
        }
        return remove(key);
    }

    /** Return the number of mappings in this BSTMap. */
    @Override
    public int size() {
        return this.size;
    }

    /** Iterator for this BSTMap. */
    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator(this.size, this);
    }

    /** Helper method to populate the keys array. */
    private void fillKeyArray(List<K> a, BSTMap<K, V> b) {
        if (b == null || b.key == null) {
            return;
        }

        fillKeyArray(a, b.left);
        a.add(b.key);
        fillKeyArray(a, b.right);
    }

    /** Update the key array when an item gets added or removed. */
    private void updateKeyArray() {
        List<K> newKeyArray = new ArrayList<>();
        fillKeyArray(newKeyArray, this);

    }
}

/** Only check if the argument is null, not this == null since can't do null.method
 * Have to use helper methods if I need more functionality for the methods of the interface
 *      example: put is returns void in Map61B interface, but I need it to return a BSTMap
 *                      also can't handle null (base case)
 *               containsKey - can't handle null (base case) with the interface method
 * Run the speed tests:
 *      insertRandom - BSTMap is pretty fast
 *      insertOrder - inserting strings starting at x and then going down the dictionary
 *              inserting on the right so there is a spindly tree, which is slow
 * **/