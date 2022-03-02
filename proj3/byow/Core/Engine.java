package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class Engine {

    /** The current Interactive Map. **/
    private InteractiveMap iMap;

    /** True if the game is set up (has a seed).*/
    private boolean isGameSetup = false;

    /** True if the user wants to save the game. **/
    private boolean save = false;

    /** The saved game map. **/
    private InteractiveMap savedMap;

    /** A String of possible movements. **/
    private String POSSIBLE_MOVES = "wasd";

    /** The Random Number Generator. **/
    private Random ran = new Random(10);

    /** The seed. **/
    private int seed;

    /** The most recent position of the mouse. **/
    private MouseLocation mouseLoc;

    TERenderer ter = new TERenderer();

    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    /** The width of the map. **/
    private int mapWidth;

    /** The height of the map. **/
    private int mapHeight;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
       // StdDraw.enableDoubleBuffering();

        mainMenu();

        this.mouseLoc = new MouseLocation(Double.MIN_VALUE, Double.MIN_VALUE);

        KeyboardCommandInput k = new KeyboardCommandInput();
        processCommands(k);

    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // TODO: Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        // two scenarios: either n or l
        // if (n) { setSeed then do commands}
        // if (l) {load seed, then do commands}

        StringCommandInput s = new StringCommandInput(input);

        processCommands(s);

       TETile[][] finalWorldFrame = this.iMap.getGameMap().getTileArray();
        return finalWorldFrame;
    }

    /**
     * A helper function to generate a main menu.
     */
    private void mainMenu() {
        StdDraw.setCanvasSize();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font mainFont = new Font("Times New Roman", Font.PLAIN, 30);
        StdDraw.setFont(mainFont);
        StdDraw.text(0.5, 0.75, "CS61B:  The Game");

        Font optionsFont = new Font("Impact", Font.PLAIN, 15);
        StdDraw.setFont(optionsFont);
        StdDraw.text(0.5, 0.4, "New Game (N)");
        StdDraw.text(0.5, 0.36, "Load Game (L)");
        StdDraw.text(0.5, 0.32, "Quit Game (Q)");
        StdDraw.show();
    }

    /** The screen where the user will enter the seed. **/
    private void seedScreen() {
        StdDraw.clear();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font mainFont = new Font("Times New Roman", Font.PLAIN, 30);
        StdDraw.setFont(mainFont);
        StdDraw.text(0.5, 0.75, "Please enter the game seed");
        StdDraw.show();
    }

    private void drawSeedToScreen(int curSeed) {
        Font optionsFont = new Font("Impact", Font.PLAIN, 15);
        StdDraw.setFont(optionsFont);
        StdDraw.text(0.5, 0.6, String.valueOf(curSeed));
        StdDraw.show();
        //StdDraw.pause(1000);
    }

    /**
     * Process user commands.
     * @param commands Instructions to be processed such as "n123aswd".
     */
    private void processCommands(CommandInput commands) {

        TERenderer t = new TERenderer();

        this.ter = t;

        int curSeed = 0;


        while(commands.hasNextInput()) {

            if (this.isGameSetup) {
                commands.mouseDisplay(this);
            }


            String curCommand = String.valueOf(commands.getNextInput()).toLowerCase();

            if (!this.isGameSetup && (curCommand.equals("n"))) {
                seedScreen();
                curSeed = findSeed(commands);
                this.drawSeedToScreen(curSeed);
                createMapAndDisplay(commands, curSeed);

            } else if (this.isGameSetup) {
                if (curCommand.equals("l")) {
                    this.save = false;
                    this.helperDisplayTERenderer(commands);

                } else if (this.POSSIBLE_MOVES.contains(curCommand)) {
                    this.helperMoveAvatar(curCommand);
                    commands.displayTileArray(this, t, this.iMap.getGameMap().getTileArray());
                } else if (curCommand.equals(":")) {
                    this.save = true;
                } else if (curCommand.equals("q")) {
                    if (!save) {
                        this.isGameSetup = false;
                    }
                    mainMenu();
                } else if (curCommand.equals("x")) {
                    // temporarily exit this loop to yes/no screen;
                    confirmNewMap(commands);
                    this.seed = RandomUtils.uniform(this.ran, 1, 1000);
                    createMapAndDisplay(commands, this.seed);
                }
            }
        }

    }

    /** A helper function to create an interactive map using the provided seed.
     * @param curSeed   An int representing the seed.
     * @return  An length 2 int array with the randomly chosen width and height.
     * **/
    private int[] helperCreateMap(int curSeed) {
        this.ran = new Random(curSeed);
        int randomWidth = RandomUtils.uniform(this.ran, 5, 100);
        int randomHeight = RandomUtils.uniform(this.ran, 5, 100);
        RandomMap gameMap = new RandomMap(randomWidth, randomHeight, curSeed);
        this.iMap = new InteractiveMap(gameMap);
        this.isGameSetup = true;

        return new int[]{randomWidth, randomHeight};
    }

    /**
     *  A helper method to process a command to move the avatar.
     * @param curCommand    A String representing the movement direction.
     */
    private void helperMoveAvatar(String curCommand) {
        InteractiveMap.Avatar userAvatar = this.iMap.getAvatarList().get(0);
        this.iMap.moveAvatarCommand(userAvatar, curCommand);
    }

    /** A helper function to initialize the TERenderer and display it on screen.
     * @param commands  The commands inputted by the user.
     * **/
    private void helperDisplayTERenderer(CommandInput commands) {
        commands.initializeTERenderer(this.ter, this.mapWidth, this.mapHeight);
        commands.displayNoMouse(this, this.iMap.getGameMap().getTileArray());
    }

    /** A helper function to create and display a map with the provided seed.
     * @param   curSeed    Integer representing the seed.
     * @param   commands   The commands inputted by the user.
     */
    private void createMapAndDisplay(CommandInput commands, int curSeed) {
        int[] mapDimensions = this.helperCreateMap(curSeed);
        this.mapWidth = mapDimensions[0];
        this.mapHeight = mapDimensions[1];
        this.helperDisplayTERenderer(commands);
    }

    /**
     * Have user confirm if they want a new map. If yes, create and display new map.
     * If no, go back to current map.
     * @param commands The commands inputted by the user. 
     */
    private void confirmNewMap(CommandInput commands) {
        while (true) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledRectangle(this.mapWidth * 0.5, this.mapHeight * 0.5,
                    this.mapWidth * 0.3, this.mapHeight * 0.3);
            StdDraw.show();
        }

    }

    /** A helper function to find the seed the user wants from the provided string. */
    private int findSeed(CommandInput stringCommand) {
        int curSeed = 0;

        char curChar = stringCommand.getNextInput();


        curSeed = addToCurSeed(curSeed, Character.getNumericValue(curChar));

        while (stringCommand.hasNextInput()) {
            curChar = stringCommand.getNextInput();

            if (curChar == 's' || curChar == 'S') {
                break;
            }

            curSeed = addToCurSeed(curSeed, Character.getNumericValue(curChar));
        }

        return curSeed;
    }

    /** A helper function to add a digit to a seed.
     * @param   curSeed     The current seed.
     * @param   add         The digit to add.
     * @return  an integer equal to the digits of curSeed followed by add.
     */
    private int addToCurSeed(int curSeed, int add) {
        return curSeed * 10 + add;
    }

    /** Get the InteractiveMap. **/
    public InteractiveMap getiMap() {
        InteractiveMap copy = new InteractiveMap(this.iMap);
        return copy;
    }

    /** Get the most recent mouse location. **/
    public MouseLocation getMouseLoc() {
        return this.mouseLoc;
    }

    /** Set the MouseLocation to m. **/
    public void setMouseLoc(MouseLocation m) {
        this.mouseLoc = m;
    }

    /** Create a MouseLocation object with the current location of the mouse. **/
    public static MouseLocation createMouseLocation() {
        return new MouseLocation(StdDraw.mouseX(), StdDraw.mouseY());
    }

    /** For storing the mouse coordinates. **/
    static class MouseLocation {

        /** The x-coordinate. **/
        double mouseX;

        /** The y-coordinate. **/
        double mouseY;

        MouseLocation(double mouseX, double mouseY) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !o.getClass().toString().equals(this.getClass().toString())) {
                return false;
            }

            MouseLocation otherMouseLocation = (MouseLocation) o;

            return this.mouseY == otherMouseLocation.mouseY && this.mouseX == otherMouseLocation.mouseX;
        }
    }





    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithInputString("");
    }
}
