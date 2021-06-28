package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
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

    public ArrayHeapMinPQ() {
        keys = new ArrayList<>();

        for (int i = 0; i < 10; i = i + 1) {
            keys.add(null);
        }

        this.size = 0;
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
        if (keys.contains(item)) {
            throw new IllegalArgumentException();
        }

        if (this.size == 0) {
            keys.set(1, new Node(item, priority));
        } else {
            int leftMostIndex = 2;

            while (leftMostIndex < keys.size() && keys.get(leftMostIndex) != null) {
                leftMostIndex = leftMostIndex * 2;
            }

            if (leftMostIndex >= keys.size()) {
                for (int i = 0; i < leftMostIndex - keys.size() + 1; i = i + 1) {
                    keys.add(null);
                }
            }




        }
    }



    @Override
    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return false;
    }

    @Override
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        return null;
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

        while (curNode.priority < parentNode.priority) {
            this.keys.set(curIndex, parentNode);
            this.keys.set(parentIndex, curNode);

            curIndex = parentIndex;
            curNode = this.keys.get(curIndex);
            parentNode = this.keys.get(parentOf(curIndex));
        }
    }

    /** Find the left most possible position to add into
     * while still maintaining the completeness of the heap. **/
    private int findLeftMost() {
        int leftMostIndex = 1;

        while (leftMostIndex < this.keys.size()) {
            Node curNode = this.keys.get(leftMostIndex);
            if (curNode == null) {
                break;
            } else {
                int tryLeft = leftChild(leftMostIndex);
                int tryRight = rightChild(leftMostIndex);

                if (tryLeft == -1 || this.keys.get(tryLeft) == null) {
                    leftMostIndex = leftMostIndex * 2;
                    break;
                }

                if (tryRight == -1 || this.keys.get(tryRight) == null) {
                    leftMostIndex = leftMostIndex * 2 + 1;
                    break;
                }

                leftMostIndex = leftMostIndex * 2;
            }
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
