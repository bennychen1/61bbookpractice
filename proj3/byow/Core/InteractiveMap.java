package byow.Core;

import byow.TileEngine.TETile;
import jh61b.junit.In;

import java.util.ArrayList;
import java.util.List;

/** A RandomMap that is interactive. **/
public class InteractiveMap {

    /** A RandomMap representing the game map. **/
    private RandomMap gameMap;

    /** A list of avatars. **/
    private List<Avatar> avatarList;


    /**
     * Default constructor for InteractiveMap.
     * @param m The game map.
     */
    InteractiveMap(RandomMap m) {
        this(m, new Avatar('@', new Point(0, 0)));
        Avatar a = this.avatarList.get(0);
        this.moveAvatar(a, this.gameMap.getRandomFloorPoint());
    }
    /**
     * The full constructor for InteractiveMap.
     * @param m A RandomMap object representing the game map.
     * @param a An Avatar for the user.
     */
    InteractiveMap(RandomMap m , Avatar a) {
        this.gameMap = m;
        this.gameMap.drawWorld();
        this.avatarList = new ArrayList<>();
        this.avatarList.add(a);
        this.placeAvatar(a);
    }

    /**
     *  Create a copy of InteractiveMap i.
     * @param   i   The InteractiveMap to copy.
     * **/
    InteractiveMap(InteractiveMap i) {
        this.gameMap = i.getGameMap();
        this.avatarList = i.getAvatarList();
    }

    InteractiveMap(RandomMap m, List<Avatar> avatarList) {
        this.gameMap = m;
        m.drawWorld();
        this.avatarList = avatarList;

        for (Avatar a : this.avatarList) {
            placeAvatar(a);
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

    /** Put the avatar in its location in the tile array of the game map.
     * @param a The avatar to place.
     * **/
    public void placeAvatar(Avatar a) {

        if (!isPointValid(a.getLocation())) {
            return;
        }

        helperToPlaceAvatar(a, a.getLocation());
    }

    /** Move the avatar to the provided point if it is a valid location. Do nothing if not valid.
     * @param   a   The avatar to move.
     * @param location  A Point object representing where to move the avatar.
     * **/
    public void moveAvatar(Avatar a, Point location) {
        if (!isPointValid(location)) {
            System.out.println("Location is not valid on game map");
            return;
        }

        this.gameMap.setTileArray(a.getLocation(), a.getConsumedTile());
        a.setLocation(location);
        helperToPlaceAvatar(a, location);
    }


    /**
     * Move the avatar according the the specified keyboard command.
     *      W - move up on the map, S move down on the map, A move to the left, D move to the right.
     * @param a  The avatar to move.
     * @param dir A char for the direction to move the avatar.
     */
    public void moveAvatarCommand(Avatar a, char dir) {
        String directionString = String.valueOf(dir).toLowerCase();
        Point newPoint;
        Point curPoint = a.getLocation();
        switch (directionString) {
            case"w":
                newPoint = curPoint.pointToTop(this.gameMap.getMaxRowIndex());
                break;
            case "s":
                newPoint = curPoint.pointToBottom();
                break;
            case "a":
                newPoint = curPoint.pointToLeft();
                break;
            case "d":
                newPoint = curPoint.pointToRight(this.gameMap.getMaxColIndex());
                break;
            default:
                newPoint = null;
        }

        this.moveAvatar(a, newPoint);
    }

    /** Return a copy of this game map. **/
    public RandomMap getGameMap() {
        RandomMap copy = new RandomMap(this.gameMap);
        return copy;
    }

    /** Returns a copy of the avatar list. **/
    public List<Avatar> getAvatarList() {
        ArrayList<Avatar> copy = new ArrayList<>(this.avatarList);
        return copy;
    }

    /**
     * A helper function to set avatar consumed tile and put avatar in the location.
     * @param a The avatar to update
     * @param location  The location to move the avatar to.
     */
    private void helperToPlaceAvatar(Avatar a, Point location) {
        a.setConsumedTile(this.gameMap.getTileAt(location));
        this.gameMap.setTileArray(location, a);
    }

}
