package GraphicsUtil;

import GameModes.*;
import Global.Global;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import GraphicsUtil.Square.SquareBoardGUI;
import GraphicsUtil.Triangle.TriangleBoardGUI;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

/**
 * Handler for all control panel GUI functionality
 */
public class ControlPanelGUI extends JPanel {
    /**
     * The JSlider for the grid size
     */
    private final JSlider gridSizeSlider;
    /**
     * The JSlider for the bomb percentage
     */
    private final JSlider bombPercentageSlider;
    /**
     * The JSlider for the cluster threshold
     */
    private final JSlider clusterThresholdSlider;
    /**
     * The max true cluster threshold that can be reached
     */
    private final int MAX_CLUSTER_THRESHOLD = 6;
    /**
     * The JButton for the new game button
     */
    private final JButton newGameButton;
    /**
     * The JButton for the hint button
     */
    private final JButton hintButton;
    /**
     * The board shape dropdown
     */
    private final JComboBox<String> boardShapeDropdown;
    /**
     * The game mode dropdown
     */
    private final JComboBox<String> gameModeDropdown;
    /**
     * The JLabel for the timer
     */
    private final JLabel timer;
    /**
     * The JLabel for the flag counter
     */
    private final JLabel flagCounter;

    /**
     * A map to convert the string displayed in the options to the board gui class
     */
    private final Map<String, Class<? extends BoardGUI>> BOARD_SHAPES = Map.of(
            "Square Board", SquareBoardGUI.class,
            "Hexagonal Board", HexagonBoardGUI.class,
            "Triangular Board", TriangleBoardGUI.class
    );
    /**
     * A map to convert the string displayed in the option to the game mode class
     */
    private final Map<String, Class<? extends Game>> GAME_MODES = Map.of(
            "Original Mode", NormalModeGame.class,
            "Anti-Flag Mode", AntiFlagModeGame.class,
            "Crazy House Mode", CrazyHouseModeGame.class,
            "Mine Tick Mode", MineTickModeGame.class,
            "Automated Board Mode", AutomatedBoardModeGame.class,
            "Climate Change Mode", ClimateChangeModeGame.class,
            "Build a Wall Mode", BuildAWallModeGame.class,
            "Atomic Mode", AtomicModeGame.class
    );

    /**
     * Creates a control panel GUI
     * @author Arjun
     */
    public ControlPanelGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boardShapeDropdown = new JComboBox<>(BOARD_SHAPES.keySet().toArray(new String[0]));
        boardShapeDropdown.setSelectedItem("Square Board");

        gameModeDropdown = new JComboBox<>(GAME_MODES.keySet().toArray(new String[0]));
        gameModeDropdown.setSelectedItem("Original Mode");

        gridSizeSlider = new JSlider(2, 100, 10);
        JLabel gridSizeLabel = new JLabel("Grid Size: " + gridSizeSlider.getValue());
        gridSizeSlider.setPaintTrack(true);
        gridSizeSlider.addChangeListener(e -> {
            int gridSize = gridSizeSlider.getValue();
            gridSizeLabel.setText("Grid Size: " + gridSize);
        });

        bombPercentageSlider = new JSlider(0, 100, 20);
        JLabel bombPercentageLabel = new JLabel("Bomb Percentage: " + bombPercentageSlider.getValue() + "%");
        bombPercentageSlider.setPaintTrack(true);
        bombPercentageSlider.addChangeListener(e -> {
            int bombPercentage = bombPercentageSlider.getValue();
            bombPercentageLabel.setText("Bomb Percentage: " + bombPercentage + "%");
        });

        clusterThresholdSlider = new JSlider(0, 100, 0);
        JLabel clusterThresholdLabel = new JLabel("Cluster Threshold: " + clusterThresholdSlider.getValue());
        clusterThresholdSlider.setPaintTrack(true);
        clusterThresholdSlider.addChangeListener(e -> {
            int clusterThreshold = clusterThresholdSlider.getValue();
            clusterThresholdLabel.setText("Cluster Threshold: " + clusterThreshold);
        });

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> newGame());

        hintButton = new JButton("Hint");
        hintButton.addActionListener(e -> {
            if (!Global.game.hint()) {
                JOptionPane.showMessageDialog(this, "No hints available.");
            }
            Global.minesweeperGUI.boardGUI.update();
        });

        timer = new JLabel("Time: ");

        flagCounter = new JLabel("Remaining flags: ");

        add(boardShapeDropdown);
        add(gameModeDropdown);
        add(gridSizeSlider);
        add(gridSizeLabel);
        add(bombPercentageSlider);
        add(bombPercentageLabel);
        add(clusterThresholdSlider);
        add(clusterThresholdLabel);
        add(newGameButton);
        add(hintButton);
        add(timer);
        add(flagCounter);
    }

    /**
     * Updates the timer
     * @param time the time to update the timer to
     * @author David
     */
    public void updateTimer(double time) {
        int a = (int) (time / 1000);
        timer.setText("Time: " + a);
        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }

    /**
     * Updates the flag counter displayed to a certain number
     * @param n the current flag count
     * @author David & Kushaan
     */
    public void updateFlagCounter(int n) {
        flagCounter.setText("Remaining flags: " + n);
        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }

    /**
     * Returns the board type
     * @return the board type
     * @author David
     */
    public String getBoardType() {
        return Objects.requireNonNull(boardShapeDropdown.getSelectedItem()).toString();
    }

    /**
     * Returns the game mode
     * @return the game mode
     * @author Kushaan
     */
    public String getGameMode() {
        return Objects.requireNonNull(gameModeDropdown.getSelectedItem()).toString();
    }

    /**
     * Starts a new game
     * @author Arjun and Akhil
     */
    public void newGame() {
        if (Global.game != null) {
            Global.game.endAllBackgroundTasks();
        }
        int gridSize = Integer.parseInt(gridSizeSlider.getValue() + "");
        int bombPercentage = Integer.parseInt(bombPercentageSlider.getValue() + "");
        int clusterThreshold = Integer.parseInt(clusterThresholdSlider.getValue() + "");

        int numBombs = (int) (gridSize * gridSize * bombPercentage / 100.0);

        try {
            Global.game = GAME_MODES.get(getGameMode())
                    .getDeclaredConstructor(Class.class, int.class, int.class, double.class, int.class, int.class)
                    .newInstance(
                            BOARD_SHAPES.get(getBoardType()),
                            gridSize,
                            numBombs,
                            clusterThreshold * (double) MAX_CLUSTER_THRESHOLD / 100.0,
                            Global.minesweeperGUI.getHeight(),
                            Global.minesweeperGUI.getWidth()
                    );

            if (Global.minesweeperGUI.boardGUI != null)
                Global.minesweeperGUI.remove(Global.minesweeperGUI.boardGUI);

            Global.minesweeperGUI.boardGUI = BOARD_SHAPES.get(getBoardType())
                    .getDeclaredConstructor()
                    .newInstance();

            Global.minesweeperGUI.add(Global.minesweeperGUI.boardGUI);

            updateFlagCounter(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }
}
