import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class ADT_Exercise {

    public static String cleanString(String s) {
        return s.toLowerCase().replaceAll("[^a-z]", "");
    }

    public static List<String> getWords(String inputFileName) {
        List<String> toReturn = new ArrayList<>();
        try {
            Scanner s = new Scanner(new FileReader(inputFileName));
            while (s.hasNext()) {
                toReturn.add(cleanString(s.next()));
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return toReturn;
    }

    public static int countUniqueWords(List<String> s) {
        Set<String> unique = new HashSet<>(s);

        /*for (String i : s) {
            unique.add(i);
        } */



        return unique.size();
    }

    public static HashMap<String, Integer> collectWordCount(List<String> targets, List<String> words) {
        HashMap<String, Integer> hm = new HashMap<>();
        for (String s : targets) {
            hm.put(s, 0);
        }

        for (String currentWord : words) {
            if (hm.containsKey(currentWord)) {
                hm.put(currentWord, hm.get(currentWord) + 1);
            }
        }

        return hm;
    }

    public static void main(String[] args) {
        List<String> textFile = getWords("Scanner.txt");

        System.out.print(textFile);

        System.out.println(countUniqueWords(textFile));


        List<String> t = new ArrayList<>();
        t.add("polar");
        t.add("bear");

        HashMap<String, Integer> h = collectWordCount(t, textFile);

        for (String k : h.keySet()) {
            System.out.println(k + " " + h.get(k));
        }

    }
}

/** Scanner: reads by word, space " " is the delimiter
 * ADT:
 *  Co     llect     ions (ADT)
 *  |          |        |
 * Lists       Sets     Maps (interfaces) (ADT)
 *  |             |        |
 *  ArrayLists   Hashsets  HashMaps (implementations)
 *  Interface - variables are all public static final
 *  Abstract classes - can have abstract or concrete methods
 *      default is concrete - equivalent of default in an interface
 *      variables and methods can be any type (public or private)
 *      abstract methods must be implemented by subclasses
 *      subclasses can only extend one subclass
 *  Importing - python importing functions/classes
 *      java - import java.io.FileReader - telling computer FileReader is the one in io package, not
 *          elsewhere.
 *      want to avoid doing import package.* - could have other packages with the same method name*/
