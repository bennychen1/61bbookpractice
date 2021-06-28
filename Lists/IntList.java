public class IntList {
    public int first;
    public IntList rest;

    /** Instantiate an IntList with first as F and rest (tail) as R. **/
    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Returns the size of this IntList, done recursively**/
    public int size() {
        if (rest == null) { /* Make sure to have a base case */
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Returns the size of this IntList, done iteratively **/
    public int iterativeSize() {
        int s = 0;
        IntList cur = this;
        while (cur != null) {
            s += 1;
            cur = cur.rest;
        }
        return s;
    }/** Returns the Ith element (zero indexed) **/
    public Integer get(int i) {
        if (i < 0 || i > this.size()) {
            return null;
        }

        IntList p = this;

        while (i != 0) {
            p = p.rest;
            i -= 1;
        }

        return p.first;
    }

    /** Returns the Ith element, using recursion **/
    public int getRecursive(int i) {
        if (i == 0) {
            return first; /* Same result with this.first */
        }

        return rest.getRecursive(i - 1); /* Same result whether this.rest.getRecurisve */
    }

    public static void main(String[] args) {
        IntList x = new IntList(3, null);
        IntList y = new IntList(2, x);
        IntList z = new IntList(1, y);

        System.out.println(z.getRecursive(2));
        System.out.println(z.get(2));
    }

    /** If can't run main function, may need to go to directory, right click, Mark Directory As: source root  **/
}
