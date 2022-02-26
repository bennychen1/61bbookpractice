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
    public void displayTileArray(Engine e, TERenderer t, TETile[][] tileArray) {
        mouseDisplay(e);
        t.renderFrame(tileArray);
    };

    @Override
    public void mouseDisplay(Engine e) {
        while (!StdDraw.hasNextKeyTyped()) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(1, 22, 30, 1);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(2, 22, String.valueOf(StdDraw.mouseX()));
            StdDraw.show();
        }
    }


}
