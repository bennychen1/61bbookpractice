package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {

    Vertex start;
    Vertex end;
    double timeOut;
    AStarGraph<Vertex> input;
    ArrayHeapMinPQ<Vertex> pq;
    HashMap<Vertex, Double> disTo;
    HashMap<Vertex, WeightedEdge<Vertex>> edgeTo;
    ArrayList<WeightedEdge<Vertex>> solutionPath;



    AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeOut) {
        this.start = start;
        this.end = end;
        this.timeOut = timeOut;
        this.input = input;
        this.disTo = new HashMap<>();
        this.edgeTo = new HashMap<>();
        solutionPath = new ArrayList<>();

    }

    SolverOutcome result() {
        return null;
    }

    public SolverOutcome outcome();
    public List<Vertex> solution();
    public double solutionWeight();
    public int numStatesExplored();
    public double explorationTime();
}
