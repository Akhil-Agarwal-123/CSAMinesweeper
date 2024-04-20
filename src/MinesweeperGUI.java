import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

public class MinesweeperGUI extends JFrame {
    private MinesweeperBoard board;
    private Map<String, ImageIcon> images;
    private final String icons = "012345678BF-";
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

        images = new java.util.HashMap<>();
        for (int i = 0; i < icons.length(); i++) {
            try {
                BufferedImage img = ImageIO.read(new File(icons.charAt(i) + ".png"));
                ImageIcon ii = new ImageIcon(img.getScaledInstance(Math.min(getHeight() - 50, getWidth() - 100)/gridSize,
                        Math.min(getHeight() - 50, getWidth() - 100)/gridSize, 0));
                images.put(icons.charAt(i) + "", ii);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        GridLayout layout = new GridLayout(gridSize, gridSize);
        JPanel gamePanel = new JPanel(layout);
        boardIcons = new JLabel[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                try {
                    ImageIcon img = images.get("-");
                    boardIcons[i][j] = new JLabel(img);
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
        String state = board.getGameState();
        if (state.equals("won")) {
            JOptionPane.showMessageDialog(this, "You won!");
        } else if (state.equals("lost")) {
            JOptionPane.showMessageDialog(this, "You lost!");
        }

        for (int k = 0; k < board.getDimension(); k++) {
            for (int l = 0; l < board.getDimension(); l++) {
                String num = board.getStringFor(k, l, state.equals("ongoing"));
                if (state.equals("won")) {
                    if (!num.equals("B"))
                        boardIcons[k][l].setBackground((k + l) % 2 == 0 ? new Color(147, 195, 242) : new Color(156, 200, 245));
                    num = "-";
                }
                try {
                    ImageIcon img = images.get(num);
                    boardIcons[k][l].setIcon(img);
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