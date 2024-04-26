package GraphicsUtil;

import BoardUtil.BoardType;
import BoardUtil.GameStatus;
import Global.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BoardGUI extends JPanel {
    private final JLabel[][] boardIcons;
    private BoardType boardType;
    public BoardGUI(BoardType type) {
        boardType = type;

        if (type == BoardType.SQUARE) {
            setLayout(new GridLayout(Global.game.getDimension(), Global.game.getDimension()));
        } else if (type == BoardType.HEXAGON) {
            double widthOfEach = Math.max(800, getWidth())/(Global.game.getDimension() + 0.5);
            double heightOfEach = Math.max(700, getHeight())/(0.25 + 0.75 * Global.game.getDimension());
            HexagonLayout layout = new HexagonLayout();
            layout.setWidthHeight(widthOfEach, heightOfEach);
            setLayout(layout);
        } else {
            // ERROR
            setLayout(null);
        }

        boardIcons = new JLabel[Global.game.getDimension()][Global.game.getDimension()];
        for (int i = 0; i < Global.game.getDimension(); i++) {
            for (int j = 0; j < Global.game.getDimension(); j++) {
                if (type == BoardType.HEXAGON) boardIcons[i][j] = new HexagonLabel(Global.game.getIcon(i, j));
                else boardIcons[i][j] = new JLabel(Global.game.getIcon(i, j));

                boardIcons[i][j].setBackground(Global.game.getBackgroundColor(i, j));
                boardIcons[i][j].setOpaque(true);

//                if (type == BoardType.HEXAGON) {
//                    boardIcons[i][j].setBounds(
//                            (int)(j * widthOfEach + (j % 2 == 0 ? 0 : widthOfEach/2)),
//                            (int)(i * heightOfEach),
//                            (int) widthOfEach,
//                            (int) heightOfEach);
//                }

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
                        update();
                    }
                });
                if (boardType == BoardType.HEXAGON) {
                    add(boardIcons[i][j]);
                } else {
                    add(boardIcons[i][j]);
                }
            }
        }
    }
    public void update() {
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
