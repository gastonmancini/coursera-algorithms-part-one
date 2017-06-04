import edu.princeton.cs.algs4.Queue;

public class Board {
    
    private int[][] blocks; // The blank block is the zero value

    public Board(int[][] blocks) {
        this.blocks = new int[blocks.length][blocks[0].length];
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks[0].length; column++) {
                this.blocks[row][column] = blocks[row][column];
            }
        }     
    }

    public int dimension() {
        return blocks.length;
    }
    
    /**
     * Returns the number of blocks out of place
     */
    public int hamming() {
        int count = 1;
        int hamming = 0;
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (row == dimension() - 1 && column == dimension()-1) break; // Skip last value of the board
                if (blocks[row][column] != count) hamming++;
                count++;
            }
        }  
        return hamming;
    }
    
    /**
     * Sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int manhattan = 0;        
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {              
                int currentValue = blocks[row][column];                        
                if (currentValue == 0) continue; // Skip the blank               
                int expectedRow = (currentValue - 1) / dimension();
                int expectedColumn = (currentValue - 1) % dimension();                
                int distance = Math.abs(row - expectedRow) + Math.abs(column - expectedColumn);
                manhattan += distance;
            }
        }  
        return manhattan;
    }
    
    /**
     * Return true if this is the goal board
     */
    public boolean isGoal() {
        if (blocks[dimension()-1][dimension()-1] != 0) return false;
        return hamming() == 0;
    }
    
    /**
     * A board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        Board newBoard = new Board(blocks);      
        // Switch the first two blocks if they are not blank
        if (newBoard.blocks[0][0] != 0 && newBoard.blocks[0][1] != 0) {
            exchangeBlocks(newBoard.blocks, 0, 0, 0, 1);
        } else { // Switch the first two elements of the second row
            exchangeBlocks(newBoard.blocks, 1, 0, 1, 1);
        }
        return newBoard;
    }
    
    private void exchangeBlocks(int[][] grid, int xBlock1, int yBlock1, int xBlock2, int yBlock2) {
        int block = grid[xBlock1][yBlock1];
        grid[xBlock1][yBlock1] = grid[xBlock2][yBlock2];
        grid[xBlock2][yBlock2] = block;
    }

    public boolean equals(Object y) {
        if (y == null || this.getClass() != y.getClass()) return false;       
        Board yBoard = (Board) y;              
        if (this.dimension() != yBoard.dimension()) return false;        
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks[0].length; column++) {
                if (this.blocks[row][column] != yBoard.blocks[row][column]) return false;
            }
        }          
        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        
        // Make a copy of the blocks and identify the empty block position
        int rowEmptyBlock = 0;
        int columnEmptyBlock = 0;
        int[][] tempBlocks = new int[dimension()][dimension()];
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                tempBlocks[row][column] = blocks[row][column];        
                if (tempBlocks[row][column] == 0) {
                    rowEmptyBlock = row;
                    columnEmptyBlock = column;
                }
            }
        }
        
        // Exchange with left block
        if (columnEmptyBlock > 0) {
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock, columnEmptyBlock - 1);
            neighbors.enqueue(new Board(tempBlocks));
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock, columnEmptyBlock - 1);
        }
        
        // Exchange with right block
        if (columnEmptyBlock < dimension() - 1) {
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock, columnEmptyBlock + 1);
            neighbors.enqueue(new Board(tempBlocks));
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock, columnEmptyBlock + 1);
        }
        
        // Exchange with top block
        if (rowEmptyBlock > 0) {
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock - 1, columnEmptyBlock);
            neighbors.enqueue(new Board(tempBlocks));
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock - 1, columnEmptyBlock);
        }

        // Exchange with bottom block
        if (rowEmptyBlock < dimension() - 1) {
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock + 1, columnEmptyBlock);
            neighbors.enqueue(new Board(tempBlocks));
            exchangeBlocks(tempBlocks, rowEmptyBlock, columnEmptyBlock, rowEmptyBlock + 1, columnEmptyBlock);
        }
        
        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}