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

    /** A union find object - each connected room will belong to a set.
     * Indices - 5x5 array - index 2 will represent tileArray[0][2]
     * index 20 tileArray[4][0];
     * */
    UnionFind roomSets;

    /** The random number generator for this map. */
    Random ran;

    /** The maximum column index of the map. */
    int maxColIndex;

    /** The maximum row index of the map. */
    int maxRowIndex;

    /** An array for choosing either hallway or nothing. */
    final String[] connectToRoom = new String[]{"Hallway", "None"};

    /** An array for choosing either hallway, room, or none to connect to a hallway. */
    final String[] connectToHallway = new String[]{"Room", "Hallway", "None"};

    /** An array for choosing either a vertical or horizontal hallway. */
    final String[] hallwayDirections = new String[]{"Horizontal", "Vertical"};

    /** The default tile type of this map. */
    TETile defaultTileType;

    /** True if the map contains an L-shaped hallway. */
    boolean containsLShapedHallway;

    /** The width of the map (total number of columns). */
    int width;

    /** The length of the map (total number of rows). */
    int length;

    /** Instantiate a basic 30x30 map. Provide a seed for randomness */
    RandomMap(int seed) {
        this(30, 30, Tileset.SAND, 1);
    }

    /** Instantiate a basic 30x30 map with the provided seed and default tile type.*/
    RandomMap(int seed, TETile tileType) {
        this(30, 30, tileType, seed);
    }

    /** Instantiate a w by l map where w is width and l is the length. Provide seed for randomness.
     * w is number of columns, which is the number of arrays, l is the number of rows, which is the length of
     * the inner arrays.
     * */
    RandomMap(int w, int l, int seed) {
       this(w, l, Tileset.SAND, seed);
    }

    /** Instantiate a w by l map where w is the width and l is the length. Specify default tile type to start with. */
    RandomMap(int w, int l, TETile tileType, int seed) {

        this.roomList = new ArrayList<>();
        this.hallwayList = new ArrayList<>();
        this.roomQueue = new LinkedList<>();
        this.hallwayQueue = new LinkedList<>();
        this.tileArray = new TETile[w][l];
        this.helperPopulateMap(this.tileArray, tileType);
        this.ran = new Random(seed);
        this.maxRowIndex = l - 1;
        this.maxColIndex = w - 1;
        this.width = w;
        this.length = l;
        this.containsLShapedHallway = false;
        this.defaultTileType = tileType;
        this.roomSets = new UnionFind(this.width * this.length);
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

        int numRooms = RandomUtils.uniform(this.ran, 5, 20);

        for (int i = 0; i < numRooms; i = i + 1) {
            Room r = generateRandomRoom(0.3);
            this.drawRoom(r);
            roomQueue.add(r);
        }

    }

    /** Draw the provided room in this map.
     * @param r    A Room object.
     * */
    private void drawRoom(Room r) {

        if (r == null) {
            return;
        }

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

    /** Draws a horizontally oriented hallway between two points.
     * @param   p1  A Point on the map.
     * @param   p2  A Point on the map.
     * @return A Point object that represents the point stopped at. Not necessarily p2.
     * */
     private Point drawHorizontalHallway(Point p1, Point p2) {

         Point startingPointHorizontal;
         Point endPointHorizontal;

         if (p1.col < p2.col) {
             startingPointHorizontal = p1;
             endPointHorizontal = p2;
         } else {
             startingPointHorizontal = p2;
             endPointHorizontal = p1;
         }

         int maxWidth = p1.horizontalDistance(p2);

         /* Width is inclusive - includes starting point and end point. Width 3 starting at col 1 = col 3 */

         int hallWayWidth = RandomUtils.uniform(this.ran, 1, maxWidth + 1);

         //drawHorizontalHallway(startingPointHorizontal, hallwayWidth)
         //find point stopped at; return it.

         // if not p2, drawVerticalHallway(pointStoppedAt, p2)
         // find point stopped at
         // if not p2, drawHorizontalHallway(pointStoopedAt, p2)

         return null;
     }



    /** Draw the horizontal walls around rooms.
     * @param r          The Room to draw the wall around.
     * @param wallRow   int, the index of the row to draw the horizontal wall.
     * */
    private void drawHorizontalWalls(Room r, int wallRow) {
        for (int curCol = r.start.col; curCol < r.start.col + r.width; curCol = curCol + 1) {
            this.tileArray[curCol][wallRow] = Tileset.WALL;
            // r.startPoint.union(new Point(curCol, wallRow)
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


    /** Generates a room of random width, length, and starting point. Limit width and lengths to
     * a specified percentage of this map's width and length.
     * @param   limit      a double representing the percentage of this map's width and length
     *                      each room can take up.
     * @return A Room object with a random start point, random width, and random length.
     * */
    private Room generateRandomRoom(double limit) {

        Point startPoint = helperFindRoomStartPoint();

        int[] roomDimensions = helperGenerateWidthLength(startPoint, limit);

        int roomWidth = roomDimensions[0];
        int roomLength = roomDimensions[1];

        return new Room(roomWidth, roomLength, startPoint);
    }

    /** A helper function to find a valid room starting point. Randomly pick a point until it
     * finds a point that is unused (is the default tile type of this map).
     * @return  A Point object representing the lower left corner of a room.
     */
    private Point helperFindRoomStartPoint() {
        Point startPoint = getRandomPoint();

        while (!helperTileAtPoint(startPoint).equals(this.defaultTileType)) {
            startPoint = getRandomPoint();
        }

        return startPoint;
    }

    /** A helper function to be used when generating random widths and lengths. Limits
     * the height and width to a given maximum.
     * @param   limitMax     int, representing the limit maximum width or length
     * @param   foundMax     int, representing the maximum without a cap.
     * @return  The minimum between the maximum found and the cap.
     */
     private int helperFindMaxLimit(int limitMax, int foundMax) {
         return Math.min(foundMax, limitMax);
     }

     /** A helper function to generate the width and length of a room at a given starting point.
      * @param  startPoint       Point representing the starting point.
      * @param  limit            The percentage cap of the map's width and length
      *                          set on the room's width and length.
      * @return A length 2 int array, with index 0 being the width and index 1 being the length.
      * */
     private int[] helperGenerateWidthLength(Point startPoint, double limit) {

         int widthLimit = (int) Math.round((this.maxRowIndex + 1) * limit);
         int lengthLimit = (int) Math.round((this.maxColIndex + 1) * limit);

         int maxWidth = helperFindMaxLimit(helperFindMaxRoomWidth(startPoint), widthLimit);

         int roomWidth = helperGenerateRandomWidthLength(maxWidth);

         int maxHeight = helperFindMaxLimit(findMaxRoomHeight(startPoint, roomWidth), lengthLimit);

         int roomHeight = helperGenerateRandomWidthLength(maxHeight);

         return new int[]{roomWidth, roomHeight};
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

    /** Convert 1d index to corresponding column and row index on this map.
     * A 5x5 map, index 7 in 1-D array corresponds to map[1][2]
     * @param   indexToConvert  int, the index to convert to a 2d array index
     * @return  A length 2 list {col, row}.
     * */
    private ArrayList<Integer> helper1DIndexConvertor(int indexToConvert) {
        int col = indexToConvert / this.maxColIndex + 1
    }

    /** Returns the tile at point p.
     * @param   p       A Point object.
     * @return  The type of tile at point p.
     * */
    private TETile helperTileAtPoint(Point p) {
        return this.tileArray[p.col][p.row];
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


    /** A helper function to randomly pick from an array A. Used for randomly picking connections to
     * rooms and hallways. */
    private String randomConnection(String[]A) {
        RandomUtils.shuffle(ran, A);
        return A[0];
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
