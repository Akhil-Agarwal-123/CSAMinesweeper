package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;
import java.util.HashSet;

public class AtomicModeGame extends NormalModeGame {
    public AtomicModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);

        final int TIME_INTERVAL = 5;
        final int NEIGHBOR_RADIUS = Math.min(1 + board.getDimension()/15, 3);

        backgroundTask = new Thread(() -> {
            while (status == GameStatus.ONGOING) {
                try {
                    Thread.sleep(TIME_INTERVAL * 1000);
                } catch (InterruptedException e) {
                    break;
                }

                if (status != GameStatus.ONGOING) {
                    break;
                }

                ArrayList<int[]> unvisitedMines = new ArrayList<>();
                for (int a = 0; a < board.getDimension(); a++) {
                    for (int b = 0; b < board.getDimension(); b++) {
                        if (!board.getVisited(a, b)) {
                            if (board.getMine(a, b) && !board.getFlagged(a, b)) {
                                unvisitedMines.add(new int[]{a, b});
                            }
                        }
                    }
                }

                if (unvisitedMines.isEmpty()) {
                    continue;
                }

                int[] randomSpot = unvisitedMines.get(Global.rand.nextInt(unvisitedMines.size()));
                HashSet<int[]> neighbors = new HashSet<>();
                neighbors.add(randomSpot);
                int depth = 0;
                while (depth < NEIGHBOR_RADIUS) {
                    HashSet<int[]> newNeighbors = new HashSet<>();
                    for (int[] neighbor : neighbors) {
                        newNeighbors.addAll(board.getValidNeighbors(neighbor[0], neighbor[1]));
                    }
                    neighbors.addAll(newNeighbors);
                    depth++;
                }

                double[][] mineMask = new double[board.getDimension()][board.getDimension()];
                for (int i = 0; i < board.getDimension(); i++) {
                    for (int j = 0; j < board.getDimension(); j++) {
                        mineMask[i][j] = 0;
                    }
                }

                int prevMines = 0;
                for (int[] neighbor : neighbors) {
                    mineMask[neighbor[0]][neighbor[1]] = 1;
                    if (board.getMine(neighbor[0], neighbor[1])) {
                        prevMines++;
                    }
                    board.setStatus(neighbor[0], neighbor[1], 0);
                    board.setVisited(neighbor[0], neighbor[1], false);
                    board.setFlagged(neighbor[0], neighbor[1], false);

                    for (int[] deepNeighbor : board.getValidNeighbors(neighbor[0], neighbor[1])) {
                        board.setFlagged(deepNeighbor[0], deepNeighbor[1], false);
                    }
                }

                board.placeMines(mineMask, prevMines);
                board.calculateNumbers();
                board.expandZeros();

                Global.minesweeperGUI.boardGUI.update();
            }
        });
    }
}
