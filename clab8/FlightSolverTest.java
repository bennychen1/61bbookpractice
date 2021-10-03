import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class FlightSolverTest {

    /**
     * Constructs an ArrayList of Flights where the ith flight has the ith start time in
     * startTimes, the ith end time in endTimes, and the ith count in passengerCounts.
     * Arguments must have the same length.
     */
    ArrayList<Flight> makeFlights(int[] startTimes, int[] endTimes, int[] passengerCounts) {
        ArrayList<Flight> flights = new ArrayList<>();
        for (int i = 0; i < startTimes.length; i++) {
            flights.add(new Flight(startTimes[i], endTimes[i], passengerCounts[i]));
        }
        return flights;
    }

    /**
     * Test with no overlapping flights.
     */
    @Test
    public void naiveTest() {
        int[] startTimes = {10, 20, 30, 40};
        int[] endTimes = {19, 29, 39, 49};
        int[] passengerCounts = {1, 2, 3, 4};
        FlightSolver solver = new FlightSolver(makeFlights(startTimes, endTimes, passengerCounts));
        assertEquals(4, solver.solve());
    }

    /**
     * Test with one overlapping flight.
     */
    @Test
    public void smallTest() {
        int[] startTimes = {10, 20, 40};
        int[] endTimes = {25, 35, 49};
        int[] passengerCounts = {1, 4, 3};
        FlightSolver solver = new FlightSolver(makeFlights(startTimes, endTimes, passengerCounts));
        assertEquals(5, solver.solve());
    }

    /**
     * Test with some overlapping flights.
     */
    @Test
    public void mediumTest() {
        int[] startTimes = {15, 23, 18, 42, 55, 75, 78};
        int[] endTimes = {27, 45, 44, 65, 90, 95, 85};
        int[] passengerCounts = {20, 30, 10, 25, 5, 10, 15};
        FlightSolver solver = new FlightSolver(makeFlights(startTimes, endTimes, passengerCounts));
        assertEquals(65, solver.solve());
    }

    @Test
    public void testFlightSovler() {
        Flight flight1 = new Flight(1, 5, 10);
        Flight flight2 = new Flight(2, 7, 3);
        Flight flight3 = new Flight(4, 8, 15);
        Flight flight4 = new Flight(1, 2, 1);
        Flight flight5 = new Flight(9, 10, 1);

        ArrayList<Flight> flights = new ArrayList<>();

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        flights.add(flight5);

        FlightSolver f = new FlightSolver(flights);

        assertEquals(28, f.solve());
    }

    @Test
    public void testFlightSolverMultiple() {
        Flight flight1 = new Flight(5, 10, 30);
        Flight flight2 = new Flight(2, 9, 15);
        Flight flight3 = new Flight(5, 8, 20);
        Flight flight4 = new Flight(1, 10, 9);
        Flight flight5 = new Flight(4, 10, 3);
        Flight flight6 = new Flight(1, 3, 50);

        ArrayList<Flight> flights = new ArrayList<>();

        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        flights.add(flight4);
        flights.add(flight5);
        flights.add(flight6);

        FlightSolver f = new FlightSolver(flights);

        assertEquals(77, f.solve());
    }

    @Test
    public void testSpeed() {

        Random r = new Random(10);

        int[] numFlights = new int[]{10, 100, 1000, 10000, 100000, 1000000};
        double[] time = new double[numFlights.length];

        for (int i = 0; i < numFlights.length; i = i + 1) {
            int curNumFlights = numFlights[i];
            ArrayList<Flight> flights = new ArrayList<>();

            for (int j = 0; j < curNumFlights; j = j + 1) {
                flights.add(new Flight(100 + (int)(Math.random() * ((500 - 100) + 1)),
                        1000 + (int)(Math.random() * (6000 - 1000) + 1),
                        100 + (int)(Math.random() * (600 - 100 + 1))));
            }

            FlightSolver f = new FlightSolver(flights);

            Stopwatch s = new Stopwatch();

            f.solve();

            time[i] = s.elapsedTime();
        }

        System.out.println(Arrays.toString(time));

    }

}
