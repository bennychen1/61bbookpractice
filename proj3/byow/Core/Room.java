package byow.Core;

import java.util.ArrayList;

/** Represents a room with width, length, lower left corner, .... **/
public class Room {

    /** The width of the room - the number of columns it takes up. **/
    int width;

    /** The length of the room - the number of rows it takes up. **/
    int length;

    /** The lower left corner of the room. **/
     Point start;

     /** A list of points that comprise the room. To be populated when drawn. */
     ArrayList<Point> roomPoints;

     /** Instantiate a room of width w and length l that starts at Point p. **/
     public Room(int w, int l, Point p) {
         this.width = w;
         this.length = l;
         this.start = p;
         this.roomPoints = new ArrayList<>();
     }

     /** Instantiate a default room of 0 width and 0 length at (0, 0). **/
     public Room() {
         this(0, 0, new Point(0, 0));
     }


}
