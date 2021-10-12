import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class SeparableEnemySolverTests {

    @Test
    public void triangleEnemies() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("A", "C");
        g.connect("A", "D");
        g.connect("C", "D");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertEquals(false, solver.isSeparable());
    }

    @Test
    public void disconnected() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("C", "D");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertEquals(true, solver.isSeparable());
    }

    @Test
    public void disconnected2() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("C", "D");
        g.connect("E", "D");
        g.connect("E", "C");
        SeparableEnemySolver solver = new SeparableEnemySolver(g);
        assertEquals(false, solver.isSeparable());
    }

    @Test
    public void input1() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party1");
        assertEquals(true, solver.isSeparable());
    }

    @Test
    public void input2() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party2");
        assertEquals(true, solver.isSeparable());
    }

    @Test
    public void input3() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party3");
        assertEquals(false, solver.isSeparable());
    }

    @Test
    public void input4() throws FileNotFoundException {
        SeparableEnemySolver solver = new SeparableEnemySolver("input/party4");
        assertEquals(false, solver.isSeparable());
    }

    @Test
    public void testSeparableThreeinput4() throws FileNotFoundException {
        SeparableEnemySolverThree solver = new SeparableEnemySolverThree("input/party3");
        assertEquals(true, solver.isSeparable());
    }

    @Test
    public void testSeparableThreeFalse() {
        Graph g = new Graph();

        g.connect("A", "B");
        g.connect("A", "D");
        g.connect("A", "C");
        g.connect("B", "C");
        g.connect("C", "D");

        SeparableEnemySolverThree solver = new SeparableEnemySolverThree(g);
        assertEquals(false, solver.isSeparable());
    }

    @Test
    public void testSeparableThreeTrue() {
        Graph g = new Graph();
        g.connect("A", "B");
        g.connect("B", "D");
        g.connect("A", "C");
        g.connect("C", "D");
        SeparableEnemySolverThree solver = new SeparableEnemySolverThree(g);
        assertEquals(true, solver.isSeparable());
    }

}
