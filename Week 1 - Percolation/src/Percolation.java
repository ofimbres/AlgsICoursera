import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private WeightedQuickUnionUF sites;
    private boolean[][] grid;
    private int topVirtualSite, bottomVirtualSite;
    private int size;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N");
        
        size = N;
        topVirtualSite = N * N;
        bottomVirtualSite = N * N + 1;
        sites  = new WeightedQuickUnionUF(N * N + 2);
        grid = new boolean[N][N];
        
        // create virtual sites
        for (int i = 0; i < N; i++) {
            sites.union(topVirtualSite, i);
            sites.union(bottomVirtualSite, size * size -1 - i);
        }
    }
    
    private void checkRanges(int i, int j) {
        if (i <= 0 || i > size)
            throw new IndexOutOfBoundsException(
                "row index i out of bounds (" + i + ")");
        
        if (j <= 0 || j > size)
            throw new IndexOutOfBoundsException(
                "column index j out of bounds (" + j + ")");
    }
    
    // convert 2D coordinates to 1D
    private int convertCoordinatesIntoIndex(int i, int j) {
        return ((i - 1) * size) + (j - 1);
    }
    
    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        checkRanges(i, j);
        
        if (isOpen(i, j)) return;
        
        grid[i - 1][j - 1] = true;
        
        // Check on sides
        int p, q;
        p = convertCoordinatesIntoIndex(i, j);
        
        // TOP
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                q = convertCoordinatesIntoIndex(i - 1, j);
                sites.union(p, q);
            }
        }
        
        // BOTTOM
        if (i < size) {
            if (isOpen(i + 1, j)) {
                q = convertCoordinatesIntoIndex(i + 1, j);
                sites.union(p, q);
            }
        }
        
        // LEFT
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                q = convertCoordinatesIntoIndex(i, j - 1);
                sites.union(p, q);
            }
        }
        
        // RIGHT
        if (j < size) {
            if (isOpen(i, j + 1)) {
                q = convertCoordinatesIntoIndex(i, j + 1);
                sites.union(p, q);
            }
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkRanges(i, j);
        
        return grid[i - 1][j - 1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        checkRanges(i, j);
        
        int q = convertCoordinatesIntoIndex(i, j);
        return isOpen(i, j) && sites.connected(topVirtualSite, q);
    }
    
    // does the system percolate?
    public boolean percolates() {
        if (size == 1) return grid[0][0];
        else return sites.connected(topVirtualSite, bottomVirtualSite);
    }
    
    // test client
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        
        StdOut.println("TopVirtualSite should be 16: " + p.topVirtualSite);
        StdOut.println("BottomVirtualSite should be 17: " + p.bottomVirtualSite);
        
        // convert2DCoordinatesTo1D
        StdOut.println("Converting (1, 1) coordinates should be 0: " + 
                       (p.convertCoordinatesIntoIndex(1, 1) == 0));
        StdOut.println("Converting (3, 2) coordinates should be 9: " + 
                       (p.convertCoordinatesIntoIndex(3, 2) == 9));
        StdOut.println("Converting (4, 4) coordinates should be 15: " + 
                       (p.convertCoordinatesIntoIndex(4, 4) == 15));
        
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(2, 4);
        
        // unions
        StdOut.println("Coodinate (3, 2) should be opened: " + (p.isOpen(3, 2)));
        StdOut.println("Coodinate (4, 4) should be not opened: " + (p.isOpen(4, 4)));
        StdOut.println("Coodinate (1, 2) should be opened: " + (p.isOpen(1, 2)));
        
        StdOut.println("Coodinate (3, 2) should be a full open site: " +
                       (p.isFull(3, 2)));
        StdOut.println("Coodinate (2, 4) should be an empty open site: " +
                       (p.isFull(2, 4)));
        StdOut.println("Coodinate (1, 4) should be an blocked site: " +
                       (p.isFull(1, 4)));
        
        StdOut.println("Enter N: ");
        int n = StdIn.readInt();
        
        p = new Percolation(n);
            
        int i, j, x = 0;
        while (!p.percolates()) {
            i = StdRandom.uniform(1, n + 1);
            j = StdRandom.uniform(1, n + 1);
            
            StdOut.println(i);
            StdOut.println(j);
            
            if (!p.isOpen(i, j)) {
                p.open(i, j);
                x++;
            }
        }
        
        StdOut.println("Open sites: " + x);
    }
}
