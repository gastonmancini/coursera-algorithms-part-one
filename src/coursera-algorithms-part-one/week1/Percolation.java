

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation data type
 * 
 * @author Gaston
 */

public class Percolation {

    /**
     * Stores the grid size
     */
    private int gridSize;
    
    /**
     * The grid of open sites.
     */
    private boolean[][] openedSites;
    
    /**
     * Keeps track of the number of opened sites on the grid
     */
    private int numberOfOpenedSites = 0;
    
    /**
     * Union find data structure with n sites 0 through n-1
     */
    private WeightedQuickUnionUF weightedQuickUnionUF;
    
    /**
     * Virtual sites are connections to top and bottom
     */
    private int virtualTopSite;   
    private int virtualBottomSite;
    
    /**
     * Initialize the Percolation data type
     * @param n the grid size
     */
    public Percolation(int gridSize) {     
        
        if (gridSize <= 0) {
            throw new IllegalArgumentException("The grid size must be greater than zero.");
        }
        
        this.gridSize = gridSize;
        
        // Initialize the grid with all sites blocked
        openedSites = new boolean[gridSize][gridSize];
        
        // The reason we add two more sites is because we have to store both virtual sites apart from the grid sites
        weightedQuickUnionUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        
        // Set the virtual top and bottom sites and open them
        virtualTopSite = 0;
        virtualBottomSite = gridSize * gridSize + 1;
    }
    
    /**
     * Opens a site. To open a site we just connect the site to all the adjacent opened sites
     * @param row Row of the site to be opened
     * @param col Column of the site to be opened
     */
    public void open(int row, int col) {
        
        numberOfOpenedSites++;
        
        // The input is from 1..gridSize, but our grid goes from 0 to gridSize - 1, 
        // that is the reason we decrement one to the row and col
        openedSites[row - 1][col - 1] = true; 
        
        int siteIndex = getUnionFindIndexOfSite(row, col);     

        // If it is a site from the top, then connect it with the virtual top site 
        if (row == 1) {
            weightedQuickUnionUF.union(virtualTopSite, siteIndex);
        }
        
        // If it is a site from the bottom, then connect it with the virtual bottom site
        if (row == gridSize) {
            weightedQuickUnionUF.union(siteIndex, virtualBottomSite);
        }
        
        // Union with the left site
        unionSites(siteIndex, row, col - 1);       
        // Union with the top site
        unionSites(siteIndex, row - 1, col);     
        // Union with the right site
        unionSites(siteIndex, row, col + 1);      
        // Union with the bottom site
        unionSites(siteIndex, row + 1, col);
    }
    
    /**
     * Check if the site is open
     * @param row Row of the site
     * @param col Column of the site
     * @return True if the site is open. Otherwise, returns false
     */
    public boolean isOpen(int row, int col) {
        assertIndexOutOfBounds(row, col);
        return (openedSites[row - 1][col - 1]);
    }
    
    /**
     * Check if the site is full, if it is connected to the virtual top site
     * @param row Row of the site
     * @param col Column of the site
     * @return True if the site it is connected to the virtual top site, false if it is not connected
     */
    public boolean isFull(int row, int col) {
        assertIndexOutOfBounds(row, col);
        int siteIndex = getUnionFindIndexOfSite(row, col);
        return weightedQuickUnionUF.connected(virtualTopSite, siteIndex);
    }
    
    /**
     * Number of open sites
     * @return The number of opened sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenedSites;
    }
    
    /**
     * The system percolates iff virtual top site is connected to virtual bottom site
     * @return True if the system percolate, false otherwise
     */
    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }
        
    /**
     * Converts the two dimensions grid coordinate system into a one dimension index to be used by the union find structure
     * @param row Row of the site to get the index
     * @param col Column of the site to get the index
     * @return The one dimension index that corresponds to the two dimensions given coordinates
     */
    private int getUnionFindIndexOfSite(int row, int col) {
        return (row - 1) * gridSize + col; 
    }
    
    /**
     * Union two sites iff the indexes are not out of bounds and if the target site is already opened
     * @param originSiteIndex the origin site index in the union find structure
     * @param targetSiteRow The target site row in the two dimensional grid
     * @param targetSiteCol The target site column in the two dimensional grid
     */
    private void unionSites(int originSiteIndex, int targetSiteRow, int targetSiteCol) {
        if (!isIndexOutOfBounds(targetSiteRow, targetSiteCol) && isOpen(targetSiteRow, targetSiteCol)) {            
            weightedQuickUnionUF.union(originSiteIndex, getUnionFindIndexOfSite(targetSiteRow, targetSiteCol));
        }
    }

    /**
     * Throws an exception if the site [row, col] is out of bounds of the grid
     * @param row Row of the site
     * @param col Column of the site
     */
    private void assertIndexOutOfBounds(int row, int col) {
        if (isIndexOutOfBounds(row, col)) {
            throw new IndexOutOfBoundsException("The index [" + row + ", " + col + "] is out of bounds.");
        }
    }

    /**
     * Returns false if the site [row, col] is out of bounds of the grid
     * @param Row of the site
     * @param Column of the site
     * @return False if the site [row, col] is out of bounds of the grid, true otherwise
     */
    private boolean isIndexOutOfBounds(int row, int col) {
        return (row - 1 < 0 || col - 1 < 0 || row - 1 >= gridSize || col - 1 >= gridSize);
    }
}