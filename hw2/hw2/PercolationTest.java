package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {
    @Test
    public void testOpen() {
        Percolation p = new Percolation(5);

        p.open(1, 1);
        p.open(1, 2);

        assertTrue(p.isOpen(1, 1));
        assertFalse(p.isOpen(3, 2));

    }

    @Test
    public void testIsFull() {
        Percolation p = new Percolation(3);

        p.open(0, 1);
        p.open(1, 0);
        p.open(1, 1);
        p.open(1, 2);

        assertTrue(p.isFull(1, 1));
        assertTrue(p.isFull(1, 2));
        assertFalse(p.isFull(0, 2));
    }

    @Test
    public void testPercolates() {
        Percolation p = new Percolation(6);

        p.open(3, 2);
        p.open(4, 1);
        p.open(0, 3);
        p.open(2, 5);
        p.open(1, 3);
        p.open(0, 1);
        p.open(2, 4);
        p.open(1, 4);
        p.open(5, 1);
        p.open(0, 5);
        p.open(4, 5);
        p.open(3, 5);
        p.open(5, 5);
        p.open(4, 2);

        assertEquals(14, p.numberOfOpenSites());

        assertTrue(p.isFull(2, 5));
        assertFalse(p.isFull(3, 2));
        assertTrue(p.isFull(0, 1));
        assertTrue(p.isFull(5, 5));

        assertTrue(p.percolates());
    }

    @Test
    public void testNotPercolates() {
        Percolation p = new Percolation(6);

        p.open(3, 2);
        p.open(4, 1);
        p.open(0, 3);
        p.open(2, 5);
        p.open(1, 3);
        p.open(0, 1);
        p.open(1, 4);
        p.open(5, 1);
        p.open(0, 5);
        p.open(4, 5);
        p.open(3, 5);
        p.open(5, 5);
        p.open(4, 2);

        assertFalse(p.percolates());
    }
}
