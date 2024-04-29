package BoardUtil;

import Global.Global;

import java.util.ArrayList;

public class HexagonBoard extends MinesweeperBoard {
    public HexagonBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    public ArrayList<int[]> getNeighbors(int i, int j) {
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
        return (i + j) % 2;
    }
}