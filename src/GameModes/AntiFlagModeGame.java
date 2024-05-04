package GameModes;

import GraphicsUtil.BoardGUI;

public class AntiFlagModeGame extends NormalModeGame {
    /**
     * Constructor for Anti-Flag mode
     * @param boardType the type of board
     * @param dim the dimension of the board
     * @param mines the number of mines on the board
     * @param clusteringThreshold the clustering threshold value
     * @param h the height of the board
     * @param w the width of the board
     */
    public AntiFlagModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    /**
     * Right click handler for Anti-Flag mode
     * @param i the row index
     * @param j the column index
     */
    public void rightClick(int i, int j) {
        javax.swing.JOptionPane.showMessageDialog(null, "You can't flag in Anti-Flag mode!");
    }
}
