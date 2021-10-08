import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolver {

    Graph g;


    /**
     * Creates a SeparableEnemySolver for a file with name filename. Enemy
     * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
     */
    SeparableEnemySolver(String filename) throws java.io.FileNotFoundException {
        this.g = graphFromFile(filename);
    }

    /** Alterntive constructor that requires a Graph object. */
    SeparableEnemySolver(Graph g) {
        this.g = g;
    }

    /**
     * Returns true if input is separable, false otherwise.
     */
    public boolean isSeparable() {

        // when do you mark a node? when it is all checked?
            // have a 1 indicate all checked, have 3 mean regular mark?

        // list out all the nodes

        // pick a random node to start, add it to an already checked list

        // add the node's enemies (neighbors) to a list

        // if any of the neighbors are already marked AND that neighbor is not already checked, then return false

        // repeat the process with one of the neighbor nodes

        /** A mark of 0 for S means the node S has not been seen yet.
         * 1 means node S belongs to group 1
         * 2 means nodes S belongs to 2
        */
        HashMap<String, Integer> marks = new HashMap<>();
        HashSet<String> nodes = new HashSet<>(this.g.labels());
        HashMap<Integer, Integer> grouping = new HashMap<>();

        grouping.put(1, 2);
        grouping.put(2, 1);

        for (String s : this.g.labels()) {
            marks.put(s, 0);
        }

        while (nodes.size() != 0) {
            LinkedList<String> fringe = new LinkedList<>();

            Iterator<String> nodesIterator = nodes.iterator();
            String initialNode = nodesIterator.next();

            fringe.add(initialNode);
            marks.put(initialNode, 1);

            while (fringe.size() != 0) {
                String ally = fringe.poll();
                nodes.remove(ally);

                for (String enemy : this.g.neighbors(ally)) {
                    if(marks.get(enemy) == 0) {
                        fringe.addLast(enemy);
                        marks.put(enemy, grouping.get(marks.get(ally)));
                    } else {
                        if (marks.get(enemy) == marks.get(ally)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /** Runs dfs from vertex v. Returns true if cycle is detected **/
    private boolean dfs(String v, String from, HashSet<String> marked, HashSet<String> unmarked) {
        for (String n : this.g.neighbors(v)) {
            if (marked.contains(n)) {
                if (!n.equals(from)) {
                    return true;
                }
            } else {
                marked.add(n);
                unmarked.remove(n);
                return dfs(n, from, marked, unmarked);
            }
        }

        return false;
    }


    /* HELPERS FOR READING IN CSV FILES. */

    /**
     * Creates graph from filename. File should be comma-separated. The first line
     * contains comma-separated names of all people. Subsequent lines each have two
     * comma-separated names of enemy pairs.
     */
    private Graph graphFromFile(String filename) throws FileNotFoundException {
        List<List<String>> lines = readCSV(filename);
        Graph input = new Graph();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                for (String name : lines.get(i)) {
                    input.addNode(name);
                }
                continue;
            }
            assert(lines.get(i).size() == 2);
            input.connect(lines.get(i).get(0), lines.get(i).get(1));
        }
        return input;
    }

    /**
     * Reads an entire CSV and returns a List of Lists. Each inner
     * List represents a line of the CSV with each comma-seperated
     * value as an entry. Assumes CSV file does not contain commas
     * except as separators.
     * Returns null if invalid filename.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<List<String>> readCSV(String filename) throws java.io.FileNotFoundException {
        List<List<String>> records = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            records.add(getRecordFromLine(scanner.nextLine()));
        }
        return records;
    }

    /**
     * Reads one line of a CSV.
     *
     * @source https://www.baeldung.com/java-csv-file-array
     */
    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        Scanner rowScanner = new Scanner(line);
        rowScanner.useDelimiter(",");
        while (rowScanner.hasNext()) {
            values.add(rowScanner.next().trim());
        }
        return values;
    }

    /* END HELPERS  FOR READING IN CSV FILES. */

}
