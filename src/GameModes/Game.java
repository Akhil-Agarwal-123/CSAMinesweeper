package GameModes;

import BoardUtil.*;
import GraphicsUtil.IconHandler;

import javax.swing.*;
import java.awt.*;

public abstract class Game {
    protected MinesweeperBoard board;
    protected GameStatus status;
    protected IconHandler iconHandler;
    protected boolean firstClick;
    protected Thread backgroundTask;

    public Game(BoardType boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        newGame(boardType, dim, mines, clusteringThreshold, h, w);
    }

    public void newGame(BoardType boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        board = boardType == BoardType.SQUARE ? new SquareBoard(dim, mines, clusteringThreshold) :
                new HexagonBoard(dim, mines, clusteringThreshold);
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

    protected void onFirstClick(int i, int j) {
        board.genBoard(i, j);
        normalClick(i, j);

        if (backgroundTask != null) {
            backgroundTask.start();
        }
    }

    protected abstract void normalClick(int i, int j);

    public abstract void rightClick(int i, int j);

    public abstract boolean hint();

    public void updateGameStatus() {
        status = board.getGameState();

        if (status != GameStatus.ONGOING && backgroundTask != null) {
            backgroundTask.interrupt();
        }
    }

    public GameStatus getLastUpdatedGameStatus() {
        return status;
    }

    public abstract Color getBackgroundColor(int i, int j);

    public abstract ImageIcon getIcon(int i, int j);

    public int getDimension() {
        return board.getDimension();
    }
}
