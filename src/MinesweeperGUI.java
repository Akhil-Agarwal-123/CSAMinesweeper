import javax.swing.*;


public class MinesweeperGUI extends JFrame {
    public MinesweeperGUI() {
        setTitle("Minesweeper");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton button = new JButton("Click me!");
        add(button);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MinesweeperGUI();
            }
        });
    }
}
