import java.util.Comparator;

public class Dog implements Comparable<Dog>  {
    int size;
    String name;

    public Dog(String name, int n) {
        this.name = name;
        this.size = n;
    }
    /*public static Object max(Object[] items) {
        int maxDex = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] > items[maxDex]) {
                maxDex = i;
            }
        }
    } */

    @Override
    public int compareTo(Dog o) {
        Dog otherDog = o;

        return this.size - otherDog.size; /* Shortcut for the comparison code */

        /*if (this.size < otherDog.size) {
            return -1;
        } else if (this.size == otherDog.size) {
            return 0;
        } else {
            return 1;
        } */
    }

    private static class DogComparatorByName implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }

    public static DogComparatorByName getComparator() {
        return new DogComparatorByName();
    }

    public void bark() {
        System.out.println(this.name);
    }
}

/** items[i] > items[maxDex] compilation error because > does not work
 * on every Object.
 * Java does not know how to compare objects? Could be by name length, a size variable, etc
 * One solution - create a function - maxDog that finds the max dog, but that would
 * mean writing a max function for all the types
 * o.size results in error because Object has no size variable - need to cast first
 * Comparator - provides a different way to order objects of a class
 *      with compareTo - comparing the this object to another (natural order)
 *       create a comparator to add different ways to order objects, compare any two objects
 *        have a comparator for name alphabetically, name by length, etc
 *   create a public function DogComparator to access the private comparator
 *   make the DogComparatorByName a static class because you don't need to instantiate
 *   a dog object to get the comparator. Notice the comparator does not use any of dog's
 *   variables or functions. Make private to follow convention
 *   Ties into subtype polymorphism
 *      a HOF approach
 *          compareDog(a, b, compareMethod) - just need to switch out compareMethod
 *       Subtype polymorphism
 *       compareDog(a, b) {
 *          a.compare(b) Change how comparison is made by changing the compare method within object a
 *                              or within a class/interface in object a's hierarchy
 *                          }      */
