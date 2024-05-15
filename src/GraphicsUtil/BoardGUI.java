package GraphicsUtil;

import BoardUtil.GameStatus;
import Global.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Handler for all board GUI functionality
 */
public abstract class BoardGUI extends JPanel {
    /**
     * The board icons of each cell
     */
    private final JLabel[][] boardIcons;

    /**
     * Creates a board GUI
     * @author Akhil
     */
    public BoardGUI() {
        setLayout(getLayoutManager());

        boardIcons = new JLabel[Global.game.getDimension()][Global.game.getDimension()];
        for (int i = 0; i < Global.game.getDimension(); i++) {
            for (int j = 0; j < Global.game.getDimension(); j++) {
                boardIcons[i][j] = getLabel(i, j); // Global.game.getIcon(i, j));

                boardIcons[i][j].setBackground(Global.game.getBackgroundColor(i, j));
                boardIcons[i][j].setOpaque(true);

                int finalI = i;
                int finalJ = j;
                boardIcons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!Global.game.getVisited(finalI, finalJ)) {
                                Global.game.leftClick(finalI, finalJ);
                            }
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            Global.game.rightClick(finalI, finalJ);
                        }
                        Global.minesweeperGUI.controlPanelGUI.updateFlagCounter(Global.game.getBoard().getRemainingFlagCount());
                        update();
                    }
                });
                add(boardIcons[i][j]);
            }
        }
    }

    /**
     * Returns the layout manager of the board
     * @return the layout manager of the board
     * @author Akhil
     */
    protected abstract LayoutManager getLayoutManager();

    /**
     * Returns the label at a certain position on the board
     * @param i the row of the label
     * @param j the column of the label
     * @return the label at a certain position on the board
     * @author Akhil
     */
    protected abstract JLabel getLabel(int i, int j);

    /**
     * Updates the board
     * @author Akhil
     */
    public void update() {
        if (Global.game.getLastUpdatedGameStatus() != GameStatus.ONGOING) {
            return;
        }
        Global.game.updateGameStatus();

        if (Global.game.getLastUpdatedGameStatus() == GameStatus.WON) {
            JOptionPane.showMessageDialog(this, "You won!");
        } else if (Global.game.getLastUpdatedGameStatus() == GameStatus.LOST) {
            JOptionPane.showMessageDialog(this, "You lost!");
        }

        for (int i = 0; i < boardIcons.length; i++) {
            for (int j = 0; j < boardIcons[i].length; j++) {
                boardIcons[i][j].setBackground(Global.game.getBackgroundColor(i, j));
                boardIcons[i][j].setIcon(Global.game.getIcon(i, j));
            }
        }
        revalidate();
        repaint();
    }
}
