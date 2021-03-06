package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

/** An interface for movement commands **/
public interface CommandInput {
    public boolean hasNextInput();
    public char getNextInput();
    public void initializeTERenderer(TERenderer t, int width, int height);
    public void displayTileArray(Engine e, TERenderer t, TETile[][] tileArray);
    public void mouseDisplay(Engine e);
    public void displayNoMouse(Engine e,TETile[][] tileArray);
    public boolean isThereSavedFile();
    public void load(Engine e);
}
