import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {

    ArrayList<Flight> flights;
    public FlightSolver(ArrayList<Flight> flights) {
        this.flights = flights;
        /* FIX ME */
    }

    public int solve() {
        /* FIX ME */
        PriorityQueue<Flight> startPQ = new PriorityQueue<>(10, new FlightComparatorStart());
        PriorityQueue<Flight> endPQ = new PriorityQueue<>(10, new FlightComparatorEnd());

        for (Flight f : flights) {
            startPQ.add(f);
            endPQ.add(f);
        }

        int curMax = 0;
        int curTotal = 0;

        while (startPQ.size() != 0 && endPQ.size() != 0) {

            int curEnd = endPQ.peek().endTime();
            int curStart = startPQ.peek().startTime();

            if (curStart <= curEnd) {
                curTotal = curTotal + startPQ.poll().passengers();
                if (curTotal > curMax) {
                    curMax = curTotal;
                }
            } else {
                curTotal = curTotal + -1 * endPQ.poll().passengers();
            }
        }

        return curMax;
    }

    class FlightComparatorStart implements Comparator<Flight> {
        @Override
        public int compare(Flight a, Flight b) {
            if (a.startTime() < b.startTime()) {
                return -1;
            } else if (a.startTime() == b.startTime()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    class FlightComparatorEnd implements Comparator<Flight> {
        @Override
        public int compare(Flight a, Flight b) {
            if (a.endTime() < b.endTime()) {
                return -1;
            } else if (a.endTime() == b.endTime()) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
