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

    /** An array for choosing either hallway or nothing. */
    final String[] connectToRoom = new String[]{"Hallway", "None"};

    /** An array for choosing either hallway, room, or none to connect to a hallway. */
    final String[] connectToHallway = new String[]{"Room", "Hallway", "None"};

    /** An array for choosing either a vertical or horizontal hallway. */
    final String[] hallwayDirections = new String[]{"Horizontal", "Vertical"};

    /** The default tile type of this map. */
    TETile defaultTileType;

    /** Instantiate a basic 30x30 map. Provide a seed for randomness */
    RandomMap(int seed) {
        this.roomList = new ArrayList<>();
        this.hallwayList = new ArrayList<>();
        this.roomQueue = new LinkedList<>();
        this.hallwayQueue = new LinkedList<>();
        this.tileArray = new TETile[30][30];
        this.helperPopulateMap(this.tileArray, Tileset.SAND);
        this.ran = new Random(seed);
        this.maxColIndex = 29;
        this.maxRowIndex = 29;
        this.defaultTileType = Tileset.SAND;
    }

    /** Instantiate a basic 30x30 map with the provided seed and default tile type.*/
    RandomMap(int seed, TETile tileType) {
        this(seed);
        this.defaultTileType = tileType;
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
        this.defaultTileType = tileType;
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

        int numRooms = RandomUtils.uniform(this.ran, 1, 10);

        for (int i = 0; i < numRooms; i = i + 1) {
            Room r = generateRandomRoom();
            this.drawRoom(r);
            roomQueue.add(r);
        }

    }

    /** Draw the provided room in this map.
     * @param r    A Room object.
     * */
    private void drawRoom(Room r) {

        int startRowIndex = r.start.row;
        int startColIndex = r.start.col;

        for (int curCol = startColIndex; curCol < startColIndex + r.width; curCol = curCol + 1) {
            TETile[] curColArray = this.tileArray[curCol];
            for (int curRow = startRowIndex; curRow < startRowIndex + r.length; curRow = curRow + 1) {
                curColArray[curRow] = Tileset.FLOOR;
                r.addPoint(new Point(curCol, curRow));
            }
        }


        drawHorizontalWalls(r, r.start.row - 1);
        drawHorizontalWalls(r, r.start.row + r.length);
        drawVerticalWalls(r, r.start.col - 1);
        drawVerticalWalls(r, r.start.col + r.width);
    }


    /** Draw the horizontal walls around rooms.
     * @param r          The Room to draw the wall around.
     * @param wallRow   int, the index of the row to draw the horizontal wall.
     * */
    private void drawHorizontalWalls(Room r, int wallRow) {
        for (int curCol = r.start.col; curCol < r.start.col + r.width; curCol = curCol + 1) {
            this.tileArray[curCol][wallRow] = Tileset.WALL;
        }
    }

    /** Draw the vertical walls around rooms.
     * @param r          The Room to draw the wall around.
     * @param wallCol   int, the index of the column to draw the vertical wall.
     * */
    private void drawVerticalWalls(Room r, int wallCol) {
        for (int curRow = r.start.row - 1; curRow <= r.start.row + r.length; curRow = curRow + 1) {
            this.tileArray[wallCol][curRow] = Tileset.WALL;
        }
    }


    /** Generates a room of random width, length, and starting point. */
    private Room generateRandomRoom() {

        Point startPoint = getRandomPoint();

        while (!helperTileAtPoint(startPoint).equals(this.defaultTileType)) {
            startPoint = getRandomPoint();
        }

        int maxWidth = (int) Math.min(helperFindMaxRoomWidth(startPoint),
                Math.round((this.maxColIndex + 1) * 0.2));

        int roomWidth = helperGenerateRandomWidthLength(maxWidth);

        int maxHeight = (int) Math.min(findMaxRoomHeight(startPoint, roomWidth)
                , Math.round((this.maxRowIndex + 1) * 0.2));

        int roomHeight = helperGenerateRandomWidthLength(maxHeight);


        return new Room(roomWidth, roomHeight, startPoint);
    }

    /** A helper function to generate a random width and random length for a room given the max value.
     * @param value int, the maximum value
     * @return  An int between 1 and value.
     *  */
    private int helperGenerateRandomWidthLength(int value) {
        if (value != 1) {
            return RandomUtils.uniform(this.ran, 1, value);
        } else {
            return value;
        }
    }

    /** Find the maximum possible width going to the right if a room drawn from the
     * lower left corner starts at point P. Must stop when a wall is hit or up to the
     * maximum column index of this map minus 1 to account for the room wall, whichever comes first.
     * @param   p       Point, the possible room's starting point, which is it's lower left corner.
     * @return  An int which represents the largest possible number of columns (width) the room can
     * take up.
     * */
    private int helperFindMaxRoomWidth(Point p) {
        int startRow = p.row;
        int startCol = p.col;

        int curWidth = 1;

        for (int curCol = startCol + 1; curCol < maxColIndex; curCol = curCol + 1) {
            if (this.tileArray[curCol][startRow].equals(Tileset.WALL)) {
                break;
            }
            curWidth = curWidth + 1;
        }

        return curWidth;
    }

    /** Find the maximum possible height for a width 1 room at the starting point. Must stop when
     * a wall is hit or up to the maximum row index of this map minus 1 to account for
     * the room wall, whichever comes first.
     * @param   p           Point, the point to start at.
     * @param   width       int, representing the number of columns to check inclusive of start point.
     * @return  An int representing the maximum height of a room at the given column.
     */
    private int findMaxRoomHeight(Point p, int width) {

        int curMaxHeight = Integer.MAX_VALUE;

        for (int w = 0; w < width; w = w + 1) {
            TETile[] curCol = this.tileArray[p.col + w];
            int curColHeight = helperFindColMaxHeight(p.row, curCol);
            curMaxHeight = Math.min(curMaxHeight, curColHeight);
        }

        return curMaxHeight;
    }

    /** A helper function that counts the number of tiles traversed before hitting a wall in a tile array,
     * starting from a given starting index. Does not count the top row because this is for room drawing.
     * @param   startIndex      int, the index to start at.
     * @param   tiles           TETile[], the tile array.
     * @return  An int representing the number of tiles iterate over before hitting a wall, includes start point.
     * */
    private int helperFindColMaxHeight(int startIndex, TETile[] tiles) {
        if (startIndex < 0 || startIndex >= tiles.length - 1) {
            return 0;
        }

        int count = 1;

        for (int curIndex = startIndex + 1; curIndex < tiles.length - 1; curIndex = curIndex + 1) {
            if (!tiles[curIndex].equals(Tileset.WALL)) {
                count = count + 1;
            } else {
                break;
            }
        }

        return count;
    }

    /** Returns the tile at point p.
     * @param   p       A Point object.
     * @return  The type of tile at point p.
     * */
    private TETile helperTileAtPoint(Point p) {
        return this.tileArray[p.col][p.row];
    }



    /**
     * populate with rooms
     *          pick random start points, draw rooms of random widths and sizes
     *          ok to keep Point as representing lower left
     * connect rooms with hallways
     *          pick a room, connect a random number of rooms to it
     *          for those rooms, repeat.
     *
     */


    // add initial room - add it to the room queue
        // while the room queue is not empty
            // select current room (pop queue)
            // for each wall point in the room (skip corners?), connect a hallway or do nothing
                // if on the vertical wall, only connect horizontal hallways. if on horizontal wall, only connect vertical hallways
                        // add hallways to hallway queue
                    // connecting hallway starting point is the wall
            // while hallway queue is not empty
                // select current hallway (pop queue)
                // for each wall point, either connect room, hallway, or nothing
                    // connecting room - replace wall point with floor, then room starts tile above or next to it
                        // add rooms to room queue
                        // find largest possible room width and length, then choose random between 1 and that length
                    // if hallway
                       // if on the vertical wall, only connect horizontal hallways. if on horizontal wall, only connect vertical hallways
                      // add hallways to queue

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


    /** Helper function to find all the possible connection points for a room or hallway.
     * The connection point will be one of the walls of the room or hallway. */
    private List<Point> findPossibleConnections(Room r) {
        return null;
    }



    /** Choose a random point to create a connection. */
    private Point helperRandomPoint(List<Point> listOfPoints) {
        // pick a random point from a list.
        return null;
    }


    /** Pick a random point on the map to draw the initial room . */
    private Point getRandomPoint() {

        int rowIndex = RandomUtils.uniform(ran, 1, this.maxRowIndex- 1);
        int colIndex = RandomUtils.uniform(ran, 1, this.maxColIndex - 1);
        return new Point(rowIndex, colIndex);
    }



    /** Helper function to return random widths and lengths that fit in the map.
    private int randomSizes() {
        return RandomUtils.uniform(ran,Math.max(this.max, this.width)) + 1;
    } */


    /** A helper function to randomly pick from an array A. Used for randomly picking connections to
     * rooms and hallways. */
    private String randomConnection(String[]A) {
        RandomUtils.shuffle(ran, A);
        return A[0];
    }


    /** Simplify the code finding possible connections to one area.
    private void helperConnector(Room r, String[] chooseFrom) {

        ArrayList<Point> possibleConnections = (ArrayList<Point>) findPossibleConnections(r);

        for (Point p : possibleConnections) {
            String connectingObject = randomConnection(chooseFrom);
            switch(connectingObject) {
                case "Room":
                    Room newRoom = new Room(randomWidth(), randomLength(), p);
                    addRoom(newRoom);
                    roomQueue.add(newRoom);
                case "Hallway":
                    String hallwayDirection = randomConnection(this.hallwayDirections);
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
    } */

    private void drawVerticalHallway(Hallway h) {}
    private void drawHorizontalHallway(Hallway h) {}
    private void checkOverlappingRoom(){
        // check starting point isn't already populated with a floor
        // have another array that marks if (i, j) is already a room.
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





}
