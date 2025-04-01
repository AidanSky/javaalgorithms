import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.List;

public class Solver {
    private SearchNode goalNode;
    private int moves = 0;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {
        Board searchBoard;
        int searchManhattan;
        int searchMoves;
        int priority;
        SearchNode previous;

        // need to construct SearchNode with this.___ for manhattan, current moves (tracked by previous SearchNode)
        public SearchNode(Board board, int move, SearchNode prev) {
            this.searchBoard = board;
            this.searchManhattan = this.searchBoard.manhattan();
            this.searchMoves = move;
            this.priority = searchManhattan+searchMoves;
            this.previous = prev;
        }

        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("cannot be null");
        // create all neighbors, determine which one is closest via manhattan, wipe others off of the priority queue, repeat process until corrects
        // create duplicate queues for twin and base
        // initialize move counter + preiority queue, initialize and insert firstnode, define min for base and twin
        MinPQ<SearchNode> baseQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        SearchNode baseFirstNode = new SearchNode(initial, moves, null);
        SearchNode twinFirstNode = new SearchNode(initial.twin(), moves, null);

        baseQueue.insert(baseFirstNode);
        twinQueue.insert(twinFirstNode);

        // insert original board into SearchNode
        // pop lowest priority and find/insert neighbors, excluding previous
        // determine whether or not the board is solved (priority = 0?)

        while (!baseQueue.isEmpty() && !twinQueue.isEmpty()) { // min or queue.min()?
            // pop min
            // find neighbors of popped
            // repeat?

            // pop minimum and maintain variable as min
            // baseMin = baseQueue.delMin();
            // // increment amount of moves for next searchnodes
            // moves++; 
            // // initialize neighbors
            // Iterable<Board> neighbors = min.searchBoard.neighbors();
            // // pull neighbors and then iterate through, making them searchnodes and adding to queue
            // for (Board neighbor : neighbors) {
            //     if (neighbor != min.searchBoard) {
            //         SearchNode newNode = new SearchNode(neighbor, moves);
            //         queue.insert(newNode);
            //     }
            // }

            // base board solver
            SearchNode baseMin = baseQueue.delMin();

            if (baseMin.searchBoard.isGoal()) {
                solvable = true;
                goalNode = baseMin;
                moves = baseMin.searchMoves;
                return;
            }
            Iterable<Board> baseNeighbors = baseMin.searchBoard.neighbors();
            for (Board neighbor : baseNeighbors) {
                if (baseMin.previous == null || !neighbor.equals(baseMin.previous.searchBoard)) {
                    SearchNode newNode = new SearchNode(neighbor, baseMin.searchMoves+1, baseMin);
                    baseQueue.insert(newNode);
                }
            }

            // twin board solver
            SearchNode twinMin = twinQueue.delMin();

            if (twinMin.searchBoard.isGoal()) {
                solvable = false;
                goalNode = twinMin;
                moves = -1;
                return;
            }
            Iterable<Board> twinNeighbors = twinMin.searchBoard.neighbors();
            for (Board neighbor : twinNeighbors) {
                if (twinMin.previous == null || !neighbor.equals(twinMin.previous.searchBoard)) {
                    SearchNode newNode = new SearchNode(neighbor, twinMin.searchMoves+1, twinMin);
                    twinQueue.insert(newNode);
                }
            }
        }
        return;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (moves() != -1) return true;
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!solvable) return null;
        List<Board> solutions = new ArrayList<>(); 
        SearchNode solutionNode = goalNode;
        while (solutionNode != null) {
            solutions.add(solutionNode.searchBoard);
            solutionNode = solutionNode.previous;
        }
        return solutions;
    }

    // test client (see below) 
    public static void main(String[] args) {
        int[][] tiles = {
            {1, 2, 3},
            {4, 0, 6},
            {7, 5, 8}
        };
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        System.out.println("Solvable: " + solver.isSolvable());
        System.out.println("Moves: " + solver.moves());
        if (solver.isSolvable()) {
            System.out.println("Solution:");
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
    }
}