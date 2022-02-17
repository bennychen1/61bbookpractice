package byow.Core;

/** A Point class to represent a location based on its row index and column index. **/
public class Point {

    /** The row index of the point. **/
    private int row;

    /** The column index of the point. **/
    private int col;

    /**
     * The full constructor for Point.
     * @param col   the column index.
     * @param row   the row index.
     */
    Point(int col, int row) {
        this.row = row;
        this.col = col;
    }

    /**
     * Create a copy of Point p.
     * @param p A Point object.
     */
    Point(Point p) {

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

    /**
     * Return the Point to this point's left upper diagonal.
     * @param topMax    An int representing the index of the top most row.
     * @return  A Point or null if this point is already on the top most row.
     */
    public Point pointLeftUpDiagonal(int topMax) {
        Point top = this.pointToTop(topMax);
        if (top != null) {
            return top.pointToLeft();
        } else {
            return null;
        }
    }

    /**
     * Return the Point to this point's right upper diagonal.
     * @param topMax    An int representing the index of the top most row.
     * @param rightMax  An int representing the idnex of the right most row.
     * @return  A Point or null if this point is already on the top most row or on the right most column.
     */
    public Point pointRightUpDiagonal(int topMax, int rightMax) {
        Point top = this.pointToTop(topMax);
        if (top != null) {
            return top.pointToRight(rightMax);
        } else {
            return null;
        }
    }

    /**
     * Return the Point to this point's left lower diagonal.
     * @return  A Point or null if this point is already on the top most row.
     */
    public Point pointLeftLowerDiagonal() {
        Point bot = this.pointToBottom();
        if (bot != null) {
            return bot.pointToLeft();
        } else {
            return null;
        }
    }

    /**
     * Return the Point to this point's right lower diagonal.
     * @param   rightMost   An int representing the right most column index.
     * @return  A Point or null if this point is already on the top most row.
     */
    public Point pointRightLowerDiagonal(int rightMost) {
        Point bot = this.pointToBottom();
        if (bot != null) {
            return bot.pointToRight(rightMost);
        } else {
            return null;
        }
    }

    /** Return the column index of this point. **/
    public int getCol() {
        return this.col;
    }

    /** Return the row index of this point. **/
    public int getRow() {
        return this.row;
    }


    @Override
    public String toString() {
        return "Column " + this.col + " Row " + this.row;
    }

    @Override
    public boolean equals(Object otherPoint) {
        if (otherPoint.getClass() != this.getClass()) {
            return false;
        }

        Point o = (Point) otherPoint;

        return (o.getCol() == this.getCol() && o.getRow() == o.getRow());
    }

}

