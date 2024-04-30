import BoardUtil.HexagonBoard;
import BoardUtil.MinesweeperBoard;
import BoardUtil.SquareBoard;
import BoardUtil.TriangleBoard;

import java.awt.*;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        MinesweeperBoard board = new TriangleBoard(10, 15, 0);
        board.genBoard(4, 4);
        board.printBoardHidden();
        board.revealSpot(4, 4);
        board.printBoardHidden();

        // Color c = new Color(-5, -5, -5);
        // System.out.println(c.toString());
        // System.out.println(c.brighter().toString());
    }
}
