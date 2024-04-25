package BoardUtil;

import Global.Global;

import java.util.ArrayList;

public class SquareBoard extends MinesweeperBoard {
    public SquareBoard(int dim, int mines, double clusteringThreshold) {
        super(dim, mines, clusteringThreshold);
    }

    public ArrayList<int[]> getNeighbors(int i, int j) {
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

    protected void printBoard(boolean hidden) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.print(" " + getStringFor(i, j, hidden) + " ");
            }
            System.out.println();
        }
    }
}