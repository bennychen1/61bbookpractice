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
    int maxColIndex;

    /** The length (number of rows) of the map. */
    int maxRowIndex;

    /** Object containing useful random functions. */
    RandomMapUtils randomFuncs;

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
        this.maxColIndex = 29;
        this.maxRowIndex = 29;
        this.randomFuncs = new RandomMapUtils(seed);
    }

    /** Instantiate a w by l map where w is width and l is the length. Provide seed for randomness.
     * w is number of columns, which is the number of arrays, l is the number of rows, which is the length of
     * the inner arrays.
     * */
    RandomMap(int w, int l, int seed) {
        this(seed);
        this.tileArray = new TETile[w][l];
        this.maxRowIndex = w  - 1;
        this.maxColIndex = l - 1;
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
        Room startRoom = helperRandomRoom(getRandomPoint());
        addRoom(startRoom);
        helperDrawWall(startRoom);
        roomQueue.add(startRoom);

        while (roomQueue.size() != 0) {
            Room curRoom = roomQueue.poll();
            helperConnector(curRoom, this.connectToRoom);
            }

            while (hallwayQueue.size() != 0) {
                Hallway curHallway = hallwayQueue.poll();
                helperConnector(curHallway, this.connectToHallway);
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
        drawHorizontalWallsAround(r);
        drawVerticalWallsAround(r);
    }

    /** Helper function to find the starting column index of a wall around Room R. */
    private int findStartColWall(Room r) {
        return r.start.col - 1;
    }

    /** Helper function to find the starting row index of a wall around Room R. */
    private int findStartRowWall(Room r) {
        return r.start.row - 1;
    }

    /** Helper function to find the ending column index of a wall around Room R. */
    private int findEndColWall(Room r) {
        return r.start.col + r.width;
    }

    /** Helper function to find the ending row index of a wall around Room R. */
    private int findEndRowWall(Room r) {
        return r.start.row + r.length;
    }

    /** Helper to draw the two vertical walls around room R. **/
    private void drawVerticalWallsAround(Room r) {
        int startColIndex = findStartColWall(r);
        int endColIndex = findEndColWall(r);

        for (int i = findStartRowWall(r); i < findEndRowWall(r); i = i + 1) {
            this.tileArray[startColIndex][i] = Tileset.WALL;
            this.tileArray[endColIndex][i] = Tileset.WALL;
        }
    }

    /** Helper to draw the two horizontal walls around room R. **/
    private void drawHorizontalWallsAround(Room r) {
        int bottomRowIndex = findStartRowWall(r);
        int topRowIndex = findEndRowWall(r);

        for (int i = findStartColWall(r); i < findEndColWall(r); i = i + 1) {
            this.tileArray[i][bottomRowIndex] = Tileset.WALL;
            this.tileArray[i][topRowIndex] = Tileset.WALL;
        }
    }

    /** Helper function to check if a specified room can be drawn based on its size. **/
    private boolean checkRoomValidSize(Room r) {
        Point startingPoint = r.start;
        int roomWidth = r.width;
        int roomLength = r.length;

        /** Guarantee starting point is valid.
        boolean checkStartColValid = startingPoint.col >= 0 && startingPoint.col < this.maxColIndex;
        boolean checkStartRowValid = startingPoint.row >= 0 && startingPoint.row < this.maxRowIndex; **/

        //boolean checkWideEnough = startingPoint.col + roomWidth - 1 < this.maxColIndex;
       //boolean checkLongEnough = startingPoint.row + roomLength - 1 < this.maxRowIndex;


        return checkRoomWidth(r) && checkRoomLength(r);
    }

    /** Check if room width will fit in the map. */
    private boolean checkRoomWidth(Room r) {
        Point startingPoint = r.start;
        return startingPoint.col + r.width - 1 < this.maxColIndex;
    }

    /** Check if room length will fit in the map. */
    private boolean checkRoomLength(Room r) {
        Point startingPoint = r.start;
        return startingPoint.row + r.length - 1 < this.maxRowIndex;
    }


    /** Helper function to find all the possible connection points for a room or hallway.
     * The connection point will be one of the walls of the room or hallway. */
    private List<Point> findPossibleConnections(Room r) {
        return null;
    }

    /** Helper to find the closest size room if given room can't be drawn at a point. */
    private Room largestValidRoom(Room r) {
        // find why room is not valid
        if (!checkRoomWidth(r) && !checkRoomLength(r)) {

        }
        return null;
    }

    /** Choose a random point to create a connection. */
    private Point helperRandomPoint(List<Point> listOfPoints) {
        // pick a random point from a list.
        return null;
    }

    /** Instantiates a room of random width and length at a point. */
    private Room helperRandomRoom(Point p) {
        Room r = new Room(randomWidth(), randomLength(), p);
        if (!checkRoomValidSize(r)) {
            r = largestValidRoom(r);
        }
        return r;
    }

    /** Pick a random point on the map to draw the initial room . */
    private Point getRandomPoint() {

        int rowIndex = RandomUtils.uniform(ran, this.maxRowIndex- 1);
        int colIndex = RandomUtils.uniform(ran, this.maxColIndex - 1);
        return new Point(rowIndex, colIndex);
    }

    /** Helper function to return a random width size. **/
    private int randomWidth() {
        return RandomUtils.uniform(ran, this.maxColIndex + 1) + 1;
    }

    /** Helper function to return a random width size. **/
    private int randomLength() {
        return RandomUtils.uniform(ran, this.maxRowIndex + 1) + 1;
    }

    /** Helper function to return random widths and lengths that fit in the map.
    private int randomSizes() {
        return RandomUtils.uniform(ran,Math.max(this.max, this.width)) + 1;
    } */

    /** Simplify the code finding possible connections to one area. */
    private void helperConnector(Room r, String[] chooseFrom) {

        ArrayList<Point> possibleConnections = (ArrayList<Point>) findPossibleConnections(r);

        for (Point p : possibleConnections) {
            String connectingObject = this.randomFuncs.randomConnection(chooseFrom);
            switch(connectingObject) {
                case "Room":
                    Room newRoom = new Room(randomWidth(), randomLength(), p);
                    addRoom(newRoom);
                    roomQueue.add(newRoom);
                case "Hallway":
                    String hallwayDirection = this.randomFuncs.randomConnection(this.hallwayDirections);
                    Hallway h;
                    if (hallwayDirection.equals("vertical")) {
                        h = new Hallway(false, randomWidth(), p);
                        drawVerticalHallway(h);;
                    } else {
                        h = new Hallway(true, randomLength(), p);
                        drawHorizontalHallway(h);
                    }
                    hallwayQueue.add(h);
                    break;
                default: break;
            }
        }

        return;
    }

    private void drawVerticalHallway(Hallway h) {}
    private void drawHorizontalHallway(Hallway h) {}
    private void checkOverlappingRoom(){
        // check starting point isn't already populated with a floor
        // have another array that marks if (i, j) is already a room.
    }

    /** Returns true if connection point P is to the left (index - 1) or below room R's starting point, then
     * drawing a room or hallway will need to be drawn going left or down rather than up or to
     * the right.
     * */
    private boolean checkToLeftOrDown(Point p, Room r) {
        return p.col < r.start.col || p.row < r.start.row;
    }

    /** Find starting point if the room or hallway ROOMTOCONNECT is
     * to the left or below the room or hallway CONNECTTO.
     * */
    private Point findStartingPoint(Room roomToConnect, Room connectTo) {
        return null;
    }

    /** Check if the Point P is valid (is on the map). **/
    private boolean isPointValid(Point p) {

        return isPointColValid(p) && isPointRowValid(p);
    }

    /** Check if Point P has a valid column index. */
    private boolean isPointColValid(Point p) {
        return p.col >= 0 && p.col < this.maxColIndex;
    }

    /** Check if Point P has a valid row index. */
    private boolean isPointRowValid(Point p) {
        return p.row >= 0 && p.row < this.maxRowIndex;
    }

    /** Find nearest valid point from Point P. Returns null if there is none. */
    private Point nearestValidPoint(Point p) {
        if ()
        return null;
    }

    /** Find the maximum width for room starting at Point p drawing from the specified direction left or right. */
    private int maxWidth(Point p, String direction) {
        if (direction.equals("left")) {
            return p.col;
        } else {
            return this.maxColIndex - p.col;
        }
    }

    /** Find maximum length for room starting at Point p drawing from the specified direction up or down. */
    private int maxLength(Point p, String direction) {
        if (direction.equals("down")) {
            return p.row;
        } else {
            return this.maxRowIndex - p.row;
        }
    }





}
