package byow.Core;

import byow.TileEngine.Tileset;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

import byow.Core.InteractiveMap.Avatar;

import java.util.ArrayList;

public class TestEngine {

    private TERenderer t = new TERenderer();


    @Test
    public void testDrawTwoRooms() {
        RandomMap m = new RandomMap(70, 30, Tileset.SAND, 2);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
        t.initialize(70, 30);
        t.renderFrame(tileArray);


        //displayRenderer();
    }

    @Test
    public void testDrawHallways() {
        RandomMap m = new RandomMap(20, 15, Tileset.SAND, 10);

        m.drawWorld();


        TETile[][] tileArray = m.getTileArray();
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

        t.initialize(30, 30);
        t.renderFrame(tileArray);

        //displayRenderer();

    }

    @Test
    public void testInteractiveMap() {
        RandomMap m = new RandomMap(10, 10, Tileset.GRASS, 2);
        Point originalPoint = new Point(1, 2);
        InteractiveMap.Avatar a = new InteractiveMap.Avatar('@', originalPoint);

        InteractiveMap iMap = new InteractiveMap(m, a);

        t.initialize(10, 10);
        t.renderFrame(iMap.getGameMap().getTileArray());


        displayRenderer();

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
    public void testInteractOneMove() {
        Engine e = new Engine();

        e.interactWithInputString("n1ss");

        //(23, 10)

        Avatar testAvatar = getFirstAvatar(e);

        InteractiveMap iMap = e.getiMap();


        Point expectedPoint2 = new Point(23, 9);
        assertEquals(expectedPoint2, testAvatar.getLocation());


        //renderAndDisplay(e);


        //assertEquals(new Point(14, 3), 1);


        Engine e1 = new Engine();

        //(9, 32)
        e1.interactWithInputString("n123sd");
        Avatar testAvatar1 = getFirstAvatar(e1);
        //System.out.println(testAvatar1.getLocation());

        //renderAndDisplay(e1);
        assertEquals(new Point(10, 32), testAvatar1.getLocation());

    }

    @Test
    public void testEngineMoveWall() {
        Engine e = new Engine();

        e.interactWithInputString("n1sdwwd");

        Avatar testAvatar = getFirstAvatar(e);

        //assertEquals(new Point(23, 10),testAvatar.getLocation());


        Engine e1 = new Engine();
        e1.interactWithInputString("n1swda");

        Avatar testAvatar1 = getFirstAvatar(e1);

        //assertEquals(new Point(22, 10), testAvatar1.getLocation());

        renderAndDisplay(e);
        //renderAndDisplay(e1);
    }

    @Test
    public void testNotCaseSensitive() {
        Engine e = new Engine();
        e.interactWithInputString("N20SAwD");
        Point point1 = new Point(15, 17);
        assertEquals(point1, getFirstAvatar(e).getLocation());
        //(15, 16)
        renderAndDisplay(e);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidAvatar() {
        RandomMap m = new RandomMap(10, 10, 2);
        Avatar a = new Avatar('@', new Point(11, 5));
        InteractiveMap iMap = new InteractiveMap(m, a);
    }

    @Test
    public void testSaving() {
        Engine firstEngine = new Engine();
        firstEngine.interactWithInputString("N1s");
    }


    private Avatar getFirstAvatar(Engine e) {

        InteractiveMap iMap = e.getiMap();

        ArrayList<Avatar> avatarList = (ArrayList<Avatar>) iMap.getAvatarList();

        Avatar testAvatar = avatarList.get(0);

        return testAvatar;
    }

    private void displayRenderer() {
        while (true) {
            ;
        }
    }

    private void renderAndDisplay(Engine e) {
        InteractiveMap iMap = e.getiMap();
        t.initialize(iMap.getGameMap().getMaxColIndex() + 1, iMap.getGameMap().getMaxRowIndex() + 1);
        t.renderFrame(iMap.getGameMap().getTileArray());
        displayRenderer();
    }





}
