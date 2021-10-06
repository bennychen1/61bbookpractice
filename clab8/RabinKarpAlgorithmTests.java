import org.junit.Test;
import static org.junit.Assert.*;

public class RabinKarpAlgorithmTests {
    @Test
    public void basic() {
        String input = "hello";
        String pattern = "ell";
        assertEquals(1, RabinKarpAlgorithm.rabinKarp(input, pattern));
    }

    @Test
    public void testRabinKarp() {
        String input = "There is an elephant";
        String pattern1 = "is a";
        String pattern2 = "is ant";

        assertEquals(-1, RabinKarpAlgorithm.rabinKarp(input, pattern2));
        assertEquals(6, RabinKarpAlgorithm.rabinKarp(input, pattern1));
    }
}
