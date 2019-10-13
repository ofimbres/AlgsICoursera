import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final boolean isSolvable;
    private final int moves;
    private final Stack<Board> pathSolution;
    
    // find a solution to the initial board (using A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("board is null");
        
        SearchNode initialNode = new SearchNode(initial, 0, null);
        SearchNode twinNode = new SearchNode(initial.twin(), 0, null);
        
        MinPQ<SearchNode> queue1 = new MinPQ<SearchNode>();
        MinPQ<SearchNode> queue2 = new MinPQ<SearchNode>();
        
        queue1.insert(initialNode);
        queue2.insert(twinNode);
        
        SearchNode currentNode1 = initialNode;
        SearchNode currentNode2 = twinNode;
        
        // Given a twin board, either original or twin board will be solvable but not both.
        while (true) {
            currentNode1 = queue1.delMin();
            currentNode2 = queue2.delMin();
            
            if (currentNode1.board().isGoal()) {
                moves = currentNode1.moves();
                isSolvable = true;
                
                // Generate path solution
                pathSolution = new Stack<Board>();
                while (currentNode1 != null) {
                    pathSolution.push(currentNode1.board());
                    currentNode1 = currentNode1.previousNode();
                }
                break;
            }
            
            // Suppose that initial board is unsolvable. This means that any of it's twin boards will be solvable.
            if (currentNode2.board().isGoal()) {
                moves = -1;
                isSolvable = false;
                pathSolution = null;
                break;
            }
            
            // Loop through neighbors
            for (Board neighborBoard : currentNode1.board().neighbors()) {
                SearchNode newNode = new SearchNode(neighborBoard, currentNode1.moves()+1, currentNode1);
                
                // To reduce unnecessary exploration of useless search nodes,
                // don't enqueue a neighbor if its board is the same as the board of the previous search node.
                if (currentNode1.previousNode() == null) {
                    queue1.insert(newNode);
                } else if (currentNode1.previousNode() != null && !neighborBoard.equals(currentNode1.previousNode().board())) {
                    queue1.insert(newNode);
                }
            }
            
            for (Board neighborBoard : currentNode2.board().neighbors()) {
                SearchNode newNode = new SearchNode(neighborBoard, currentNode2.moves()+1, currentNode2);
                
                if (currentNode2.previousNode() == null) {
                    queue2.insert(newNode);
                } else if (currentNode2.previousNode() != null && !neighborBoard.equals(currentNode2.previousNode().board())) {
                    queue2.insert(newNode);
                }
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return pathSolution;
    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final int moves;
        private final SearchNode previousNode;
        private final int manhattan;
        
        public SearchNode(Board board, int moves, SearchNode previousNode) {
            this.board = board;
            this.moves = moves;
            this.previousNode = previousNode;
            this.manhattan = board.manhattan();
        }
        
        public Board board() { return board; }
        
        public int moves() { return moves; }
        
        public SearchNode previousNode() { return previousNode; }
        
        public int compareTo(SearchNode other) {
            int m1 = this.manhattan + this.moves;
            int m2 = other.manhattan + other.moves;
            return m1 - m2;
        }
    }
    
    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}