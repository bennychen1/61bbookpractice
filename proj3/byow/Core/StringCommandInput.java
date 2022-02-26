package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import static byow.Core.Engine.createMouseLocation;

public class StringCommandInput implements CommandInput{

    /** The String command. **/
    String commandString;

    /** The next index. **/
    int curIndex;

    /**
     * The constructor for StringCommandInput
     * @param s A String representing the commands.
     */
    StringCommandInput(String s) {
        this.commandString = s;
        this.curIndex = 0;
    }

    /**
     * @return boolean, True if there is a next char, false otherwise.
     */
    @Override
    public boolean hasNextInput() {
        return this.curIndex < commandString.length();
    }

    /**
     * @return The char that curIndex is pointing at.
     */
    @Override
    public char getNextInput() {
        char toReturn =  commandString.charAt(this.curIndex);
        this.curIndex = this.curIndex + 1;
        return toReturn;
    }

    @Override
    public void initializeTERenderer(TERenderer t, int width, int height) {
        ;
    }

    @Override
    public void displayTileArray(Engine e, TERenderer t, TETile[][] tileArray) {
        ;
    }

    @Override
    public void mouseDisplay(Engine e) {
        ;
    }


}
