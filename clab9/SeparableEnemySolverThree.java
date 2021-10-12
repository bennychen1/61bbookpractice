import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SeparableEnemySolverThree {

        Graph g;


        /**
         * Creates a SeparableEnemySolver for a file with name filename. Enemy
         * relationships are biderectional (if A is an enemy of B, B is an enemy of A).
         */
        SeparableEnemySolverThree(String filename) throws java.io.FileNotFoundException {
            this.g = graphFromFile(filename);
        }

        /** Alterntive constructor that requires a Graph object. */
        SeparableEnemySolverThree(Graph g) {
            this.g = g;
        }

        /**
         * Returns true if input is separable, false otherwise.
         */
        public boolean isSeparable() {

            HashMap<String, Integer> marking = new HashMap<>();
            HashMap<String, HashSet<Integer>> possibleMarking = new HashMap<>();
            HashSet<String> nodes = new HashSet<>();
            HashSet<Integer> markingLabels = new HashSet<>();
            HashMap<Integer, Integer> enemyMarkings = new HashMap<>();

            enemyMarkings.put(1, 2);
            enemyMarkings.put(2, 3);
            enemyMarkings.put(3, 1);

            markingLabels.add(1);
            markingLabels.add(2);
            markingLabels.add(3);


            for (String s : this.g.labels()) {
                marking.put(s, 0);
                possibleMarking.put(s, markingLabels);
                nodes.add(s);
            }

            while (nodes.size() > 0) {
                Iterator<String> nodesIterator = nodes.iterator();
                String startNode = nodesIterator.next();

                LinkedList<String> fringe = new LinkedList<>();

                fringe.addLast(startNode);

                marking.put(startNode, 1);

                while (fringe.size() > 0) {
                    String curNode = fringe.pop();
                    nodes.remove(curNode);
                    int curNodeMark = marking.get(curNode);
                    int markNeighbors = enemyMarkings.get(curNodeMark);

                    for (String enemy : this.g.neighbors(curNode)) {
                        if (nodes.contains(enemy)) {
                            int enemyMarking = marking.get(enemy);
                            HashSet<Integer> curPossibleMarkings = possibleMarking.get(enemy);
                            if (enemyMarking == 0) {
                                marking.put(enemy, markNeighbors);
                                curPossibleMarkings.remove(curNodeMark);
                                curPossibleMarkings.remove(markNeighbors);
                            } else if (enemyMarking == curNodeMark) {
                                if (curPossibleMarkings.size() == 0) {
                                    return false;
                                } else {
                                    int updatedMarking = curPossibleMarkings.iterator().next();
                                    marking.put(enemy, updatedMarking);
                                    curPossibleMarkings.remove(updatedMarking);
                                }
                            }

                            fringe.add(enemy);
                        }
                    }
                }
            }



            return true;
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
