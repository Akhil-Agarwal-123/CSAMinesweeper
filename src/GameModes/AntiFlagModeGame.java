package GameModes;

import GraphicsUtil.BoardGUI;

public class AntiFlagModeGame extends NormalModeGame {
    public AntiFlagModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    public void rightClick(int i, int j) {
        javax.swing.JOptionPane.showMessageDialog(null, "You can't flag in Anti-Flag mode!");
    }
}
