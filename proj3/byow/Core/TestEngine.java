package byow.Core;

import byow.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class TestEngine {


    @Test
    public void testDrawTwoRooms() {
        RandomMap m = new RandomMap(70, 30, Tileset.SAND, 20);

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

        assertEquals(17, m.helper2DIndexConvertor(new Point(2, 3)));
    }

    @Test
    public void testUnion() {
        RandomMap m = new RandomMap(5,5, 1);
        m.drawWorld();
    }

}
