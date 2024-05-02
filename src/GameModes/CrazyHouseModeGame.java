package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;

public class CrazyHouseModeGame extends NormalModeGame {

    public CrazyHouseModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    protected void normalClick(int i, int j) {
        board.revealSpot(i, j);

        // flags won't be needed on new board
        board.clearFlags();

        // don't regen board if game is over
        GameStatus gameState = board.getGameState();
        if (gameState != GameStatus.ONGOING) {
            return;
        }

        // generate a new board with special mine mask
        double[][] mineMask = new double[board.getDimension()][board.getDimension()];
        for (int a = 0; a < board.getDimension(); a++) {
            for (int b = 0; b < board.getDimension(); b++) {
                if (board.getVisited(a, b)) {
                    mineMask[a][b] = 0;
                } else {
                    mineMask[a][b] = 1;
                    ArrayList<int[]> neighbors = board.getValidNeighbors(a, b);
                    for (int[] neighbor : neighbors) {
                        if (board.getVisited(neighbor[0], neighbor[1])) {
                            mineMask[a][b] = 2.5;
                        }
                    }
                }
            }
        }
        board.genBoard(mineMask);
        board.expandZeros();
    }
}
