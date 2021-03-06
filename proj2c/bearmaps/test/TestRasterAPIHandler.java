package bearmaps.test;

import org.junit.Before;
import org.junit.Test;
import bearmaps.proj2c.server.handler.impl.RasterAPIHandler;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.StringJoiner;
import java.util.Arrays;

import static bearmaps.proj2c.utils.Constants.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Test of the rastering part of the assignment.*/
public class TestRasterAPIHandler {
    private static final double DOUBLE_THRESHOLD = 0.000000001;
    private static DecimalFormat df2 = new DecimalFormat(".#########");
    private static final String PARAMS_FILE = "../library-sp19/data/proj2c_test_inputs/raster_params.txt";
    private static final String RESULTS_FILE = "../library-sp19/data/proj2c_test_inputs/raster_results.txt";
    private static final int NUM_TESTS = 8;
    private static RasterAPIHandler rasterer;


    @Before
    public void setUp() throws Exception {
        rasterer = new RasterAPIHandler();
    }

    @Test
    public void testFromHTML() {
        Map<String, Double> requestParams = new HashMap<>();

        requestParams.put("lrlon", -122.20908713544797);
        requestParams.put("ullon", -122.3027284165759);
        requestParams.put("lrlat", 37.848731523430196);
        requestParams.put("ullat", 37.88708748276975);
        requestParams.put("w", 305.0);
        requestParams.put("height", 300.0);

        double raster_ul_lon=-122.2998046875;
        int depth=1;
        double raster_lr_lon=-122.2119140625;
        double raster_lr_lat=37.82280243352756;
        String[][]render_grid= new String[2][2];
        render_grid[0][0] = "d1_x0_y0.png";
        render_grid[0][1] = "d1_x1_y0.png";
        render_grid[1][0] = "d1_x0_y1.png";
        render_grid[1][1] = "d1_x1_y1.png";
        double raster_ul_lat=37.892195547244356;
        boolean query_success=true;

        Map<String, Object> expectedResults = new HashMap<>();

        expectedResults.put("raster_ul_lon", raster_ul_lon);
        expectedResults.put("depth", depth);
        expectedResults.put("raster_ul_lat", raster_ul_lat);
        expectedResults.put("raster_lr_lat", raster_lr_lat);
        expectedResults.put("raster_lr_lon", raster_lr_lon);
        expectedResults.put("render_grid", render_grid);
        expectedResults.put("query_success", query_success);

        String msg = "Your results did not match the expected results for input "
                + mapToString(requestParams) + ".\n";

        Map<String, Object> actual = rasterer.processRequest(requestParams, null);

        checkParamsMap(msg, expectedResults, actual);
    }

    @Test
    public void testProcessRequests() throws Exception {
        List<Map<String, Double>> testParams = paramsFromFile();
        List<Map<String, Object>> expectedResults = resultsFromFile();

        for (int i = 0; i < NUM_TESTS; i++) {
            System.out.println(String.format("Running test: %d", i));
            Map<String, Double> params = testParams.get(i);
            Map<String, Object> actual = rasterer.processRequest(params, null);
            Map<String, Object> expected = expectedResults.get(i);
            String msg = "Your results did not match the expected results for input "
                         + mapToString(params) + ".\n";
            checkParamsMap(msg, expected, actual);
        }
    }

    @Test
    public void testCornerCases() throws Exception {
        double ullon = -122.4;
        double ullat = 37.877266154010954;
        double lrlon = -122.22275132672245;
        double lrlat = 37.85829260830337;
        double w = 613.0;
        double h = 676.0;

        Map<String, Double> requestParams = new HashMap<>();
        requestParams.put("ullon", ullon);
        requestParams.put("ullat", ullat);
        requestParams.put("lrlon", lrlon);
        requestParams.put("lrlat", lrlat);
        requestParams.put("w", w);
        requestParams.put("h", h);

        Map<String, Object> results = rasterer.processRequest(requestParams, null);

        // Should start at left edge
        assertEquals(ROOT_ULLON, results.get("raster_ul_lon"));

        requestParams.put("ullon", -122.3125);
        requestParams.put("lrlon", -121.0562);

        results = rasterer.processRequest(requestParams, null);

        // Should end at right edge
        assertEquals(ROOT_LRLON, results.get("raster_lr_lon"));

        requestParams.put("ullat", 38.2);
        requestParams.put("lrlat", 36.2);

        results = rasterer.processRequest(requestParams, null);

        assertEquals(ROOT_ULLAT, results.get("raster_ul_lat"));
        assertEquals(ROOT_LRLAT, results.get("raster_lr_lat"));

        requestParams.put("lrlon", -122.2930);
        requestParams.put("ullon", -122.4555);
        requestParams.put("ullat", 37.8712);
        requestParams.put("lrlat", 37.8362);
        requestParams.put("w", 325.0);

        results = rasterer.processRequest(requestParams, null);

        assertEquals(0, results.get("depth"));
        assertArrayEquals(new String[][]{{"d0_x0_y0.png"}}, (String[][]) results.get("render_grid"));

        requestParams.put("w", 100.0);
        requestParams.put("lrlon", -122.23);
        requestParams.put("ullon", -122.28);

        results = rasterer.processRequest(requestParams, null);
        assertEquals(0, results.get("depth"));
        assertArrayEquals(new String[][]{{"d0_x0_y0.png"}}, (String[][]) results.get("render_grid"));

    }

    @Test
    public void testCorrectDepth() {
        double lrlon = -122.2341865897522;
        double ullon = -122.2775740027771;
        double lrlat = 37.8489039094583;
        double ullat = 37.87116527455201;
        double w = 1011.0;
        double h = 657.0;

        HashMap<String, Double> request = new HashMap<>();

        request.put("lrlon", lrlon);
        request.put("ullon", ullon);
        request.put("lrlat", lrlat);
        request.put("ullat", ullat);
        request.put("w", w);
        request.put("h", h);


        Map<String, Object> results = rasterer.processRequest(request, null);
        assertEquals(3, results.get("depth"));

    }

    private List<Map<String, Double>> paramsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(PARAMS_FILE), Charset.defaultCharset());
        List<Map<String, Double>> testParams = new ArrayList<>();
        int lineIdx = 2; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Double> params = new HashMap<>();
            params.put("ullon", Double.parseDouble(lines.get(lineIdx)));
            params.put("ullat", Double.parseDouble(lines.get(lineIdx + 1)));
            params.put("lrlon", Double.parseDouble(lines.get(lineIdx + 2)));
            params.put("lrlat", Double.parseDouble(lines.get(lineIdx + 3)));
            params.put("w", Double.parseDouble(lines.get(lineIdx + 4)));
            params.put("h", Double.parseDouble(lines.get(lineIdx + 5)));
            testParams.add(params);
            lineIdx += 6;
        }
        return testParams;
    }

    private List<Map<String, Object>> resultsFromFile() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(RESULTS_FILE), Charset.defaultCharset());
        List<Map<String, Object>> expected = new ArrayList<>();
        int lineIdx = 4; // ignore comment lines
        for (int i = 0; i < NUM_TESTS; i++) {
            Map<String, Object> results = new HashMap<>();
            results.put("raster_ul_lon", Double.parseDouble(lines.get(lineIdx)));
            results.put("raster_ul_lat", Double.parseDouble(lines.get(lineIdx + 1)));
            results.put("raster_lr_lon", Double.parseDouble(lines.get(lineIdx + 2)));
            results.put("raster_lr_lat", Double.parseDouble(lines.get(lineIdx + 3)));
            results.put("depth", Integer.parseInt(lines.get(lineIdx + 4)));
            results.put("query_success", Boolean.parseBoolean(lines.get(lineIdx + 5)));
            lineIdx += 6;
            String[] dimensions = lines.get(lineIdx).split(" ");
            int rows = Integer.parseInt(dimensions[0]);
            int cols = Integer.parseInt(dimensions[1]);
            lineIdx += 1;
            String[][] grid = new String[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    grid[r][c] = lines.get(lineIdx);
                    lineIdx++;
                }
            }
            results.put("render_grid", grid);
            expected.add(results);
        }
        return expected;
    }

    private void checkParamsMap(String err, Map<String, Object> expected,
                                            Map<String, Object> actual) {
        for (String key : expected.keySet()) {
            assertTrue(err + "Your results map is missing "
                       + key, actual.containsKey(key));
            Object o1 = expected.get(key);
            Object o2 = actual.get(key);

            if (o1 instanceof Double) {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertTrue(errMsg, Math.abs((Double) o1 - (Double) o2) < DOUBLE_THRESHOLD);
            } else if (o1 instanceof String[][]) {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertArrayEquals(errMsg, (String[][]) o1, (String[][]) o2);
            } else {
                String errMsg = genDiffErrMsg(err, expected, actual);
                assertEquals(errMsg, o1, o2);
            }
        }
    }

    /** Generates an actual/expected message from a base message, an actual map,
     *  and an expected map.
     */
    private String genDiffErrMsg(String basemsg, Map<String, Object> expected,
                                 Map<String, Object> actual) {
        return basemsg + "Expected: " + mapToString(expected) + ", but got\n"
                       + "Actual  : " + mapToString(actual);
    }

    /** Converts a Rasterer input or output map to its string representation. */
    private String mapToString(Map<String, ?> m) {
        StringJoiner sj = new StringJoiner(", ", "{", "}");

        List<String> keys = new ArrayList<>();
        keys.addAll(m.keySet());
        Collections.sort(keys);

        for (String k : keys) {

            StringBuilder sb = new StringBuilder();
            sb.append(k);
            sb.append("=");
            Object v = m.get(k);

            if (v instanceof String[][]) {
                sb.append(Arrays.deepToString((String[][]) v));
            } else if (v instanceof Double) {
                sb.append(df2.format(v));
            } else {
                sb.append(v.toString());
            }
            String thisEntry = sb.toString();

            sj.add(thisEntry);
        }

        return sj.toString();
    }

}
