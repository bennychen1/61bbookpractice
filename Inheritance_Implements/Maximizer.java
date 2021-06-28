public class Maximizer {
    public static Comparable max(Comparable[] items) {
        Comparable currentMax = items[0];
        for (int i = 1; i < items.length; i++) {
            if (currentMax.compareTo(items[i])< 0) {
                currentMax = items[i];
            }
        }

        /*public static Object max(Object[] items) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] > items[maxDex]) {
                maxDex = i;
            }
        }
    } */

        return currentMax;
    }

    public static void main(String[] args) {
        Dog[] dogs = {new Dog("Dogo", 1),
                new Dog( "Dog2", 2), new Dog("Dog 30",5)};
        Dog maxDog = (Dog) max(dogs);
        System.out.println(maxDog.name);
    }
}

/** Instead of taking in an Object array, take in a OurComparable array (Dog is-a OurComparable)
 * Because of the OurComparable interface, every OurComparable will have a compareTo method
 * max is static because in main, there is a max(Dog)
 * Using inheritance, max function can be generalized - don't need to write a new function
 * for each class
 * Dog, Maximizer, DogLauncher, and OurComparable
 *  If Dog does not have a compareTo method, then the Dog class will not compile
 *      Dog implements OurComparable, so it must have implement a compareTo method
 *  If Dog does not implement OurComparable, DogLauncher will not compile
 *      Maximizer will still compile (take out the main function)
 *      the error comes when max is used by DogLauncher, not when Maximizer is compiled (no code is run during compile time)
 *      Dog and Maximizer aren't really related
 * Key takeaway
 *      Using inheritance, how objects are compared can be specified within the classes
 *      Example of subtype polymorphism items[i].compareTo(items[i+1]) rather than compare(items[i], items[i+1]
 *  The issue with OurComparable: you need to cast. In Dog.java, the compareTo method requires casting
 *  Dog otherDog = (Dog) o because the compareTo in OurComparable is compareTo(Object o)
 *  OurComparable is not used in any libraries - Java's Comparable is - the max function
 *  wouldn't work for Strings or other classes since they would not OurComparables.
 *  There are already max, min functions, etc that work with Comparable*/
