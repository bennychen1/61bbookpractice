package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/** An object representing a randomly generated map. **/
public class RandomMap {

    /** A list of all the rooms in the map. */
    List<Room> roomList;

    /** A list of all the hallways. */
    List<Hallway> hallwayList;

    /** A Queue containing the rooms that have already been explored. */
    Queue<Room> roomQueue;

    /** A Queue containing the hallways that have already been explored. */
    Queue<Hallway> hallwayQueue;

    /** The tile array that represents this map. */
    TETile[][] tileArray;

    /** Instantiate a basic 30x30 map. */
    RandomMap() {
        this.roomList = new ArrayList<>();
        this.hallwayList = new ArrayList<>();
        this.roomQueue = new LinkedList<>();
        this.hallwayQueue = new LinkedList<>();
        this.tileArray = new TETile[30][30];
        this.helperPopulateMap(this.tileArray, Tileset.NOTHING);
    }

    /** Instantiate a w by l map where w is width and l is the length.
     * w is number of columns, which is the number of arrays, l is the number of rows, which is the length of
     * the inner arrays.
     * */
    RandomMap(int w, int l) {
        this();
        this.tileArray = new TETile[w][l];
        this.helperPopulateMap(this.tileArray, Tileset.NOTHING);
    }

    /** Instantiate a w by l map where w is the width and l is the length. Specify default tile type to start with. */
    RandomMap(int w, int l, TETile tileType) {
        this(w, l);
        this.helperPopulateMap(this.tileArray, tileType);
    }


    /** Helper function to populate tile array during instantiation with the specified tile type.
     * @param   arr         TETile[][], the 2d tile array representing the map.
     * @param   tileType    TETile, the type of tile to populate the map with.
     * */
    private void helperPopulateMap(TETile[][] arr, TETile tileType) {
        for (int i = 0; i < arr.length; i = i + 1) {
            TETile[] curArr = arr[i];
            for (int j = 0; j < curArr.length; j = j + 1) {
                curArr[j] = tileType;
            }
        }
    }

    /** Draw the world (tessalate).
     *
     */
    public void drawWorld() {
        // add a room - add to room queue
        // for each possible point of connection, connect a hallwayor nothing.
            // add hallways and rooms to the appropriate queues.
        // for each hallway, find all possible connection points, add room, hallway, or nothing.
            // add items to the queue.
        // go back to room queue, find possible connections, add connections, ...
        // only add to queues if valid to avoid infinite loops
        // have a seen hashSet
        // have whole thing be a graph? 
        /**
         * for each room in roomQ
         *      pick a room - mark it so it doesn't get revisited again.
         *      for each connection point - add hall or nothing, add halls to queue.
         *          for each hall in hallQ
         *              pick a hall - mark it so it doesn't get revisited again.
         *              find possible connections, add hall, room, or nothing.
         */
    }

    /** Add a room to the map.
     * @param r   The Room to add.
     * */
    public void addRoom(Room r) {
        // check if valid room
        // draw room floors
        // put a wall around it
    }

    /** Helper function to draw a wall around a room or hallway. */
    private void helperDrawWall(Room r) {

    }

    /** Helper function to check if a specified room can be drawn. **/
    private boolean checkRoomValid(Room r) {
        return false;
    }

    /** Helper function to find all the possible connection points for a room or hallway. */
    private List<Point> findPossibleConnections(Room r) {
        return null;
    }

    /** Helper to find the closest size room if given room can't be drawn at a point. */
    private Room largestValidRoom(Room r) {
        return null;
    }



}
