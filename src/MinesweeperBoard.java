import java.util.Random;

public class MinesweeperBoard {
    private final int[][] statuses;
    private final int d;
    private final int mines;
    private final double clusteringThreshold;
    private final boolean[][] visited, flagged;

    public MinesweeperBoard(int d, int mines, double clusteringThreshold) {
        statuses = new int[d][d];
        visited = new boolean[d][d];
        flagged = new boolean[d][d];
        this.d = d;
        this.mines = mines;
        this.clusteringThreshold = clusteringThreshold;

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
        int[] dij = {-1, 0, 1};
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                statuses[i][j] = 0; // not mine
            }
        }

        // place mines
        double thresholds[] = new double[d * d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                thresholds[i * d + j] = 1;
            }
        }
        for (int currMineCount = 0; currMineCount < mines; currMineCount++) {
            // normalize probabilities (softmax)
            double max = -1;
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    double t = thresholds[i * d + j];
                    if (t > max) {
                        max = t;
                    }
                }
            }

            double[] probabilities = new double[d * d];
            double sum = 0;
            for (int i = 0; i < d; i++) {
                for (int j = 0; j < d; j++) {
                    probabilities[i * d + j] = Math.exp(thresholds[i * d + j] - max);
                    sum += probabilities[i * d + j];
                }
            }

            // find random spot
            int idx = 0;
            for (double r = Math.random() * sum; idx < d * d - 1; idx++) {
                r -= probabilities[idx];
                if (r <= 0.0) break;
            }

            // place mine
            int r = idx / d;
            int c = idx % d;
            statuses[r][c] = -1;
            thresholds[r * d + c] = 0;

            // update thresholds
            for (int a : dij) {
                for (int b : dij) {
                    int newR = r + a;
                    int newC = c + b;
                    if (newR < d && newR >= 0 && newC < d && newC >= 0) {
                        if (thresholds[newR * d + newC] == 1) thresholds[newR * d + newC] += clusteringThreshold;
                    }
                }
            }
        }

        // calculate numbers
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

    public boolean hint() {
        int[][] possible = new int[d * d][2];
        int count = 0;
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (!visited[i][j] && !flagged[i][j] && statuses[i][j] != -1) {
                    possible[count][0] = i;
                    possible[count][1] = j;
                    count++;
                }
            }
        }
        if (count == 0) {
            return false;
        }
        Random rand = new Random(System.currentTimeMillis());
        int index = rand.nextInt(count);
        revealSpot(possible[index][0], possible[index][1]);
        return true;
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