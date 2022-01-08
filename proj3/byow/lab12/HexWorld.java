package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /** The height of the world. **/
    int h;

    /** The width of the world. **/
    int w;

    /** The world to render. **/
    TETile[][] world;

    /** The tile engine renderer. **/
    TERenderer ter;

    HexWorld(int w, int h) {
        this.w = w;
        this.h = h;
        this.world = new TETile[w][h];
        this.ter = new TERenderer();
        this.ter.initialize(w, h);

        for (int i = 0; i < w; i = i + 1) {
            for (int j = 0; j < h; j = j + 1) {
                world[i][j] = Tileset.WALL;
            }
        }
    }

    /** Draws a hexagon of length s (width of first row) with r rows at the specified x and y coordinate.
     * @param   s   int, the base width of the hexagon.
     * @param   col   int, the column index to start the hexagon bottom side.
     * @param   row   int, the row index to start the hexagon bottom side.
     * @param   r   int, the height of the hexagon.
     * **/
    void addHexagon(int s, int col, int row, int r) {
        if (s < 2 || r < 4 || r % 2 != 0 || !canDrawHexagon(s, col, row, r)) {
            return;
        }

        int numRows = r / 2;
        int curAdd = 0;
        int curCol = helperFindLeftDiagonal(col, r);
        int endCol = helperFindRightDiagonal(col, s, r);

        for (; curCol <= endCol; curCol = curCol + 1) {
           TETile[] currentTileArray = this.world[curCol];

           int topRow = row + numRows + curAdd;
           int bottomRow = row + numRows - 1 - curAdd;

           helperDrawColumns(currentTileArray, bottomRow, topRow);

           curAdd = iterateCurAdd(curCol, curAdd, s, col);

        }

    }

    /** Helper method to check if a hexagon can be drawn within the bounds of the world.
     * @param   s       int, the base width of the hexagon.
     * @param   col     int, the bottom left column index to start the hexagon.
     * @param   row     int, the row index of the bottom of the hexagon.
     * @param   r       int, the height of the hexagon.
     * **/
    private boolean canDrawHexagon(int s, int col, int row, int r) {
        if (col + s - 1 >= this.w || row + r >= this.h || !checkWidestPoint(s, col, r)) {
            return false;
        }

        return true;
    }

    /** Helper method to check if the width of the hexagon fits within the world. **/
    private boolean checkWidestPoint(int s, int col, int r) {

        if (helperFindLeftDiagonal(col, r) < 0 || helperFindRightDiagonal(col, s, r) >= this.w) {
            return false;
        }

        return true;
    }

    /** Helper method to draw in a row between specified columns. **/
    private void helperDrawColumns(TETile[] tileArray, int start, int end) {
        for (int col = start; col <= end; col = col + 1) {
            tileArray[col] = Tileset.AVATAR;
        }
    }

    /** Helper to calculate the number of columns in the hexagon. **/
     private int helperNumberOfColumns(int s, int r) {
         return s + r - 2;
     }

     /** Helper to find left diagonal column. **/
     private int helperFindLeftDiagonal(int col, int r) {
         return col - ((r - 2)/2);
     }

    /** Helper to find right diagonal column. **/
    private int helperFindRightDiagonal(int col, int s, int r) {
        return col + s - 1 + ((r - 2) /2);
    }

    /** Helper function to determine how to iterate curAdd for the for loop in addHexagon. If the current
     * column is to the left of the starting point, the increment by 1. If within the columns making up
     * the base of the hexagon, then don't increment. After, decrement curAdd.
     * @param  curCol       int, the current column index.
     * @param  curValue     int, the current value of curAdd.
     * @param  s            int, the side length of the hexagon.
     * @param  col          int, the starting column of the hexagon.
    */
    private int iterateCurAdd(int curCol, int curValue, int s, int col) {
        if (curCol < col) {
            return curValue + 1;
        } else if (curCol >= col && curCol < col + s - 1) {
            return curValue;
        } else {
            return curValue - 1;
        }
    }

    /** Draws a tessalation of 19 hexagons each with side length 3 and 6 deep starting at the given point.
     * @param  p      HexagonPoint, the leftmost point of the bottom hexagon.
    **/
    private void tessalate(HexagonPoint p) {
        HexagonPoint curLeftPoint = p;
        HexagonPoint curRightPoint = p;
        int numDraw = 5;

        for (int i = 0; i < 3; i = i + 1) {
            if (curLeftPoint.col == curRightPoint.col) {
                drawAbove(curLeftPoint, 5);
            } else {
                drawAbove(curLeftPoint, numDraw);
                drawAbove(curRightPoint, numDraw);
            }

            curLeftPoint = helperFindLeftDiagonal(curLeftPoint);
            curRightPoint = helperFindRightDiagonal(curRightPoint);
            numDraw = numDraw - 1;

        }
    }

    /** Helper method that finds the starting point of a hexagon above the provided starting point.   */
    private HexagonPoint helperFindAbove(HexagonPoint p) {
        int toReturnRow = p.row + 6;
        return new HexagonPoint(p.col, toReturnRow);
    }

    /** Helper method that draws the specified number of hexagons going upward from the starting point.
     * If numDraw is 3, then draws a total of three hexagons.
     * @param   p       HexagonPoint representing the bottom left point to start.
     * @param   numDraw int, the number of hexagons to draw.
     * */
    private void drawAbove(HexagonPoint p, int numDraw) {
        HexagonPoint curPoint = p;
        for (int i = 0; i < numDraw; i = i + 1) {
            addHexagon(3, curPoint.col, curPoint.row, 6);
            curPoint = helperFindAbove(curPoint);
        }
    }

    /** Helper method that finds the starting point of a hexagon diagonally to the left of the starting point.   */
    private HexagonPoint helperFindLeftDiagonal(HexagonPoint p) {
        int toReturnRow = p.row + 3;
        int toReturnCol = p.col - 5;
        return new HexagonPoint(toReturnCol, toReturnRow);
    }

    /** Helper method that finds the starting point of a hexagon diagonally to the right of the starting point.   */
    private HexagonPoint helperFindRightDiagonal(HexagonPoint p) {
        int toReturnRow = p.row + 3;
        int toReturnCol = p.col + 3 - 1 + 3;
        return new HexagonPoint(toReturnCol, toReturnRow);
    }

    /** Helper to draw hexagon at the HexagonPoint of side length 3 and depth of 6. **/
    private void helperDrawHexagon(HexagonPoint p) {
        addHexagon(3, p.col, p.row, 6);
    }

    static class HexagonPoint {
        int row;
        int col;

        HexagonPoint(int c, int r) {
            this.row = r;
            this.col = c;
        }
    }


    public static void main(String[] args) {
        HexWorld h = new HexWorld(50, 40);
        h.world[5][5] = Tileset.FLOWER;
        //h.addHexagon(3, 5, 6, 6);
        //h.addHexagon(3, 10, 0, 8);
        //h.addHexagon(5, 15, 15, 6);
        //h.addHexagon(3, 25, 1, 8);
        //h.addHexagon(2, 15, 23, 6);
        h.tessalate(new HexWorld.HexagonPoint(20, 1));
        h.ter.renderFrame(h.world);


    }
}
