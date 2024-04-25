import BoardUtil.HexagonBoard;
import BoardUtil.MinesweeperBoard;
import BoardUtil.SquareBoard;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MinesweeperBoard board = new HexagonBoard(10, 15, 0);
        board.genBoard(4, 4);
        board.printBoardAllRevealed();
        board.printBoardHidden();
        ArrayList<int[]> neighbors = board.getNeighbors(4, 4);
        for (int[] neighbor : neighbors) {
            System.out.println(neighbor[0] + " " + neighbor[1]);
        }
    }
}
