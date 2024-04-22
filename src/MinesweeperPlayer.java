import java.util.Scanner;

public class MinesweeperPlayer {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter board dimension: ");
        int d = scan.nextInt();
        int mines = (int) (d * d * 0.2);
        MinesweeperBoard board = new MinesweeperBoard(d, mines, 0);
        int i, j;

        board.printBoardHidden();
        System.out.print("Enter row number: ");
        i = scan.nextInt() - 1;
        System.out.print("Enter column number: ");
        j = scan.nextInt() - 1;
        board.regenUntilPlayable(i, j);
        board.revealSpot(i, j);

        do {
            board.printBoardHidden();
            System.out.println();
//            board.printBoardAllRevealed();
//            System.out.println();

            System.out.print("Enter row number: ");
            i = scan.nextInt() - 1;
            System.out.print("Enter column number: ");
            j = scan.nextInt() - 1;

            System.out.print("Would you like to toggle the flag (F) or reveal the space (R)? ");
            char c = scan.next().charAt(0);
            if (c == 'F' || c == 'f') board.toggleFlag(i, j);
            else board.revealSpot(i, j);
        } while (board.getGameState().equals("ongoing"));

        board.printBoardHidden();
        System.out.println();
        if (board.getGameState().equals("won")) System.out.println("You won!");
        else {
            board.printBoardAllRevealed();
            System.out.println();
            System.out.println("You lost!");
        }
    }
}