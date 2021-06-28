package hw2;

import java.util.Random;

public class PercolationStats {

    /** Cumulative sum of the percolation thresholds. **/
     double[] percolationThresholds;

    /** Number of experiments. **/
    int T;

    /** Perform T independent experiments on an NxN grid. **/
    public PercolationStats(int N, int T, PercolationFactory pf) {
        Percolation p = pf.make(N);


        double totalGrids = N * N;


        for (int i = 0; i < T; i = i + 1) {

            while (!p.percolates()) {

                Random r = new Random();
                int toOpenRow = r.nextInt(N);
                int toOpenCol = r.nextInt(N);

                p.open(toOpenRow, toOpenCol);
            }

            double numOpenSites = p.numberOfOpenSites;

            double percentage = numOpenSites / totalGrids;

            percolationThresholds[i] = percentage;
        }
    }

    /** Sample mean of the percolation thresholds. **/
    public double mean() {

        double sum = 0;

        for (int i = 0; i < this.T; i = i + 1) {
            sum = this.percolationThresholds[i];
        }

        return sum / this.T;
    }

    /** Sample standard deviation of the percolation thresholds. **/
    public double stddev() {
        double sum = 0;

        for (int i = 0; i < this.T; i = i + 1) {
            sum = Math.pow(this.percolationThresholds[i] - mean(), 2);
        }

        return sum / this.T;
    }

    /** Returns the low endpoint of the 95% confidence interval of the percolation thresholds. **/
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(this.T));
    }

    /** Returns the high endpoint of the 95% confidence interval of the percolation thresholds. **/
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(this.T));
    }

}
