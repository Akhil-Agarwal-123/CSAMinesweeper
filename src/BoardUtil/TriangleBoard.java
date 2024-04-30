package BoardUtil;

import java.util.ArrayList;

public class TriangleBoard extends MinesweeperBoard {
    public TriangleBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    public ArrayList<int[]> getNeighbors(int i, int j) {
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

    protected void printBoard(boolean hidden) {
        for (int i = 0; i < dim; i++) {
            if (i % 2 == 0) System.out.print("  ");
            for (int j = 0; j < dim; j++) {
                System.out.print(" " + getStringFor(i, j, hidden) + " ");
            }
            System.out.println();
        }
    }

    public int getCellId(int i, int j) {
        return (i + j) % 2;
    }
}