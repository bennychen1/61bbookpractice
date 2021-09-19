package bearmaps.test;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;

public class TestAugmentedStreetMapGraph {
    private static final String OSM_DB_PATH = "../library-sp19/data/proj2c_xml/berkeley-2019.osm.xml";
    private static final String OSM_DB_PATH_SMALL = "../library-sp19/data/proj2c_xml/berkeley-2019-small.osm.xml";
    private static final String OSM_DB_PATH_TINY = "../library-sp19/data/proj2c_xml/tiny-clean.osm.xml";
    private static final double minLat = 37.8228;
    private static final double maxLat = 37.8922;
    private static final double minLon = -122.2997999;
    private static final double maxLon = -122.2118997;

    @Test
    public void testClosest() {
        AugmentedStreetMapGraph a = new AugmentedStreetMapGraph(OSM_DB_PATH);

        long location1 = 318886232;
        double lon1 = -122.259583;
        double lat1 = 37.876139;
        assertEquals(location1, a.closest(lon1, lat1));

        double lon2 = -122.259584;
        assertEquals(location1, a.closest(lon2, lat1));

        long location2 = 1281866063;
        double lon3 = -122.26009;
        double lat3 = 37.875613;
        assertEquals(location2, a.closest(lon3, lat3));

        long location3 = 239669251;
        double lat4 = 37.87020191;
        double lon4 = -122.26590281;
        assertEquals(location3, a.closest(lon4, lat4));
    }

    // Speed - N is the number of points in the graph, not number of nearest calls
    @Test
    public void testSpeed() {

        String[] points = new String[]{OSM_DB_PATH_TINY, OSM_DB_PATH_SMALL, OSM_DB_PATH};
        double[] time = new double[points.length];


        Random rLat = new Random();
        Random rLon = new Random();
        double[][] latLon = new double[1000][2];

        for (int i = 0; i < latLon.length; i = i + 1) {
            double lon = minLon + (maxLon - minLon) * rLon.nextDouble();
            double lat = minLat + (maxLat - minLat) * rLat.nextDouble();

            latLon[i][0] = lon;
            latLon[i][1] = lat;
        }

        for (int i = 0; i < points.length; i = i + 1) {
            AugmentedStreetMapGraph a = new AugmentedStreetMapGraph(points[i]);
            Stopwatch s = new Stopwatch();
            for (int j = 0; j < latLon.length; j = j + 1) {
                a.closest(latLon[j][0], latLon[j][1]);
            }

            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));



    }

}
