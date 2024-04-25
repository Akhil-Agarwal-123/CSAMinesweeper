package GameModes;

import BoardUtil.GameStatus;
import BoardUtil.MinesweeperBoard;
import BoardUtil.SquareBoard;
import GraphicsUtil.IconHandler;

import javax.swing.*;
import java.awt.*;

public abstract class Game {
    protected MinesweeperBoard board;
    protected GameStatus status;
    protected IconHandler iconHandler;
    protected boolean firstClick;

    public Game(int dim, int mines, double clusteringThreshold, int h, int w) {
        newGame(dim, mines, clusteringThreshold, h, w);
    }

    public void newGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        board = new SquareBoard(dim, mines, clusteringThreshold);
        status = GameStatus.ONGOING;
        iconHandler = new IconHandler(h, w, dim);
        firstClick = true;
    }

    public void leftClick(int i, int j) {
        if (firstClick) onFirstClick(i, j);
        else normalClick(i, j);

        firstClick = false;
    }

    public boolean getVisited(int i, int j) {
        return board.getVisited(i, j);
    }

    protected abstract void onFirstClick(int i, int j);

    protected abstract void normalClick(int i, int j);

    public abstract void rightClick(int i, int j);

    public abstract boolean hint();

    public abstract void updateGameStatus();

    public GameStatus getLastUpdatedGameStatus() {
        return status;
    }

    public abstract Color getBackgroundColor(int i, int j);

    public abstract ImageIcon getIcon(int i, int j);

    public int getDimension() {
        return board.getDimension();
    }
}
