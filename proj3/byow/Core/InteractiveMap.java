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
     * Constructor with a default avatar.
     * @param m The game map.
     */
    InteractiveMap(RandomMap m) {
        this(m, new Avatar('@', m.getRandomFloorPoint()));
    }
    /**
     * The full constructor for InteractiveMap.
     * @param m A RandomMap object representing the game map.
     * @param a An Avatar for the user.
     */
    InteractiveMap(RandomMap m , Avatar a) {
        this.gameMap = m;
        this.gameMap.drawWorld();
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
    private boolean isPointValid(Point p) {
        if (p == null) {
            return false;
        }
        return (p.getRow() >= 0 && p.getCol() >= 0) && (p.getRow() < gameMap.getMaxRowIndex() &&
                p.getCol() < gameMap.getMaxColIndex());
    }

    /** Put the avatar in its location in the tile array of the game map.  **/
    public void placeAvatar() {
        this.originalTile = this.gameMap.getTileAt(this.userAvatar.getLocation());
        this.gameMap.setTileArray(this.userAvatar.getLocation(), this.userAvatar);
    }

    /** Move the avatar to the provided point if it is a valid location. Do nothing if not valid.
     * @param location  A Point object representing where to move the avatar.
     * **/
    public void moveAvatar(Point location) {
        if (!isPointValid(location)) {
            System.out.println("Location is not valid on game map");
            return;
        }

        this.gameMap.setTileArray(this.userAvatar.getLocation(), this.originalTile);
        this.originalTile = this.gameMap.getTileAt(location);
        this.gameMap.setTileArray(location, this.userAvatar);
    }

    /**
     * Move the avatar according the the specified keyboard command.
     *      W - move up on the map, S move down on the map, A move to the left, D move to the right.
     * @param dir
     */
    public void moveAvatarCommand(char dir) {
        Point newPoint;
        Point curPoint = this.userAvatar.getLocation();
        switch (dir) {
            case'w':
                newPoint = curPoint.pointToTop(this.gameMap.getMaxRowIndex());
                break;
            case 's':
                newPoint = curPoint.pointToBottom();
                break;
            case 'a':
                newPoint = curPoint.pointToLeft();
                break;
            case 'd':
                newPoint = curPoint.pointToRight(this.gameMap.getMaxColIndex());
                break;
            default:
                newPoint = null;
        }

        this.moveAvatar(newPoint);


    }

    /** Return a copy of this game map. **/
    public RandomMap getGameMap() {
        RandomMap copy = new RandomMap(this.gameMap);
        return copy;
    }

    /** Returns the current original tile. **/
    public TETile getOriginalTile() {
        return this.originalTile;
    }

}
