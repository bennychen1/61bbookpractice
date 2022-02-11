package byow.Core;

import byow.TileEngine.TETile;

import java.awt.Color;


public class Avatar extends TETile {

    /** A Point representing the current location of the avatar on the map. */
    private Point location;


    Avatar() {
        this('@', new Point(0, 0));
    }

    Avatar(char icon, Point location) {
        super(icon, Color.white, Color.black, "avatar");
        this.location = location;
    }

}
