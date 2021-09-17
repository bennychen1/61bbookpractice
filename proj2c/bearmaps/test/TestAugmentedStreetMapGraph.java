package bearmaps.test;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAugmentedStreetMapGraph {
    private static final String OSM_DB_PATH = "../library-sp19/data/proj2c_xml/berkeley-2019.osm.xml";

    @Test
    public static void testClosest() {
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


    }

}
