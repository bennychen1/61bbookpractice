package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.*;

/** An object representing a randomly generated map. **/
public class RandomMap {

    /** A list of all the rooms in the map. */
    private List<Room> roomList;

    /** The number of rooms in the map. */
    private int numRooms;


    /** The tile array that represents this map. */
    private TETile[][] tileArray;

    /** A union find object - each connected room will belong to a set.
     * Indices - 5x5 array - index 2 will represent tileArray[0][2]
     * index 20 tileArray[4][0];
     * */
    private UnionFind roomSets;

    /** The random number generator for this map. */
    private Random ran;

    /** The maximum column index of the map. */
    private int maxColIndex;

    /** The maximum row index of the map. */
    private int maxRowIndex;

    /** The default tile type of this map. */
    private TETile defaultTileType;

    /** The width of the map (total number of columns). */
    private int width;

    /** The length of the map (total number of rows). */
    private int length;

    /** Hallway drawers. */
    private final HallwayDrawer[] hallwayDrawers = new HallwayDrawer[]{new HorizontalHallwayDrawer(),
            new VerticalHallwayDrawer()};

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
        this.tileArray = new TETile[w][l];
        this.helperPopulateMap(this.tileArray, tileType);
        this.ran = new Random(seed);
        this.maxRowIndex = l - 1;
        this.maxColIndex = w - 1;
        this.width = w;
        this.length = l;
        this.defaultTileType = tileType;
        this.roomSets = new UnionFind(this.width * this.length);
        this.numRooms = 0;
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

        addRooms();
        addHallways();
    }

    /**
     * Add a random number of rooms to this map.
     * */
    private void addRooms() {
        int numRooms = RandomUtils.uniform(this.ran, 5, 20);

        for (int i = 0; i < numRooms; i = i + 1) {
            Room r = generateRandomRoom(0.3);
            this.drawRoom(r);
            roomList.add(r);
            this.numRooms = this.numRooms + 1;
        }
    }

    /**
     * Add the hallways to the map.
     * */
    private void addHallways() {
        HashSet<Room> unionedRooms = new HashSet<>();

        Collections.shuffle(this.roomList, this.ran);

        Room randomInitialRoom = this.roomList.get(0);
        unionedRooms.add(randomInitialRoom);

        while (unionedRooms.size() < this.roomList.size()) {
            hallwayBetweenTwoRooms(unionedRooms);
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
                this.roomSets.union(helper2DIndexConvertor(curCol, curRow),
                     helper2DIndexConvertor(r.start));
            }
        }


        drawHorizontalWalls(r, r.start.row - 1);
        drawHorizontalWalls(r, r.start.row + r.length);
        drawVerticalWalls(r, r.start.col - 1);
        drawVerticalWalls(r, r.start.col + r.width);
    }

    /** Draw a hallway or hallways to connect two rooms at two random points. One room will be chosen
     * from each of two sets of rooms.
     * @param   set1      A Set of Rooms that are connected (unioned).
     * */
    private void hallwayBetweenTwoRooms(Set set1) {
        Room r1 = helperRandomRoomFromSet(set1);
        Room r2 = helperRandomRoom();

        Point p1 = helperRandomRoomPoint(r1);
        Point p2 = helperRandomRoomPoint(r2);

        HallwayDrawer currHallwayDrawer = this.chooseRandomHallwayDrawer();

        FinishHallwayInformation currentArgs = new FinishHallwayInformation(r1, p1, r2, p2);

        int i = 0;

        while (!this.roomSets.connected(helper2DIndexConvertor(r1.start),
                helper2DIndexConvertor(r2.start))) {

            currentArgs = currHallwayDrawer.draw(this, currentArgs.r1, currentArgs.p1, currentArgs.r2,
                    currentArgs.p2);

            currHallwayDrawer = chooseRandomHallwayDrawer();

            i = i + 1;

        }

        set1.add(r2);

    }

    /** A helper function to randomly choose a direction to draw a hallway. **/
    private HallwayDrawer chooseRandomHallwayDrawer() {
        RandomUtils.shuffle(this.ran, this.hallwayDrawers);
        return this.hallwayDrawers[0];
    }

    /** Get a random room from the room list.
     * @return a Room on this map. */
    private Room helperRandomRoom() {
        Collections.shuffle(this.roomList, this.ran);
        return this.roomList.get(0);
    }

    /**
     * Returns a randomly selected room from the provided set of rooms.
     * @param roomSet   A Set of Rooms
     * @return  A Room chosen at random.
     */
    private Room helperRandomRoomFromSet(Set roomSet) {
        ArrayList<Room> roomsAsList = new ArrayList<>(roomSet);
        Collections.shuffle(roomsAsList, this.ran);
        return roomsAsList.get(0);
    }

    /** Get a random point in a room.
     * @return a Point object. */
    private Point helperRandomRoomPoint(Room r) {
        Collections.shuffle(r.roomPoints, this.ran);
        return r.roomPoints.get(0);
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

        int tries = 0;
        while (!helperTileAtPoint(startPoint).equals(this.defaultTileType) && tries < 20) {
            startPoint = getRandomPoint();
            tries = tries + 1;
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
     * @return  A length 2 int array representing {col, row}.
     * */
    private int[] helper1DIndexConvertor(int indexToConvert) {
        int colIndex = indexToConvert / this.width;
        int rowIndex = indexToConvert % this.width;

        return new int[]{colIndex, rowIndex};
    }

    /** Convert the column index and row index into a 1d index.
     * A 5x5 map, map[1][2] corresponds to index 7 - the 8th element.
     * @param   colIndex  int, the column index.
     * @param   rowIndex  int, the row index
     * @return  an int representing the corresponding 1d index.
     * */
    public int helper2DIndexConvertor(int colIndex, int rowIndex) {
        return (rowIndex * this.width) + colIndex;
    }

    /** Convert a Point object into its corresponding 1d index.
     * A 5x5 map, a point at (1, 2) corresponds to index 7 - the 8th element.
     * @param   p   A point object
     * @return  an int representing the corresponding 1d index.
     * */
    public int helper2DIndexConvertor(Point p) {
        return helper2DIndexConvertor(p.col, p.row);
    }

    /** Returns the tile at point p.
     * @param   p       A Point object.
     * @return  The type of tile at point p.
     * */
    private TETile helperTileAtPoint(Point p) {
        return this.tileArray[p.col][p.row];
    }

    /** Pick a random point on the map to draw the initial room . */
    private Point getRandomPoint() {

        int rowIndex = RandomUtils.uniform(ran, 1, this.maxRowIndex- 1);
        int colIndex = RandomUtils.uniform(ran, 1, this.maxColIndex - 1);
        return new Point(colIndex, rowIndex);
    }


    /** Get a deep copy of this map's tile array. */
    public TETile[][] getTileArray() {
        TETile[][] toReturn = new TETile[this.width][this.length];
        for (int i = 0; i <= this.maxColIndex; i = i + 1) {
            System.arraycopy(this.tileArray[i], 0, toReturn[i], 0, this.length);
        }
        return toReturn;
    }

    /** Returns the TETile at the specified Point.
     * @param   p   A Point object
     * @return  A TETile object that represents the tile type at the point in this map.
     * */
    public TETile getTileAt(Point p) {
        return this.tileArray[p.col][p.row];
    }

    /** Returns the max column index of this map. */
    public int getMaxColIndex() {
        return this.maxColIndex;
    }

    /** Returns the max row index of this map. */
    public int getMaxRowIndex() {
        return this.maxRowIndex;
    }

    /** Returns the Random Number Generator of this map. */
    public Random getRan() {
        return this.ran;
    }


    /** Set the tile at the given column and row to a specified tile type in this map's tile array.
     * @param   col         The column index of the tile to change
     * @param   row         The row index of the tile to change.
     * @param   tileType    The type of tile to change the tile to.
     * */
    public void setTileArray(int col, int row, TETile tileType) {
        this.tileArray[col][row] = tileType;
    }

    /** Set the tile at the given Point to a specified tile type in this map's tile array.
     * @param   p           A Point representing the location to chagne the tile.
     * @param   tileType    The type of tile to change the tile to.
     * */
    public void setTileArray(Point p, TETile tileType) {
        setTileArray(p.col, p.row, tileType);
    }

    /** Public method to union two points in this map.
     * @param   index1  The index of the first point.
     * @param   index2  The index of the second point.
     * */
    public void unionPoints(int index1, int index2) {
        this.roomSets.union(index1, index2);
    }






}
