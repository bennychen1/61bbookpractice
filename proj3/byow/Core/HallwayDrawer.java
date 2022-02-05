package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public abstract class HallwayDrawer {
    /** Draws a hallway of random length between p1 and p2.
     * Returns the last point of the drawn hallway.
     * @param m     A RandomMap where the hallway will be drawn.
     * @param r1    A random Room in the map
     * @param p1    A random Point in Room r1
     * @param r2    A random Room in the map.
     * @param p2    A random point in Room r2.
     * @return  A FinishHallwayInformation that will contain the point the hallway segment finishes at,
     *             and the room and point not used when drawing the hallway segment.
     */
    public abstract FinishHallwayInformation draw(RandomMap m, Room r1, Point p1, Room r2,
                     Point p2);

    /** Returns a random number of tiles that the hallway segment will contain, between 1 and
     * the provided maximum.
     * @param maxTiles   an int representing the maximum number of tiles.
     * @param m          A RandomMap that has the random number generator to use.
     * @return  An int representing the number of tiles the hallway segment will have.
     */
    public int helperNumTiles(RandomMap m, int maxTiles) {
        return RandomUtils.uniform(m.ran, 1, maxTiles + 2);
    }

    /** Draws a floor at a specified location and unions that location with the points in the provided room.
     * @param   m               A RandomMap object
     * @param   curTileArray    A TETile array that represents a column in the map.
     * @param   curRow          An int representing the row index of the location.
     * @param   newPoint        A Point object representing the location of the tile at the provided row in the provided column.
     * @param   startRoom       A Room object.
     * */
    public void helperDrawAndUnion(RandomMap m, TETile[] curTileArray, int curRow,
                                   Point newPoint, Room startRoom) {
        if (!curTileArray[curRow].equals(Tileset.FLOOR)) {
            m.setTileArray(newPoint, Tileset.FLOOR);
        }

        m.unionPoints(m.helper2DIndexConvertor(startRoom.start),
                m.helper2DIndexConvertor(newPoint));
    }


}
