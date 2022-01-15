package byow.Core;

public class Hallway extends Room{

    /** Is hallway vertical or horizontal. **/
    boolean isHorizontal;

    Hallway(boolean isHorizontal, int size, Point start) {

        super(size, size, start);
        
        if (isHorizontal) {
            this.length = 1;
        } else {
            this.width = 1;
        }

    }
}
