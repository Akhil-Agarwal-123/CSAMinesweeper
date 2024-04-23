package GameModes;

import BoardUtil.GameStatus;

import javax.swing.*;
import java.awt.*;

public class CrazyHouseModeGame extends Game {
    public CrazyHouseModeGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        super(dim, mines, clusteringThreshold, h, w);
    }

    protected void onFirstClick(int i, int j) {
        board.regenUntilPlayable(i, j);
        normalClick(i, j);
    }

    protected void normalClick(int i, int j) {
        board.revealSpot(i, j);
    }

    public void rightClick(int i, int j) {
        board.toggleFlag(i, j);
    }

    public boolean hint() {
        return board.hint();
    }

    public void updateGameStatus() {
        status = board.getGameState();
    }

    public Color getBackgroundColor(int i, int j) {
        if (status == GameStatus.WON && !board.getStringFor(i, j, false).equals("B")) {
            return (i + j) % 2 == 0 ?
                    new Color(147, 195, 242) :
                    new Color(156, 200, 245);
        }
        return (i + j) % 2 == 0 ?
                new Color(172, 208, 94) :
                new Color(179, 214, 101);
    }

    public ImageIcon getIcon(int i, int j) {
        if (status == GameStatus.WON)
            return iconHandler.getIcon("-");
        return iconHandler.getIcon(board.getStringFor(i, j, status == GameStatus.ONGOING));
    }
}
