package byow.Core;

/** Create a Chase Map. **/
public class ChaseMapCreator implements MapCreator{

    /**
     * Create a Chase Map based on the provided RandomMap.
     * @param m A RandomMap.
     * @return A Chase Map with m as the game map.
     */
    @Override
    public ChaseMap createMap(RandomMap m) {
        return new ChaseMap(m);
    }
}
