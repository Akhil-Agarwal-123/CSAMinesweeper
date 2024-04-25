package BoardUtil;

import Global.Global;
import java.util.ArrayList;

public class MinesweeperBoard {
    private final int[][] statuses;
    private final int dim, mines;
    private final double clusteringThreshold;
    private final boolean[][] visited, flagged;

    public MinesweeperBoard(int dim, int mines, double clusteringThreshold) {
        statuses = new int[dim][dim];
        visited = new boolean[dim][dim];
        flagged = new boolean[dim][dim];
        this.dim = dim;
        this.mines = mines;
        this.clusteringThreshold = clusteringThreshold;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                statuses[i][j] = 0;
                visited[i][j] = false;
                flagged[i][j] = false;
            }
        }
    }

    public boolean inRange(int i, int j) {
        return i >= 0 && i < dim && j >= 0 && j < dim;
    }

    public void clearFlags() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                flagged[i][j] = false;
            }
        }
    }

    public boolean getVisited(int i, int j) {
        return visited[i][j];
    }

    public void setVisited(int i, int j, boolean value) {
        visited[i][j] = value;
    }

    public void genBoard(int i, int j) {
        double[][] mineMask = new double[dim][dim];
        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
                mineMask[a][b] = 1;
            }
        }

        // 0 probability for spawning a mine on a clicked cell
        int[] dij = {-1, 0, 1};
        for (int a : dij) {
            for (int b : dij) {
                if (inRange(i + a, j + b)) {
                    mineMask[i + a][j + b] = 0;
                }
            }
        }
        generateBoard(mineMask);
    }

    public void genBoard(double[][] mineMask) {
        generateBoard(mineMask);
    }

    private void generateBoard(double[][] mineMask) {
        int[] dij = {-1, 0, 1};
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                statuses[i][j] = 0; // not mine
            }
        }

        // place mines
        double[] thresholds = new double[dim * dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                thresholds[i * dim + j] = mineMask[i][j];
            }
        }
        for (int currMineCount = 0; currMineCount < mines; currMineCount++) {
            // normalize probabilities (softmax)
            double max = -1;
            for (double threshold : thresholds) {
                max = Math.max(max, threshold);
            }

            double[] probabilities = new double[dim * dim];
            double sum = 0;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (thresholds[i * dim + j] == 0) probabilities[i * dim + j] = 0;
                    else probabilities[i * dim + j] = Math.exp(thresholds[i * dim + j] - max);

                    sum += probabilities[i * dim + j];
                }
            }

            // find random spot
            int idx = 0;
            for (double r = Global.rand.nextDouble() * sum; idx < dim * dim; idx++) {
                r -= probabilities[idx];
                if (r <= 0) break;
            }

            // place mine
            int r = idx / dim;
            int c = idx % dim;
            statuses[r][c] = -1;
            thresholds[r * dim + c] = 0;

            // update thresholds
            for (int a : dij) {
                for (int b : dij) {
                    int newR = r + a;
                    int newC = c + b;
                    if (inRange(newR, newC) && thresholds[newR * dim + newC] == 1) {
                        thresholds[newR * dim + newC] += clusteringThreshold;
                    }
                }
            }
        }

        // calculate numbers for non-bomb squares
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) if (statuses[i][j] != -1) {
                int count = 0;
                for (int a : dij) {
                    for (int b : dij) {
                        if (inRange(i + a, j + b) && statuses[i + a][j + b] == -1) {
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
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
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
        ArrayList<int[]> possible = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (!visited[i][j] && !flagged[i][j] && statuses[i][j] != -1) {
                    possible.add(new int[] {i, j});
                    count++;
                }
            }
        }
        if (count == 0) {
            return false;
        }
        int index = Global.rand.nextInt(count);
        revealSpot(possible.get(index)[0], possible.get(index)[1]);
        return true;
    }

    public void placeMine(int i, int j) {
        int[] dij = {-1, 0, 1};

        statuses[i][j] = -1;

        for (int a : dij) {
            for (int b : dij) {
                if (inRange(i + a, j + b) && statuses[i + a][j + b] != -1) {
                    statuses[i + a][j + b]++;
                }
            }
        }
    }

    public boolean getMine(int i, int j) {
        return statuses[i][j] == -1;
    }

    public int[][] getStatuses() {
        return statuses;
    }

    public void setStatuses(int[][] statuses) {
        for (int i = 0; i < dim; i++) {
            System.arraycopy(statuses[i], 0, this.statuses[i], 0, dim);
        }
    }

    public void revealSpot(int i, int j) {
        if (visited[i][j]) return;
        visited[i][j] = true;

        if (statuses[i][j] == 0) {
            int[] dij = {-1, 0, 1};
            for (int a : dij) {
                for (int b : dij) {
                    if (inRange(i + a, j + b) && statuses[i + a][j + b] != -1) {
                        revealSpot(i + a, j + b);
                    }
                }
            }
        }
    }

    public GameStatus getGameState() {
        boolean uncoveredAllNonBombs = true;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (statuses[i][j] != -1 && !visited[i][j]) {
                    uncoveredAllNonBombs = false;
                }
                if (statuses[i][j] == -1 && visited[i][j]) {
                    return GameStatus.LOST;
                }
            }
        }

        return uncoveredAllNonBombs ? GameStatus.WON : GameStatus.ONGOING;
    }

    public void toggleFlag(int i, int j) {
        flagged[i][j] = !flagged[i][j];
    }

    public int getDimension() {
        return dim;
    }
}