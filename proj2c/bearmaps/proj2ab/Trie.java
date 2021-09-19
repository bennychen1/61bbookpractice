package bearmaps.proj2ab;
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

        while (curNode.links.containsKey(key.charAt(curChar)) && curChar < key.length()) {
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
}
