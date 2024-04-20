import java.util.Random;

public class MinesweeperBoard {
    private final int[][] statuses;
    private final int d;
    private final int mines;
    private final boolean[][] visited, flagged;

    public MinesweeperBoard(int d, int mines) {
        statuses = new int[d][d];
        visited = new boolean[d][d];
        flagged = new boolean[d][d];
        this.d = d;
        this.mines = mines;

        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                statuses[i][j] = 0;
                visited[i][j] = false;
                flagged[i][j] = false;
            }
        }

        generateBoard();
    }

    public void regenUntilPlayable(int i, int j) {
        while (!isZero(i, j)) generateBoard();
    }

    public boolean isZero(int i, int j) {
        return statuses[i][j] == 0;
    }

    public void generateBoard() {
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                statuses[i][j] = 0; // not mine
            }
        }

        // place mines
        int currMineCount = 0;
        while (currMineCount < mines) {
            int i = rand.nextInt(d);
            int j = rand.nextInt(d);
            if (statuses[i][j] != -1) {
                statuses[i][j] = -1;
                currMineCount++;
            }
        }

        // calculate numbers
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

    public void printBoardAllRevealed() {
        printBoard(false);
    }
    public void printBoardHidden() {
        printBoard(true);
    }

    private void printBoard(boolean hidden) {
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                System.out.print(" " + getStringFor(i, j, hidden) + " ");
            }
            System.out.println();
        }
    }

    public String getStringFor(int i, int j, boolean hidden) {
        if (flagged[i][j] && !visited[i][j] && hidden) return "F";
        else if (!visited[i][j] && hidden) return "-";
        else {
            if (statuses[i][j] == -1) return "B";
            else return "" + statuses[i][j];
        }
    }

    public int getCell(int i, int j) {
        return statuses[i][j];
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

    public String getGameState() {
        boolean full = true;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (statuses[i][j] != -1 && !visited[i][j]) {
                    full = false;
                }
                if (statuses[i][j] == -1 && visited[i][j]) {
                    return "lost";
                }
            }
        }

        return full ? "won" : "ongoing";
    }

    public void toggleFlag(int i, int j) {
        flagged[i][j] = !flagged[i][j];
    }

    public int getDimension() {
        return d;
    }
}