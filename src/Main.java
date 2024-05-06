import GraphicsUtil.MinesweeperGUI;

/**
 * Driver class to run to play the game
 */
public class Main {
    /**
     * Main method to run the Minesweeper game
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        new MinesweeperGUI("Minesweeper").setVisible(true);
    }
}
