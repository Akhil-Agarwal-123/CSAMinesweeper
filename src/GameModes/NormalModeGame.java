package GameModes;

import BoardUtil.GameStatus;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class NormalModeGame extends Game {

    public NormalModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
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
        if (board.getWalled(i, j)) {
            return colorMap.get("WALL").get(board.getCellId(i, j));
        }

        if (status != GameStatus.ONGOING) {
            // if game is over display mines as red
            if (board.getMine(i, j)) {
                return colorMap.get("MINE").get(board.getCellId(i, j));
            }
        }

        if (status == GameStatus.WON) {
            // display cells as blue if you win
            if (!board.getMine(i, j)) {
                return colorMap.get("WATER").get(board.getCellId(i, j));
            }
        }

        if (board.getVisited(i, j)) {
            // visited cells are light brown
            return colorMap.get("SAND").get(board.getCellId(i, j));
        } else {
            // unvisited cells are green
            return colorMap.get("GRASS").get(board.getCellId(i, j));
        }
    }

    public ImageIcon getIcon(int i, int j) {
        if (status == GameStatus.WON)
            return iconHandler.getIcon("-");
        return iconHandler.getIcon(board.getStringFor(i, j, status == GameStatus.ONGOING));
    }
}
