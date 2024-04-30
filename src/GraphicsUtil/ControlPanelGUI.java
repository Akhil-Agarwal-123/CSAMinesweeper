package GraphicsUtil;

import GameModes.*;
import Global.Global;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import GraphicsUtil.Square.SquareBoardGUI;
import GraphicsUtil.Triangle.TriangleBoardGUI;

import javax.swing.*;
import java.util.Map;
import java.util.Objects;

public class ControlPanelGUI extends JPanel {
    private final JSlider gridSizeSlider;
    private final JSlider bombPercentageSlider;
    private final JSlider clusterThresholdSlider;
    private final int MAX_CLUSTER_THRESHOLD = 6;
    private final JButton newGameButton;
    private final JButton hintButton;
    private final JComboBox<String> boardShapeDropdown, gameModeDropdown;

    private final Map<String, Class<? extends BoardGUI>> BOARD_SHAPES = Map.of(
            "Square Board", SquareBoardGUI.class,
            "Hexagonal Board", HexagonBoardGUI.class,
            "Triangular Board", TriangleBoardGUI.class
    );
    private final Map<String, Class<? extends Game>> GAME_MODES = Map.of(
            "Original Mode", NormalModeGame.class,
            "Anti-Flag Mode", AntiFlagModeGame.class,
            "Crazy House Mode", CrazyHouseModeGame.class,
            "Mine Tick Mode", MineTickModeGame.class,
            "Automated Board Mode", AutomatedBoardModeGame.class
    );

    public ControlPanelGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boardShapeDropdown = new JComboBox<>(BOARD_SHAPES.keySet().toArray(new String[0]));
        boardShapeDropdown.setSelectedItem(BOARD_SHAPES.keySet().iterator().next());

        gameModeDropdown = new JComboBox<>(GAME_MODES.keySet().toArray(new String[0]));
        gameModeDropdown.setSelectedItem(GAME_MODES.keySet().iterator().next());

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
    }

    public String getBoardType() {
        return Objects.requireNonNull(boardShapeDropdown.getSelectedItem()).toString();
    }
    public String getGameMode() {
        return Objects.requireNonNull(gameModeDropdown.getSelectedItem()).toString();
    }

    public void newGame() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }
}
