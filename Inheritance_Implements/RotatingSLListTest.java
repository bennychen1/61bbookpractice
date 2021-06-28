import org.junit.Test;
import static org.junit.Assert.*;

public class RotatingSLListTest {

    @Test
    public void testRotate() {
        RotatingSLList<String> s = new RotatingSLList<>();
        s.addFirst("One");
        s.addFirst("Two");
        s.addFirst("Three");

        s.rotate();

        assertEquals("One", s.getFirst());
        assertEquals("Three", s.get(1));
        assertEquals("Two", s.get(2));
    }

}
