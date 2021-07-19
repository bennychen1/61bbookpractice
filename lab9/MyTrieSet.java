import java.sql.Array;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class MyTrieSet implements TrieSet61B {

    /** Root of the trie that is a null node. */
    Node root;


    public MyTrieSet() {
        this.root = new Node(Character.MIN_VALUE, false);
    }

    private class Node {

         private char c;

         private TreeMap<Character, Node> links;

        /** If true, then this node and every node above it form a key. **/
        private boolean isKey;

        private Node(char c, boolean isKey) {
            this.c = c;
            this.isKey = isKey;
            this.links = new TreeMap<>();
        }

        private boolean hasChar(char c) {
            return links.containsKey(c);
        }




    }

    @Override
    /** Clears all items out of Trie */
    public void clear() {
        this.root = new Node(Character.MIN_VALUE, false);
    }

    @Override
    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {

        Node curNode = this.root;

        for (int i = 0; i < key.length(); i = i + 1) {
            char curChar = key.charAt(i);
            if (curNode.links.containsKey(curChar)) {
                curNode = curNode.links.get(curChar);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    /** Inserts string KEY into Trie */
    public void add(String key){

        int track = 0;

        Node curNode = this.root;

        /* Iterate through the key and the trie until there's a character that's not in the trie.
        * adding "green" and the trie has "gree", then the loop will stop at the second "e" node.  */
        while (track < key.length() && curNode.hasChar(key.charAt(track))) {
            curNode = curNode.links.get(key.charAt(track));
            track = track + 1;
        }

        for (int i = track; i < key.length(); i = i + 1) {
            char curChar = key.charAt(i);

            boolean isKey;

            if (i == key.length() - 1) {
                if (curChar == curNode.c) {
                    curNode.isKey = true;
                }
                isKey = true;
            } else {
                isKey = false;
            }

            curNode.links.put(curChar, new Node(curChar, isKey));
            curNode = curNode.links.get(curChar);
        }

    }

    @Override
    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix){
        /* Find the node that is the last character of PREFIX */
        Node curNode = this.root;
        for (int i = 0; i <prefix.length(); i = i + 1) {
            char curChar = prefix.charAt(i);
            curNode = curNode.links.get(curChar);

            if (curNode == null) {
                return new ArrayList<String>();
            }
        }

        ArrayList<String> toReturn = new ArrayList<>();

        if (curNode.isKey) {
            toReturn.add(prefix);
        }

        for (Map.Entry<Character, Node> entry : curNode.links.entrySet()) {
            keysWithPrefixHelper(prefix, toReturn, entry.getValue());
        }

        return toReturn;
    }

    private void keysWithPrefixHelper(String prefix, List<String> result, Node n) {
        if (n.isKey) {
            result.add(prefix + n.c);
        }

        for (Map.Entry<Character, Node> entry : n.links.entrySet()) {
            keysWithPrefixHelper(prefix + n.c, result, entry.getValue());
        }
    }

    @Override
    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key){
        if(this.root.links.get(key.charAt(0)) == null) {
            return "";
        }

        String best = "";
        Node curNode = this.root;
        int track = 0;
        char curChar = key.charAt(track);
        String seenChars = "";

        while (track < key.length() && curNode.links.containsKey(curChar)) {
            curNode = curNode.links.get(curChar);
            seenChars = seenChars + curNode.c;
            if (curNode.isKey) {
                best = seenChars;
            }

            track = track + 1;

            if (track < key.length()) {
                curChar = key.charAt(track);
            }
        }

        return best;





    }
}
