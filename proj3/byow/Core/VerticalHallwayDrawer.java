package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class VerticalHallwayDrawer extends HallwayDrawer{

    @Override
    public FinishHallwayInformation draw(RandomMap m, Room r1, Point p1,
                                         Room r2, Point p2) {

        Point startPoint;
        Room startRoom;


        Point endPoint;
        Room endRoom;

        if (p1.getRow() < p2.getRow()) {
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


        int startingCol = startPoint.getCol();
        int startingRow = startPoint.getRow();

        int maxHeight = p1.verticalDistance(p2);

        int numTiles = helperNumTiles(m, maxHeight);

        Point curPoint = startPoint;

        TETile[] curTileArray = m.getTileArray()[startingCol];

        for (int curRow = startingRow; curRow < startingRow + numTiles; curRow = curRow + 1) {
            curPoint = helperDrawAndUnion(m, curTileArray, startingCol, curRow, startRoom);
        }

        return new FinishHallwayInformation(endRoom, endPoint, startRoom, curPoint);
    }
}
