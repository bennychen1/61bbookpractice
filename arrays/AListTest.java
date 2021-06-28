import org.junit.Test;
import static org.junit.Assert.*;

public class AListTest {
    @Test
    public void testAddLast() {
        AList<Integer> a = new AList();
        a.addLast(1);
        a.addLast(2);

        assertEquals(2, a.size());
        assertEquals(Integer.valueOf(2), a.getLast());
        assertEquals(Integer.valueOf(1), a.get(0));
    }

    @Test
    public void testRemoveLast() {
        AList<Integer> a = new AList();
        a.addLast(3);
        a.addLast(5);
        a.addLast(7);

        Integer e = a.removeLast();

        assertEquals(Integer.valueOf(5), a.getLast());
        assertEquals(2, a.size());
        assertEquals(Integer.valueOf(7), e);

        a.addLast(9);

        assertEquals(Integer.valueOf(9), a.getLast());
        assertEquals(Integer.valueOf(5), a.get(1));
        assertEquals(3, a.size());

    }
}
