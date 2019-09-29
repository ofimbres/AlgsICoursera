import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] blocks;
    private final int n;
    private int emptySlotI;
    private int emptySlotJ;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blockz) { 
        if (blockz == null) throw new NullPointerException("blockz is null");
        
        this.n = blockz.length;
        this.blocks = createBlocksCopy(blockz);
        findBlankSlot(blockz, blockz.length);
    }
    
    private void findBlankSlot(int[][] blockz, int boardSize) {
        
        for (int i = 0; i < boardSize; ++i) {
            if (blockz[i].length != boardSize)
                throw new IllegalArgumentException("An inner array of blocks does not meet the required dimensions. Index: " + i);
            
            for (int j = 0; j < boardSize; ++j) {
                if (blockz[i][j] == 0) {
                    emptySlotI = i;
                    emptySlotJ = j;
                    return;
                }
            }
        }
        
        throw new IllegalArgumentException("No empty slot");
    }
    
    // board dimension n
    public int dimension() {
        return blocks.length;
    }
    
    // number of blocks out of place
    public int hamming() { 
        int count = 0;
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {                
                if (blocks[i][j] == 0)
                    continue;
                
                if (blocks[i][j] != (j+1) + (n*i))
                    count++;
            }
        }
        
        return count;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int i2, j2;
                // Board 3x3
                //          determine i   determine j
                // 0 = 0    ignored       ignored
                // 1 = 0    (1-1)/3 = 0   (1-1)%3 = 0
                // 2 = 0    (2-1)/3 = 0
                // 3 = 0    (3-1)/3 = 0
                // 4 = 1    3/3 = 1
                // 5 = 1    4/3 = 1
                // 6 = 2    5/3 = 1
                // 7 = 2    6/3 = 2
                // 8 = 2
                
                // Board 2x2
                //          determine i   determine j
                // 0 = 0    ignored       ignored
                // 1        (1-1)/2 = 0   (1-1)%2 = 0
                // 2        (2-1)/2 = 0   (2-1)%2 = 1
                if (blocks[i][j] == 0) continue; 
                
                i2 = (blocks[i][j] - 1) / n;
                j2 = (blocks[i][j] - 1) % n;
                
                count += Math.abs(i2 - i) +
                         Math.abs(j2 - j);
            }
        }
        
        return count;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        // find available neighbors of the blank slot and choose the first two neighbors to swap
        List<Integer[]> possibleSlots = new ArrayList<Integer[]>(2); // a slot point (i, j)
        
        int i = emptySlotI;
        int j = emptySlotJ;
        
        // Top
        if ((i-1) >= 0)
            possibleSlots.add(new Integer[] { i-1, j });
        
        // Bottom
        if ((i+1) <= (n-1))
            possibleSlots.add(new Integer[] { i+1, j });
        
        // Left
        if ((j-1) >= 0 && possibleSlots.size() < 2)
            possibleSlots.add(new Integer[] { i, j-1 });
        
        // Right
        if ((j+1) <= (n-1) && possibleSlots.size() < 2)
            possibleSlots.add(new Integer[] { i, j+1 });
        
        Integer[] slot1 = possibleSlots.get(0);
        Integer[] slot2 = possibleSlots.get(1);
        
        int[][] twinBlocks = createBlocksCopy(blocks);
        swap(twinBlocks, slot1[0], slot1[1], slot2[0], slot2[1]);
        return new Board(twinBlocks);
    }
    
    // does this board equal y?
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        
        Board that = (Board) other;
        if (this.dimension() != that.dimension()) return false;
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (this.blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i = emptySlotI;
        int j = emptySlotJ;
        
        int capacity = (n <= 2) ? 2 : 4; // If board 2x2, it can have max 2 neighbors
        List<Board> al = new ArrayList<Board>(capacity);
        int[][] blocksCopy;
        
        // Top
        if ((i-1) >= 0) {
            blocksCopy = createBlocksCopy(blocks);
            swap(blocksCopy, i, j, i-1, j);
            al.add(new Board(blocksCopy));
        }
        
        // Bottom
        if ((i+1) <= (n-1)) {
            blocksCopy = createBlocksCopy(blocks);
            swap(blocksCopy, i, j, i+1, j);
            al.add(new Board(blocksCopy));
        }
        
        // Left
        if ((j-1) >= 0) {
            blocksCopy = createBlocksCopy(blocks);
            swap(blocksCopy, i, j, i, j-1);
            al.add(new Board(blocksCopy));
        }
        
        // Right
        if ((j+1) <= (n-1)) {
            blocksCopy = createBlocksCopy(blocks);
            swap(blocksCopy, i, j, i, j+1);
            al.add(new Board(blocksCopy));
        }
        
        return al;
    }
    
    // string representation of this board (in the output format specified below))
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    private int[][] createBlocksCopy(int[][] blockz) {
        int[][] copy = new int[n][n];
        
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                copy[i][j] = blockz[i][j];
            }
        }
        
        return copy;
    }
    
    private void swap(int[][] array, int i, int j, int i2, int j2) {
        int temp = array[i][j];
        array[i][j] = array[i2][j2];
        array[i2][j2] = temp;
    }
}