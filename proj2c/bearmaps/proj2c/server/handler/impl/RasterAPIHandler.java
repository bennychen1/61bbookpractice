package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bearmaps.proj2c.utils.Constants.*;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }

    /**
     * The lonDpp of each depth D. Index 0 is the lonDPP of depth 0, 1 is lonDPP of depth 1, etc.
     * **/
    private double[] depthLonDPP = {lonDPP(0), lonDPP(1), lonDPP(2), lonDPP(3)
            , lonDPP(4), lonDPP(5), lonDPP(6), lonDPP(7)};

    /**
     * Find the midpoint longitude of depth D
     * **/
    private double midPoint(int D) {
        if (D == 0) {
            return (ROOT_ULLON + ROOT_LRLON) / 2;
        }

        return (ROOT_ULLON + midPoint(D - 1)) / 2;
    }

    /**
     * The lonDPP of depth D.
     * */
    private double lonDPP(int D) {
        if (D == 0) {
            return (ROOT_LRLON - ROOT_ULLON) / 256;
        }
        return (midPoint(D - 1) - ROOT_ULLON) / 256;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        //System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        //System.out.println(requestParams);
        Map<String, Object> results = new HashMap<>();
        /*System.out.println("Since you haven't implemented RasterAPIHandler.processRequest, nothing is displayed in "
                + "your browser."); */
        /* Compute LDPP of the query box from request params.
        How is requestParams guaranteed to have the necessary params? done at Line 33 APIRouteHandler
        Check the length of requestParams to make sure it has everything? Use parseRequestParams at top
        i = longitude (x coordinate)
        j = latitude (y coordinate)
        In 2-D array, y constant in a given array - so j determines which array
        */

        double lrlon = requestParams.get("lrlon");
        double ullon = requestParams.get("ullon");
        double lrlat = requestParams.get("lrlat");
        double ullat = requestParams.get("ullat");
        double w = requestParams.get("w");

        // Query_success is false if the WHOLE requested box is outside of the ROOT rectangle
            // ULLON is east of ROOT LRLON OR LRLON is West of ROOT ULLON OR
                // ULLAT is south of ROOT LRLAT or LRLAT is north of ROOT ULLAT
        // Can have partial result like so:
            // If requested lrlon is east of ROOT LRLon, then j should stop at sqrt(numTiles) - 1
                // 64 squares, 0-7 tile number, then j should stop at 7
            // If requested ullon is west of ROOT ULLON, then j should start at 0.
            // If request ullat north of ROOT ULat, i should start at 0
            // If request lrlat south of ROOT LRLAT, i should stop at sqrt(numTiles) - 1
        // Why raster  lr lat does not match up

        boolean lonOffMap = ullon > ROOT_LRLON || lrlon < ROOT_ULLON;
        boolean latOffMap = ullat < ROOT_LRLAT || lrlat > ROOT_ULLAT;
        boolean invalidRequestBox = ullat < lrlat || ullon > lrlon;

        if (lonOffMap || latOffMap || invalidRequestBox) {
            results.put("render_grid", new String[][]{{"d0_x0_y0.png"}});
            results.put("raster_ul_lon", ROOT_ULLON);
            results.put("raster_lr_lon", ROOT_LRLON);
            results.put("raster_ul_lat", ROOT_ULLAT);
            results.put("raster_lr_lat", ROOT_LRLAT);
            results.put("depth", 1);
            results.put("query_success", false);
            return results;
        }

        results.put("query_success", true);

        double requestlondpp = (lrlon - ullon) / w;

        // Find the right depth - the greatest depth LonDPP that is less than request LonDPP
        // LonDPP that is just under request lonDPP
        int depth = 0;

        while (requestlondpp < depthLonDPP[depth]) { // stop once depth Lon DPP is < request LonDPP
            if (depth == 7) {
                break;
            }
            depth = depth + 1;
        }

        results.put("depth", depth);

        int totalNumTiles = ((Double) Math.pow(4, depth)).intValue();



        // Each tile will cover the total lon/lat divided by the number of tiles
        // Invariant: |LRLON| > |ULLON|, ULLAT > LRLAT
        // If left lat to right lat goes from 0 to 1, and there are 10x10 tiles, then each tile = 0.1 lat
        double eachTileLon = (ROOT_LRLON - ROOT_ULLON) / Math.sqrt(totalNumTiles);
        double eachTileLat = (ROOT_ULLAT - ROOT_LRLAT) / Math.sqrt(totalNumTiles);

        // Longitude is x-cooridnate, determine j (index within an array)
        // Latitiude is y-coordinate, determine i (which array from 2d array)
        //How many tiles to get to the requested lon and lat from the root if each tile covers for example
        // 0.1 lat and 0.1 lon.
        //Example: depth has 10 tiles. User request starts at lon 0.25 then I need to get to tile 2
                // (0.25 - 0) / 0.1 = 2
        Double startTileIndexIDouble = Math.abs(ROOT_ULLON - ullon) / eachTileLon;
        Double startTileIndexJDouble = Math.abs(ROOT_ULLAT - ullat) / eachTileLat;
        Double endTileIndexIDouble = Math.abs(ROOT_ULLON - lrlon) / eachTileLon;
        Double endTileIndexJDouble = Math.abs(ROOT_ULLAT - lrlat) / eachTileLat;


        //Check if starts or ends exceed ROOT boundaries
        if (ullon < ROOT_ULLON) {
            startTileIndexIDouble = 0.0;
        }

        if (lrlon > ROOT_LRLON) {
            endTileIndexIDouble = Math.sqrt(totalNumTiles) - 1;
        }

        if (ullat > ROOT_ULLAT) {
            startTileIndexJDouble = 0.0;
        }

        if (lrlat < ROOT_LRLAT) {
            endTileIndexJDouble = Math.sqrt(totalNumTiles) - 1;
        }


        int startTileIndexJ = startTileIndexJDouble.intValue(); //Tile number
        int startTileIndexI = startTileIndexIDouble.intValue();
        int endTileIndexJ = endTileIndexJDouble.intValue();
        int endTileIndexI = endTileIndexIDouble.intValue();

        // Raster ul and lr lon and lat.
        // If I start at tile 2, then lon is 0.2 (2 * 0.1)
        // If I stop at tile 3, then lon is 0.4 (0.1 * (3+1))
        double rasterullon = startTileIndexI * eachTileLon + ROOT_ULLON;
        double rasterullat = startTileIndexJ * eachTileLat * -1 + ROOT_ULLAT;
        double rasterlrlon = (endTileIndexI + 1) * eachTileLon + ROOT_ULLON;
        double rasterlrlat = (endTileIndexJ + 1) * eachTileLat * -1 + ROOT_ULLAT;

        results.put("raster_ul_lon", rasterullon);
        results.put("raster_ul_lat", rasterullat);
        results.put("raster_lr_lon", rasterlrlon);
        results.put("raster_lr_lat", rasterlrlat);

        // If starting tile lon-wise is 1, and end tile is 3, then there should be 3 -2 + 1 = 3 tiles (1, 2, 3)
        String[][] images = new String[endTileIndexJ - startTileIndexJ + 1][endTileIndexI - startTileIndexI +1];

        if (depth == 0) {
            images[0] = new String[]{"d0_x0_y0"};
        } else {


            // (1, 2), (1,3) etc; maybe dictionary or hashmap(1 -> 2, 3, 4, 5, etc then combine later)
            int imagesIndexJ = 0;
            for (int j = startTileIndexJ; j <= endTileIndexJ; j = j + 1) {
                int imagesIndexI = 0;
                for (int i = startTileIndexI; i <= endTileIndexI; i = i + 1) {
                    StringBuilder depthString = new StringBuilder("d");
                    depthString.append(depth);
                    depthString.append("_x");
                    depthString.append(i);
                    depthString.append("_y");
                    depthString.append(j);
                    depthString.append(".png");

                    images[imagesIndexJ][imagesIndexI] = depthString.toString();
                    imagesIndexI = imagesIndexI + 1;
                }
                imagesIndexJ = imagesIndexJ + 1;
                /**
                 for each j
                 can just add depthString + xi + yj to the images array
                 **/

            }
        }

        results.put("render_grid", images);

        return results;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
