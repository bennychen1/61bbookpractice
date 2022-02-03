package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HorizontalHallwayDrawer implements HallwayDrawer{

    @Override
    public Point draw(RandomMap m, Room r1, Point p1,
                     Room r2, Point p2) {

        Point startPoint;
        Room startRoom;

        if (p1.col < p2.col) {
            startPoint= p1;
            startRoom = r1;

        } else {
            startPoint = p2;
            startRoom = r2;
        }


        int startingCol = startPoint.col;
        int startingRow = startPoint.row;

        int maxWidth = p1.horizontalDistance(p2);

        int numTiles = RandomUtils.uniform(m.ran, 1, maxWidth + 2);


        for (int curCol = startingCol; curCol < startingCol + numTiles; curCol = curCol + 1) {
            TETile[] curTileArray = m.getTileArray()[curCol];
            if (!curTileArray[startingRow].equals(Tileset.FLOOR)) {
                m.setTileArray(curCol, startingRow, Tileset.FLOOR);
                m.unionPoints(m.helper2DIndexConvertor(startRoom.start),
                        m.helper2DIndexConvertor(curCol, startingRow));
            }
        }
    }
}
