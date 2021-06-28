package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** An NxN grid.
     * If grid[i][j] = 0, the the site [i,j] is blocked.
     * if grid[i][j] = 1, the site is open.
     * Site on the grid can also be presented as an integer S
     *      where S / N gives the row index and S % N gives the col index.
     * **/
    int[][]grid;

    /** The dimesion of the grid **/
    int size;

    /** A WQU of the open sites. Two sites are in the same union if they can be reached
     * by going through consecutive open sites. **/
    WeightedQuickUnionUF w;

    /** The number of open sites. **/
    int numberOfOpenSites;

    /** Create an NxN grid with all sites initially blocked. **/
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("The grid size must be greater than 0.");
        }
        this.grid = new int[N][N];
        this.size = N;
        w = new WeightedQuickUnionUF((N * N) + (N * N) + (N * N));

        for (int i = (N * N) + 1; i < (N * N) + (N * N); i = i + 1) {
            w.union((N * N), i);
        }

        for (int i = 0; i < N; i = i + 1) {
            w.union((N * N) + 1, i);
        }

        /**
        for (int i = (N * N) + (N * N) + 1; i < 3 * (N * N); i = i + 1) {
            w.union(2 * (N * N), i);
        }

        for (int i = (N * N) - N; i < (N * N); i = i + 1) {
            w.union((2 * (N * N)), i);
        } **/
    }

    /** Open the site (row, col) if it is not already open.
     * Throws exception if (row, col) in not valid.
     * **/
    public void open(int row, int col) {
        checkRowCol(row, col);
        if (isOpen(row, col)) {
            return;
        }
        this.grid[row][col] = 1;

        int curSite = convertRowCol(row, col);

        int left = col - 1;
        int right = col + 1;
        int up = row + 1;
        int down = row - 1;


        if(left >= 0 && isOpen(row, left)) {
            w.union(convertRowCol(row, left), curSite);
        }

        if(right < size && isOpen(row, right)) {
            w.union(convertRowCol(row, right), curSite);
        }

        if (down >= 0 && isOpen(down, col)) {
            w.union(convertRowCol(down, col), curSite);
        }

        if (up < size && isOpen(up, col)) {
            w.union(convertRowCol(up, col), curSite);
        }

        numberOfOpenSites = numberOfOpenSites + 1;

        if (checkBottomRow(row, col) && isFull(row, col)) {
            w.union(convertRowCol(row, col), (this.size * this.size * 2));
        }

    }

    /** Returns true if (row, col) is open.
     * Return false otherwise.
     */
    public boolean isOpen(int row, int col) {
        checkRowCol(row, col);
        return this.grid[row][col] == 1;
    }

    /** Returns true if the site (row, col) is full.
     *  Return false otherwise
     * **/
    public boolean isFull(int row, int col) {
        checkRowCol(row, col);
        int coordinate = convertRowCol(row, col);
        if (coordinate >= 0 && coordinate < size && !isOpen(row, col)) {
            return false;
        }
        int root = w.find(coordinate);
        return root == (size * size);
    }

    /** Returns the number of open sites on the grid. */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /** Returns true if the grid percolates. */
    public boolean percolates() {
        return w.connected(this.size * this.size,  2 * this.size * this.size);
    }

    /** Checks if (row, col) is a valid index for the the grid.
     * Throws IndexOutofBound Exception if either ROW or COL is >= N.
     * Throws IllegalArgumentException if either ROW or COL is < 0.
     **/
    public void checkRowCol(int row, int col) {
        if (row >= grid.length || col >= grid.length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException("Row or column is invalid.");
        }
    }

    /** Converts a site into its integer representation based on (row, col)
     * S = row * N + col.
     * (2, 1) becomes 7 in a 3x3 grid.
     */
    public int convertRowCol(int row, int col) {
        checkRowCol(row, col);
        return row * this.grid.length + col;
    }

    /** Returns true if site is in the bottom row. */
    private boolean checkBottomRow(int row, int col) {
        int totalSites = this.size * this.size;
        if (convertRowCol(row, col) >= totalSites - this.size && convertRowCol(row, col) < totalSites) {
            return true;
        } else {
            return false;
        }
    }


}
