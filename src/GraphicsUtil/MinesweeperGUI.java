package GraphicsUtil;

import Global.Global;

import javax.swing.*;
import java.awt.*;

public class MinesweeperGUI extends JFrame {
    public BoardGUI boardGUI;
    public ControlPanelGUI controlPanelGUI;

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