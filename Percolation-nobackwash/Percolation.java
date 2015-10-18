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
    
    private int getRowCoordinate(int idx) { return idx / size; }
    private int getColumnCoordinate(int idx) { return idx % size; }
    
    private int retrieveStatus(int q) {
        int root = sites.find(q);
        return grid[getRowCoordinate(root)][getColumnCoordinate(root)];
    }
    
    private int connectToNeighbor(int p, int q, int neighborStatus) {
        neighborStatus |= retrieveStatus(q);
        sites.union(p, q);
        
        return neighborStatus;
    }

    /**
     * open site (row i, column j) if it is not open already
     * @param i - row
     * @param j - column
     */
    public void open(int i, int j) {
        checkRanges(i, j);
        
        if (isOpen(i, j)) return;
        
        // check on neighbors
        int p = xyTo1D(i, j);
        int currentNeighborStatus = 0;
        
        // top
        if (i > 1 && isOpen(i - 1, j))
            currentNeighborStatus |=
                connectToNeighbor(p, xyTo1D(i - 1, j), currentNeighborStatus);
        
        // bottom
        if (i < size && isOpen(i + 1, j))
            currentNeighborStatus |=
                connectToNeighbor(p, xyTo1D(i + 1, j), currentNeighborStatus);
        
        // left
        if (j > 1 && isOpen(i, j - 1))
            currentNeighborStatus |=
                connectToNeighbor(p, xyTo1D(i, j - 1), currentNeighborStatus);
        
        // right
        if (j < size && isOpen(i, j + 1))
            currentNeighborStatus |=
                connectToNeighbor(p, xyTo1D(i, j + 1), currentNeighborStatus);
        
        grid[i - 1][j - 1] |= OPEN;
        
        // we do a 5th find(S) to get the root of the newly
        // generated connected component results from opening the site S
        int root, ir, jr;
        root = sites.find(p);
        ir = getRowCoordinate(root);
        jr = getColumnCoordinate(root);
        
        // finally we update the status of the new root by combining the old
        // status information into the new root in constant time.
        grid[ir][jr] |= grid[i - 1][j - 1] | currentNeighborStatus;
        
        // we keep the status like connect to the top, connect to the bottom,
        // making constant time operation in percolates operation
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
