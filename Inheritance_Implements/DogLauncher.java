import java.util.Comparator;
public class DogLauncher {
    public static void main(String[] args) {
        Dog d1 = new Dog("Elyse", 3);
        Dog d2 = new Dog("Sture", 9);
        Dog d3 = new Dog("Benjamin", 15);
        Dog[] dogs = new Dog[]{d1, d2, d3};

        Dog d = (Dog) Maximizer.max(dogs);
        System.out.println(d.name);

        /*
        Dog.DogComparatorByName dc = new Dog.DogComparatorByName();
        if (dc.compare(d1, d2) < 0) {
            d1.bark();
        } else {
            d2.bark();
        } Before making DogComparatorByName private*/

        /*
        Dog.DogComparatorByName dc = new Dog.DogComparatorByName();
        if (dc.compare(d1, d2) < 0) {
            d1.bark();
        } else {
            d2.bark();
        } Because DogComparatorByName is private, need a container for DogComparatorByName
            DogComparatorByName is a Comparator*/

        Comparator<Dog> dc = Dog.getComparator();
        if (dc.compare(d1, d2) < 0) {
            d1.bark();
        } else {
            d2.bark();
        }

        }
}

