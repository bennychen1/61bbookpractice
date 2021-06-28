import static org.junit.Assert.*;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/** Tests by Brendan Hu, Spring 2015, revised for 2016 by Josh Hug */
public class TestMyHashMap {

    @Test
    public void sanityGenericsTest() {
        try {
            MyHashMap<String, String> a = new MyHashMap<String, String>();
            MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
            MyHashMap<Integer, String> c = new MyHashMap<Integer, String>();
            MyHashMap<Boolean, Integer> e = new MyHashMap<Boolean, Integer>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
            //make sure put is working via containsKey and get
            assertTrue(null != b.get("hi" + i)
                        && b.containsKey("hi" + i)); 
        }
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        assertEquals(null, b.get("starChild"));
        b.put("starChild", 5);
        assertNotEquals(null, b.get("starChild"));
        b.put("KISS", 5);
        assertNotEquals(null, b.get("KISS"));
        assertNotEquals(null, b.get("starChild"));
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
        }
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi") && b.get("hi") != null);
    }

    /* 
    * Sanity test for keySet
    */
    @Test
    public void sanityKeySetTest() {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
        HashSet<String> values = new HashSet<String>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);   
            values.add("hi" + i);
        }
        assertEquals(455, b.size()); //keys are there
        Set<String> keySet = b.keySet();
        assertTrue(values.containsAll(keySet));
        assertTrue(keySet.containsAll(values));
    }

    /*
     * Test for general functionality and that the properties of Maps hold.
     */
    @Test
    public void functionalityTest() {
        MyHashMap<String, String> dictionary = new MyHashMap<>();
        assertEquals(0, dictionary.size());

        // can put objects in dictionary and get them
        dictionary.put("hello", "world");
        assertTrue(dictionary.containsKey("hello"));
        assertEquals("world", dictionary.get("hello"));
        assertEquals(1, dictionary.size());

        // putting with existing key updates the value
        dictionary.put("hello", "kevin");
        assertEquals(1, dictionary.size());
        assertEquals("kevin", dictionary.get("hello"));

        // putting key in multiple times does not affect behavior
        MyHashMap<String, Integer> studentIDs = new MyHashMap<String, Integer>();
        studentIDs.put("sarah", 12345);
        assertEquals(1, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("alan", 345);
        assertEquals(2, studentIDs.size());
        assertEquals(12345, studentIDs.get("sarah").intValue());
        assertEquals(345, studentIDs.get("alan").intValue());
        assertTrue(studentIDs.containsKey("sarah"));
        assertTrue(studentIDs.containsKey("alan"));

        // handle values being the same
        assertEquals(345, studentIDs.get("alan").intValue());
        studentIDs.put("evil alan", 345);
        assertEquals(345, studentIDs.get("evil alan").intValue());
        assertEquals(studentIDs.get("evil alan"), studentIDs.get("alan"));
    }

    @Test
    public void testPut() {
        MyHashMap<String, String> a = new MyHashMap<>();

        a.put("Color", "Green");
        a.put("Size", "Tall");

        assertEquals(2, a.size());
        assertEquals("Green", a.get("Color"));
    }

    @Test
    public void testBasic() {
        MyHashMap<String, String> a = new MyHashMap<>();

        a.put("Color", "Green");
        a.put("Size", "Tall");

        /* Don't need to worry about autoboxing because size returns int, not Integer. */
        assertEquals(2, a.size());

        assertFalse(a.containsKey("Weight"));
        assertTrue(a.containsKey("Size"));

        assertNull(a.get("Weight"));
        assertEquals("Tall", a.get("Size"));

        a.put("Size", "Medium");
        assertEquals("Medium", a.get("Size"));

        a.clear();

        assertEquals(0, a.size());
    }

    @Test
    public void testRemove() {
        MyHashMap<String, Integer> a = new MyHashMap<>();

        a.put("Color", 5);
        a.put("Weight", 100);
        a.put("Count", 10);

        Integer weightValue = a.remove("Weight");
        Integer heightValue = a.remove("Height");

        assertEquals(2, a.size());
        assertEquals(100, weightValue, 0);
        assertNull(a.get("Weight"));
        assertNull(heightValue);

        a.remove("Count");
        Integer colorValue = a.remove("Color");

        assertEquals(0, a.size());
        assertNull(a.get("Color"));
        assertEquals(5, colorValue, 0);
    }

    @Test
    public void testRemoveWithValue() {
        MyHashMap<String, Integer> a = new MyHashMap<>();

        a.put("Color", 5);
        a.put("Weight", 100);
        a.put("Count", 10);

        Integer weightValue = a.remove("Weight", 100);
        Integer colorValue = a.remove("Count", 2);

        assertEquals(2, a.size());
        assertEquals(100, weightValue, 0);
        assertNull(colorValue);
    }

    @Test
    public void testResizing() {
        MyHashMap<Integer, String> a = new MyHashMap<>(100, 0.75);

        for (int i = 0; i < 10000; i = i + 1) {
            a.put(i, "hi" + i);
        }

        assertEquals("hi100", a.get(100));
        assertEquals("hi0", a.get(0));
        assertEquals(10000,a.size());
        assertTrue(a.size() / a.getCurCapacity() < 0.75);
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestMyHashMap.class);
    }
}
