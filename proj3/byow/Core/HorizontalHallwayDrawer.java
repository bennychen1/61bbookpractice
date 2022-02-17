package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HorizontalHallwayDrawer extends HallwayDrawer{

    @Override
    public FinishHallwayInformation draw(RandomMap m, Room r1, Point p1,
                     Room r2, Point p2) {


        Point startPoint;
        Room startRoom;

        Point endPoint;
        Room endRoom;

        if (p1.getCol() < p2.getCol()) {
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

        int maxWidth = p1.horizontalDistance(p2);

        int numTiles = helperNumTiles(m, maxWidth);
        
        Point curPoint = startPoint;

        for (int curCol = startingCol; curCol < startingCol + numTiles; curCol = curCol + 1) {
            TETile[] curTileArray = m.getTileArray()[curCol];
            curPoint = helperDrawAndUnion(m, curTileArray, curCol, startingRow, startRoom);
        }

        return new FinishHallwayInformation(endRoom, endPoint, startRoom, curPoint);
    }
}
