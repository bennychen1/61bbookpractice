package Encapsulation;

import java.util.LinkedList;

public class Stack<Item> {
    LinkedList<Item> lst;

    void push(Item x) {
        lst.push(x);
    }
}
/**
 *  This implementation is called delegation
 *          it delegates the execution of push to the LinkedList class
 *          Stack does not extend LinkedList so it is not a LinkedList
 *  An Extension implementation would look like Stack<Item> extends LinkedList<Item>
 *      Stack is-a LinkedList
 *  General implementation by making lst List lst
 *          any implementation of list would work
 *  **/
