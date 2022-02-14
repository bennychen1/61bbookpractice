package byow.Core;

import byow.TileEngine.TETile;

import java.awt.Color;

/** A Tile that represents the avatar. **/
public class Avatar extends TETile {

    /** A Point representing the current location of the avatar on the map. */
    private Point location;

    /** The tile that the avatar took the place of. **/
    private TETile consumedTile;

    /** The default constructor for the avatar. **/
    Avatar() {
        this('@', new Point(0, 0));
    }


    /**
     * Instantiate an avatar displayed as the provided char that will be placed on the specified location
     * on a map.
     * @param icon  how the avatar will be shown on a map.
     * @param location A Point representing where this will be initially placed on a map.
     */
    Avatar(char icon, Point location) {
        super(icon, Color.white, Color.black, "avatar");
        this.location = location;
    }

    /** A getter method to get the Point location of the avatar. **/
    public Point getLocation() {
        return new Point(this.location.getCol(), this.location.getRow());
    }

    /**
     * A setter method to set the location for this avatar
     * @param col  the column index to set the location to.
     * @param row  the row index to set the location to.
     */
    public void setLocation(int col, int row) {
        this.location = new Point(col, row);
    }

    /**
     * Move to the provided location.
     * @param location A Point representing a location.
     */
    public void setLocation(Point location) {
        this.location = location;
    }

    /** Return the tile the avatar took the place of. **/
    public TETile getConsumedTile() {
        return this.consumedTile;
    }
}
