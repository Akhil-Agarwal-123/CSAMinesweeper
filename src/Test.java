import BoardUtil.HexagonBoard;

public class Test {
    public static void main(String[] args) {
        HexagonBoard board = new HexagonBoard(10, 15, 0);
        board.genBoard(0, 0);
        board.printBoardAllRevealed();
        board.printBoardHidden();
    }
}
