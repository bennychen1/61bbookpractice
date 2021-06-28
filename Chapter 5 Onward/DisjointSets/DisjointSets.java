package DisjointSets;

public interface DisjointSets {
    /** Connect int X and int Y **/
    void connect(int x, int y);

    /** Check if int X and int Y are connected **/
    boolean isConnected(int x, int y);
}

/** DisjointSets is an ADT - implementations of it will be able to connect two items
 * and check that two items are connected (in the same set). */