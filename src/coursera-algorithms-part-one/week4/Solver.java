import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    
    private class Node implements Comparable<Node> {
        private Board board;
        private Node parent;
        public Node(Board board, Node parent) {
            this.board = board;
            this.parent = parent;
        }
        @Override
        public int compareTo(Node node) {
            return this.board.manhattan() - node.board.manhattan();
        }
    }

    private Node lastNode = null;
    private MinPQ<Node> nodes = new MinPQ<Node>();
    private MinPQ<Node> twinNodes = new MinPQ<Node>();
    private boolean solvable = false;
    private boolean twinSolvable = false;
     
    /**
     * Find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        if (initial == null) {
            throw new NullPointerException("The initial board cannot be null.");
        }
        
        nodes.insert(new Node(initial, null));
        twinNodes.insert(new Node(initial.twin(), null));
        
        while (!solvable && !twinSolvable) {
            solvable = doStep(nodes);
            twinSolvable = doStep(twinNodes);
        }
    }
    
    /**
     * Delete from the priority queue the search node with the minimum priority, 
     * and insert onto the priority queue all neighboring search nodes. 
     * Repeat this procedure until the search node dequeued corresponds to a goal board.
     */
    private boolean doStep(MinPQ<Node> priorityQueue) {
        Node minimumPriorityNode = priorityQueue.delMin();       
        if (minimumPriorityNode.board.isGoal()) {
            lastNode = minimumPriorityNode;
            return true;     
        }
        for (Board neighbor : minimumPriorityNode.board.neighbors()) {           
            if (minimumPriorityNode.parent == null || 
                !neighbor.equals(minimumPriorityNode.parent)) {
                    priorityQueue.insert(new Node(neighbor, minimumPriorityNode));   
            }
        }       
        return false;
    }

    /**
     * Return true if the initial board is solvable.
     * If the initial twin board reaches the goal, then the initial board is not solvable.
     */
    public boolean isSolvable() {
        return solvable;
    }
    
    /**
     * Min number of moves to solve initial board; -1 if unsolvable.
     * Count the height of the tree
     */
    public int moves() {
        if (!isSolvable()) return -1;
        int moves = 0;
        Node currentNode = lastNode;
        while (currentNode.parent != null) {
            moves++;
            currentNode = currentNode.parent;
        }
        return moves;
    }
    
    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;      
        Queue<Board> solution = new Queue<Board>();
        Node currentNode = lastNode;
        while (currentNode.parent != null) {
            solution.enqueue(currentNode.board);
            currentNode = currentNode.parent;
        } 
        return solution;
    }
    
    /**
     * Solve a slider puzzle
     * @param args
     */
    public static void main(String[] args) {
        
        // Create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // Solve the puzzle
        Solver solver = new Solver(initial);

        // Print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
}