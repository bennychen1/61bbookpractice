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



}
