package byow.Core;

public class ChaseGame implements GameType{

    /**
     * Check if game is over.
     * @param i A ChaseMap.
     * @return True if the chaser is in the same location as the user. False otherwise.
     */
    @Override
    public boolean isPlaying(GameMap i) {
        ChaseMap c = (ChaseMap) i;

        return c.caught();
    }
}
