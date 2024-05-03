package BoardUtil;

import Global.Global;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MinesweeperBoard {
    protected int[][] statuses;
    protected final int dim;
    protected int mines;
    protected final double clusteringThreshold;
    protected boolean[][] visited, flagged, walled;
    protected int flagCount;

    public MinesweeperBoard(int dim, int mines, double clusteringThreshold) {
        statuses = new int[dim][dim];
        visited = new boolean[dim][dim];
        flagged = new boolean[dim][dim];
        walled = new boolean[dim][dim];
        this.dim = dim;
        this.mines = mines;
        this.clusteringThreshold = clusteringThreshold;
        flagCount = 0;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                statuses[i][j] = 0;
                visited[i][j] = false;
                flagged[i][j] = false;
                walled[i][j] = false;
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

    public ArrayList<int[]> getValidNeighbors(int i, int j) {
        ArrayList<int[]> ret = new ArrayList<>();
        ArrayList<int[]> allNeighbors = getAllNeighbors(i, j);
        for (int[] a : allNeighbors) {
            if (!walled[a[0]][a[1]]) {
                ret.add(a);
            }
        }
        return ret;
    }

    protected abstract ArrayList<int[]> getAllNeighbors(int i, int j);

    public abstract int getCellId(int i, int j);

    public int[][] getStatuses() {
        return statuses;
    }

    public void setStatues(int[][] statuses) {
        this.statuses = statuses;
    }

    public int getStatus(int i, int j) {
        return statuses[i][j];
    }

    public void setStatus(int i, int j, int value) {
        statuses[i][j] = value;
    }

    public boolean getFlagged(int i, int j) {
        return flagged[i][j];
    }

    public boolean getVisited(int i, int j) {
        return visited[i][j];
    }

    public boolean[][] getAllVisited() {
        return visited;
    }

    public boolean[][] getAllFlagged() {
        return flagged;
    }

    public void setAllFlagged(boolean[][] flagged) {
        this.flagged = flagged;
    }

    public void setFlagged(int i, int j, boolean value) {
        flagged[i][j] = value;

        if (flagged[i][j] != value) {
            flagCount += value ? 1 : -1;
        }
    }

    public void setVisited(int i, int j, boolean value) {
        visited[i][j] = value;
    }

    public void setAllVisited(boolean[][] visited) {
        this.visited = visited;
    }

    public void setWalled(int i, int j, boolean value) {
        walled[i][j] = value;
        setVisited(i, j, true);
    }

    public boolean getWalled(int i, int j) {
        return walled[i][j];
    }

    public void expandZeros() {
        boolean[][] prevVisited = new boolean[dim][dim];

        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
                prevVisited[a][b] = visited[a][b];
                visited[a][b] = false;
            }
        }

        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
                if (prevVisited[a][b]) {
                    revealSpot(a, b);
                }
            }
        }
    }

    public void genBoard(int i, int j) {
        double[][] mineMask = new double[dim][dim];
        for (int a = 0; a < dim; a++) {
            for (int b = 0; b < dim; b++) {
                mineMask[a][b] = 1;
            }
        }

        // 0 probability for spawning a mine on a clicked cell
        ArrayList<int[]> neighbors = getValidNeighbors(i, j);
        for (int[] neighbor : neighbors) {
            int a = neighbor[0];
            int b = neighbor[1];
            mineMask[a][b] = 0;
        }
        mineMask[i][j] = 0;

        generateBoard(mineMask);
    }

    public void genBoard(double[][] mineMask) {
        generateBoard(mineMask);
    }

    protected void generateBoard(double[][] mineMask) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                statuses[i][j] = 0; // not mine
            }
        }

        placeMines(mineMask, mines);
        calculateNumbers();
    }

    public void placeMines(double[][] mineMask, int mines) {
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
            ArrayList<int[]> neighbors = getValidNeighbors(r, c);
            for (int[] neighbor : neighbors) {
                int a = neighbor[0];
                int b = neighbor[1];
                if (thresholds[a * dim + b] > 0) {
                    thresholds[a * dim + b] += clusteringThreshold;
                }
            }
        }
    }

    public void calculateNumbers() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (statuses[i][j] != -1) {
                    int count = 0;

                    ArrayList<int[]> dij = getValidNeighbors(i, j);
                    for (int[] neighbor : dij) {
                        int a = neighbor[0];
                        int b = neighbor[1];
                        if (statuses[a][b] == -1) {
                            count++;
                        }
                    }

                    statuses[i][j] = count;
                }
            }
        }
    }

    public void printBoardAllRevealed() {
        printBoard(false);
    }

    public void printBoardHidden() {
        printBoard(true);
    }

    protected abstract void printBoard(boolean hidden);

    public String getStringFor(int i, int j, boolean hidden) {
        if (walled[i][j]) return "W";
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
        statuses[i][j] = -1;
        mines++;

        ArrayList<int[]> neighbors = getValidNeighbors(i, j);
        for (int[] neighbor : neighbors) {
            int a = neighbor[0];
            int b = neighbor[1];
            if (statuses[a][b] != -1) {
                statuses[a][b]++;
            }
        }
    }

    public boolean getMine(int i, int j) {
        return statuses[i][j] == -1;
    }

    public void revealSpot(int i, int j) {
        if (visited[i][j]) return;
        visited[i][j] = true;

        if (statuses[i][j] == 0) {
            ArrayList<int[]> neighbors = getValidNeighbors(i, j);

            for (int[] neighbor : neighbors) {
                int a = neighbor[0], b = neighbor[1];
                if (statuses[a][b] != -1) {
                    revealSpot(a, b);
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

    public int getNumMines() {
        return mines;
    }

    public double getClusteringThreshold() {
        return clusteringThreshold;
    }
}