import java.util.LinkedList;
/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    /** The string. **/
    LinkedList<Character> s;

    /** Sum part of the hash function **/
    int hashSum;

    // Consider using a linkedlist or queue

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        /* FIX ME */
        this.s = new LinkedList<>();
        this.hashSum = 0;
        int i = 0;
        for (Character c : s.toCharArray()) {
            this.s.addLast(c);
            this.hashSum = this.hashSum + ((int) c + (int) Math.pow(UNIQUECHARS, i));
            i = i + 1;
        }
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        /* FIX ME */
        char firstChar = this.s.removeFirst();
        this.hashSum = this.hashSum - (int) firstChar * (int) Math.pow(UNIQUECHARS, 0);
        this.s.addLast(c);
        this.hashSum = this.hashSum + (int) c * (int) Math.pow(UNIQUECHARS, this.s.size() - 1);
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        /* FIX ME */
        for (int i = 0; i < this.s.size(); i = i + 1) {
            strb.append(this.s.get(i));
        }

        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        /* FIX ME */
        return s.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        /* FIX ME */
        if (o.getClass() != this.getClass()) {
            return false;
        }
        String oString = (String) o;
        return this.s.equals(oString);
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        /* FIX ME */
        return this.hashSum % PRIMEBASE;
    }
}
