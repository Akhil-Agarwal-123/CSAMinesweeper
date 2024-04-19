import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MinesweeperGUI extends JFrame implements ActionListener {
    private static MinesweeperBoard board;
    private boolean firstClick;
    private final JButton[][] buttons;

    private void updateButtonsFromBoard() {
        boolean won = board.won();
        boolean lost = board.lost();
        for (int k = 0; k < board.getDimension(); k++) {
            for (int l = 0; l < board.getDimension(); l++) {
                buttons[k][l].setText(won ? "W" : lost ? "L" : board.getStringFor(k, l, true));
                buttons[k][l].setEnabled(!won && !lost);
            }
        }
        repaint();
    }

    public MinesweeperGUI(String title) {
        super(title);
        firstClick = true;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(board.getDimension(), board.getDimension()));
        buttons = new JButton[board.getDimension()][board.getDimension()];
        for (int i = 0; i < board.getDimension(); i++) {
            for (int j = 0; j < board.getDimension(); j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setActionCommand("Grid: " + i + " " + j);
                buttons[i][j].addActionListener(this);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            board.toggleFlag(finalI, finalJ);
                            updateButtonsFromBoard();
                        }
                    }
                });
                panel.add(buttons[i][j]);
            }
        }
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().split(" ")[0].equals("Grid:")) {
            int i = Integer.parseInt(e.getActionCommand().split(" ")[1]);
            int j = Integer.parseInt(e.getActionCommand().split(" ")[2]);
            if (firstClick) board.regenUntilGoodFirst(i, j);
            firstClick = false;
            board.revealSpot(i, j);
            updateButtonsFromBoard();
        }
        repaint();
    }

    public static void main(String[] args) {
        board = new MinesweeperBoard(14);
        MinesweeperGUI gui = new MinesweeperGUI("Minesweeper");
        gui.setSize(1920, 1080);
        gui.setVisible(true);
    }
}