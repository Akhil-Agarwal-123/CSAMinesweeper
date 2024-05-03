package GameModes;

import BoardUtil.*;
import Global.Global;
import GraphicsUtil.BoardGUI;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import GraphicsUtil.IconHandler;
import GraphicsUtil.Square.SquareBoardGUI;
import GraphicsUtil.Triangle.TriangleBoardGUI;

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
    protected Thread timerTask;
    protected double currentTime;
    protected double startTime;
    protected final Map<String, ArrayList<Color>> colorMap = Map.of(
            "GRASS", new ArrayList<>(List.of(new Color(172, 208, 94))),
            "SAND", new ArrayList<>(List.of(new Color(215, 184, 153))),
            "WATER", new ArrayList<>(List.of(new Color(147, 195, 242))),
            "MINE", new ArrayList<>(List.of(new Color(255, 0, 0))),
            "WALL", new ArrayList<>(List.of(new Color(150, 150, 150)))
    );
    protected final Map<Class<? extends BoardGUI>, Class<? extends MinesweeperBoard>> BOARD_TYPES = Map.of(
            SquareBoardGUI.class, SquareBoard.class,
            HexagonBoardGUI.class, HexagonBoard.class,
            TriangleBoardGUI.class, TriangleBoard.class
    );

    private final double COLOR_OFFSET = 0.97;

    public Game(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
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

    public void newGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        try {
            board = BOARD_TYPES.get(boardType).getDeclaredConstructor(int.class, int.class, double.class)
                    .newInstance(dim, mines, clusteringThreshold);
        } catch (Exception e) {
            e.printStackTrace();
        }

        status = GameStatus.ONGOING;
        iconHandler = new IconHandler(h, w, dim);
        firstClick = true;
    }

    public void leftClick(int i, int j) {
        if (board.getWalled(i, j)) return;

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

        timerTask = new Thread(() -> {
            //
            startTime = System.currentTimeMillis();
            while (status == GameStatus.ONGOING) {
                try {
                    Thread.sleep(995);
                } catch (InterruptedException e) {
                    break;
                }
                if (status != GameStatus.ONGOING) {
                    break;
                }
                currentTime = System.currentTimeMillis() - startTime;
                Global.minesweeperGUI.controlPanelGUI.updateTimer(currentTime);
            }
        });
        timerTask.start();

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
