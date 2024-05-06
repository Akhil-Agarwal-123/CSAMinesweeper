package GraphicsUtil;

import Global.Global;

import javax.swing.*;
import java.awt.*;

/**
 * Overall GUI handler for entire screen
 */
public class MinesweeperGUI extends JFrame {
    /**
     * The board GUI handler
     */
    public BoardGUI boardGUI;
    /**
     * The control panel GUI handler
     */
    public ControlPanelGUI controlPanelGUI;

    /**
     * Creates a minesweeper GUI with a certain title
     * @param title the title of the minesweeper GUI
     */
    public MinesweeperGUI(String title) {
        super(title);
        setSize(1900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        Global.minesweeperGUI = this;

        controlPanelGUI = new ControlPanelGUI();
        add(controlPanelGUI, BorderLayout.WEST);
        controlPanelGUI.newGame();

        setLocationRelativeTo(null);
    }
}