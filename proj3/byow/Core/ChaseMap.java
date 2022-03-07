package byow.Core;

import byow.Core.InteractiveMap.Avatar;

/** The user must try to escape from a chase object for as long as possible. **/

public class ChaseMap extends InteractiveMap {

    /** The chasing object. Can't be modified after instantiation.  **/
    private final Avatar chaser;

    /** The user's avatar. Can't be modified after instantiation. **/
    private final Avatar userAvatar;

    ChaseMap(RandomMap m) {
        super(m);

        this.userAvatar = super.getAvatarList().get(0);

        Point randomPoint = m.getRandomFloorPoint();

        while (randomPoint.equals(this.userAvatar.getLocation())) {
            randomPoint = m.getRandomFloorPoint();
        }

        this.chaser = new Avatar('<', randomPoint);

    }
}
