package bearmaps.proj2ab;
import bearmaps.proj2ab.Trie;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestTrie {
    @Test
    public void testPrefix() {
        Trie t = new Trie();

        t.add("stand");
        t.add("goo");
        t.add("sir");
        t.add("stir");
        t.add("good");
        t.add("stack");

        ArrayList<String> result = new ArrayList<>();

        result.add("stand");
        result.add("stir");
        result.add("stack");

        ArrayList<String> actual = (ArrayList<String>)t.keysWithPrefix("st");

        assertTrue(result.size() == actual.size() && result.containsAll(actual));
    }

    @Test
    public void testPrefix2() {
        Trie t = new Trie();

        t.add("stand");
        t.add("goo");
        t.add("sir");
        t.add("stir");
        t.add("good");
        t.add("stack");
        t.add("st");

        ArrayList<String> result = new ArrayList<>();

        result.add("stand");
        result.add("stir");
        result.add("stack");
        result.add("st");

        ArrayList<String> actual = (ArrayList<String>)t.keysWithPrefix("st");

        assertTrue(result.size() == actual.size() && result.containsAll(actual));
    }
}
