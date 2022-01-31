package byow.Core;

public interface HallwayDrawer {
    /** Draws a hallway numTiles long either vertically or horizontally from point startPoint.
     * Size includes p1.
     *
     */
    public void draw(RandomMap m, Point startPoint, int numTiles);
}
