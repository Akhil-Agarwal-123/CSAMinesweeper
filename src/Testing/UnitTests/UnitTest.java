package Testing.UnitTests;

import BoardUtil.GameStatus;
import GameModes.Game;

import java.util.Scanner;

/**
 * Abstract class to be overridden with various different games to test on
 */
public abstract class UnitTest {
    /**
     * A scanner for user input
     */
    private final Scanner scan;
    /**
     * The current game
     */
    private final Game game;

    /**
     * Returns a new instance of a game to play on
     * @return the game to start off with
     * @author Akhil
     */
    protected abstract Game getGame();

    /**
     * Default constructor which creates a game
     * @author Akhil
     */
    public UnitTest() {
        game = getGame();
        scan = new Scanner(System.in);
    }

    /**
     * Plays a single move
     * @param leftClick did the user left click?
     * @param i the row index
     * @param j the column index
     * @author Akhil
     */
    public void playMove(boolean leftClick, int i, int j) {
        if (leftClick) {
            game.leftClick(i, j);
        } else {
            game.rightClick(i, j);
        }
        game.updateGameStatus();
    }

    /**
     * Prints out the board, either all revealed or only the visited revealed
     * @param hidden whether to reveal only the visited cells
     * @author Akhil
     */
    public void printBoard(boolean hidden) {
        if (hidden) game.getBoard().printBoardHidden();
        else game.getBoard().printBoardAllRevealed();
    }

    /**
     * Makes one move in the game based on the user's input
     * @param printHidden whether the board should be printed hidden or not
     * @author Akhil
     */
    public void moveWithUserInput(boolean printHidden) {
        System.out.print("Which row are you clicking on (1-" + game.getDimension() + "): ");
        int i = scan.nextInt();
        while (i < 1 || i > game.getDimension()) {
            System.out.print("Out of bounds. Enter from 1 to " + game.getDimension() + ": ");
            i = scan.nextInt();
        }

        System.out.print("Which column are you clicking on (1-" + game.getDimension() + "): ");
        int j = scan.nextInt();
        while (j < 1 || j > game.getDimension()) {
            System.out.print("Out of bounds. Enter from 1 to " + game.getDimension() + ": ");
            j = scan.nextInt();
        }

        System.out.print("Enter L to left click or R to right click: ");
        String s = scan.next();
        while (!s.equals("L") && !s.equals("R")) {
            System.out.print("Enter either L or R: ");
            s = scan.next();
        }

        playMove(s.equals("L"), i-1, j-1);
        printBoard(printHidden);
    }

    /**
     * Plays until the board is either won or lost based on user input
     * @param printHidden whether to print the board in its hidden state or not
     * @author Akhil
     */
    public void playUntilEnd(boolean printHidden) {
        while (game.getLastUpdatedGameStatus() == GameStatus.ONGOING) {
            moveWithUserInput(printHidden);
        }
    }
}
