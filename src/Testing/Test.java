package Testing;

import GameModes.BuildAWallModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;

/**
 * Class to run unit tests from
 */
public class Test {
    /**
     * Driver method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        UnitTest test = new UnitTest() {
            @Override
            protected Game getGame() {
                return new BuildAWallModeGame(HexagonBoardGUI.class, 10, 10, 0, 0, 0);
            }
        };
        test.printBoard(true);
        test.playUntilEnd(true);
    }
}
