package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import edu.princeton.cs.algs4.RandomSeq;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

/** Commands typed in from the keyboard. **/
public class KeyboardCommandInput implements CommandInput {

    /** The current command **/
    char curCommand;

    /** Previous user commands from a saved game. **/
    LinkedList<String> loadedCommands;


    KeyboardCommandInput() {
        this.curCommand = Character.MIN_VALUE;
        this.loadedCommands = new LinkedList<>();
    }

    @Override
    public boolean hasNextInput() {
        return true;
    }

    @Override
    public char getNextInput() {

        if (!this.loadedCommands.isEmpty()) {
            return this.loadedCommands.pop().charAt(0);
        }

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
        t.initialize(width, height + 1);
    }

    @Override
    public void displayTileArray(Engine e, TERenderer t, TETile[][] tileArray) {
        t.renderFrame(tileArray);
    };

    @Override
    public void displayNoMouse(Engine e, TETile[][] tileArray ) {
        e.getTer().renderFrame(tileArray);
    }

    @Override
    public void mouseDisplay(Engine e) {
        while (!StdDraw.hasNextKeyTyped() && this.loadedCommands.isEmpty()) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(1, e.getTer().getHeight(), e.getTer().getWidth(), 1);
            StdDraw.setPenColor(StdDraw.WHITE);
            /**
            StdDraw.text(2, e.ter.getHeight() - 0.5, String.valueOf(StdDraw.mouseX()));
            StdDraw.text(5, e.ter.getHeight() - 0.5, String.valueOf(StdDraw.mouseY())); **/

            RandomMap gameMap = e.getiMap().getGameMap();

            int xInt = (int) StdDraw.mouseX();
            int yInt = (int) StdDraw.mouseY();

            Point p = new Point(xInt, yInt);

            if (e.getiMap().getGameMap().isPointOnMap(p)) {
                StdDraw.text(2, e.getTer().getHeight() - 0.5, gameMap.getTileAt(p).description());
            }

            StdDraw.show();
        }
    }

    @Override
    public boolean isThereSavedFile() {
        return checkIfSavedFiles();
    }

    @Override
    public void load(Engine e) {
        File commandsFile = new File(Engine.COMMAND_FILE_PATH);
        if (commandsFile.exists()) {

            try {
                BufferedReader commandReader = new BufferedReader(new FileReader(commandsFile));


                String curCommand = commandReader.readLine();


                while (curCommand != null) {
                    this.loadedCommands.add(curCommand);
                    curCommand = commandReader.readLine();
                }

            } catch (Exception exc) {
                exc.printStackTrace();
            }


            // read in the file
            // record key presses using Robot class

        }
    }

    /** Check if there is a savedGame folder with files in it. **/
    private boolean checkIfSavedFiles() {
        return new File(Engine.COMMAND_FILE_PATH).exists();
    }


}
