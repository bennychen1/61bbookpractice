import static org.junit.Assert.*;

public class BubbleGrid {
    /** Bubbles are represented in a 2D array, coordinates are the row and col indices.
     * 0 = empty, 1 = bubble
     */
    int[][] bubbles;

    /** Number of rows **/
    int r;

    /** Number of columns **/
    int c;

    public BubbleGrid(int[][]grid) {
        bubbles = grid;
        r = grid.length;
        c = grid[0].length;
    }

    public int[] popBubbles(int[][]darts) {
        return null;
    }

    public static void main(String[] args) {
        int[][]grid = new int[][]{{1, 0, 0}, {1, 1, 0}, {1, 1, 1}};
        int[][] darts = new int[][]{{2, 0}, {1, 1}};
        int[] result = new int[]{2, 0};

        BubbleGrid b = new BubbleGrid(grid);
        assertArrayEquals(result, b.popBubbles(darts));

    }
}
