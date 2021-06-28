public class WordUtils {
    public static String longestString(List61b<String> s) {
        String currentLongest = (String) s.getFirst();
        for (int i = 1; i < s.size(); i++) {
            String currentString = (String) s.get(i);
            if (currentString.length() > currentLongest.length()) {
                currentLongest = currentString;
            }
        }

        return currentLongest;
    }

    public static void main(String[] args) {
        SLList<String> s = new SLList<>();
        s.addFirst("Elephant");
        s.addFirst("Green");
        s.addLast("Blue");
        String longest = longestString(s);
        System.out.println(longest);

        AList<String> a = new AList<>();
        a.addLast("Elephant");
        a.addLast("Green");
        a.addFirst("Blue");
        System.out.println(longestString(a));
    }
}

/**
 * Because both Alist and SLList are a List61b, WordUtils will work for both types of List61bs.
 * */
