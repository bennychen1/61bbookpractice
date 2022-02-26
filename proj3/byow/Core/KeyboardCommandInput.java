package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

import static byow.Core.Engine.createMouseLocation;

/** Commands typed in from the keyboard. **/
public class KeyboardCommandInput implements CommandInput {

    /** The current command **/
    char curCommand;

    KeyboardCommandInput() {
        this.curCommand = Character.MIN_VALUE;
    }

    @Override
    public boolean hasNextInput() {
        return true;
    }

    @Override
    public char getNextInput() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                this.curCommand = StdDraw.nextKeyTyped();
                return this.curCommand;
            }
        }

        //System.out.println("a");
        //return Character.MIN_VALUE;
    }

    @Override
    public void initializeTERenderer(TERenderer t, int width, int height) {
        t.initialize(width, height);
    }

    @Override
    public void displayTileArray(TERenderer t, TETile[][] tileArray) {
        t.renderFrame(tileArray);
    };

    @Override
    public void mouseDisplay(Engine e) {
        Engine.MouseLocation curMouseLocation = createMouseLocation();
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.clear();
            e.setMouseLoc(curMouseLocation);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(3, 0.9, "                           ");
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.text(3, 0.9, String.valueOf(curMouseLocation.mouseX));
            curMouseLocation = createMouseLocation();
            StdDraw.show();
        }
    }


}
