import static org.junit.Assert.*;

import edu.princeton.cs.algs4.BST;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

/** Tests by Brendan Hu, Spring 2015, revised for 2016 by Josh Hug */
public class TestBSTMap {

	@Test
    public void sanityGenericsTest() {
    	try {
    		BSTMap<String, String> a = new BSTMap<String, String>();
	    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
	    	BSTMap<Integer, String> c = new BSTMap<Integer, String>();
	    	BSTMap<Boolean, Integer> e = new BSTMap<Boolean, Integer>();
	    } catch (Exception e) {
	    	fail();
	    }
    }

    //assumes put/size/containsKey/get work
	@Test
    public void sanityClearTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1+i);
            //make sure put is working via containsKey and get
            assertTrue( null != b.get("hi" + i) && (b.get("hi"+i).equals(1+i))
                        && b.containsKey("hi" + i));
        }
        assertEquals(455, b.size());
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(null,b.get("starChild"));
        assertEquals(0, b.size());
        b.put("starChild", 5);
        assertTrue(((Integer) b.get("starChild")).equals(5));
        b.put("KISS", 5);
        assertTrue(((Integer) b.get("KISS")).equals(5));
        assertNotEquals(null,b.get("starChild"));
        assertEquals(2, b.size());
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++)
            b.put("hi" + i, 1);
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
    	BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi") && b.get("hi") != null);
    }

    @Test
    public void testContainsKey() {
	    BSTMap<String, Integer> b = new BSTMap<>("Apple", 10);
	    b.put("Grape", 5);
	    b.put("Orange", 11);

	    assertTrue(b.containsKey("Grape"));
	    assertFalse(b.containsKey("Banana"));
    }

    @Test
    public void testGet() {
	    BSTMap<String, String> b = new BSTMap<>();

	    b.put("Color", "Green");
	    b.put("Size", "Tall");
	    b.put("Shape", "Triangle");


	    assertEquals("Green", b.get("Color"));
	    assertEquals("Triangle", b.get("Shape"));
	    assertEquals(3, b.size());
	    assertNull(b.get("Weight"));

	    b.put("Shape", "Circle");

	    assertEquals("Circle", b.get("Shape"));
    }

    @Test
    public void testRemove() {
	    BSTMap<String, Integer> b = new BSTMap<>();

	    b.put("Apple", 5);
	    b.put("Lemon", 10);
	    b.put("Grape", 7);
	    b.put("Orange", 15);
	    b.put("Banana", 2);

	    b.remove("Lemon");
	    b.remove("Pear");

	    assertEquals(4, b.size());
	    assertFalse(b.containsKey("Lemon"));

	    /*
	    b.remove("Orange", 15);
	    b.remove("Grape", 1);

	    assertTrue(b.containsKey("Grape"));
	    assertFalse(b.containsKey("Orange"));
	    assertEquals(3, b.size());

	    Set<String> keys = new HashSet();

	    keys.add("Grape");
	    keys.add("Apple");
	    keys.add("Banana");

	    assertEquals(keys, b.keySet()); */

        BSTMap<String, Integer> oneNode = new BSTMap<>("Dog", 2);

        oneNode.remove("Dog");

        assertEquals(0, oneNode.size());
    }

    @Test
    public void testRemoveMultiple() {
	    BSTMap<String, Integer> b = new BSTMap<>();

	    b.put("B", 1);
	    b.put("A", 3);
	    b.put("L", 2);
	    b.put("O", 5);
	    b.put("G", 6);
	    b.put("C", 10);
	    b.put("H", 3);
	    b.put("P", 7);
	    b.put("M", 11);
	    b.put("E", 2);

	    b.remove("G");
	    b.remove("O");
	    b.remove("A");

	    List<String> keysInOrder = new ArrayList<>();

	    Iterator<String> keyIterator = b.iterator();

	    while (keyIterator.hasNext()) {
	        keysInOrder.add(keyIterator.next());
        }

	    assertEquals("B", keysInOrder.get(0));
	    assertEquals("C", keysInOrder.get(1));
	    assertEquals("M", keysInOrder.get(5));
    }

    @Test
    public void testKeySet() {
	    BSTMap<String, Integer> b = new BSTMap<>();

	    b.put("Apple", 5);
	    b.put("Carrot", 3);
	    b.put("Berry", 2);
	    b.put("Tomato", 9);

	    Set<String> s = b.keySet();

	    assertTrue(s.contains("Berry"));
	    assertTrue(s.contains("Apple"));
	    assertEquals(4, s.size());
    }

    @Test
    public void testIteration() {
	    BSTMap<String, Integer> b = new BSTMap<>();

	    b.put("Apple", 12);
	    b.put("Orange", 10);
	    b.put("Grape", 5);
	    b.put("Pear", 20);
	    b.put("Cranberry", 2);

        Iterator<String> bIterator = b.iterator();

        List<String> bKeys = new ArrayList<>();

        while (bIterator.hasNext()) {
            bKeys.add(bIterator.next());
        }

        assertEquals("Apple", bKeys.get(0));
        assertEquals("Grape", bKeys.get(2));
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestBSTMap.class);
    }
}
