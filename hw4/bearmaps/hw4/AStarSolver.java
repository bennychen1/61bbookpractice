package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import bearmaps.proj2ab.DoubleMapPQ;

import java.util.*;

import bearmaps.proj2ab.ExtrinsicMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    Vertex start;
    Vertex end;
    double timeOut;
    AStarGraph<Vertex> input;
    ExtrinsicMinPQ<Vertex> pq;
    HashMap<Vertex, Double> disTo;
    HashMap<Vertex, Vertex> edgeTo;
    ArrayList<Vertex> solutionPath;
    ArrayList<Vertex> output;
    double constructorTime;
    int numStatesExplored;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeOut) {
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
        this.numStatesExplored = 0;
        this.output = new ArrayList<>();

        this.pq.add(start, 0 + input.estimatedDistanceToGoal(start, end));
        this.edgeTo.put(start, start);
        this.disTo.put(start, 0.0);

        while (pq.size() > 0 && s.elapsedTime() < timeOut && !pq.getSmallest().equals(end)) {
            this.solutionPath.add(pq.getSmallest());
            for (WeightedEdge<Vertex> e : input.neighbors(pq.removeSmallest())) {
                relax(e);
            }

            this.numStatesExplored = this.numStatesExplored + 1;
        }

        if (pq.size() != 0 && pq.getSmallest().equals(end)) {
            Vertex curVertex = end;
            while (!curVertex.equals(start)) {
                this.output.add(curVertex);
                curVertex = this.edgeTo.get(curVertex);
            }

            output.add(start);
            Collections.reverse(output);
            solutionPath.add(end);
        }


        this.constructorTime = s.elapsedTime();


    }

    SolverOutcome result() {
        if (this.pq.size() == 0) {
            return SolverOutcome.UNSOLVABLE;
        } else if (this.pq.getSmallest().equals(this.end)) {
            return SolverOutcome.SOLVED;
        } else {
            return SolverOutcome.TIMEOUT;
        }
    }

    void relax(WeightedEdge<Vertex> e) {
        /** Take into account input.estimatedDistance
         * potentialNewDistance = distanceToP + edgeWeight + heurisitc (q to end)
         * heuristic should not be added in disTo (should be completely separate), only for the pq
         * distance from start to p + edgeWeight < current distance to q
         *      update distance to q to p + edgeweight
         *      changepriority q or add q to PQ with priority = potentialNewDistance
         *
         * **/
        Vertex p = e.from();
        Vertex q = e.to();
        double edgeWeight = e.weight();
        boolean hasQBeenSeen = disTo.containsKey(q);

        if (!hasQBeenSeen) {
            disTo.put(q, Double.MAX_VALUE);
        }

        double newDistance = disTo.get(p) + edgeWeight;
        double potentialDistanceHeuristic = newDistance + input.estimatedDistanceToGoal(q, this.end);

        if (newDistance < disTo.get(q)) {
            disTo.put(q, newDistance);
            edgeTo.put(q, p);
            if (pq.contains(q)) {
                pq.changePriority(q, potentialDistanceHeuristic);
            } else {
                pq.add(q, potentialDistanceHeuristic);
            }
        }

    }

    public SolverOutcome outcome() {
        return result();
    }
    public List<Vertex> solution() {
        return output;
    }
    public double solutionWeight() {
        return disTo.get(this.end);
    };
    public int numStatesExplored(){
        return this.numStatesExplored;
    }
    public double explorationTime() {
        return this.constructorTime;
    }
}
