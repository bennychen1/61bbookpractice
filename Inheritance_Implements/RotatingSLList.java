public class RotatingSLList<T> extends SLList<T> {

    /**Rotate list to the right */
    void rotate(){
        T lastItem = removeLast();
        addFirst(lastItem);
    }

}

/** Inherits variables, but access in the superclass can't be private */
