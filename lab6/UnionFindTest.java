import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {
    @Test
    public void testUnionFind() {
        UnionFind u = new UnionFind(10);

        u.union(1, 2);
        u.union(2, 5);
        u.union(3, 5);

        u.union(4, 7);
        u.union(6, 8);
        u.union(8, 9);
        u.union(4, 9);

        assertTrue(u.connected(1, 5));
        assertFalse(u.connected(1, 9));
        assertEquals(2, u.parent(1));
        assertEquals(2, u.parent(5));
        assertEquals(4, u.sizeOf(2));
        assertEquals(2, u.find(3));

        u.union(5, 7);

        assertEquals(8, u.find(1));
        assertEquals(8, u.parent(1));
        assertEquals(8, u.find(7));

        u.union(5, 0);

        assertEquals(10, u.sizeOf(3));
        assertEquals(8, u.parent(5));
    }
}
