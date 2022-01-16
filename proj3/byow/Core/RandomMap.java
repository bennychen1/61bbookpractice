package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

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

    /** The random number generator for this map. */
    Random ran;

    /** The width (number of columns) of the map. */
    int width;

    /** The length (number of rows) of the map. */
    int length;

    /** An array for choosing either hallway or nothing. */
    final String[] connectToRoom = new String[]{"Hallway", "None"};

    /** An array for choosing either hallway, room, or none to connect to a hallway. */
    final String[] connectToHallway = new String[]{"Room", "Hallway", "None"};

    /** An array for choosing either a vertical or horizontal hallway. */
    final String[] hallwayDirections = new String[]{"Horizontal", "Vertical"};

    /** Instantiate a basic 30x30 map. Provide a seed for randomness */
    RandomMap(int seed) {
        this.roomList = new ArrayList<>();
        this.hallwayList = new ArrayList<>();
        this.roomQueue = new LinkedList<>();
        this.hallwayQueue = new LinkedList<>();
        this.tileArray = new TETile[30][30];
        this.helperPopulateMap(this.tileArray, Tileset.NOTHING);
        this.ran = new Random(seed);
        this.width = 30;
        this.length = 30;
    }

    /** Instantiate a w by l map where w is width and l is the length. Provide seed for randomness.
     * w is number of columns, which is the number of arrays, l is the number of rows, which is the length of
     * the inner arrays.
     * */
    RandomMap(int w, int l, int seed) {
        this(seed);
        this.tileArray = new TETile[w][l];
        this.width = w;
        this.length = l;
        this.helperPopulateMap(this.tileArray, Tileset.NOTHING);
    }

    /** Instantiate a w by l map where w is the width and l is the length. Specify default tile type to start with. */
    RandomMap(int w, int l, TETile tileType, int seed) {
        this(w, l, seed);
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
        Room startRoom = new Room(randomSizes(), randomSizes(), getRandomPoint());
        addRoom(startRoom);
        helperDrawWall(startRoom);
        roomQueue.add(startRoom);

        while (roomQueue.size() != 0) {
            Room curRoom = roomQueue.poll();
            ArrayList<Point> possibleConnectionPoints = (ArrayList<Point>) findPossibleConnections(curRoom);
            for (Point potentialPoint : possibleConnectionPoints) {
                String connectingObject = randomRoomConnection();
                if (connectingObject.equals("Hallway")) {
                    String direction = randomHallwayDir();
                    // make a hallway starting at point p and add it to hallway queue.
                    // Can you have a horizontal hallway connect to top of room? 
                } else {
                    continue;
                }
            }

            while (hallwayQueue.size() != 0) {
                Hallway curHallway = hallwayQueue.poll();
                ArrayList<Point> possibleHallwayConnections = (ArrayList<Point>) findPossibleConnections(curHallway);
                for (Point potentialPoint : possibleHallwayConnections) {
                    // connecting object Room, Hallway, or nothing
                    // add to roomQ or hallwayQ if needed.
                }
            }
        }
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

    /** Choose a random point to create a connection. */
    private Point helperRandomPoint(List<Point> listOfPoints) {
        // pick a random point from a list.
        return null;
    }

    /** Instantiates a room of random width and length at a point. */
    private Room helperRandomRoom(Point p) {
        // largestValidRoom?
        return null;
    }

    /** Pick a random point on the map to draw the initial room . */
    private Point getRandomPoint() {

        int rowIndex = RandomUtils.uniform(ran, this.length - 1);
        int colIndex = RandomUtils.uniform(ran, this.width - 1);
        return new Point(rowIndex, colIndex);
    }

    /** Helper function to return random widths and lengths that fit in the map. */
    private int randomSizes() {
        return RandomUtils.uniform(ran,Math.max(this.length, this.width)) + 1;
    }

    /** A helper function to randomly pick hallway or nothing to connect to a room. */
    private String randomRoomConnection() {
        RandomUtils.shuffle(ran, this.connectToRoom);
        return this.connectToRoom[0];
    }

    /** A helper function to randomly pick a room, another hallway, or nothing to a hallway. */
    private String randomHallwayConnection() {
        RandomUtils.shuffle(ran, this.connectToHallway);
        return this.connectToHallway[0];
    }

    /** A helper function to randomly pick a vertical or horizontal hallway to connect to another hallway. */
    private String randomHallwayDir() {
        RandomUtils.shuffle(ran, this.hallwayDirections);
        return this.hallwayDirections[0];
    }





}
