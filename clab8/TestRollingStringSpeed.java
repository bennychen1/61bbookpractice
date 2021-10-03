import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.Stopwatch;

public class TestRollingStringSpeed {

    String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int[] LENGHTS = new int[]{100, 1000, 10000, 100000, 1000000};


    @Test
    public void testAdd() {
        RollingString rs = new RollingString("hello", 5);
        rs.addChar('o');
        String s = rs.toString();
        assertTrue(rs.toString().equals("elloo"));
    }
    @Test
    public void testAddSpeed() {
        ArrayList<RollingString> r = new ArrayList<>();
        double time[] = new double[LENGHTS.length];

        for (int i = 0; i < LENGHTS.length; i = i + 1) {
            r.add(new RollingString(createString(LENGHTS[i]), LENGHTS[i]));
        }

        for (int i = 0; i < LENGHTS.length; i = i + 1) {
            RollingString rs = r.get(i);
            Stopwatch s = new Stopwatch();
            for (int j = 0; j < 100000; j = j + 1) {
                int randomChar = (int)(Math.random() * (25 + 1));
                rs.addChar(CHARS.charAt(randomChar));
            }

            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));
    }

    /** Create a string of length LEN with random chars. */
    private String createString(int len) {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < len; i = i + 1) {
            int randomChar = (int)(Math.random() * (25 + 1));
            s.append(CHARS.charAt(randomChar));
        }

        return s.toString();
    }


}
