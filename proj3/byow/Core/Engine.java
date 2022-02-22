package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

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

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        mainMenu();

        KeyboardCommandInput k = new KeyboardCommandInput();

        while(true) {
            while (k.hasNextInput()) {
                String curCommand = String.valueOf(k.getNextInput()).toLowerCase();

                if (curCommand.equals("n")) {
                    seedScreen();
                    int curSeed = findSeed(k);
                    this.ran = new Random(curSeed);
                    int randomWidth = RandomUtils.uniform(this.ran, 5, 100);
                    int randomHeight = RandomUtils.uniform(this.ran, 5, 100);
                    RandomMap gameMap = new RandomMap(randomWidth, randomHeight, curSeed);
                    this.iMap = new InteractiveMap(gameMap);
                    this.isGameSetup = true;
                }
            }
        }

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

        while(s.hasNextInput()) {
            String curCommand = String.valueOf(s.getNextInput()).toLowerCase();

            if (!this.isGameSetup && (curCommand.equals("n"))) {
                int curSeed = findSeed(s);
                this.ran = new Random(curSeed);
                int randomWidth = RandomUtils.uniform(this.ran, 5, 100);
                int randomHeight = RandomUtils.uniform(this.ran, 5, 100);
                RandomMap gameMap = new RandomMap(randomWidth, randomHeight, curSeed);
                this.iMap = new InteractiveMap(gameMap);
                this.isGameSetup = true;

            } else if (curCommand.equals("l")) {
                continue;

            } else if (this.POSSIBLE_MOVES.indexOf(curCommand) >= 0) {
                InteractiveMap.Avatar userAvatar = this.iMap.getAvatarList().get(0);
                this.iMap.moveAvatarCommand(userAvatar, curCommand);
            } else if (curCommand.equals(":")) {
                this.save = true;
            } else {
                break;
            }

            if (!save) {
                this.isGameSetup = false;
            }

        }

       TETile[][] finalWorldFrame = this.iMap.getGameMap().getTileArray();
        return finalWorldFrame;
    }

    /**
     * A helper function to generate a main menu.
     */
    private void mainMenu() {
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
    }

    /** The screen where the user will enter the seed. **/
    private void seedScreen() {
        StdDraw.clear();
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        Font mainFont = new Font("Times New Roman", Font.PLAIN, 30);
        StdDraw.setFont(mainFont);
        StdDraw.text(0.5, 0.75, "Please enter the game seed");



    }

    /**
     * Process user commands.
     * @param commands Instructions to be processed such as "n123aswd".
     */
    private void processCommands(CommandInput commands) {

        while(commands.hasNextInput()) {
            String curCommand = String.valueOf(commands.getNextInput()).toLowerCase();

            if (!this.isGameSetup && (curCommand.equals("n"))) {
                int curSeed = findSeed(commands);
                this.ran = new Random(curSeed);
                int randomWidth = RandomUtils.uniform(this.ran, 5, 100);
                int randomHeight = RandomUtils.uniform(this.ran, 5, 100);
                RandomMap gameMap = new RandomMap(randomWidth, randomHeight, curSeed);
                this.iMap = new InteractiveMap(gameMap);
                this.isGameSetup = true;

            } else if (curCommand.equals("l")) {
                continue;

            } else if (this.POSSIBLE_MOVES.indexOf(curCommand) >= 0) {
                InteractiveMap.Avatar userAvatar = this.iMap.getAvatarList().get(0);
                this.iMap.moveAvatarCommand(userAvatar, curCommand);
            } else if (curCommand.equals(":")) {
                this.save = true;
            } else {
                break;
            }

            if (!save) {
                this.isGameSetup = false;
            }


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





    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithInputString("");
    }
}
