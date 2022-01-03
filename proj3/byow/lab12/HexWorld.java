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
     * @param   x   int, the x coordinate to start the hexagon.
     * @param   y   int, the y coordinate to start the hexagon.
     * @param   r   int, the height of the hexagon.
     * **/
    void addHexagon(int s, int x, int y, int r) {
        if (s < 2 || r < 4 || r % 2 != 0 || !canDrawHexagon(s, x, y, r)) {
            return;
        }

        int numRows = r / 2;
        int curAdd = 0;

        for (int counter = 0; counter < numRows; counter = counter + 1){
            TETile[] currentTileArrayLeft = this.world[x - numRows + 1 + counter];
            TETile[] currentTileArrayRight = this.world[x + s + numRows - 2 - counter];
            int topCol = y + numRows + curAdd;
            int bottomCol = y + numRows -1 - curAdd;
            helperDrawColumns(currentTileArrayLeft, bottomCol, topCol);
            helperDrawColumns(currentTileArrayRight, bottomCol, topCol);
            curAdd = curAdd + 1;
        }

    }

    /** Helper method to check if a hexagon can be drawn within the bounds of the world.
     * @param   s   int, the base width of the hexagon.
     * @param   x   int, the x coordinate to start the hexagon.
     * @param   y   int, the y coordinate to start the hexagon.
     * @param   r   int, the height of the hexagon.
     * **/
    private boolean canDrawHexagon(int s, int x, int y, int r) {
        if (x + s > this.w || y + r > this.h || !checkWidestPoint(s, x, y, r)) {
            return false;
        }

        return true;
    }

    /** Helper method to check if the width of the hexagon fits within the world. **/
    private boolean checkWidestPoint(int s, int x, int y, int r) {
        int addToEachSideAtWidestPoint = (r - 2) / 2;

        if (x - addToEachSideAtWidestPoint < 0 || (x + s - 1) + addToEachSideAtWidestPoint >= this.w) {
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



    public static void main(String[] args) {
        HexWorld h = new HexWorld(15, 12);
        h.world[5][5] = Tileset.FLOWER;
        h.addHexagon(2, 5, 5, 6);
        h.ter.renderFrame(h.world);


    }
}
