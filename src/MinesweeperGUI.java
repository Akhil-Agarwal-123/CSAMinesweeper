import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MinesweeperGUI extends JFrame {
    private MinesweeperBoard board;
    private JLabel[][] boardIcons;
    private JTextField gridSizeField;
    private JTextField bombPercentageField;
    private JButton newGameButton;
    private boolean firstClick = true;

    public MinesweeperGUI(String title) {
        super(title);
        setSize(1900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.WEST);

        newGame(10, 20); // Default grid size and bomb percentage

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
        newGameButton.addActionListener(e -> {
            int gridSize = Integer.parseInt(gridSizeField.getText());
            int bombPercentage = Integer.parseInt(bombPercentageField.getText());
            firstClick = true;
            newGame(gridSize, bombPercentage);
        });

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

        if (boardIcons != null) {
            remove(boardIcons[0][0].getParent());
        }

        GridLayout layout = new GridLayout(gridSize, gridSize);
        JPanel gamePanel = new JPanel(layout);
        boardIcons = new JLabel[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                try {
                    BufferedImage img = ImageIO.read(new File("-.png"));
                    ImageIcon ii = new ImageIcon(img.getScaledInstance(Math.min(getHeight() - 50, getWidth() - 100)/gridSize,
                            Math.min(getHeight() - 50, getWidth() - 100)/gridSize, 0));
                    boardIcons[i][j] = new JLabel(ii);
                    boardIcons[i][j].setBackground((i + j) % 2 == 0 ? new Color(172, 208, 94) : new Color(179, 214, 101));
                    boardIcons[i][j].setOpaque(true);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                int finalI = i;
                int finalJ = j;
                boardIcons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (firstClick) {
                                board.regenUntilPlayable(finalI, finalJ);
                                firstClick = false;
                            }
                            board.revealSpot(finalI, finalJ);
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            board.toggleFlag(finalI, finalJ);
                        }
                        updateButtonsFromBoard();
                    }
                });
                gamePanel.add(boardIcons[i][j]);
            }
        }

        add(gamePanel);
        revalidate();
        repaint();
    }

    private void updateButtonsFromBoard() {
        boolean lost = board.lost();
        boolean won = board.won();
        for (int k = 0; k < board.getDimension(); k++) {
            for (int l = 0; l < board.getDimension(); l++) {
                String num = board.getStringFor(k, l, !lost && !won);
                if (won) {
                    if (!num.equals("B"))
                        boardIcons[k][l].setBackground((k + l) % 2 == 0 ? new Color(147, 195, 242) : new Color(156, 200, 245));
                    num = "-";
                }
                try {
                    BufferedImage img = ImageIO.read(new File(num + ".png"));
                    ImageIcon ii = new ImageIcon(img.getScaledInstance(Math.min(getHeight() - 50, getWidth() - 100) / boardIcons.length,
                            Math.min(getHeight() - 50, getWidth() - 100) / boardIcons.length, 0));
                    boardIcons[k][l].setIcon(ii);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new MinesweeperGUI("Minesweeper").setVisible(true);
    }
}