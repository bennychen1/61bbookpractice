package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import jh61b.junit.In;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/** A RandomMap that is interactive. **/
public class InteractiveMap implements GameMap{

    /** A RandomMap representing the game map. **/
    private RandomMap gameMap;

    /** A list of avatars. **/
    private List<Avatar> avatarList;

    InteractiveMap() {
        this(new RandomMap(5));
    }

    InteractiveMap(RandomMap m) {
        this.gameMap = m;
        this.gameMap.drawWorld();
        this.avatarList = new ArrayList<>();
        this.addUserAvatar();
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
        this.placeInitialAvatar(a);
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
     * Add a avatar at a random floor point.
     */
    public void addUserAvatar() {
        Avatar a = new Avatar('@', this.gameMap.getRandomFloorPoint());
        this.placeAvatar(a);
        this.avatarList.add(a);
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
     * Use for initial placement only.
     * @param a The avatar to place.
     * **/
    public void placeInitialAvatar(Avatar a) throws IllegalArgumentException {

        if (!this.gameMap.isPointOnMap(a.getLocation())) {
            throw new IllegalArgumentException("Avatar needs to be located on the map");
        }

        helperToPlaceAvatar(a, a.getLocation());
    }

    /** Put the avatar in its location in the tile array of the game map.
     * Not for (0, 0) avatar in the one argument constructor.
     * @param a The avatar to place.
     * **/
    public void placeAvatar(Avatar a) throws IllegalArgumentException {

        if (!this.gameMap.isPointOnMap(a.getLocation())) {
            throw new IllegalArgumentException("Avatar needs to be located on the map");
        }

        if (!this.gameMap.isPointFloor(a.getLocation())) {
            throw new IllegalArgumentException("Avatar can only be placed on a floor tile");
        }

        helperToPlaceAvatar(a, a.getLocation());
    }

    /** Move the avatar to the provided point if it is a valid location. Do nothing if not valid.
     * @param   a   The avatar to move.
     * @param location  A Point object representing where to move the avatar.
     * **/
    public void moveAvatar(Avatar a, Point location) {
       if (!this.gameMap.isPointOnMap(location)) {
           System.out.println("Location " + location.toString() + " not on map");
           return;
       }

       if (!this.gameMap.isPointFloor(location)) {
           System.out.println(location.toString() + " is not a floor point");
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
     * @param dir A String for the direction to move the avatar.
     */
    public Point moveAvatarCommand(Avatar a, String dir) {
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

        return newPoint;
    }

    /** Return a copy of this game map. **/
    public RandomMap getGameMap() {
        RandomMap copy = new RandomMap(this.gameMap);
        return copy;
    }

    @Override
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
    void helperToPlaceAvatar(Avatar a, Point location) {

        TETile tileAtLocation = this.gameMap.getTileAt(location);

        if (tileAtLocation.description().equals("avatar")) {
            a.setConsumedTile(((Avatar) tileAtLocation).getConsumedTile());
        } else {
            a.setConsumedTile(this.gameMap.getTileAt(location));
        }
        this.gameMap.setTileArray(location, a);
    }

    /** Returns true to keep game going. **/
    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public void displayFinish(String finishText) {
        ;
    }

    @Override
    public String finishText() {
        return "";
    }

    @Override
    public void displayTurns(Engine e) {
        ;
    }

    /**
     * Create a copy of another InteractiveMap.
     * @param otherMap The InteractiveMap to create a copy of.
     * @return A copy of the other map. Null if the other map is not an InteractiveMap.
     */
    @Override
    public InteractiveMap copy(GameMap otherMap) {
        if (!otherMap.getClass().toString().equals(this.getClass().toString())) {
            return null;
        }
        return new InteractiveMap((InteractiveMap) otherMap);
    }

}
