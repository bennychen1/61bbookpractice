package byow.Core;

/** Represents a room with width, length, lower left corner, .... **/
public class Room {

    /** The width of the room - the number of columns it takes up. **/
    int width;

    /** The length of the room - the number of rows it takes up. **/
    int length;

    /** The lower left corner of the room. **/
     Point start;

     /** Instantiate a room of width w and length l that starts at Point p. **/
     public Room(int w, int l, Point p) {
         this.width = w;
         this.length = l;
         this.start = p;
     }

     /** Instantiate a default room of 0 width and 0 length at (0, 0). **/
     public Room() {
         this.width = 0;
         this.length = 0;
         this.start = new Point(0, 0);
     }

}
