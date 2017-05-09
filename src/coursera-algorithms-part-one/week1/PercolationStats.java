

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * PercolationStats performs a series of computational experiments.
 * It uses a MonteCarlo simulation to estimate the percolation threshold.
 * 
 * @author Gaston
 */
public class PercolationStats {
    
    /**
     * Thresholds results of the experiments
     */
    private double[] thresholds;
    
    /**
     * Number of experiments to be done
     */
    private int numberOfExperiments;
    
    /**
     * Perform trials independent experiments on an n-by-n grid
     * @param gridSize The grid size
     * @param numberOfExperiments The number of experiments
     */
    public PercolationStats(int gridSize, int numberOfExperiments) {
        
        if (gridSize <= 0 || numberOfExperiments <= 0) {
            throw new IllegalArgumentException();
        }
        
        this.numberOfExperiments = numberOfExperiments;
        this.thresholds = new double[numberOfExperiments];
        
        // Repeating this computation experiment numberOfExperiments times 
        for (int i = 0; i < numberOfExperiments; i++) {
            
            // Initialize all sites to be blocked.
            Percolation percolation = new Percolation(gridSize);
            
            // Repeat the following until the system percolates:
            while (!percolation.percolates()) {
                
                // Choose a site uniformly at random among all blocked sites.
                int randomRow = StdRandom.uniform(1, gridSize + 1);
                int randomColumn = StdRandom.uniform(1, gridSize + 1);
                
                // Open the site.
                percolation.open(randomRow, randomColumn);
            }
            
            // The fraction of sites that are opened when the system percolates provides 
            // an estimate of the percolation threshold
            thresholds[i] = (double) (percolation.numberOfOpenSites() / (double) (gridSize * gridSize));
        }
    }
    
    /**
     * Sample mean of percolation thresholds
     * @return The mean of the percolation thresholds
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }
    
    /**
     * Sample standard deviation of percolation threshold
     * @return The standard deviation of percolation threshold
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    
    /**
     * Low endpoint of 95% confidence interval
     * @return The low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(numberOfExperiments));
    }
    
    /**
     * High endpoint of 95% confidence interval
     * @return The high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(numberOfExperiments));
    }

    /**
     * The program takes two command-line arguments n and T, performs T independent computational experiments 
     * on an n-by-n grid, and prints the sample mean, sample standard deviation, and the 95% confidence 
     * interval for the percolation threshold.
     * @param args[0] Integer that represents the gridSize
     * @param args[1] Integer that represents the number of experiments
     */
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int numberOfExperiments = Integer.parseInt(args[1]);
        
        PercolationStats stats = new PercolationStats(gridSize, numberOfExperiments);
        
        StdOut.println("mean = " + stats.mean());
        StdOut.println("standard deviation = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLo() + " , " + stats.confidenceHi());     
    }
}