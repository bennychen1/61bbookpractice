package byow.Core;

/** Checks that map meets the criteria specified in the spec. */
public class MapChecker {

    RandomMap m;

    /** Instantiates a map checker with the given map. */
    public MapChecker(RandomMap m) {
        this.m = m;
    }

    /** Returns true if this map has no overlapping rooms. Return false otherwise. */
    public boolean checkNoOverlappingRooms() {
        return false;
    }

    /** Returns true if map contains an L shaped hallway. */
    public boolean containsLShapedHallway() {
        return false;
    }
}
