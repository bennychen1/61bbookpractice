package byow.Core;

import java.util.List;

/** A grouping for the types of game maps. **/
public interface GameMap {
    public boolean isPlaying();
    public RandomMap getGameMap();
    public List<Avatar> getAvatarList();
    public void moveAvatarCommand(Avatar userAvatar, String curCommand);
    public GameMap copy(GameMap otherMap);

}
