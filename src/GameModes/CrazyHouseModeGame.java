package GameModes;

import BoardUtil.GameStatus;

import javax.swing.*;
import java.awt.*;

public class CrazyHouseModeGame extends NormalModeGame{
    public CrazyHouseModeGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        super(dim, mines, clusteringThreshold, h, w);
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
        int[] dij = new int[] { -1, 0, 1 };
        for (int a = 0; a < board.getDimension(); a++) {
            for (int b = 0; b < board.getDimension(); b++) {
                if (board.getVisited(a, b)) {
                    mineMask[a][b] = 0;
                } else {
                    mineMask[a][b] = 1;
                    for (int di : dij) {
                        for (int dj : dij) {
                            if (board.inRange(a + di, b + dj) && board.getVisited(a + di, b + dj)) {
                                mineMask[a][b] = 2.5;
                            }
                        }
                    }
                }
            }
        }
        board.genBoard(mineMask);

        for (int a = 0; a < board.getDimension(); a++) {
            for (int b = 0; b < board.getDimension(); b++) {
                board.setVisited(a, b, false);
            }
        }
        for (int a = 0; a < board.getDimension(); a++) {
            for (int b = 0; b < board.getDimension(); b++) {
                if (mineMask[a][b] == 0 && !board.getVisited(a, b)) {
                    board.revealSpot(a, b);
                }
            }
        }
    }
}
