package BoardUtil;

import java.util.ArrayList;

/**
 * The board which handles square placements and neighbors
 */
public class SquareBoard extends MinesweeperBoard {
    /**
     * Constructor to make square board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     */
    public SquareBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    /**
     * Gets all neighbors of a certain board point in the square
     * @param i the row index
     * @param j the column index
     * @return the neighbors of the point at (i, j)
     */
    protected ArrayList<int[]> getAllNeighbors(int i, int j) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[] dij = {-1, 0, 1};
        for (int a : dij) {
            for (int b : dij) {
                if (a == 0 && b == 0) continue;
                if (inRange(i + a, j + b)) {
                    neighbors.add(new int[]{i + a, j + b});
                }
            }
        }
        return neighbors;
    }

    /**
     * Prints the board with a certain visibility
     * @param hidden whether or not the board is hidden
     */
    protected void printBoard(boolean hidden) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(" " + getStringFor(i, j, hidden) + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the cell id for coloring based on the indices
     * @param i the row index
     * @param j the column index
     * @return the cell id of the indices
     */
    public int getCellId(int i, int j) {
        return (i + j) % 2;
    }
}