package GraphicsUtil;

import GameModes.*;
import Global.Global;

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
    private final JComboBox<String> gameModeDropdown;

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
                    .getDeclaredConstructor(int.class, int.class, double.class, int.class, int.class)
                    .newInstance(
                            gridSize,
                            numBombs,
                            clusterThreshold * (double) MAX_CLUSTER_THRESHOLD / 100.0,
                            Global.minesweeperGUI.getHeight(),
                            Global.minesweeperGUI.getWidth()
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Global.minesweeperGUI.boardGUI != null)
            Global.minesweeperGUI.remove(Global.minesweeperGUI.boardGUI);

        Global.minesweeperGUI.boardGUI = new BoardGUI();
        Global.minesweeperGUI.add(Global.minesweeperGUI.boardGUI);

        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }
}
