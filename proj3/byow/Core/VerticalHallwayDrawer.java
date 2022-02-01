package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class VerticalHallwayDrawer implements HallwayDrawer{

    @Override
    public void draw(RandomMap m, Room startRoom, Point startPoint, int numTiles) {
        int startingCol = startPoint.col;
        int startingRow = startPoint.row;

        TETile[] curTileArray = m.getTileArray()[startingCol];

        for (int curRow = startingRow; curRow < startingRow + numTiles; curRow = curRow + 1) {
            if (!curTileArray[startingRow].equals(Tileset.FLOOR)) {
                m.setTileArray(startingCol, curRow, Tileset.FLOOR);
                m.unionPoints(m.helper2DIndexConvertor(startPoint),
                        m.helper2DIndexConvertor(startingCol, curRow) );
            }
        }
    }
}
