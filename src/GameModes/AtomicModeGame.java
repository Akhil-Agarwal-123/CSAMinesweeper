package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;

public class AtomicModeGame extends NormalModeGame {
    public AtomicModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);

        final int TIME_INTERVAL = 5;
        final int NEIGHBOR_RADIUS = 1;

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
                            if (board.getMine(a, b)) {
                                unvisitedMines.add(new int[]{a, b});
                            }
                        }
                    }
                }

                if (unvisitedMines.isEmpty()) {
                    continue;
                }

                int[] randomSpot = unvisitedMines.get(Global.rand.nextInt(unvisitedMines.size()));

                Global.minesweeperGUI.boardGUI.update();
            }
        });
    }
}
