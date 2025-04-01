
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
// throw or throws
// project basically needs to create unions as added to the grid, then return a check for whether or not there is a union between virtuals
// where do i insert the quickunion constructor?
// insert in percolation?, maintain id etc because algs4 quickunion only includes count() find() and union()
// assign a binary for if open, then check if each nearby grid is open
public class Percolation {
    // initialize necessary variables
    private int n;
    // private int[] id = new int [n*n+2];
    private boolean[] light;
    private WeightedQuickUnionUF quickunion;
    private int virtualone;
    private int virtualtwo;
    private int counter = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // should create an array of arrays for length and width of grid, 'initially blocked' just means that none should have a union in their id by default
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        // initialize id array, which assigns each grid square a number 1 - (n*n), + two for virtual entry and exit
        this.n = n; //?
        // this.id = new int[n*n+2]; 
        this.quickunion = new WeightedQuickUnionUF(n*n+2);
        light = new boolean[n*n]; //?
        // for (int i = 0; i < n; i++) {
        //     for (int o = 0; o < n; o++) {
        //         this.light[(i)*n+(o)] = false;
        //     }
        // }
        this.virtualone = n*n;
        this.virtualtwo = n*n+1;
    }
    
    // private int getIndex(int row, int col) {
    //     return (row - 1) * n + (col - 1);
    // }
    private int getIndex(int row, int col) {
        return (row-1) * n + (col-1);
    }
    
    private void validgrid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Invalid row or column");
        }
    }
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validgrid(row, col);
        if (isOpen(row, col)) return;
        light[getIndex(row, col)] = true;
        counter++;

        if (row < n && isOpen(row+1, col)) quickunion.union(getIndex(row, col), getIndex(row + 1, col));
        if (row > 1 && isOpen(row-1, col)) quickunion.union(getIndex(row, col), getIndex(row - 1, col));
        if (col < n && isOpen(row, col+1)) quickunion.union(getIndex(row, col), getIndex(row, col+1));
        if (col > 1 && isOpen(row, col-1)) quickunion.union(getIndex(row, col), getIndex(row, col-1));

        if (row == 1) quickunion.union(getIndex(row, col), virtualone);
        if (row == n) quickunion.union(getIndex(row, col), virtualtwo);
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
            validgrid(row, col);
            return light[getIndex(row, col)]; 
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validgrid(row, col);
        return quickunion.find(getIndex(row, col)) == quickunion.find(virtualone);
    }
    // returns the number of open sites
    public int numberOfOpenSites() {
        return counter;
    }
    // does the system percolate?
    public boolean percolates() {
        return quickunion.find(virtualone) == quickunion.find(virtualtwo);
    }
    // // test client (optional)
    // public static void main(String[] args) {
    //     int a = 1;
    //     a += 1;
    // }
}
