package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    Vertex start;
    Vertex end;
    double timeOut;
    AStarGraph<Vertex> input;
    ArrayHeapMinPQ<Vertex> pq;
    HashMap<Vertex, Double> disTo;
    HashMap<Vertex, WeightedEdge<Vertex>> edgeTo;
    ArrayList<WeightedEdge<Vertex>> solutionPath;
    double constructorTime;



    AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeOut) {
        Stopwatch s = new Stopwatch();
        this.start = start;
        this.end = end;
        this.timeOut = timeOut;
        this.input = input;
        this.disTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        this.pq = new ArrayHeapMinPQ<>();
        this.solutionPath = new ArrayList<>();
        this.constructorTime = 0;

        pq.add(start, 0 + input.estimatedDistanceToGoal(start, end));

        while (pq.size() > 0 || s.elapsedTime() < timeOut || pq.getSmallest().equals(end)) {
            for (WeightedEdge<Vertex> e : input.neighbors(pq.removeSmallest())) {
                relax(e);
                }
            }
        }


    }

    SolverOutcome result() {
        if (this.pq.getSmallest().equals(this.end)) {
            return SolverOutcome.SOLVED;
        } else if (this.pq.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else {
            return SolverOutcome.TIMEOUT;
        }
    }

    void relax(WeightedEdge<Vertex> e) {
        /** Take into account input.estimatedDistance
         * potentialNewDistance = distanceToP + edgeWeight + heurisitc (q to end)
         * heuristic should not be added in disTo (should be completely separate)
         * distance from start to p + edgeWeight < current distance to q
         *      update distance to q to p + edgeweight
         *      changepriority q or add q to PQ with priority = potentialNewDistance
         *
         * **/

    }

    public SolverOutcome outcome();
    public List<Vertex> solution();
    public double solutionWeight();
    public int numStatesExplored();
    public double explorationTime();
}
