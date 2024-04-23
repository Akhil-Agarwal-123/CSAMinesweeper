package GameModes;

import BoardUtil.GameStatus;

import javax.swing.*;
import java.awt.*;

public class AntiFlagModeGame extends NormalModeGame {
    public AntiFlagModeGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        super(dim, mines, clusteringThreshold, h, w);
    }

    public void rightClick(int i, int j) {
        javax.swing.JOptionPane.showMessageDialog(null, "You can't flag in Anti-Flag mode!");
    }
}
