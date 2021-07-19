import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * Created by Jenny Huang on 3/12/19.
 */
public class TestMyTrieSet {

    // assumes add/contains work
    @Test
    public void sanityClearTest() {
        MyTrieSet t = new MyTrieSet();
        for (int i = 0; i < 455; i++) {
            t.add("hi" + i);
            //make sure put is working via contains
            assertTrue(t.contains("hi" + i));
        }
        t.clear();
        for (int i = 0; i < 455; i++) {
            assertFalse(t.contains("hi" + i));
        }
    }

    // assumes add works
    @Test
    public void sanityContainsTest() {
        MyTrieSet t = new MyTrieSet();
        assertFalse(t.contains("waterYouDoingHere"));
        t.add("waterYouDoingHere");
        assertTrue(t.contains("waterYouDoingHere"));
    }

    // assumes add works
    @Test
    public void sanityPrefixTest() {
        String[] saStrings = new String[]{"same", "sam", "sad", "sap"};
        String[] otherStrings = new String[]{"a", "awls", "hello"};

        MyTrieSet t = new MyTrieSet();
        for (String s: saStrings) {
            t.add(s);
        }
        for (String s: otherStrings) {
            t.add(s);
        }

        List<String> keys = t.keysWithPrefix("sa");
        for (String s: saStrings) {
            assertTrue(keys.contains(s));
        }
        for (String s: otherStrings) {
            assertFalse(keys.contains(s));
        }
    }

    @Test
    public void testAddAndContainsAndClear() {
        MyTrieSet t = new MyTrieSet();

        String[] s = new String[]{"ball", "bang", "ban", "bale", "angle"};

        for (String s1 : s) {
            t.add(s1);
        }

        for (String s1 : s) {
            assertTrue(t.contains(s1));
        }

        assertFalse(t.contains("all"));

        t.clear();

        assertFalse(t.contains("ball"));

        t.add("green");

        assertTrue(t.contains("green"));
    }

    @Test
    public void testKeysWithPrefix() {
        MyTrieSet t = new MyTrieSet();

        String[] words = new String[]{"circumnavigate", "circumscribe", "circum",
                "pear","circumvent", "circus"};

        String[] circumWords = new String[]{"circumnavigate", "circum","circumscribe", "circumvent"};

        for (String w : words) {
            t.add(w);
        }

        List<String> circumPrefix = t.keysWithPrefix("circum");

        assertFalse(circumPrefix.contains("circus"));

        for (String w : circumWords) {
            assertTrue(t.contains(w));
        }
    }

    @Test
    public void testLongPrefixBasic() {
        MyTrieSet t = new MyTrieSet();

        t.add("car");
        t.add("carpet");
        t.add("carpus");
        t.add("carpenter");

        assertEquals("car", t.longestPrefixOf("carp"));
        assertEquals("car", t.longestPrefixOf("carpe"));
    }

    @Test
    public void testLongestPrefix() {
        MyTrieSet t = new MyTrieSet();

        String[] words = new String[] {"water", "plant", "ca", "dog", "car", "pants"
                , "canada", "waterhole"};

        for (String w : words) {
            t.add(w);
        }

        String carpetPrefix = t.longestPrefixOf("carpet");

        assertEquals("car", carpetPrefix);

        String canadasPrefix = t.longestPrefixOf("canadas");

        assertEquals("canada", canadasPrefix);

        String wateringPrefix = t.longestPrefixOf("watering");

        assertEquals("water", wateringPrefix);

        t.add("waterin");
        assertEquals("waterin", t.longestPrefixOf("watering"));

        String calPrefix = t.longestPrefixOf("cal");

        assertEquals("ca", calPrefix);

        assertEquals("", t.longestPrefixOf("pant"));
    }

    @Test
    public void testKeysWithPrefixAndLongestPrefix() {
        MyTrieSet t = new MyTrieSet();

        String[] words = new String[]{"automobile", "automatic", "car", "walk",
                "approach", "autonomous", "authentic"};

        String[] autoWords = new String[]{"automobile", "automatic", "autonomous"};

        for (String w : words) {
            t.add(w);
        }

        List<String> wordsInTrie = t.keysWithPrefix("auto");

        for (String w : autoWords) {
            assertTrue(wordsInTrie.contains(w));
        }

        t.add("hello-to");

        wordsInTrie = t.keysWithPrefix("auto");

        assertFalse(wordsInTrie.contains("hello-to"));
        assertFalse(wordsInTrie.contains("hello"));

        wordsInTrie = t.keysWithPrefix("autom");

        assertTrue(wordsInTrie.contains("automobile"));
        assertTrue(wordsInTrie.contains("automatic"));
        assertFalse(wordsInTrie.contains("autonomous"));


        t.add("carp");

        assertEquals("", t.longestPrefixOf("autotune"));
        assertEquals("carp", t.longestPrefixOf("carpet"));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyTrieSet.class);
    }



}
