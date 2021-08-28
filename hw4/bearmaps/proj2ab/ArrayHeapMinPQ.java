package bearmaps.proj2ab;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Hashtable;

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
    private Set keySet;

    /** Hashtable to store the nodes. **/
    private Hashtable<T, Node> nodes;

    public ArrayHeapMinPQ() {
        keys = new ArrayList<>();

        for (int i = 0; i < 10; i = i + 1) {
            keys.add(null);
        }

        this.size = 0;

        keySet = new HashSet(){};
        nodes = new Hashtable<>();
    }

    /** Represents the Nodes of the tree. Stores key and the priority.  */
    private class Node {

        T key;
        double priority;
        int index;

        Node(T key, double priority, int index) {
            this.key = key;
            this.priority = priority;
            this.index = index;
        }

        void setIndex(int index) {
            this.index = index;
        }

    }

    @Override
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (this.contains(item)) {
            throw new IllegalArgumentException(String.format("The ArrayMinHeapPQ already has an item" +
                    "%s", item.getClass().getSimpleName(), item));
        }

        /**
        if (this.size + 1 > this.keys.size() - 1) {
            this.keys.add(null);
        }  **/

        int indexToAdd;
        Node nodeToAdd;

        if (size == 0) {
            indexToAdd = 1;
            nodeToAdd = new Node(item, priority, 1);
        this.keys.set(1, nodeToAdd);
        } else {
            indexToAdd = size + 1;
            this.keys.add(null);
            nodeToAdd = new Node(item, priority, indexToAdd);
            this.keys.set(size + 1, nodeToAdd);
        }

        swapUp(indexToAdd);

        this.size = size + 1;

        this.keySet.add(item);
        this.nodes.put(item, nodeToAdd);

    }



    @Override
    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return this.keySet.contains(item);
    }

    @Override
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (size == 0) {
            throw(new NoSuchElementException("The ArrayHeapMinPQ is empty"));
        }

        return this.keys.get(1).key;
    }

    @Override
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (size == 0) {
            throw(new NoSuchElementException("The ArrayHeapMinPQ is empty"));
        }

        T toReturn = this.keys.get(1).key;

        if (size == 1) {
            this.keys.set(1, null);
        } else {

            swap(1, size);

            this.keys.set(size, null);

            swimDown(1);
        }

        this.keySet.remove(toReturn);
        this.size = this.size - 1;
        return toReturn;
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
        if (!keySet.contains(item)) {
            throw(new NoSuchElementException(String.format("The ArrayMinHeapPQ does not have item %s",
                    item.getClass().getSimpleName(), item)));
        }

        Node nodeToChange = this.nodes.get(item);
        nodeToChange.priority = priority;

        swapUp(nodeToChange.index);
        swimDown(nodeToChange.index);

    }

    /** Returns the keySet for testing. **/
    public Set getKeySet() {
        return this.keySet;
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

            curNode.setIndex(parentIndex);
            parentNode.setIndex(curIndex);

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
            return 0;
        } else {
            return index * 2;
        }
    }

    /** Returns the right most index of the heap. **/
    private int rightMost() {
        if (size % 2 == 0) {
            return size - 1;
        } else {
            return size;
        }
    }

    /** Returns the right child of the item at INDEX. If leaf node, return -1. **/
    private int rightChild(int index) {
        if ((index * 2) + 1 >= this.keys.size()) {
            return 0;
        } else {
            return (index * 2) + 1;
        }
    }

    /** Swaps the node at index A with the node at index B. **/
    private void swap(int A, int B) {
        Node a = this.keys.get(A);
        Node b = this.keys.get(B);
        a.setIndex(B);
        b.setIndex(A);
        this.keys.set(A, this.keys.get(B));
        this.keys.set(B, a);
    }

    /** Swap the node at INDEX down until it is smaller than both its left and right children nodes. **/
    private void swimDown(int index) {
        int curIndex = index;

        int indexToSwap = index;

        Node curNode = this.keys.get(curIndex);
        Node left = this.keys.get(leftChild(index));
        Node right = this.keys.get(rightChild(index));

        /* Right can only be null if left is also null, so loop only stops when
            * both left and right are null
            * left has a node but right is null
            * the priority of the current node is smaller than the priority of both child nodes
        */
        while (left != null && right != null &&
                (curNode.priority > left.priority || curNode.priority > right.priority)) {
            if (left.priority <= right.priority) {
                indexToSwap = leftChild(curIndex);
            } else {
                indexToSwap = rightChild(curIndex);
            }

            swap(curIndex, indexToSwap);

            curIndex = indexToSwap;

            /* curNode = this.keys.get(curIndex); */

            /* indexToSwap = leftChild(curIndex); */
            left = this.keys.get(leftChild(curIndex));
            right = this.keys.get(rightChild(curIndex));
        }

        if (left != null && right == null && curNode.priority > left.priority) {
            indexToSwap = leftChild(curIndex);
            swap(curIndex, indexToSwap);
        }



    }


}
