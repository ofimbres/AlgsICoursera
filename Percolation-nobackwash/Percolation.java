/**
 * Created by Oscar Fimbres on 17.10.15.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private static final short //BLOCKED = 0,
        OPEN = 1,
        CONNECTTOTOP = 2,
        CONNECTTOBOTTOM = 4;
    private WeightedQuickUnionUF sites;
    private short[][] grid;
    private int size;
    private boolean percolates;
    
    /**
     * create N-by-N grid, with all sites blocked
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) throw new java.lang.IllegalArgumentException("N");
        
        size = N;
        sites  = new WeightedQuickUnionUF(N * N);
        grid = new short[N][N];

        // create virtual sites
        for (int i = 0; i < N; i++) {
            grid[0][i] |= CONNECTTOTOP;
            grid[N - 1][i] |= CONNECTTOBOTTOM;
            
            // not necessary to add other bucle only for BLOCKED status
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
    
    private int xyTo1D(int i, int j) {
        return ((i - 1) * size) + (j - 1);
    }
    
    private int getRowCoordinate(int idx) {
        return idx / size;
    }
    
    private int getColumnCoordinate(int idx) {
        return idx % size;
    }
    
    private int retrieveStatus(int q) {
        int root = sites.find(q);
        return grid[getRowCoordinate(root)][getColumnCoordinate(root)];
    }
    
    /**
     * open site (row i, column j) if it is not open already
     * @param i - row
     * @param j - column
     */
    public void open(int i, int j) {
        checkRanges(i, j);
        
        if (isOpen(i, j)) return;
        
        // Check on sides
        int p, q, root, ir, jr;
        p = xyTo1D(i, j);
        
        int[] status = new int[4];
        
        // TOP
        if (i > 1) {
            if (isOpen(i - 1, j)) {
                q = xyTo1D(i - 1, j);
                status[0] = retrieveStatus(q);
                sites.union(p, q);
            }
        }
        
        // BOTTOM
        if (i < size) {
            if (isOpen(i + 1, j)) {
                q = xyTo1D(i + 1, j);
                status[1] = retrieveStatus(q);
                sites.union(p, q);
            }
        }
        
        // LEFT
        if (j > 1) {
            if (isOpen(i, j - 1)) {
                q = xyTo1D(i, j - 1);
                status[2] = retrieveStatus(q);
                sites.union(p, q);
            }
        }
        
        // RIGHT
        if (j < size) {
            if (isOpen(i, j + 1)) {
                q = xyTo1D(i, j + 1);
                status[3] = retrieveStatus(q);
                sites.union(p, q);
            }
        }
        
        grid[i - 1][j - 1] |= OPEN;
        
        root = sites.find(p);
        ir = getRowCoordinate(root);
        jr = getColumnCoordinate(root);
        
        grid[ir][jr] |= grid[i - 1][j - 1];
        grid[ir][jr] |= status[0] | status[1] | status[2] | status[3];
        
        if (!percolates && (grid[ir][jr] & (CONNECTTOTOP | CONNECTTOBOTTOM)) ==
            (CONNECTTOTOP | CONNECTTOBOTTOM))
            percolates = true;
    }
    
    /**
     * is site (row i, column j) open?
     * @param i - row
     * @param j - column
     * @return true - site is open, else - false
     */
    public boolean isOpen(int i, int j) {
        checkRanges(i, j);
        
        return (grid[i - 1][j - 1] & OPEN) == OPEN;
    }
    
    /**
     * is site (row i, column j) full?
     * @param i - row
     * @param j - column
     * @return true - site is full, else - false
     */
    public boolean isFull(int i, int j) {
        checkRanges(i, j);
        
        int q = xyTo1D(i, j);
        
        int root = sites.find(q);
        return (grid[getRowCoordinate(root)][getColumnCoordinate(root)] &
                (CONNECTTOTOP | OPEN)) == (CONNECTTOTOP | OPEN);
    }
    
    /**
     * is system percolated?
     * @return true - system is percolated, else - false
     */
    public boolean percolates() {
        return percolates;
    }
    
    // test client
    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        
        // convert2DCoordinatesTo1D
        StdOut.println("Converting (1, 1) coordinates should be 0: " + 
                       (p.xyTo1D(1, 1) == 0));
        StdOut.println("Converting (3, 2) coordinates should be 9: " + 
                       (p.xyTo1D(3, 2) == 9));
        StdOut.println("Converting (4, 4) coordinates should be 15: " + 
                       (p.xyTo1D(4, 4) == 15));
        
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
