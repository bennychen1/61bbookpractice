package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    /** Draw a rectangle of floor tiles at the specified width and length
     * with the upper left corner at the specified point in a tile array.
     * @param   tileArray   TETile[][], the tile array to draw the rectangle in.
     * @param   width       int, the width of the rectangle. How many columns it takes up.
     * @param   length      int, the length of the rectangle. How many rows it takes up.
     * @param   p           Point, the point of the upper left corner of the rectangle.
     * **/
    public void drawRectangle(TETile[][] tileArray, int width, int length, Point p) {
        int startRow = p.row;
        int startCol = p.col;

        int endRow = p.row - length + 1;
        int endCol = p.col + width - 1;

        for (int i = startCol; i <= endCol; i = i + 1) {
            TETile[] currArray = tileArray[i];
            drawRectangleColumns(currArray, endRow, startRow);
        }


    }

    /** Draw floor tiles in the specified array from start index to end index inclusive.
     * @param   tileArray   TETile[], the array to draw the tiles in, represents a column.
     * @param   startRow    int, the starting index.
     * @param   endRow      int, the ending index.
     * **/
    public void drawRectangleColumns(TETile[] tileArray, int startRow, int endRow) {

        for (int i = startRow; i <= endRow; i = i + 1) {
            tileArray[i] = Tileset.FLOOR;
        }


    }
    

    public static void main(String[] args) {
        Engine e = new Engine();
        Point p = new Point(5, 6);

        TETile[][] tileArray = new TETile[10][10];
        for (int i = 0; i < 10; i = i + 1) {
            TETile[] currArray = tileArray[i];
            for (int j = 0; j < 10; j = j + 1) {
                currArray[j] = Tileset.SAND;
            }
        }

        e.drawRectangle(tileArray, 2, 3, p);

        TERenderer t = new TERenderer();
        t.initialize(10, 10);
        t.renderFrame(tileArray);
    }
}
