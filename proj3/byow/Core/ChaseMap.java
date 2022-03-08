package byow.Core;

import byow.Core.InteractiveMap.Avatar;


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

    /** A list of valid moves. **/


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
    }

    /**
     * Check if the chase object has caught the user avatar.
     * @return True if the chase avatar has the same location as the user avatar.
     */
    @Override
    public boolean isPlaying() {
        return this.chaser.getLocation().equals(this.userAvatar.getLocation());
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
    public void moveAvatarCommand(Avatar a, String dir) {
        super.moveAvatarCommand(a, dir);

        int randomDirIndex = RandomUtils.uniform(this.ran, 0, 3);

        String randomDir = String.valueOf(Engine.POSSIBLE_MOVES.charAt(randomDirIndex));

        super.moveAvatarCommand(this.chaser, randomDir);

        this.numTurns = this.numTurns + 1;

    }
}
