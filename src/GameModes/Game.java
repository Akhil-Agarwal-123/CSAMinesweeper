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

/**
 * Abstract class to handle all gameplay
 */
public abstract class Game {
    /**
     * The board
     */
    protected MinesweeperBoard board;
    /**
     * The board status
     */
    protected GameStatus status;
    /**
     * The icon handler
     */
    protected IconHandler iconHandler;
    /**
     * Whether the first click has not occurred
     */
    protected boolean firstClick;
    /**
     * A background task to run in parallel with the game running
     */
    protected Thread backgroundTask;
    /**
     * A thread for handling the timer
     */
    protected Thread timerTask;
    /**
     * The current time calculated by the timer thread
     */
    protected double currentTime;
    /**
     * The start time of the timer
     */
    protected double startTime;
    /**
     * A map from a square theme to an arraylist storing variations of the colors to use on the board based on cell id
     */
    protected final Map<String, ArrayList<Color>> colorMap = Map.of(
            "GRASS", new ArrayList<>(List.of(new Color(172, 208, 94))),
            "SAND", new ArrayList<>(List.of(new Color(215, 184, 153))),
            "WATER", new ArrayList<>(List.of(new Color(147, 195, 242))),
            "MINE", new ArrayList<>(List.of(new Color(255, 0, 0))),
            "WALL", new ArrayList<>(List.of(new Color(150, 150, 150)))
    );
    /**
     * A map from the type of board GUI to the type of board
     */
    protected final Map<Class<? extends BoardGUI>, Class<? extends MinesweeperBoard>> BOARD_TYPES = Map.of(
            SquareBoardGUI.class, SquareBoard.class,
            HexagonBoardGUI.class, HexagonBoard.class,
            TriangleBoardGUI.class, TriangleBoard.class
    );
    /**
     * The amount to offset each square by within a theme
     */
    private final double COLOR_OFFSET = 0.97;

    /**
     * Constructor for Game
     * @param boardType the type of board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @param h the height of the board
     * @param w the width of the board
     * @author Arjun
     */
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

    /**
     * Starts a new game
     * @param boardType the type of board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @param h the height of the board
     * @param w the width of the board
     * @author Akhil
     */
    public void newGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        try {
            board = BOARD_TYPES.get(boardType).getDeclaredConstructor(int.class, int.class, double.class)
                    .newInstance(dim, mines, clusteringThreshold);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timerTask != null && timerTask.isAlive()) {
            timerTask.interrupt();
        }
        status = GameStatus.ONGOING;
        iconHandler = new IconHandler(h, w, dim);
        firstClick = true;
    }

    /**
     * Left click handler for Game
     * @param i the row index
     * @param j the column index
     * @author Akhil
     */
    public void leftClick(int i, int j) {
        if (board.getWalled(i, j)) return;

        if (firstClick) onFirstClick(i, j);
        else normalClick(i, j);

        firstClick = false;
    }

    /**
     * Gets the visited status of a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the visited status of the spot
     * @author David
     */
    public boolean getVisited(int i, int j) {
        return board.getVisited(i, j);
    }

    /**
     * Runs when the user does their first left click on the board
     * @param i the row index
     * @param j the column index
     * @author Arjun and David
     */
    protected void onFirstClick(int i, int j) {
        board.genBoard(i, j);
        normalClick(i, j);

        timerTask = new Thread(() -> {
            startTime = System.currentTimeMillis();
            while (status == GameStatus.ONGOING) {
                currentTime = System.currentTimeMillis() - startTime;
                if (Global.minesweeperGUI != null)
                    Global.minesweeperGUI.controlPanelGUI.updateTimer(currentTime);
                try {
                    Thread.sleep(995);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        timerTask.start();

        if (backgroundTask != null) {
            backgroundTask.start();
        }
    }

    /**
     * Left click handler for Game (when not first click)
     * @param i the row index
     * @param j the column index
     * @author Akhil
     */
    protected abstract void normalClick(int i, int j);

    /**
     * Right click handler for Game
     * @param i the row index
     * @param j the column index
     * @author Akhil
     */
    public abstract void rightClick(int i, int j);

    /**
     * Gets a hint for the user from the board
     * @return whether a hint was given
     * @author Akhil
     */
    public abstract boolean hint();

    /**
     * Gets the game status
     * @author Arjun
     */
    public void updateGameStatus() {
        status = board.getGameState();

        if (status != GameStatus.ONGOING && backgroundTask != null) {
            backgroundTask.interrupt();
        }
        if (status != GameStatus.ONGOING && timerTask != null) {
            timerTask.interrupt();
        }
    }

    /**
     * Gets the game status
     * @return the game status
     * @author David
     */
    public GameStatus getLastUpdatedGameStatus() {
        return status;
    }

    /**
     * Gets the background color of a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the background color of the spot
     * @author Akhil
     */
    public abstract Color getBackgroundColor(int i, int j);

    /**
     * Gets the icon for a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the icon for the spot
     * @author Akhil
     */
    public abstract ImageIcon getIcon(int i, int j);

    /**
     * Gets the dimension of the board
     * @return the dimension of the board
     * @author David
     */
    public int getDimension() {
        return board.getDimension();
    }

    /**
     * Gets the internal board
     * @return the internal board
     * @author David
     */
    public MinesweeperBoard getBoard() {
        return board;
    }

    /**
     * Ends all background tasks that are still occurring
     * @author Akhil
     */
    public void endAllBackgroundTasks() {
        if (timerTask != null && timerTask.isAlive()) {
            timerTask.interrupt();
        }
        if (backgroundTask != null && backgroundTask.isAlive()) {
            backgroundTask.interrupt();
        }
    }
}
