package Testing.UnitTests.Square;

import GameModes.Game;
import GameModes.NormalModeGame;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square normal mode
 */
public class SquareNormalUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new NormalModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
