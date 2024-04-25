package BoardUtil;

import Global.Global;

import java.util.ArrayList;

public class HexagonBoard extends MinesweeperBoard {
    public HexagonBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    public ArrayList<int[]> getNeighbors(int i, int j) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[] dij = {-1, 1};
        for (int h : dij) {
            if (inRange(i, j + h)) {
                neighbors.add(new int[]{i, j + h});
            }
        }

        int offset = (i % 2 == 0) ? -1 : 1;
        for (int v : dij) {
            if (inRange(i + v, j)) {
                neighbors.add(new int[]{i + v, j});
            }
            if (inRange(i + v, j + offset)) {
                neighbors.add(new int[]{i + v, j + offset});
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
}