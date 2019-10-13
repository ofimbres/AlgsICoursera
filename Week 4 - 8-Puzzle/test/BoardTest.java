import junit.framework.TestCase;
import java.util.*;

public class BoardTest extends TestCase {
    public void testHammingPriority() {
        int[][] blocks = { {8,1,3}, {4,0,2}, {7,6,5} };
        Board b = new Board(blocks);
        assertEquals("Hamming priority function with 5 as score", 5, b.hamming());
        
        blocks = new int[][] { {1,2,3}, {4,5,6}, {7,8,0} };
        b = new Board(blocks);
        assertEquals("Hamming priority function with all correct positions", 0, b.hamming());
        
        blocks = new int[][] { {2,3,4}, {5,6,7}, {8,0,1} };
        b = new Board(blocks);
        assertEquals("Hamming priority function with all wrong positions", 8, b.hamming());
        
        blocks = new int[][] { {1,2}, {3,0} };
        b = new Board(blocks);
        assertEquals("Hamming priority function with all correct positions in 2x2", 0, b.hamming());
    }
    
    public void testManhattanPriority() {
        int[][] blocks = { {8,1,3}, {4,0,2}, {7,6,5} };
        Board b = new Board(blocks);
        assertEquals("Manhattan priority function with 10 as score", 10, b.manhattan());
        
        blocks = new int[][] { {1,2,3}, {4,5,6}, {7,8,0} };
        b = new Board(blocks);
        assertEquals("Manhattan priority function with all correct positions", 0, b.manhattan());
        
        blocks = new int[][] { {2,3,4}, {5,6,7}, {8,0,1} };
        b = new Board(blocks);
        assertEquals("Manhattan priority function with all wrong positions", 15, b.manhattan());
        
        blocks = new int[][] { {1,2}, {3,0} };
        b = new Board(blocks);
        assertEquals("Manhattan priority function with all correct positions in 2x2", 0, b.manhattan());
    }
    
    public void testNeighbors_emptySlotOnCenter() {
        int[][] blocks = new int[][] { {8,1,3}, {4,0,2}, {7,6,5} };
        Board b = new Board(blocks);
        int count = 0;
        List<Board> list = new ArrayList<Board>();
        
        list.add(new Board(new int[][] { {8,0,3}, {4,1,2}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {0,4,2}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {4,2,0}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {4,6,2}, {7,0,5} }));
        
        for (Board bn: b.neighbors()) {
            count++;
            assertTrue("Must exist the neighbor " + count + " in the set", list.contains(bn));
            list.remove(bn);
        }
        
        assertEquals("Set of results must be empty", 0, list.size());
        assertEquals("Empty slot has 4 of neighbors", 4, count);
    }
    
    public void testNeighbors_emptySlotOnCorner() {
        int[][] blocks = new int[][] { {8,1,3}, {4,0,2}, {7,6,5} };
        Board b = new Board(blocks);
        int count = 0;
        List<Board> list = new ArrayList<Board>();
        
        list.add(new Board(new int[][] { {8,0,3}, {4,1,2}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {0,4,2}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {4,2,0}, {7,6,5} }));
        list.add(new Board(new int[][] { {8,1,3}, {4,6,2}, {7,0,5} }));
        
        for (Board bn: b.neighbors()) {
            count++;
            assertTrue("Must exist the neighbor " + count + " in the set", list.contains(bn));
            list.remove(bn);
        }
        
        assertEquals("Set of results must be empty", 0, list.size());
        assertEquals("Empty slot has 4 of neighbors", 4, count);
    }
    
    public void testNeighbors_emptySlotOnTopCenter() {
        int[][] blocks = new int[][] { {1,0,3}, {4,5,6}, {7,8,2} };
        Board b = new Board(blocks);
        int count = 0;
        List<Board> list = new ArrayList<Board>();
        
        list.add(new Board(new int[][] { {0,1,3}, {4,5,6}, {7,8,2} }));
        list.add(new Board(new int[][] { {1,3,0}, {4,5,6}, {7,8,2} }));
        list.add(new Board(new int[][] { {1,5,3}, {4,0,6}, {7,8,2} }));
        
        for (Board bn: b.neighbors()) {
            count++;
            assertTrue("Must exist the neighbor " + count + " in the set", list.contains(bn));
            list.remove(bn);
        }
        
        assertEquals("Set of results must be empty", 0, list.size());
        assertEquals("Empty slot has 3 of neighbors", 3, count);
    }
    
    public void testIsGoal() {
        Board b1 = new Board(new int[][] { {2,3,4}, {5,6,7}, {8,0,1} });
        Board b2 = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,8,0} });
        Board b3 = new Board(new int[][] { {1,2}, {3,0} });
        
        assertFalse("Board 3x3 is incomplete", b1.isGoal());
        assertTrue("Board 3x3 is complete", b2.isGoal());
        assertTrue("Board 2x2 incomplete", b3.isGoal());
    }
    
    public void testTwin() {
        int[][] blocks = new int[][] { {1,2}, {3,0} };
        Board b = new Board(blocks); // initial board
        
        Board twin = b.twin();
        List<Board> possibleTwins = new ArrayList<Board>();
        // empty slot must still be in same position as initial board
        // board 2x2 can have only 3 possible twins
        possibleTwins.add(new Board(new int[][] { {2,1}, {3,0} }));
        possibleTwins.add(new Board(new int[][] { {3,2}, {1,0} }));
        possibleTwins.add(new Board(new int[][] { {1,3}, {2,0} }));
        
        assertTrue("Twin should be one of the options presented", possibleTwins.contains(twin));
        assertEquals("A new twin should generate the same twin board", twin, b.twin());
    }
    
    public void testEquals() {
        Board b1 = new Board(new int[][] { {8,0,3}, {4,1,2}, {7,6,5} });
        Board b2 = new Board(new int[][] { {8,0,3}, {4,1,2}, {7,6,5} });
        Board b3 = new Board(new int[][] { {8,0,3}, {4,2,1}, {7,6,5} });
        
        assertFalse(b1.equals(null));
        assertTrue(b1.equals(b1));
        assertTrue(b1.equals(b2));
        assertFalse(b1.equals(b3));
        assertFalse(b2.equals(b3));
    }
    
    public void testToString() {
         Board b1 = new Board(new int[][] { {8,0,3}, {4,1,2}, {7,6,5} });
         String boardStringified = "3\n 8  0  3 \n 4  1  2 \n 7  6  5 \n";
         
         assertEquals("ToString() function must produce correct output", boardStringified, b1.toString());
    }
}