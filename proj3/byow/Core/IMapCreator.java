package byow.Core;

/** Creates an Interactive Map. **/
public class IMapCreator implements MapCreator{
    /**
     * Create an InteractiveMap with the provided RandomMap.
     * @param m A RandomMap.
     * @return An InteractiveMap with m as the game map.
     */
    @Override
    public InteractiveMap createMap(RandomMap m) {
        return new InteractiveMap(m);
    }

}
