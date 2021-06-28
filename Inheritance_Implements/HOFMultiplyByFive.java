public class HOFMultiplyByFive {
    public static int doTwice(IntUnaryFunction f, int x) {
        return f.apply(f.apply(x));
    }

    public static void main(String[] args) {
        MultiplyByFive f = new MultiplyByFive();
        System.out.println(doTwice(f, 5));
    }
}

/** Use interface inheritance to write a function that takes
 * in a function as an argument to get the effect of f(f(x))*/
