package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.Random;

public class Engine {

    /** The current Interactive Map. **/
    private InteractiveMap iMap;

    /** True if the game is set up (has a seed).*/
    private boolean isGameSetup = false;

    /** True if the user wants to save the game. **/
    private boolean save = false;

    /** A String of possible movements. **/
    private String POSSIBLE_MOVES = "WASD";

    /** The Random Number Generator. **/
    private Random ran = new Random(10);

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {

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
            char curCommand = s.getNextInput();

            if (!this.isGameSetup && (curCommand == 'n' || curCommand == 'N')) {
                int curSeed = findSeed(s);
                this.ran = new Random(curSeed);
                int randomWidth = RandomUtils.uniform(this.ran, 5, 100);
                int randomHeight = RandomUtils.uniform(this.ran, 5, 100);
                RandomMap gameMap = new RandomMap(randomWidth, randomHeight, curSeed);
                this.iMap = new InteractiveMap(gameMap);
                this.isGameSetup = true;

            } else if (this.POSSIBLE_MOVES.indexOf(curCommand) != 0) {
                InteractiveMap.Avatar userAvatar = this.iMap.getAvatarList().get(0);
                this.iMap.moveAvatarCommand(userAvatar, curCommand);
            } //else if ()
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

    /** A helper function to find the seed the user wants from the provided string. */
    private int findSeed(StringCommandInput stringCommand) {
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

    /** Stores the information from a user's input string.  */
    static class StringCall {

        /** "N" for new game, "L" for load game*/
        private String userOption;

        /** The seed for the randomly generated map. */
        private int seed;

        /** Movement commands, quit, and/or quit and save */
        private String commands;

        /** A String with all possible digits. */
        private final String POSSIBLE_DIGITS = "0123456789";

        StringCall(String inputString) throws IllegalArgumentException {
            // regex
            // set variables based on regex grouping
            // if not valid string, return null;
            // N and L have different string structure
            // If N, save the commands to a variable, then Lxx would just add xx to the variable


            int curSeed = 0;
            boolean seedSet = false;
            boolean save = false;

            StringBuilder userOptionsString = new StringBuilder();
            StringBuilder commandsString = new StringBuilder();

            for (char c : inputString.toCharArray()) {
                if (c == 'n' || c == 'N') {
                    userOptionsString.append("n");
                } else if (!seedSet && POSSIBLE_DIGITS.indexOf(c) != -1) {
                    curSeed = addToCurSeed(curSeed, Character.getNumericValue(c));
                } else if (!seedSet && (c == 's' || c == 'S')) {
                     seedSet = true;
                } else if (c == ':') {
                    save = true;
                } else if (c == 'q') {

                    commandsString.append(c);
                }
            }

            this.userOption = userOptionsString.toString();
            this.commands = commandsString.toString();
            this.seed = curSeed;

            if (this.userOption == "") {
                throw new IllegalArgumentException("String must start with N");
            }

        }

        /** A helper function to add a digit to a seed.
         * @param   curSeed     The current seed.
         * @param   add         The digit to add.
         * @return  an integer equal to the digits of curSeed followed by add.
          */
        private int addToCurSeed(int curSeed, int add) {
            return curSeed * 10 + add;
        }

        /** Return the user option. */
        public String getUserOption() {
            return this.userOption;
        }

        /** Return the seed.  */
        public int getSeed() {
            return this.seed;
        }

        /** Return the commands.  */
        public String commands() {
            return this.commands;
        }

    }


    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithInputString("");
    }
}
