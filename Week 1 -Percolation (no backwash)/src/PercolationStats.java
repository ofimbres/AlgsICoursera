import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int n, t;
    private double[] thresholds;
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException(
               "Neither 'N' or 'T' cannot be equals or less than zero.");
        
        n = N;
        t = T;
        
        Percolation p;
        thresholds = new double[T];
        int i, j, x;
        
        for (int z = 0; z < T; z++) {   
            x = 0;
            p = new Percolation(n);
            
            while (!p.percolates()) {
                i = StdRandom.uniform(1, n + 1);
                j = StdRandom.uniform(1, n + 1);
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    x++;
                }
            }
            
            thresholds[z] = x / (double) (n * n);
            /*StdOut.println("Threshold " + String.valueOf(z + 1) + ": " +
                           thresholds[z]);*/
        }
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
    
    // sample standard deviation of percolation threashold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(t));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(t));
    }
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        
        Stopwatch sw = new Stopwatch();
        PercolationStats ps = new PercolationStats(n, t);
        double secondsElapsed = sw.elapsedTime();
        
        StdOut.println("Seconds elapsed = " + secondsElapsed);
        
        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() +
                       ", " + ps.confidenceHi());
    }
}
