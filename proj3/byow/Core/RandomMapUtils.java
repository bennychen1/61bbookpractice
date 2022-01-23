package byow.Core;

import java.util.Random;

/** A class with utility functions to generate random points, random widths, random lengths, etc.  */
public class RandomMapUtils {

    /** The random number generator. **/
    Random ran;

    /** Instantiate with the random number generator using the provided seed. **/
    RandomMapUtils(int seed) {
        this.ran = new Random(seed);
    }

    /** Randomly pick from an array A.*/
    public String randomConnection(String[]A) {
        RandomUtils.shuffle(ran, A);
        return A[0];
    }
    
}
