package BoardUtil;

import java.util.ArrayList;

/**
 * The board which handles triangular placements and neighbors
 */
public class TriangleBoard extends MinesweeperBoard {
    /**
     * Constructor to make triangular board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @author Arjun
     */
    public TriangleBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    /**
     * Gets all neighbors of a certain board point in the triangle
     * @param i the row index
     * @param j the column index
     * @return the neighbors of the point at (i, j)
     * @author Arjun
     */
    protected ArrayList<int[]> getAllNeighbors(int i, int j) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        if ((i + j) % 2 == 0) {
            // add right
            if (inRange(i, j + 1)) {
                neighbors.add(new int[]{i, j + 1});
            }
        } else {
            // add left
            if (inRange(i, j - 1)) {
                neighbors.add(new int[]{i, j - 1});
            }
        }

        // always add top/bottom
        if (inRange(i + 1, j)) {
            neighbors.add(new int[]{i + 1, j});
        }
        if (inRange(i - 1, j)) {
            neighbors.add(new int[]{i - 1, j});
        }
        return neighbors;
    }

    /**
     * Prints the board with a certain visibility
     * @param hidden whether or not the board is hidden
     * @author Arjun
     */
    protected void printBoard(boolean hidden) {
        for (int i = 0; i < dim; i++) {
            if (i % 2 == 0) System.out.print("  ");
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
     * @author Arjun
     */
    public int getCellId(int i, int j) {
        return (i + j) % 2;
    }
}
