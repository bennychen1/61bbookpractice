package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/** Implements a min heap using an array representation of a binary tree. **/
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /** Nodes of the binary tree. Index 1 is the root of the tree. Index 0 is not used.
     * Left child of k: k * 2
     * Right child of k: (k * 2) + 1
     * Parent of k: k / 2
     */
    ArrayList<Node> keys;

    /** Number of items in the Heap. **/
    int size;

    /** Set containing the key values in the ArrayHeapMinPQ **/
    Set keySet;

    public ArrayHeapMinPQ() {
        keys = new ArrayList<>();

        for (int i = 0; i < 10; i = i + 1) {
            keys.add(null);
        }

        this.size = 0;

        keySet = new HashSet(){};
    }

    /** Represents the Nodes of the tree. Stores key and the priority.  */
    private class Node {

        T key;
        double priority;

        Node(T key, double priority) {
            this.key = key;
            this.priority = priority;
        }

    }

    @Override
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (this.contains(item)) {
            throw new IllegalArgumentException();
        }

        /**
        if (this.size + 1 > this.keys.size() - 1) {
            this.keys.add(null);
        }  **/

        int indexToAdd;

        if (size == 0) {
            indexToAdd = 1;
            this.keys.set(1, new Node(item, priority));
        } else if (size <= 9) {
            indexToAdd = size;
            this.keys.set(size, new Node(item, priority));
        } else {
            indexToAdd = size;
            this.keys.add(new Node(item, priority));
        }

        swapUp(indexToAdd);

        this.size = size + 1;

        this.keySet.add(item);

    }



    @Override
    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return this.keySet.contains(item);
    }

    @Override
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        return this.keys.get(1).key;
    }

    @Override
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        return null;
    }

    @Override
    /* Returns the number of items in the PQ. */
    public int size() {
        return this.size;
    }

    @Override
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        return;
    }

    /** Switch the item at INDEX until it is smaller than it's parent. **/
    private void swapUp(int index) {
        int parentIndex = parentOf(index);

        int curIndex = index;
        Node curNode = this.keys.get(index);
        Node parentNode = this.keys.get(parentIndex);

        while (curIndex >= 1 && curNode.priority < parentNode.priority) {
            this.keys.set(curIndex, parentNode);
            this.keys.set(parentIndex, curNode);

            curIndex = parentIndex;
            curNode = this.keys.get(curIndex);
            parentNode = this.keys.get(parentOf(curIndex));
            parentIndex = parentOf(curIndex);

            T x = curNode.key;
            T y = parentNode.key;
        }
    }

    /** Find the left most possible position to add into
     * while still maintaining the completeness of the heap. **/
    private int leftMost() {

        int leftMostIndex;

        if (this.keys.get(this.size - 1) != null) {
            leftMostIndex = this.size + 1;
        } else {
            leftMostIndex = this.size;
        }



        /** Add to ArrayList so there isn't an index out of bounds exception. **/
        if (leftMostIndex >= this.keys.size()) {
            for (int i = 0; i < leftMostIndex - keys.size() + 1; i = i + 1) {
                keys.add(null);
            }
        }

        return leftMostIndex;
    }



    /** Returns the parent index of the item at INDEX. **/
    private int parentOf(int index) {
        if (index == 1) {
            return 1;
        } else {
            return index / 2;
        }
    }

    /** Returns the left child of the item at INDEX. If leaf node, return -1. **/
    private int leftChild(int index) {
        if (index * 2 >= this.keys.size()) {
            return -1;
        } else {
            return index * 2;
        }
    }

    /** Returns the right child of the item at INDEX. If leaf node, return -1. **/
    private int rightChild(int index) {
        if ((index * 2) + 1 >= this.keys.size()) {
            return -1;
        } else {
            return (index * 2) + 1;
        }
    }


}
