package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HorizontalHallwayDrawer implements HallwayDrawer{

    @Override
    public void draw(RandomMap m, Room startRoom, Point startingPoint, int numTiles) {
        int startingCol = startingPoint.col;
        int startingRow = startingPoint.row;

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
