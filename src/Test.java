import BoardUtil.HexagonBoard;
import BoardUtil.MinesweeperBoard;
import BoardUtil.SquareBoard;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MinesweeperBoard board = new HexagonBoard(10, 15, 0);
        board.genBoard(4, 4);
        board.printBoardHidden();
        board.revealSpot(4, 4);
        board.printBoardHidden();
    }
}
