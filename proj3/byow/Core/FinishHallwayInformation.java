package byow.Core;

/** Stores arguments to use for drawing the next hallway segment. Contains instance variables for
 * each argument in draw function in the HallwayDrawer interface besides map.
 */
public class FinishHallwayInformation {
    /** A Room object. */
    Room r1;

    /** A Point in r1. */
    Point p1;

    /** A Room object. */
    Room r2;

    /** A Point in r2. */
    Point p2;

    FinishHallwayInformation(Room r1, Point p1, Room r2, Point p2) {
        this.r1 = r1;
        this.p1 = p1;
        this.r2 = r2;
        this.p2 = p2;
    }
}
