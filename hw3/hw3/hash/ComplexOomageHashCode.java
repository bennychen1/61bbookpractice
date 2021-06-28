package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class ComplexOomageHashCode {
    public static void main(String[] args) {
        List<Integer> o1 = new ArrayList<>();
        List<Integer> o2 = new ArrayList<>();

        o1.add(1);
        o1.add(0);
        o1.add(0);
        o1.add(0);
        o1.add(0);

        o2.add(2);
        o2.add(0);
        o2.add(0);
        o2.add(0);
        o2.add(0);

        ComplexOomage oo1 = new ComplexOomage(o1);
        ComplexOomage oo2 = new ComplexOomage(o2);

        System.out.println(oo1.hashCode() + " " + oo2.hashCode());

    }
}
/**
 * 256 ^ x = 0 when x >= 4 because:
 *  Integers are 32 bit numbers (4 bytes)
 *  multiply by 256 (2 ^ 8) add eight zeroes, shift everything right
 *  so n * 256 ^ x is 0 for any n when x >= 4.
 *  Complex Oomage hashcode: (((((x1 * 256) * 256) + x2) * 256) + x3) * 256 ....
 *  for the fifth parameter, 256 multiplied four times (total will be 0) then add the fifth parameter
 *  Adding between the first and last parameters throws total off
 *          if all the parameters in between are 0, then total will be a multiple of 256.
 *
 *
 * **/
