package GameModes;

import BoardUtil.BoardType;
import BoardUtil.GameStatus;

import javax.swing.*;
import java.awt.*;

public class NormalModeGame extends Game {
    public NormalModeGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        super(BoardType.SQUARE, dim, mines, clusteringThreshold, h, w);
    }
    protected NormalModeGame(BoardType boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
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

    public Color getBackgroundColor(int i, int j) {
        if (status != GameStatus.ONGOING) {
            // if game is over display mines as red
            if (board.getMine(i, j)) {
                Color c = colorMap.get("MINE").get(0);
                for (int k = 0; k < board.getCellId(i, j); k++) {
                    // make it slightly darker (multiply rgbs by 0.95)
                    c = new Color((int) (c.getRed() * 0.95), (int) (c.getGreen() * 0.95), (int) (c.getBlue() * 0.95));
                }
                return c;
                // return colorMap.get("MINE").get(board.getCellId(i, j));
            }
        }

        if (status == GameStatus.WON) {
            // display cells as blue if you win
            if (!board.getMine(i, j)) {
                Color c = colorMap.get("WATER").get(0);
                for (int k = 0; k < board.getCellId(i, j); k++) {
                    c = new Color((int) (c.getRed() * 0.95), (int) (c.getGreen() * 0.95), (int) (c.getBlue() * 0.95));
                }
                return c;
                // return colorMap.get("WATER").get(board.getCellId(i, j));
            }
        }

        if (board.getVisited(i, j)) {
            // visited cells are light brown
            Color c = colorMap.get("SAND").get(0);
            for (int k = 0; k < board.getCellId(i, j); k++) {
                c = new Color((int) (c.getRed() * 0.95), (int) (c.getGreen() * 0.95), (int) (c.getBlue() * 0.95));
            }
            return c;
            // return colorMap.get("SAND").get(board.getCellId(i, j));
        } else {
            // unvisited cells are green
            Color c = colorMap.get("GRASS").get(0);
            for (int k = 0; k < board.getCellId(i, j); k++) {
                c = new Color((int) (c.getRed() * 0.95), (int) (c.getGreen() * 0.95), (int) (c.getBlue() * 0.95));
            }
            return c;
            // return colorMap.get("GRASS").get(board.getCellId(i, j));
        }
    }

    public ImageIcon getIcon(int i, int j) {
        if (status == GameStatus.WON)
            return iconHandler.getIcon("-");
        return iconHandler.getIcon(board.getStringFor(i, j, status == GameStatus.ONGOING));
    }
}
