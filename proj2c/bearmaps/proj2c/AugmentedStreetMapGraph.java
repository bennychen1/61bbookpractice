package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.PointSet;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2ab.Trie;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    //super(dbPath) - populates nodes and neighbors instance variables from StreetMapGraph
        // nodes - each long like 318886222 is a vertex in the graph (key in the hashmap)
            // maps to a Node object that will have the lat, lon, and name if there is one
        // neighbors - long (vertex) as the key, then a set of edges to its neighbors

    // dbPath contains the vertices and their locations

    HashMap<Point, Node> nodeToPoint;
    PointSet k;
    Trie t;
    HashMap<String, String> cleanStringToActual;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // Own comment: separates the node from the vertices itself - maybe useful for the point set.
                // actually won't have nodes and neighbors from the StreetMapGraph since they are private
                // need to use the public get methods
        // You might find it helpful to uncomment the line below:
        this.nodeToPoint = new HashMap<>();
        List<Node> nodes = this.getNodes();
        this.cleanStringToActual = new HashMap<>();
        this.t = new Trie();

        for (Node n : nodes) {
            nodeToPoint.put(new Point(n.lon(), n.lat()), n);
            if (n.name() != null) {
                cleanStringToActual.put(cleanString(n.name()), n.name());
                t.add(cleanString(n.name()));
            }
        }

        ArrayList<Point> pointList = new ArrayList<>();

        for (Point p : nodeToPoint.keySet()) {
            Node n = this.nodeToPoint.get(p);
            List<WeightedEdge<Long>> adjacentVertices = this.neighbors(n.id());
            if (adjacentVertices.size() != 0) {
                pointList.add(p);
            }
        }

        this.k = new KDTree(pointList);

        //have a KDTree instance variable and put the points in a KDTree
        // closest would just be KDTree nearest - need to account for the nodes without neighbors
        // don't add nodes without neighbors to the tree


    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point p =  this.k.nearest(lon, lat);
        return this.nodeToPoint.get(p).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        ArrayList<String> prefixResults = (ArrayList<String>) this.t.keysWithPrefix(prefix);
        ArrayList<String> toReturn = new ArrayList<>();
        for (String s : prefixResults) {
            toReturn.add(this.cleanStringToActual.get(s));
        }
        return toReturn;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        return new LinkedList<>();
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
