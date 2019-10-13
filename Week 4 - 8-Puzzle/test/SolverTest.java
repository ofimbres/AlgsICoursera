import junit.framework.TestCase;
import java.util.*;

public class SolverTest extends TestCase {
    public void testIsSolvable() {
        int[][] blocks = { {0,1,3}, {4,2,5}, {7,8,6} };
        Board b = new Board(blocks);
        Solver solver = new Solver(b);
        assertTrue("isSolvable should be true", solver.isSolvable());
        
        blocks = new int[][] { {1,2,3}, {4,5,6}, {8,7,0} };
        b = new Board(blocks);
        solver = new Solver(b);
        assertFalse("isSolvable should be false", solver.isSolvable());
    }
    
    public void testMoves() {
        int[][] blocks = { {0,1,3}, {4,2,5}, {7,8,6} };
        Board b = new Board(blocks);
        Solver solver = new Solver(b);
        assertEquals("moves should be 4", 4, solver.moves());
        
        blocks = new int[][] { {1,2,3}, {4,5,6}, {8,7,0} };
        b = new Board(blocks);
        solver = new Solver(b);
        assertEquals("moves should be -1", -1, solver.moves());
    }
    
    public void testSolution_SolvableCase() {
        int[][] blocks = { {0,1,3}, {4,2,5}, {7,8,6} };
        Board b = new Board(blocks);
        Solver solver = new Solver(b);
        
        assertNotNull("solution should not be null", solver.solution());
        assertTrue("solution should have items", solver.solution().iterator().hasNext());
        
        // validate steps
        Board[] expectedSolution = new Board[5];
        expectedSolution[0] = new Board(new int[][] { {0,1,3}, {4,2,5}, {7,8,6} });
        expectedSolution[1] = new Board(new int[][] { {1,0,3}, {4,2,5}, {7,8,6} });
        expectedSolution[2] = new Board(new int[][] { {1,2,3}, {4,0,5}, {7,8,6} });
        expectedSolution[3] = new Board(new int[][] { {1,2,3}, {4,5,0}, {7,8,6} });
        expectedSolution[4] = new Board(new int[][] { {1,2,3}, {4,5,6}, {7,8,0} });
        
        int i = 0;
        for (Board bo : solver.solution()) {
            assertEquals(expectedSolution[i++], bo);
        }
    }
    
    public void testSolution_NotSolvableCase() {
        int[][] blocks = { {1,2,3}, {4,5,6}, {8,7,0} };
        Board b = new Board(blocks);
        Solver solver = new Solver(b);
        
        assertNull("solution should be null", solver.solution());
    }
}