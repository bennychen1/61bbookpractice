package byow.Core;

/** A Point class to represent a location based on its row index and column index. **/
public class Point {

        /** The row index of the point. **/
        int row;

        /** The column index of the point. **/
        int col;

        Point(int col, int row) {
            this.row = row;
            this.col = col;
        }

    /** Returns the horizontal distance between two points.
     * If this point is in column 1 and p1 is in column 3, horizontal distance is 2.
     * @param   p1  The first Point object
     */
    public int horizontalDistance(Point p1) {
        return Math.abs(this.col - p1.col);
    }

    /** Returns the vertical distance between this point and another.
     * If this point is at row 1, p1 is at row 3, then vertical distance is 2.
     * @param   p1  The first Point object
     */
    public int verticalDistance(Point p1) {
        return Math.abs(this.row - p1.row);
    }

    /** Returns a Point located to the left of this Point. Returns null if point is already
     located on the leftmost column. */
    public Point pointToLeft() {
        if (this.col == 0) {
            return null;
        }

        return new Point(this.col - 1, this.row);
    }

    /** Returns a Point located to the right of this Point. Returns null if point is already
     located on the rightmost column as specified
     * @param   rightMax      An integer representing the right most column index.
     */
    public Point pointToRight(int rightMax) {
        if (this.col == rightMax) {
            return null;
        }

        return new Point(this.col + 1, this.row);
    }

    /** Returns a Point located to the bottom of this point. Returns null if point is already
     located on the bottom row. */
    public Point pointToBottom() {
        if (this.row == 0) {
            return null;
        }

        return new Point(this.col, this.row - 1);
    }

    /** Returns a Point located to the top of this point. Returns null if point is already
     located on the top row as specified.
     * @param   topMax      An integer representing the index of the topmost row.
     */
    public Point pointToTop(int topMax) {
        if (this.row == topMax) {
            return null;
        }

        return new Point(this.col, this.row + 1);
    }

    @Override
    public String toString() {
        return "Column " + this.col + " Row " + this.row;
    }

}

