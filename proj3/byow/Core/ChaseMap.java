package byow.Core;



import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;

/** The user must try to escape from a chase object for as long as possible - count by number of turns. **/

public class ChaseMap extends InteractiveMap {

    /** The chasing object. Can't be modified after instantiation.  **/
    private final Avatar chaser;

    /** The user's avatar. Can't be modified after instantiation. **/
    private final Avatar userAvatar;

    /** The number of turns in this game (number of moves by the user). **/
    private int numTurns;

    /** The game map. **/
    private RandomMap gameMap;

    /** The RandomNumberGenerator. **/
    private Random ran;

    /** The text to display when the game ends. **/
    private String finishString;


    ChaseMap(RandomMap m) {

        super(m);

        this.gameMap = m;

        this.ran = this.gameMap.getRan();

        this.userAvatar = super.getAvatarList().get(0);

        Point randomPoint = m.getRandomFloorPoint();

        while (randomPoint.equals(this.userAvatar.getLocation())) {
            randomPoint = m.getRandomFloorPoint();
        }

        this.chaser = new Avatar('<', randomPoint);
        super.placeAvatar(this.chaser);
    }

    /** Create a copy of another Chase Map. **/
    ChaseMap(ChaseMap otherMap) {
        super();
        this.gameMap = otherMap.gameMap;
        this.chaser = otherMap.chaser;
        this.userAvatar = otherMap.userAvatar;
    }

    /**
     * Check if the chase object has caught the user avatar.
     * @return True if the chase avatar has the same location as the user avatar.
     */
    @Override
    public boolean isPlaying() {
        return !this.chaser.getLocation().equals(this.userAvatar.getLocation());
    }

    @Override
    public void displayFinish(String finishText) {
        while (true) {
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);

            double rectangleX = this.gameMap.getMaxColIndex() * 0.5;
            double rectangleY = this.gameMap.getMaxRowIndex() * 0.5;

            //StdDraw.filledRectangle(rectangleX, rectangleY, this.gameMap.getMaxColIndex() * 0.3, this.gameMap.getMaxRowIndex() * 0.3);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(rectangleX - 5, rectangleY, finishText);
            StdDraw.show();
        }
    }

    @Override
    public String finishText() {
        return this.finishString;
    }


    /**
     * Create a copy of another Chase Map.
     * @param otherMap The InteractiveMap to create a copy of.
     * @return A copy of the other ChaseMap. Null if the other map is not a ChaseMap.
     */
    @Override
    public ChaseMap copy(GameMap otherMap) {
        if (!otherMap.getClass().toString().equals(this.getClass().toString())) {
            return null;
        }
        return new ChaseMap((ChaseMap) otherMap);
    }

    @Override
    /** Return a copy of this game map. **/
    public RandomMap getGameMap() {
        RandomMap copy = new RandomMap(this.gameMap);
        return copy;
    }



    /** Get the user Avatar. **/
    public Avatar getUserAvatar() {
        return this.userAvatar;
    }

    /** Get the chase object. **/
    public Avatar getChaser() {
        return this.chaser;
    }

    /** Get the number of turns. **/
    public int getNumTurns() {
        return this.numTurns;
    }

    /**
     * Move the user avatar in the direction provided by the user. Also move the chase avatar in
     * a random direction.
     * @param a  The avatar to move.
     * @param dir A String for the direction to move the avatar.
     */
    @Override
    public Point moveAvatarCommand(Avatar a, String dir) {

        Point userMoveLocation = super.moveAvatarCommand(a, dir);

        this.moveAvatar(a, userMoveLocation);

        if (!isPlaying()) {
            this.finishString = "User has caught the chaser.";
            return userMoveLocation;
        }

        this.helperMoveChaser();

        if (!isPlaying()) {
            this.finishString = "Chaser has caught the user.";
        }

        this.numTurns = this.numTurns + 1;

        return userMoveLocation;
    }

    /**
     * Move the chase avatar.
     */
    private void helperMoveChaser() {

        Point currentLocation = this.chaser.getLocation();

        int randomDirIndex = RandomUtils.uniform(this.ran, 0, 4);

        String randomDir = String.valueOf(Engine.POSSIBLE_MOVES.charAt(randomDirIndex));

        Point chaserMoveLocation = super.moveAvatarCommand(this.chaser, randomDir);

        this.moveAvatar(this.chaser, chaserMoveLocation);

        while (this.chaser.getLocation().equals(currentLocation)) {
            this.helperMoveChaser();
        }
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

        if (!this.gameMap.isPointFloor(location)
                && !this.gameMap.getTileAt(location).description().equals("avatar")) {
            System.out.println(location.toString() + " is not a floor point");
            return;
        }

        this.gameMap.setTileArray(a.getLocation(), a.getConsumedTile());
        a.setLocation(location);
        super.helperToPlaceAvatar(a, location);
    }
}
