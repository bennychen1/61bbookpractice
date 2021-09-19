package bearmaps.proj2ab;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Trie {

    Node root;

    private class Node {
        char c;
        boolean isKey;
        TreeMap<Character, Node> links;

        Node(char c, boolean isKey) {
            this.c = c;
            this.isKey = isKey;
            this.links = new TreeMap<>();
        }

        boolean hasChar(char c) {
            return links.containsKey(c);
        }
    }

    Trie() {
        this.root = new Node(Character.MIN_VALUE, false);
    }

    void add(String key) {
        Node curNode = this.root;

        // find where to start
        int curChar = 0;

        while (curChar < key.length() && curNode.links.containsKey(key.charAt(curChar))) {
            curChar = curChar + 1;
            curNode = this.root.links.get(key.charAt(curChar));
        }

        // Add up to last character
        while (curChar < key.length() - 1) {
            curNode.links.put(key.charAt(curChar), new Node(key.charAt(curChar), false));
            curChar = curChar + 1;
            curNode = curNode.links.get(key.charAt(curChar));
        }

        // Add last character
        curNode.links.put(key.charAt(curChar), new Node(key.charAt(curChar), true));
    }

    // Handle length 0 in AugmentedStreetMapGraph
    List<String> keysWithPrefix(String prefix) {
        int curChar = 0;
        Node curNode = this.root;
        // find where to start
        while (curChar < prefix.length() && curNode.links.containsKey(prefix.charAt(curChar))) {
            curChar = curChar + 1;
            curNode = curNode.links.get(prefix.charAt(curChar));
        }
        ArrayList<String> toReturn = new ArrayList<>();

        if (curChar != prefix.length()) {
            return toReturn;
        }

        if (curNode.isKey) {
            toReturn.add(prefix);
        }

        if (curChar == prefix.length()) {
            for (Character c : curNode.links.keySet()) {
                String s = createString();
                toReturn.add(s);
            }
        }

        return toReturn;
    }

    private String createString() {
        return null;
    }
}
