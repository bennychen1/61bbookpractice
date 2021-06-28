package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Random;

/** An implementation of a Clorus creature */

public class Clorus extends Creature {
    /** A Clorus always has the name "clorus" */
    private final String name = "clorus";

    /** A Clorus always has an r value of 34*/
    private final int r = 34;

    /** A Clorus always has a g value of 0 */
    private final int g = 0;

    /** A Clorus always has a b value of 231 */
    private final int b = 231;

    /** A Clorus loses 0.03 energy when it moves */
    private final double moveEnergy = 0.03;

    /** A Clorus loses 0.01 energy when it chooses to stay */
    private final double stayEnergy = 0.01;

    /** Create a Clorus with energy = 1 */
    public Clorus() {
        this(1);
    }

    /** Create a Clorus with energy equal to ENERGY */
    public Clorus(double energy) {
        super("clorus");
        this.energy = energy;
    }

    @Override
    /** A Clorus loses 0.03 energy when it moves */
    public void move() {
        this.energy = this.energy - moveEnergy;
    }

    @Override
    /** A Clorus loses 0.01 energy when it stays.
     * Cloruses like to move.  */
    public void stay() {
        this.energy = this.energy - stayEnergy;
    }

    @Override
    /** When a Clorus replicates, it produces an offspring with half of
     * the Clorus's energy. The Clorus loses half of its energy. No energy
     * is loss in the process.
     */
    public Clorus replicate() {
        Clorus r = new Clorus(this.energy / 2);
        this.energy = this.energy / 2;
        return r;
    }

    @Override
    /** A Clorus chooses an action according to the following:
     *      1. If there are no empty squares, the Clorus will stay.
     *      2. Otherwise, if any Plips are seen, a Clorus will attack one
     *          of them randomly.
     *      3. Otherwise, if a clorus has energy >= 1, it will replicate to
     *          a random empty square.
     *      4. Otherwise, the Clorus will move towards an empty square at random.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<Direction>();
        Deque<Direction> plipNeighbors = new ArrayDeque<Direction>();

        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            } else if (!neighbors.get(d).name().equals("clorus") &&
                !neighbors.get(d).name().equals("impassible")) {
                plipNeighbors.add(d);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipNeighbors.size() > 0) {
            Random r = new Random();
            int randomIndex = r.nextInt(plipNeighbors.size());
            Direction d = null;

            for (int i = 0; i <= randomIndex; i = i + 1) {
                d = plipNeighbors.peek();
                plipNeighbors.pop();
                plipNeighbors.add(d);
            }
            return new Action(Action.ActionType.ATTACK, d);
        } else if (this.energy >= 1) {
            Random r = new Random();
            int randomIndex = r.nextInt(emptyNeighbors.size());
            Direction d = null;

            for (int i = 0; i <= randomIndex; i = i + 1) {
                d = emptyNeighbors.peek();
                emptyNeighbors.pop();
                emptyNeighbors.add(d);
            }

            return new Action(Action.ActionType.REPLICATE, d);
        } else {
            Random r = new Random();
            int randomIndex = r.nextInt(emptyNeighbors.size());
            Direction d = null;

            for (int i = 0; i <= randomIndex; i = i + 1) {
                d = emptyNeighbors.peek();
                emptyNeighbors.pop();
                emptyNeighbors.add(d);
            }

            return new Action(Action.ActionType.MOVE, d);
        }
    }

    @Override
    /** When a Clorus attacks, it gains the energy of the Creature it attacks */
    public void attack(Creature c) {
        this.energy = this.energy + c.energy();
    }

    @Override
    /** Returns a color of r = 34, green = 0, blue = 231 */
    public Color color() {
        return color(r, g, b);
    }



}

/* All constructors need to directly call super or call one of the constructors that call super
*       because in this case, the Creature class nor the Occupant class has no blank constructor
*       wouldn't be able to call super() (no args)
* Division: divide a double and an int (either order) the int is cast to double so
*   the output would be a double x.abc (with decimal)*/
