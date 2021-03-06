package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class Engine {

    /** The current Interactive Map. **/
    private GameMap iMap;

    /** True if the game is set up (has a seed).*/
    private boolean isGameSetup = false;

    /** True if the user wants to save the game. **/
    private boolean save = false;


    /** A String of possible movements. **/
    public static final String POSSIBLE_MOVES = "wasd";

    /** The Random Number Generator. **/
    private Random ran = new Random(10);

    /** The seed. **/
    private int seed;

    private TERenderer ter;

    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    /** The width of the map. **/
    private int mapWidth;

    /** The height of the map. **/
    private int mapHeight;

    /** The project directory. **/
    public static final String GAME_DIR = System.getProperty("user.dir") + "\\byow\\core";

    /** The project directory. **/
    public static final String COMMAND_FILE_PATH = System.getProperty("user.dir") + "\\byow\\core\\savedGames\\userInput.txt";

    private MapCreator mapCreator;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
       // StdDraw.enableDoubleBuffering();

        this.mapCreator = new IMapCreator();

        mainMenu();

        KeyboardCommandInput k = new KeyboardCommandInput();
        processCommands(k);

    }

    /** Play chasing game. **/
    public void interactWithKeyboardChaseMap() {
        mainMenu();

        this.mapCreator = new ChaseMapCreator();

        KeyboardCommandInput k = new KeyboardCommandInput();
        processCommands(k);
        ;
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

        this.mapCreator = new IMapCreator();

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

        int curSeed;

        ArrayList<String> keysPressed = new ArrayList<>();

        while(commands.hasNextInput()) {

            if (this.isGameSetup) {
                commands.mouseDisplay(this);
            }


            String curCommand = String.valueOf(commands.getNextInput()).toLowerCase();

            keysPressed.add(curCommand);

            if (!this.isGameSetup) {
                if (curCommand.equals("n")) {
                    seedScreen();
                    curSeed = findSeed(keysPressed, commands);
                    this.drawSeedToScreen(curSeed);
                    createMapAndDisplay(commands, curSeed);
                    this.isGameSetup = true;
                } else if (commands.isThereSavedFile() && curCommand.equals("l"))  { // add extra condition here hasSavedFile
                    this.save = false;
                    commands.load(this);
                    keysPressed.remove("l");
                    //this.helperDisplayTERenderer(commands);
                    //this.isGameSetup = true;
                }

                } else if (this.POSSIBLE_MOVES.contains(curCommand)) {
                    this.helperMoveAvatar(curCommand);
                    commands.displayTileArray(this, t, this.iMap.getGameMap().getTileArray());
                } else if (curCommand.equals(":")) {
                    this.save = true;

                    this.isGameSetup = false;

                    quitSaveScreen(commands);

                    writeCommandsToFile(keysPressed);

                    // write each item in the keysPressed arraylist to a file - each command is one line

                    return;

                } else if (curCommand.equals("q")) {
                    this.isGameSetup = false;
                    return;
                } else if (curCommand.equals("x")) {
                    // temporarily exit this loop to yes/no screen;
                    drawConfirmationScreen();
                    if(confirmNewMap(keysPressed, commands)) {
                        this.seed = RandomUtils.uniform(this.ran, 1, 1000);
                        createMapAndDisplay(commands, this.seed);
                    }
                }

                if (isGameSetup) {
                    if (!this.iMap.isPlaying()) {
                        this.iMap.displayFinish(this.iMap.finishText());
                        break;
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
        this.iMap = this.mapCreator.createMap(gameMap);
        this.isGameSetup = true;

        return new int[]{randomWidth, randomHeight};
    }

    /**
     *  A helper method to process a command to move the avatar.
     * @param curCommand    A String representing the movement direction.
     */
    private void helperMoveAvatar(String curCommand) {
        Avatar userAvatar = this.iMap.getAvatarList().get(0);
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
     * @param keysPressed The keys entered by the user.
     * @param commands The commands inputted by the user.
     * @return Boolean, either true is y or n is pressed, false otherwise.
     */
    private boolean confirmNewMap(ArrayList<String> keysPressed, CommandInput commands) {
        while (commands.hasNextInput()) {
            String curCommand = String.valueOf(commands.getNextInput()).toLowerCase();

            if (curCommand.equals("y")) {
                keysPressed.add(curCommand);
                return true;
            }

            if (curCommand.equals("n")) {
                keysPressed.add(curCommand);
                helperDisplayTERenderer(commands);
                return false;
            }


            drawConfirmationScreen();
        }

        return false;
    }

    /** Draws the new map confirmation screen. **/
    private void drawConfirmationScreen() {
        StdDraw.setPenColor(StdDraw.BLUE);

        double drawWidth = Math.min(this.mapWidth * 0.3, 5);
        double drawHeight = Math.min(this.mapHeight * 0.3, 5);

        double textX = this.mapWidth * 0.5 - 0.2;
        double textY = this.mapHeight * 0.5 + 1;

        StdDraw.filledRectangle(this.mapWidth * 0.5, this.mapHeight * 0.5,
                drawWidth, drawHeight);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(textX, textY, "Draw new map?");
        StdDraw.text(textX, textY - 1, "Y to confirm");
        StdDraw.text(textX, textY - 2, "N return to map");
        StdDraw.show();
    }

    /** The screen after the user types ":"
     * @param commands The commands inputted by the user.
     * **/
    private void quitSaveScreen(CommandInput commands) {

        drawText("Press Q to close the window");

        char nextKey = commands.getNextInput();

        while (commands.hasNextInput()) {

            if (nextKey == 'q' || nextKey== 'Q') {
                return;
            }

            drawText("Press Q to close the window");
            nextKey = commands.getNextInput();
        }
    }

    /**
     * Draws a rectangle in the middle of the screen with the provided text.
     * @param text A String that the user wants to display.
     */
    private void drawText(String text) {
        StdDraw.setPenColor(StdDraw.BLUE);

        double drawWidth = Math.min(this.mapWidth * 0.3, 5);
        double drawHeight = Math.min(this.mapHeight * 0.3, 5);

        double textX = this.mapWidth * 0.5 - 0.2;
        double textY = this.mapHeight * 0.5 + 1;

        StdDraw.filledRectangle(this.mapWidth * 0.5, this.mapHeight * 0.5,
                drawWidth, drawHeight);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(textX, textY, text);
        StdDraw.show();
    }

    /** A helper function to find the seed the user wants from the provided string.
     * Adds the keys pressed to the recorded list of commands keysPressed. */
    private int findSeed(ArrayList<String> keysPressed, CommandInput stringCommand) {
        int curSeed = 0;

        while (stringCommand.hasNextInput()) {
            char curChar = stringCommand.getNextInput();
            keysPressed.add(String.valueOf(curChar));


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


    /**
     * Write the commands from this run of engine to the savedGames folder.
     * Overwrite if a set of commands is already saved.
     * @param commands An ArrayList containing the keys pressed.
     */
    private void writeCommandsToFile(ArrayList<String> commands) {

        String pathToSavedFolder = this.GAME_DIR + "\\savedGames";

        File savedGameDir = new File(pathToSavedFolder);

        if (!savedGameDir.exists()) {
            savedGameDir.mkdir();
        }

        File commandsText = new File(this.COMMAND_FILE_PATH);

        if (commandsText.exists()) {
            commandsText.delete();
        }


        try {


            if (commandsText.createNewFile()) {
                FileWriter fw = new FileWriter(commandsText, true);

                for (int i = 0; i < commands.size() - 1; i = i + 1) {
                    String s = commands.get(i);
                    fw.write(s + "\n");
                }

                fw.close();
            }

            } catch(Exception e){
                e.printStackTrace();
            }


    }

    /** Get the InteractiveMap. **/
    public GameMap getiMap() {
        return this.iMap.copy(this.iMap);
    }

    public TERenderer getTer() {
        return this.ter;
    }

    /** Set isGameSetUp to true. **/
    public void setGameSetup() {
        this.isGameSetup = true;
    }

    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithInputString("");
    }
}
