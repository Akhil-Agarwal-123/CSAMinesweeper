package Testing.UnitTests.Square;

import GameModes.Game;
import GameModes.MineTickModeGame;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square mine tick mode
 */
public class SquareMineTickUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new MineTickModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
