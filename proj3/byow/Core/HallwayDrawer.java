package byow.Core;

public interface HallwayDrawer {
    /** Draws a hallway of random length between p1 and p2.
     * Returns the last point of the drawn hallway.
     * @param m     A RandomMap where the hallway will be drawn.
     * @param r1    A random Room in the map
     * @param p1    A random Point in Room r1
     * @param r2    A random Room in the map.
     * @param p2    A random point in Room r2.
     */
    public FinishHallwayInformation draw(RandomMap m, Room r1, Point p1, Room r2,
                     Point p2);
}
