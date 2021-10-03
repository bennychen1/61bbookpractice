import java.util.List;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Comparator;

public class FlightSolver {
    List<Flight> flights;

    public FlightSolver(List<Flight> flights) {
        this.flights = flights;
    }

    /** Returns the largest number of passengers that are in flight at once. **/
    public int solve() {
        PriorityQueue<Flight> startPQ = new PriorityQueue<>(10, new FlightComparatorStart());
        PriorityQueue<Flight> endPQ = new PriorityQueue<>(10, new FlightComparatorEnd());

        for (Flight f : flights) {
            startPQ.add(f);
            endPQ.add(f);
        }

        int curMax = 0;
        int curTotal = 0;

        while (startPQ.size() != 0 && endPQ.size() != 0) {

            int curEnd = endPQ.peek().end;
            int curStart = startPQ.peek().start;

            if (curStart <= curEnd) {
                curTotal = curTotal + startPQ.poll().numPassengers;
                if (curTotal > curMax) {
                    curMax = curTotal;
                }
            } else {
                curTotal = curTotal + -1 * endPQ.poll().numPassengers;
            }
        }

        return curMax;
    }

    class FlightComparatorStart implements Comparator<Flight> {
        @Override
        public int compare(Flight a, Flight b) {
            if (a.start < b.start) {
                return -1;
            } else if (a.start == b.start) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    class FlightComparatorEnd implements Comparator<Flight> {
        @Override
        public int compare(Flight a, Flight b) {
            if (a.end < b.end) {
                return -1;
            } else if (a.end == b.end) {
                return 0;
            } else {
                return 1;
            }
        }
    }

}

// Record the start number of passengers, then end time is negative that number of passengers
// Each end time is an interval and then find the max within that interval
// Keep track of the flight duration
// PQ of times sorted by number of passengers, then return the max.
// <start1, start2, end1, start3, end2, end3> - each end is an interval to put in PQ
    // end1 = start1+start2, end2 = start3, end3 = start3 + end2
// don't need to return the minute with the most passengers
// Two PQs - one with start as priority and one with end as priority
    // poll endPQ and then remove min from startPQ and add passengers until the start time is over the endPQ polled time
    //.have a variable tracking max seen.
    // subtract endPQ polled passengers from total
    // make sure startPQ is not empty - go until startPQ is empty
