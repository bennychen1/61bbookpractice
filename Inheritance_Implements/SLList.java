public class SLList<T> implements List61b<T> {
    protected int size;
    protected IntNode sentinel;

    private class IntNode<T> {
        T item;
        IntNode next;
        public IntNode(T item) {
            this.item = item;
        }

        public IntNode(T item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    public SLList() {
        sentinel = new IntNode(null, null);
    }

    public SLList(T item) {
        sentinel = new IntNode(null, null);
        sentinel.next = new IntNode(item, null);
        size = 1;
    }

    @Override
    public void addLast(T item) {
        IntNode p = sentinel;

        for (int i = 0; i < size; i++) {
            p = p.next;
        }

        p.next = new IntNode(item, null);

        size += 1;
    }

    @Override
    public T getLast() {
        IntNode p = sentinel;

        for (int i = 0; i < size; i++) {
            p = p.next;
        }

        return (T) p.item;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeLast() {
        IntNode p = sentinel;

        for (int i = 0; i < size - 1; i++) {
            p = p.next;
        }

        T toReturn = (T) p.next.item;

        p.next = null;

        size -= 1;

        return toReturn;
    }

    @Override
    public T getFirst() {
        return (T) sentinel.next.item;
    }

    @Override
    public void addFirst(T item) {
        IntNode toAdd = new IntNode(item, sentinel.next);
        sentinel.next = toAdd;
        size += 1;
    }

    @Override
    public T get(int i) {
        IntNode p = sentinel;

        while (i >= 0 && i < size) {
            p = p.next;
            i -= 1;
        }

        return (T) p.item;
    }

    @Override
    public void print() {
        for (IntNode p = sentinel.next; p != null; p = p.next) {
            System.out.print(p.item + " ");
        }

        System.out.println();
    }

}
