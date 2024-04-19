import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MinesweeperGUI extends JFrame {
    private MinesweeperBoard board;
    private JButton[][] buttons;
    private JTextField gridSizeField;
    private JTextField bombPercentageField;
    private JButton newGameButton;
    private boolean firstClick = true;

    public MinesweeperGUI() {
        setTitle("Minesweeper");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.WEST);

        newGame(10, 20); // Default grid size and bomb percentage

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel gridSizeLabel = new JLabel("Grid Size:");
        gridSizeField = new JTextField("10");
        JLabel bombPercentageLabel = new JLabel("Bomb Percentage:");
        bombPercentageField = new JTextField("20");
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new NewGameListener());

        panel.add(gridSizeLabel);
        panel.add(gridSizeField);
        panel.add(bombPercentageLabel);
        panel.add(bombPercentageField);
        panel.add(newGameButton);

        return panel;
    }

    private void newGame(int gridSize, int bombPercentage) {
        int numBombs = (int) (gridSize * gridSize * bombPercentage / 100.0);
        board = new MinesweeperBoard(gridSize, numBombs);

        if (buttons != null) {
            remove(buttons[0][0].getParent());
        }

        JPanel gamePanel = new JPanel(new GridLayout(gridSize, gridSize));
        buttons = new JButton[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addMouseListener(new CellClickListener(i, j));
                gamePanel.add(buttons[i][j]);
            }
        }

        add(gamePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void updateButtonsFromBoard() {
        for (int k = 0; k < board.getDimension(); k++) {
            for (int l = 0; l < board.getDimension(); l++) {
                String num = board.getStringFor(k, l, true);
                buttons[k][l].setText(num);
                buttons[k][l].setEnabled(num.equals("-"));
            }
        }
        repaint();
    }

    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int gridSize = Integer.parseInt(gridSizeField.getText());
            int bombPercentage = Integer.parseInt(bombPercentageField.getText());
            firstClick = true;
            newGame(gridSize, bombPercentage);
        }
    }

    private class CellClickListener implements MouseListener {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (firstClick) {
                board.regenUntilPlayable(row, col);
                firstClick = false;
            }
            if (e.getButton() == MouseEvent.BUTTON1) {
                board.revealSpot(row, col);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                board.toggleFlag(row, col);
            }
            updateButtonsFromBoard();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MinesweeperGUI().setVisible(true);
            }
        });
    }
}