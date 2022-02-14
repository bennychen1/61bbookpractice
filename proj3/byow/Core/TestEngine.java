package byow.Core;

import byow.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class TestEngine {


    @Test
    public void testDrawTwoRooms() {
        RandomMap m = new RandomMap(70, 30, Tileset.SAND, 2);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
        TERenderer t = new TERenderer();
        t.initialize(70, 30);
        t.renderFrame(tileArray);


        //displayRenderer();
    }

    @Test
    public void testDrawHallways() {
        RandomMap m = new RandomMap(20, 15, Tileset.SAND, 10);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
        TERenderer t = new TERenderer();
        t.initialize(20, 15);
        t.renderFrame(tileArray);

        //displayRenderer();
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
        assertEquals(4, left.getCol());
        assertEquals(6, right.getCol());
        assertEquals(4, leftUpDiagonal.getCol());
        assertEquals(7, leftUpDiagonal.getRow());
        assertEquals(6, rightUpDiagonal.getCol());
        assertEquals(7, rightUpDiagonal.getRow());
    }


    @Test
    public void testInteractWithInputString() {
        Engine e = new Engine();

        TETile[][] tileArray = e.interactWithInputString("n123s");

        TERenderer t = new TERenderer();
        t.initialize(30, 30);
        t.renderFrame(tileArray);

        displayRenderer();

    }

    @Test
    public void testInteractiveMap() {
        RandomMap m = new RandomMap(10, 10, Tileset.GRASS, 2);
        Point originalPoint = new Point(1, 2);
        Avatar a = new Avatar('@', originalPoint);

        InteractiveMap iMap = new InteractiveMap(m, a);

        TERenderer t = new TERenderer();
        t.initialize(10, 10);
        t.renderFrame(iMap.getGameMap().getTileArray());


        //displayRenderer();

        Point point1 = new Point(1, 3);

        iMap.moveAvatar(a, point1);

        assertEquals(Tileset.FLOOR.description(), iMap.getGameMap().getTileAt(originalPoint).description());
        assertEquals(Tileset.FLOOR.description(), a.getConsumedTile().description());
        assertEquals(a, iMap.getGameMap().getTileAt(point1));
        assertEquals(point1, a.getLocation());

        Point pointOffMap = new Point(1, 12);

        iMap.moveAvatar(a, pointOffMap);

        assertEquals(point1, a.getLocation());
    }

    @Test
    public void testIsPointValid() {
        RandomMap m = new RandomMap(10, 10, Tileset.SAND, 2);

    }

    private void displayRenderer() {
        while (true) {
            ;
        }
    }



}
