import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int[][] tiles;
    private final int n;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("tiles cannot be null");
        }

        this.n = tiles.length;
        // error testing for if individual tile is null or if either array piece is not equal to n

        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        // define variables for n, and for each grid element

        // define string that will be returned
        String stringBoard = String.valueOf(n);
        stringBoard = stringBoard.concat("\n");
        // create loop that adds new lines to stringboard for each element 1+n*n
        for (int k = 0; k < n; k++) {
            for (int l = 0; l < n; l++) {
                stringBoard = stringBoard.concat(String.valueOf(tiles[k][l])).concat(" ");
            }
            stringBoard = stringBoard.concat("\n");
        }
        return stringBoard;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        // create array of fully correct board, then compare each array element with the real grid
        // should copy array be array of array, or just single array with all values?
        // should copy be 0 indexed or 1 indexed?
        // how to deal with 9th point? can this be hedgehogged? -- should edge case be added to specify what happens in this case (ie. if _ == 0 or something?)

        // array of correct board can be created while comparing by keeping a counter for each time we iterate through the two for loops
        // is blank square always in (9)

        // add to hammingCounter for each incorrect comparison

        // int[] copy = new int[n*n-1]; // copy[n*n] should not be included to avoid counting missing position as additional incorrect
        // int counter = 0; // this should start at 1, right? // how will shift to 1 index change array?
        // int incorrect = 0;

        // for (int m = 0; m < n; m++) {
        //     for (int o = 0; o < n; o++) { // does this work with ignoring the 9th?
        //         if (tiles[m][o] != 0 ) {
        //             if (m == n-1 && o == n-1) {
        //                 if (tiles[m][o] != 0) {
        //                     incorrect++;
        //                 }
        //             } else {
        //                 copy[counter] = ++counter;
        //                 if (copy[counter] != tiles[m][o]) {
        //                     incorrect++;
        //                 }
        //             } 
        //         }
        //     }
        // }

        int incorrect = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int current = tiles[i][j];
                if (current == 0) continue;
                int goal = i * n + j + 1;
                if (goal >= n*n) {
                    goal = 0;
                }
                if (tiles[i][j] != goal) incorrect++;
            }
        }
        return incorrect;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // sum of vertical and horizontal distance from correct position

        // if in position board[3][3], needs to be in board[1][2], this is 3 positions away, or [3-1] + [3-2], so can be calculated in array of arrays

        // iterate through this.tiles, determining whether or not a given number is incorrect, and if it is incorrect, determine where it should be, then perform [x-y] + [x-y]


        // create correct array of arrays, then compare? must read 9th position, which should be 0
        // int[][] correctArray = new int[n][n];
        // int counter = 1;
        // int manhattan = 0;
        // correctArray[n-1][n-1] = n*n;
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         // initialize correctArray, must be done separately, right?
        //         correctArray[i][j] = counter++; 
        //     }
        // }

        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //     // compare correctArray to real Array
        //         if (correctArray[i][j] != tiles[i][j] && tiles[i][j] != 0) {
        //             // if do not match, do [x-y]+[x-y] and add to manhattan
        //             // need way to match current position to correct position, or vice versa
        //                 // is the only way to do this via iterating through one of the lists again?

        //             // iterate through tiles[][] to find equivalent of correctArray[i][j], and once found, perform transaction
        //             outerLoop: for (int k = 0; k < n; k++) {
        //                 for (int l = 0; l < n; l++) {
        //                     if (tiles[k][l] == correctArray[i][j]) {
        //                         // perform equation to determine amount to add to manhattan
        //                         manhattan += (Math.abs(k-i)+Math.abs(l-j)); // does this need to be calculated with abs***
        //                         break outerLoop;
        //                     }
        //                 }
        //             }
        //         }
        //     }
        // }
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int current = tiles[i][j];
                if (current == 0) continue;
                int correctRow = (current-1) / n;
                int correctCol = (current-1) % n;
                manhattan += Math.abs(correctRow - i) + Math.abs(correctCol - j);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    // does this board equal y?
    public boolean equals(Object y) { // Object vs Board?
        // check if n is equivalent
        
        // check if positions are equivalent via counter
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        
        Board that = (Board) y;

        if (that.n != this.n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (that.tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // returns all possible next moves
        // returns all possible next boards in the form of an Iterable (sorta like an array of objects)
        // edge cases for if in center vs on corner vs on edge
        // is this implementation too slow?
        // initialize return element
        List<Board> boards = new ArrayList<>();
        int zeroRow = 0;
        int zeroCol = 0;

        int[][] copyTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyTiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        // create all possible next board combinations, keeping edge cases in mind 
        // if [x+1] > 0, [x-1] < 0, [y+1] > 0, [y-1] < 0 then do not consider as a possible next move
            // can do this by breaking current consideration in for loop? or maybe just 4 separate if checks
                // need to find where this.tiles == 0, and then check possibilities from there
                // if > n, invalud
                // if < 0, invalid (or is it less than 1)
        // add next board possibilities to boards iterable

        // these need to be boards, not arrays of arrays

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] direction : directions) {
            int copyRow = zeroRow + direction[0];
            int copyCol = zeroCol + direction[1];
            if (copyRow >= 0 && copyRow < n && copyCol >= 0 && copyCol < n) {
                int[][] copy = new int[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        copy[i][j] = tiles[i][j];
                    }
                }
                copy[zeroRow][zeroCol] = copy[copyRow][copyCol];
                copy[copyRow][copyCol] = 0;
                boards.add(new Board(copy));
            }
        }
    // how to create neighbor board after determining it is valid?
    // does a whole new one need to be created, or can a swap be done on a copy?
    // most likely must be a whole new copy, as otherwise would be working wiht original's data 
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // should swap the first and second non zero tiles, doesn't matter what they are, used for determining solvability

        // check for first two non zero elements, then swap
            // can this be done as part of one loop, or must an initialization loop be done, and then a detection/swap loop?
        
        // initialize variables
        int[][] twinArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
            twinArray[i][j] = tiles[i][j];
            }
        }
        // *** better to store indexes rather than values so can swap in same loop
        int dummyOne = 0;
        int dummyTwo = 0;
        int y = 0;
        int u = 0;

        // iterate through n^2, filling out twin array with swaps
        outerLoop: for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // setup the two numbers that should be swapped
                if (this.tiles[i][j] != 0 && dummyOne == 0) {
                    y = i;
                    u = j;
                    dummyOne = this.tiles[i][j];
                } else if (this.tiles[i][j] != 0 && dummyOne != 0) { // initiate swap here
                    dummyTwo = this.tiles[i][j];
                    twinArray[y][u] = dummyTwo;
                    twinArray[i][j] = dummyOne;
                    break outerLoop;
                }
            }
        }
        return new Board(twinArray);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] scrambledTiles = {
            {8, 1, 3},
            {4, 0, 2},
            {7, 6, 5}
        };
        Board scrambled = new Board(scrambledTiles);
        System.out.println("\nScrambled Board:\n" + scrambled);
        System.out.println("Hamming: " + scrambled.hamming()); // Should be 5
        System.out.println("Manhattan: " + scrambled.manhattan()); // Should be 10
        System.out.println("Is Goal: " + scrambled.isGoal()); // Should be false

        // Test neighbors
        System.out.println("\nNeighbors:");
        for (Board neighbor : scrambled.neighbors()) {
            System.out.println(neighbor);
        }

        // Test twin
        System.out.println("Twin:\n" + scrambled.twin());

        // Test equals
        System.out.println("Equals self? " + scrambled.equals(scrambled));
    }
}