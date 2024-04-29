package GameModes;

import BoardUtil.*;
import GraphicsUtil.IconHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Game {
    protected MinesweeperBoard board;
    protected GameStatus status;
    protected IconHandler iconHandler;
    protected boolean firstClick;
    protected Thread backgroundTask;
    protected final Map<String, ArrayList<Color>> colorMap = Map.of(
            "GRASS", new ArrayList<>(List.of(new Color(172, 208, 94))),
            "SAND", new ArrayList<>(List.of(new Color(215, 184, 153))),
            "WATER", new ArrayList<>(List.of(new Color(147, 195, 242))),
            "MINE", new ArrayList<>(List.of(new Color(255, 0, 0)))
    );
    private final double COLOR_OFFSET = 0.97;

    public Game(BoardType boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        newGame(boardType, dim, mines, clusteringThreshold, h, w);

        for (String key : colorMap.keySet()) {
            for (int i = 1; i < 4; i++) {
                colorMap.get(key).add(new Color(
                        (int) (colorMap.get(key).get(i - 1).getRed() * COLOR_OFFSET),
                        (int) (colorMap.get(key).get(i - 1).getGreen() * COLOR_OFFSET),
                        (int) (colorMap.get(key).get(i - 1).getBlue() * COLOR_OFFSET)
                ));
            }
        }
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
