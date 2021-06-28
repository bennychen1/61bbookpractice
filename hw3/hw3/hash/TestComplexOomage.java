package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.

        /** Current hash function: the parameters are x1, x2, x3, .... xN
         * hash function is:
         *  (x1 * 256 ^ (N - 1)) + (x2 * 256 ^ (N - 2)) + (x3 * 256 ^ (N - 3)) .... + (xN * 256 ^ 0))
         *  256 ^ i = 0 when i >= 4 so if each Complex Oomage has 4 parameters, the first part of
         *  the hash function is always zero. **/

       int N = 100;

       for (int i = 0; i < N; i = i + 1) {
           List<Integer> params = new ArrayList<>();
           params.add(StdRandom.uniform(1, 100));
           params.add(0);
           params.add(0);
           params.add(0);

           deadlyList.add(new ComplexOomage(params));
       }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
