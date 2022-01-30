package byow.Core;

public class UnionFind {
    /** nodes[i] represents the immediate root of i. If nodes[i] < 0, |nodes[i]| represents the number
     * of elements in the set that i belongs to and i is the root.*/
    int[] nodes;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        nodes = new int[n];
        for (int i = 0; i < nodes.length; i = i + 1) {
            nodes[i] = -1;
        }
        // TODO
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex < 0 || vertex >= nodes.length) {
            throw new IllegalArgumentException("Vertex is not in any set");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        validate(v1);
        return nodes[find(v1)] * -1;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        validate(v1);
        return nodes[v1];

    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        validate(v1);
        validate(v2);

        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);

        if (sizeOf(v1) <= sizeOf(v2)) {
            int addSize = sizeOf(v1);
            nodes[find(v1)] = find(v2);
            nodes[find(v2)] = nodes[find(v2)] + addSize * -1;
        } else {
            int addSize = sizeOf(v2);
            nodes[find(v2)] = find(v1);
            nodes[find(v1)] = nodes[find(v1)] + addSize * -1;
        }

        // TODO
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        if (nodes[vertex] < 0) {
            return vertex;
        } else {
            int toReturn = find(nodes[vertex]);
            nodes[vertex] = toReturn;
            return toReturn;
        }
    }
}
