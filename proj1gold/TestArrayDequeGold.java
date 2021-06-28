import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
public class TestArrayDequeGold {

    @Test
    public void test1() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution();
        StudentArrayDeque<Integer> stu = new StudentArrayDeque<>();
        ArrayList<String> methodCalls = new ArrayList<>();


        for (int i = 0; i < 10; i = i + 1) {
            double randomNum = StdRandom.uniform();

            if (randomNum < 0.5) {
                ads.addFirst(i);
                stu.addFirst(i);
                methodCalls.add("addFirst(" + i + ")");
            } else {
                ads.addLast(i);
                stu.addLast(i);
                methodCalls.add("addFirst("+ i+ ")");
            }
        }



        while (true) {
            if (ads.size() == 0 || stu.size() == 0) {
                return;
            }
            double whichFunction = StdRandom.uniform();
            if (whichFunction <= 0.33) {
                methodCalls.add("removeFirst()");
                assertEquals(methodCalls.toString(), ads.removeFirst(), stu.removeFirst());
            } else if (whichFunction > 0.33 && whichFunction < 0.66) {
                methodCalls.add("removeLast()");
                assertEquals(methodCalls.toString(), ads.removeLast(), stu.removeLast());
            } else {
                int getIndex = StdRandom.uniform(stu.size());
                methodCalls.add("getIndex(" + getIndex + ")");
                assertEquals(methodCalls.toString(), ads.get(getIndex), stu.get(getIndex));
            }

        }
    }
}
