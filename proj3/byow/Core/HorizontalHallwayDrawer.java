package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HorizontalHallwayDrawer implements HallwayDrawer{

    @Override
    public FinishHallwayInformation draw(RandomMap m, Room r1, Point p1,
                     Room r2, Point p2) {

        Point startPoint;
        Room startRoom;

        Point endPoint;
        Room endRoom;

        if (p1.col < p2.col) {
            startPoint= p1;
            startRoom = r1;
            endPoint = p2;
            endRoom = r2;

        } else {
            startPoint = p2;
            startRoom = r2;
            endPoint = p1;
            endRoom = r1;
        }


        int startingCol = startPoint.col;
        int startingRow = startPoint.row;

        int maxWidth = p1.horizontalDistance(p2);

        int numTiles = RandomUtils.uniform(m.ran, 1, maxWidth + 2);


        Point curPoint = startPoint;

        for (int curCol = startingCol; curCol < startingCol + numTiles; curCol = curCol + 1) {
            TETile[] curTileArray = m.getTileArray()[curCol];
            Point newPoint = new Point(curCol, startingRow);
            curPoint = newPoint;
            if (!curTileArray[startingRow].equals(Tileset.FLOOR)) {
                m.setTileArray(newPoint, Tileset.FLOOR);
            }

            m.unionPoints(m.helper2DIndexConvertor(startRoom.start),
                    m.helper2DIndexConvertor(newPoint));
            ;
        }

        return new FinishHallwayInformation(endRoom, endPoint, startRoom, curPoint);
    }
}
