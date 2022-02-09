package byow.Core;

import byow.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class TestEngine {


    @Test
    public void testDrawTwoRooms() {
        RandomMap m = new RandomMap(70, 30, Tileset.SAND, 5);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
        TERenderer t = new TERenderer();
        t.initialize(70, 30);
        t.renderFrame(tileArray);

        int i = 0;

        while (i == 0) {
            continue;
        }
    }

    @Test
    public void testDrawHallways() {
        RandomMap m = new RandomMap(20, 15, Tileset.SAND, 10);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
        TERenderer t = new TERenderer();
        t.initialize(20, 15);
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

    @Test
    public void testPoint() {
        Point p = new Point(5, 6);

        Point left = p.pointToLeft();
        Point right = p.pointToRight(10);
        Point rightNull = p.pointToRight(5);
        Point leftUpDiagonal = p.pointLeftUpDiagonal(7);
        Point rightUpDiagonal = p.pointRightUpDiagonal(7, 6);

        assertNull(rightNull);
        assertEquals(4, left.col);
        assertEquals(6, right.col);
        assertEquals(4, leftUpDiagonal.col);
        assertEquals(7, leftUpDiagonal.row);
        assertEquals(6, rightUpDiagonal.col);
        assertEquals(7, rightUpDiagonal.row);
    }

    @Test
    public void testInteractWithInputStringStringCall() {
        Engine.StringCall s = new Engine.StringCall("n123s");
        assertEquals("n", s.getUserOption());
        assertEquals(123, s.getSeed());

        Engine.StringCall s2 = new Engine.StringCall("N1367532e1");
        assertEquals("n", s2.getUserOption());
        assertEquals(1367532, s2.getSeed());
    }

}
