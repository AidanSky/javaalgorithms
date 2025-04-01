import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private int t;
    private double[] threshold; 
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.t = trials;
        this.threshold = new double[t];
        if (t <= 0) {
            throw new IllegalArgumentException("trials must be greater than 0");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }

        // create new percolations for amount of selected trials
        for (int i = 0; i < t; i++) {
            Percolation test = new Percolation(n);

            while (!test.percolates()) {
                // check if random site is open, if not open it and repeat until percolation 
                int randomone = StdRandom.uniformInt(n);
                int randomtwo = StdRandom.uniformInt(n);

                // does this need to go inside of a while loop?
                if (!test.isOpen(randomone+1, randomtwo+1)) {
                    test.open(randomone+1, randomtwo+1);
                }
            }
            // check how many open sites were required for percolation
            threshold[i] = (double)test.numberOfOpenSites()/ (double)(n*n);
        }        
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev())/(Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev())/(Math.sqrt(t));
    }
   // test client (see below)
   public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Input two arguments: (grid size) (trials)");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        if ((n<=0) || (t <= 0)) {
            throw new IllegalArgumentException("Input two positive integers");
        }
        
        PercolationStats trials = new PercolationStats(n, t);

        System.out.println("mean:" + trials.mean());
        System.out.println("stddev:" + trials.stddev());
        System.out.println("Confidence interval:" + trials.confidenceLo() + ", " + trials.confidenceHi());
    }
}