package GraphicsUtil;

import GameModes.NormalModeGame;
import Global.Global;

import javax.swing.*;

public class ControlPanelGUI extends JPanel {
    private final JSlider gridSizeSlider;
    private final JSlider bombPercentageSlider;
    private final JSlider clusterThresholdSlider;
    private final int MAX_CLUSTER_THRESHOLD = 6;
    private final JButton newGameButton;
    private final JButton hintButton;
    public ControlPanelGUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        add(gridSizeSlider);
        add(gridSizeLabel);
        add(bombPercentageSlider);
        add(bombPercentageLabel);
        add(clusterThresholdSlider);
        add(clusterThresholdLabel);
        add(newGameButton);
        add(hintButton);
    }

    public void newGame() {
        int gridSize = Integer.parseInt(gridSizeSlider.getValue() + "");
        int bombPercentage = Integer.parseInt(bombPercentageSlider.getValue() + "");
        int clusterThreshold = Integer.parseInt(clusterThresholdSlider.getValue() + "");

        int numBombs = (int) (gridSize * gridSize * bombPercentage / 100.0);

        Global.game = new NormalModeGame(gridSize, numBombs,
                clusterThreshold * MAX_CLUSTER_THRESHOLD / 100.0,
                Global.minesweeperGUI.getHeight(), Global.minesweeperGUI.getWidth());
        if (Global.minesweeperGUI.boardGUI != null)
            Global.minesweeperGUI.remove(Global.minesweeperGUI.boardGUI);

        Global.minesweeperGUI.boardGUI = new BoardGUI();
        Global.minesweeperGUI.add(Global.minesweeperGUI.boardGUI);

        Global.minesweeperGUI.revalidate();
        Global.minesweeperGUI.repaint();
    }
}
