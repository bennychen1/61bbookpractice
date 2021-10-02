package bearmaps.test;

import bearmaps.proj2c.Router;

public class TestDistance {
    TestDistance() {
    }
    private static double distance(double lonV, double lonW, double latV, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    public static double bearing(double lonV, double lonW, double latV, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /** Checks that a value is between the given ranges.*/
    private static boolean numInRange(double value, double from, double to) {
        return value >= from && value <= to;
    }

    /**
     * Calculates what direction we are going based on the two bearings, which
     * are the angles from true north. We compare the angles to see whether
     * we are making a left turn or right turn. Then we can just use the absolute value of the
     * difference to give us the degree of turn (straight, sharp, left, or right).
     * @param prevBearing A double in [0, 360.0]
     * @param currBearing A double in [0, 360.0]
     * @return the Navigation Direction type
     */
    private static int getDirection(double prevBearing, double currBearing) {
        double absDiff = Math.abs(currBearing - prevBearing);
        if (numInRange(absDiff, 0.0, 15.0)) {
            return Router.NavigationDirection.STRAIGHT;

        }
        if ((currBearing > prevBearing && absDiff < 180.0)
                || (currBearing < prevBearing && absDiff > 180.0)) {
            // we're going right
            if (numInRange(absDiff, 15.0, 30.0) || absDiff > 330.0) {
                // bearmaps.proj2c.example of high abs diff is prev = 355 and curr = 2
                return Router.NavigationDirection.SLIGHT_RIGHT;
            } else if (numInRange(absDiff, 30.0, 100.0) || absDiff > 260.0) {
                return Router.NavigationDirection.RIGHT;
            } else {
                return Router.NavigationDirection.SHARP_RIGHT;
            }
        } else {
            // we're going left
            if (numInRange(absDiff, 15.0, 30.0) || absDiff > 330.0) {
                return Router.NavigationDirection.SLIGHT_LEFT;
            } else if (numInRange(absDiff, 30.0, 100.0) || absDiff > 260.0) {
                return Router.NavigationDirection.LEFT;
            } else {
                return Router.NavigationDirection.SHARP_LEFT;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(distance(0.2, 0.5, 38.2, 38.5));
        double bearing1 = bearing(0.2, 0.1, 38.2, 38.1);
        double bearing2 = bearing(0.1, 0.4, 38.1, 38.1);
        System.out.println(getDirection(bearing1, bearing2));
        System.out.println(distance(0.1, 0.4, 38.1, 38.1));
    }
}
