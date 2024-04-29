package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.util.ArrayList;

public class AutomatedBoardModeGame extends NormalModeGame {
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

                Global.minesweeperGUI.boardGUI.update();
            }
        });
    }
}
