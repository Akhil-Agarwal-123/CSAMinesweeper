import java.util.Random;

public class MinesweeperBoard {
    private final int[][] statuses;
    private final int d;
    private final boolean[][] visited, flagged;

    public MinesweeperBoard(int d) {
        statuses = new int[d][d];
        visited = new boolean[d][d];
        flagged = new boolean[d][d];
        this.d = d;

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                statuses[i][j] = 0;
                visited[i][j] = false;
                flagged[i][j] = false;
            }
        }

        generateBoard();
    }

    public boolean isZero(int i, int j) {
        return statuses[i][j] == 0;
    }

    public void generateBoard() {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (rand.nextDouble() < 0.2) {
                    statuses[i][j] = -1; // mine
                } else {
                    statuses[i][j] = 0; // not mine
                }
            }
        }
        int[] dij = {-1, 0, 1};
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) if (statuses[i][j] != -1) {
                int count = 0;
                for (int a : dij) {
                    for (int b : dij) {
                        if (i + a < d && i + a >= 0 && j + b < d && j + b >= 0 && statuses[i + a][j + b] == -1) {
                            count++;
                        }
                    }
                }
                statuses[i][j] = count;
            }
        }
    }
    private void printBoard(boolean hidden) {
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (flagged[i][j] && !visited[i][j]) System.out.print(" F ");
                else if (!visited[i][j] && hidden) System.out.print(" - ");
                else {
                    if (statuses[i][j] == -1) System.out.print(" B ");
                    else System.out.print(" " + statuses[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
    public void printBoardAllRevealed() {
        printBoard(false);
    }
    public void printBoardHidden() {
        printBoard(true);
    }

    public void revealSpot(int i, int j) {
        if (visited[i][j]) return;
        visited[i][j] = true;
        if (statuses[i][j] == 0) {
            int[] dij = {-1, 0, 1};
            for (int a : dij) {
                for (int b : dij) {
                    if (i + a < d && i + a >= 0 && j + b < d && j + b >= 0 &&
                            statuses[i + a][j + b] != -1) {
                        revealSpot(i + a, j + b);
                    }
                }
            }
        }
    }

    public boolean won() {
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (statuses[i][j] != -1 && !visited[i][j]) return false;
            }
        }
        return true;
    }

    public boolean lost() {
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (statuses[i][j] == -1 && visited[i][j]) return true;
            }
        }
        return false;
    }

    public void toggleFlag(int i, int j) {
        flagged[i][j] = !flagged[i][j];
    }
}
