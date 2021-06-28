package creatures;

import huglife.Action;
import huglife.Direction;
import huglife.Empty;
import huglife.Impassible;
import huglife.Occupant;
import static org.junit.Assert.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.HashMap;
import org.junit.Test;

public class ClorusTest {
    @Test
    public void testMovement() {
        Clorus c = new Clorus(10);
        c.stay();
        assertEquals(9.99, c.energy(), 0.001);
        c.move();
        assertEquals(9.96, c.energy(), 0.001);
    }
    @Test
    public void testReplicate() {
        Clorus c = new Clorus();
        Clorus r = (Clorus) c.replicate();

        assertEquals((int) r.energy(), (int) c.energy() / 2);
        assertEquals((int) c.energy(), (int) r.energy());
    }

    @Test
    public void testAttack(){
        Clorus c = new Clorus(5);
        Plip p = new Plip(1);

        c.attack(p);

        assertEquals(6, c.energy(), 0.001);
    }

    @Test
    public void testChooseAction() {
        Clorus c = new Clorus(5);

        Map<Direction, Occupant> noEmptySquares = new HashMap<>();

        noEmptySquares.put(Direction.TOP, new Impassible());
        noEmptySquares.put(Direction.BOTTOM, new Impassible());
        noEmptySquares.put(Direction.LEFT, new Impassible());
        noEmptySquares.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(noEmptySquares);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        Map<Direction, Occupant> noEmptySquares2 = new HashMap<>();

        noEmptySquares2.put(Direction.TOP, new Clorus(2));
        noEmptySquares2.put(Direction.BOTTOM, new Plip(1));
        noEmptySquares2.put(Direction.LEFT, new Impassible());
        noEmptySquares2.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(noEmptySquares2);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        Map<Direction, Occupant> onePlip = new HashMap<>();

        onePlip.put(Direction.TOP, new Clorus(2));
        onePlip.put(Direction.BOTTOM, new Plip(2));
        onePlip.put(Direction.LEFT, new Empty());
        onePlip.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(onePlip);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);

        Clorus c2 = new Clorus(2);

        Map<Direction, Occupant> replicateEmptySquare = new HashMap<>();

        replicateEmptySquare.put(Direction.TOP, new Clorus(2));
        replicateEmptySquare.put(Direction.BOTTOM, new Impassible());
        replicateEmptySquare.put(Direction.LEFT, new Empty());
        replicateEmptySquare.put(Direction.RIGHT, new Clorus(3));

        actual = c2.chooseAction(replicateEmptySquare);
        expected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);

        assertEquals(expected, actual);

        c2.replicate();
        c2.replicate();
        assertEquals(0.5, c2.energy(), 0.001);

        actual = c2.chooseAction(replicateEmptySquare);
        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

        assertEquals(expected, actual);

    }



}
