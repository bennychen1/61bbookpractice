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

    /** The tile rendering engine. **/
    TERenderer ter;

    HexWorld(int w, int h) {
        this.ter = new TERenderer();
        this.w = w;
        this.h = h;

        ter.initialize(w, h);
    }

    /** Draws a hexagon of length s (width of first row) with r rows at the specified x and y coordinate.
     * @param   s   int, the base width of the hexagon.
     * @param   x   int, the x coordinate to start the hexagon.
     * @param   y   int, the y coordinate to start the hexagon.
     * @param   r   int, the height of the hexagon.
     * **/
    void addHexagon(int s, int x, int y, int r) {
        if (s < 2 || r < 4 || !canDrawHexagon(s, x, y, r)) {
            return;
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


    }

    private boolean checkWidestPoint(int s, int x, int y, int r) {
        int addToEachSideAtWidestPoint = (r - 2) / 2;

        if (x - addToEachSideAtWidestPoint < 0 || (x + s - 1) + addToEachSideAtWidestPoint >= this.w) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();

        ter.initialize(5, 4);


    }
}
