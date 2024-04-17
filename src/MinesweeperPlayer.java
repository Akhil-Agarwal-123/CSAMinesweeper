import java.util.Scanner;

public class MinesweeperPlayer {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter board dimension: ");
        int d = scan.nextInt();
        MinesweeperBoard board = new MinesweeperBoard(d);
        int i, j;

        System.out.print("Enter row number: ");
        i = scan.nextInt() - 1;
        System.out.print("Enter column number: ");
        j = scan.nextInt() - 1;
        while (!board.isZero(i, j)) {
            board.generateBoard();
        }
        board.revealSpot(i, j);

        do {
            board.printBoardHidden();
            System.out.println();
            board.printBoardAllRevealed();
            System.out.println();

            System.out.print("Enter row number: ");
            i = scan.nextInt() - 1;
            System.out.print("Enter column number: ");
            j = scan.nextInt() - 1;
            board.revealSpot(i, j);
        } while (!board.won() && !board.lost());
        if (board.won()) System.out.println("You won!");
        else System.out.println("You lost!");
    }
}
