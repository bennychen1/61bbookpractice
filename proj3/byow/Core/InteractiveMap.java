package byow.Core;

import jh61b.junit.In;

/** A RandomMap that is interactive. **/
public class InteractiveMap {

    /** A RandomMap representing the game map. **/
    private RandomMap gameMap;

    /** The avatar of the user. **/
    private Avatar userAvatar;

    /**
     * The full constructor for InteractiveMap.
     * @param m A RandomMap object representing the game map.
     * @param a An Avatar for the user.
     */
    InteractiveMap(RandomMap m , Avatar a) {
        this.gameMap = m;
        this.userAvatar = a;
    }

}
