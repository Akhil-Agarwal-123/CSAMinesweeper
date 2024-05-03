package BoardUtil;

import java.util.ArrayList;

/**
 * The board which handles hexagonal placements and neighbors
 */
public class HexagonBoard extends MinesweeperBoard {
    /**
     * Constructor to make hexagon board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     */
    public HexagonBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    /**
     * Gets all neighbors of a certain board point in the hexagon
     * @param i the row index
     * @param j the column index
     * @return the neighbors of the point at (i, j)
     */
    protected ArrayList<int[]> getAllNeighbors(int i, int j) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[][] dij;
        if (i % 2 == 0) {
            dij = new int[][] { {-1, -1}, {-1, 0}, {0, -1}, {0, 1}, {1, -1}, {1, 0} };
        } else {
            dij = new int[][] { {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, 0}, {1, 1} };
        }
        for (int[] d : dij) {
            if (inRange(i + d[0], j + d[1])) {
                neighbors.add(new int[] { i + d[0], j + d[1] });
            }
        }
        return neighbors;
    }

    protected void printBoard(boolean hidden) {
        for (int i = 0; i < dim; i++) {
            if (i % 2 == 1) System.out.print("  ");
            for (int j = 0; j < dim; j++) {
                System.out.print(" " + getStringFor(i, j, hidden) + " ");
            }
            System.out.println();
        }
    }

    public int getCellId(int i, int j) {
        if (i % 2 == 0) {
            return j % 3;
        } else {
            return (j + 2) % 3;
        }
    }
}