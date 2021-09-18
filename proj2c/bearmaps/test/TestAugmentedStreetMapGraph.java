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

    @Test
    public void testSpeed() {

        AugmentedStreetMapGraph a = new AugmentedStreetMapGraph(OSM_DB_PATH);

        Random rLat = new Random();
        Random rLon = new Random();

        int[] numPoints = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] time = new double[numPoints.length];

        for (int i = 0; i < numPoints.length; i = i + 1) {
            int numRandomPoints = numPoints[i];
            double[][] latLon = new double[numRandomPoints][2];
            for (int j = 0; j < numRandomPoints; j = j + 1) {
                double lon = minLon + (maxLon - minLon) * rLon.nextDouble();
                double lat = minLat + (maxLat - minLat) * rLat.nextDouble();

                latLon[j][0] = lon;
                latLon[j][1] = lat;
            }

            Stopwatch s = new Stopwatch();
            for (int k = 0; k < numRandomPoints; k = k + 1) {
                a.closest(latLon[k][0], latLon[k][1]);
            }

            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));



    }

}
