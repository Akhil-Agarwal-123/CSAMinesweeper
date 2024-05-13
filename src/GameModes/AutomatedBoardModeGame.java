package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;

/**
 * Game Mode: Cells of the board reveal themselves with a set time interval, having a 5% chance of being a bomb
 */
public class AutomatedBoardModeGame extends NormalModeGame {
    /**
     * Constructor for Automated Board mode
     * @param boardType the type of board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @param h the height of the board
     * @param w the width of the board
     * @author Arjun
     */
    public AutomatedBoardModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);

        final int TIME_INTERVAL = 5;  // seconds
        final double CHANCE_BOMB = 0.05;

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
                ArrayList<int[]> unvisitedCells = new ArrayList<>();
                for (int a = 0; a < board.getDimension(); a++) {
                    for (int b = 0; b < board.getDimension(); b++) {
                        if (!board.getVisited(a, b)) {
                            if (board.getMine(a, b)) {
                                unvisitedMines.add(new int[]{a, b});
                            } else {
                                unvisitedCells.add(new int[]{a, b});
                            }
                        }
                    }
                }

                if (Global.rand.nextDouble() < CHANCE_BOMB && !unvisitedMines.isEmpty()) {
                    int[] randomSpot = unvisitedMines.get(Global.rand.nextInt(unvisitedMines.size()));
                    normalClick(randomSpot[0], randomSpot[1]);
                } else if (!unvisitedCells.isEmpty()) {
                    int[] randomSpot = unvisitedCells.get(Global.rand.nextInt(unvisitedCells.size()));
                    normalClick(randomSpot[0], randomSpot[1]);
                }

                if (Global.minesweeperGUI != null)
                    Global.minesweeperGUI.boardGUI.update();
            }
        });
    }
}
