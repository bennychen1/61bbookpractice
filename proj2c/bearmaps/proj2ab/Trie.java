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

    public Trie() {
        this.root = new Node(Character.MIN_VALUE, false);
    }

    public void add(String key) {
        Node curNode = this.root;

        // find where to start
        int curChar = 0;

        while (curChar < key.length() && curNode.links.containsKey(key.charAt(curChar))) {
            curNode = curNode.links.get(key.charAt(curChar));
            curChar = curChar + 1;
        }

        if (curChar == key.length()) {
            curNode.isKey = true;
            return;
        }

        // Add up to last character
        while (curChar < key.length() - 1) {
            curNode.links.put(key.charAt(curChar), new Node(key.charAt(curChar), false));
            curNode = curNode.links.get(key.charAt(curChar));
            curChar = curChar + 1;
        }

        // Add last character
        curNode.links.put(key.charAt(curChar), new Node(key.charAt(curChar), true));
    }

    // Handle length 0 in AugmentedStreetMapGraph
    public List<String> keysWithPrefix(String prefix) {
        int curChar = 0;
        Node curNode = this.root;
        // find where to start
        while (curChar < prefix.length() && curNode.links.containsKey(prefix.charAt(curChar))) {
            curNode = curNode.links.get(prefix.charAt(curChar));
            curChar = curChar + 1;
        }
        ArrayList<String> toReturn = new ArrayList<>();

        if (curChar != prefix.length()) {
            return toReturn;
        }

        if (curNode.isKey) {
            toReturn.add(prefix);
        }

        for (Character c : curNode.links.keySet()) {
            ArrayList<String> result = new ArrayList<>();
            toReturn.addAll(createString(prefix, curNode.links.get(c), result));
        }

        return toReturn;
    }

    private List<String> createString(String s, Node curNode,ArrayList<String> result) {
        if (curNode.isKey) {
            result.add(s + curNode.c);
        }

        for (Character c : curNode.links.keySet()) {
            createString(s + curNode.c, curNode.links.get(c), result);
        }
        return result;
    }
}
