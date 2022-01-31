package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HorizontalHallwayDrawer implements HallwayDrawer{

    @Override
    public void draw(RandomMap m, Point startingPoint, int numTiles) {
        int startingCol = startingPoint.col;
        int startingRow = startingPoint.row;

        for (int curCol = startingCol; curCol < startingCol + numTiles; curCol = curCol + 1) {
            TETile[] curTileArray = m.getTileArray()[curCol];
            if (!curTileArray[startingRow].equals(Tileset.FLOOR)) {
                curTileArray[startingRow] = Tileset.FLOOR;
                this.roomSets.union(helper2DIndexConvertor(startRoom.start),
                        helper2DIndexConvertor(curCol, startingRow));
            }
        }
    }
}
