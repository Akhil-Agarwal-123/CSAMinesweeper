package GameModes;

import BoardUtil.GameStatus;
import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

/**
 * Game Mode: Classic minesweeper
 */
public class NormalModeGame extends Game {
    /**
     * Constructor for Normal mode
     * @param boardType the type of board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @param h the height of the board
     * @param w the width of the board
     */
    public NormalModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    /**
     * Left click handler for Normal mode
     * @param i the row index
     * @param j the column index
     */
    protected void normalClick(int i, int j) {
        board.revealSpot(i, j);
    }

    /**
     * Right click handler for Normal mode
     * @param i the row index
     * @param j the column index
     */
    public void rightClick(int i, int j) {
        board.toggleFlag(i, j);
        Global.minesweeperGUI.controlPanelGUI.updateFlag(flags);
        flags++;
    }

    /**
     * Gets a hint for the user from the board
     * @return whether a hint was given
     */
    public boolean hint() {
        return board.hint();
    }

    /**
     * Gets the background color of a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the background color of the spot
     */
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

    /**
     * Gets the icon for a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the icon for the spot
     */
    public ImageIcon getIcon(int i, int j) {
        if (status == GameStatus.WON)
            return iconHandler.getIcon("-");
        return iconHandler.getIcon(board.getStringFor(i, j, status == GameStatus.ONGOING));
    }
}
