package byow.Core;

import byow.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class TestEngine {

    @Test
    public void testDrawRectangle() {

        TETile[][] tileArray = createTileArray();

        Engine e = new Engine();
        Point p = new Point(10, 15);

        e.drawRectangle(tileArray, 5, 3, p);


        assertEquals(Tileset.FLOOR, tileArray[15][10]);
        assertEquals(Tileset.FLOOR, tileArray[19][10]);
        assertEquals(Tileset.FLOOR, tileArray[19][9]);
        assertEquals(Tileset.FLOOR, tileArray[16][8]);
        assertEquals(Tileset.SAND, tileArray[20][10]);

        TERenderer t = new TERenderer();
        t.initialize(30, 30);
        t.renderFrame(tileArray);

        int i = 0;

        while (i == 0) {
            continue;
        }
    }

    private TETile[][] createTileArray() {
        TETile[][] tileArray = new TETile[30][30];
        for (int i = 0; i < 30; i = i + 1) {
            TETile[] currArray = tileArray[i];
            for (int j = 0; j < 30; j = j + 1) {
                currArray[j] = Tileset.SAND;
            }
        }

        return tileArray;
    }

    @Test
    public void testDrawTwoRooms() {
        RandomMap m = new RandomMap(70, 30, Tileset.SAND, 10);

        m.drawWorld();


        TETile[][] tileArray = m.tileArray;
        TERenderer t = new TERenderer();
        t.initialize(70, 31);
        t.renderFrame(tileArray);

        int i = 0;

        while (i == 0) {
            continue;
        }
    }

    @Test
    public void testIndexConverter() {
        RandomMap m = new RandomMap(5, 5, 1);

        assertEquals(1, m.helper2DIndexConvertor(1, 0));
        assertEquals(17, m.helper2DIndexConvertor(2, 3));
        assertEquals(24, m.helper2DIndexConvertor(4, 4));
    }

}
