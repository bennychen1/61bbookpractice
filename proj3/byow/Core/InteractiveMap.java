package byow.Core;

import byow.TileEngine.TETile;
import jh61b.junit.In;

/** A RandomMap that is interactive. **/
public class InteractiveMap {

    /** A RandomMap representing the game map. **/
    private RandomMap gameMap;

    /** The avatar of the user. **/
    private Avatar userAvatar;

    /** The original tile that the Avatar took the place of. */
    private TETile originalTile;

    /**
     * The full constructor for InteractiveMap.
     * @param m A RandomMap object representing the game map.
     * @param a An Avatar for the user.
     */
    InteractiveMap(RandomMap m , Avatar a) {
        this.gameMap = m;
        this.userAvatar = a;
        this.originalTile = null;

        if (isPointValid(this.userAvatar.getLocation())) {
            placeAvatar();
        }
    }

    /**
     * Returns true if p is a valid location on the game map.
     * @param p A Point object to check
     * @return  True if p is a valid point on the map, false otherwise.
     */
    public boolean isPointValid(Point p) {
        return (p.getRow() >= 0 && p.getCol() >= 0) && (p.getRow() < gameMap.getMaxRowIndex() &&
                p.getCol() < gameMap.getMaxColIndex());
    }

    /** Put the avatar in its location in the tile array of the game map.  **/
    public void placeAvatar() {
        this.originalTile = this.gameMap.getTileAt(this.userAvatar.getLocation());
        this.gameMap.setTileArray(this.userAvatar.getLocation(), this.userAvatar);
    }

}
