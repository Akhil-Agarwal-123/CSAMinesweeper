package Testing.UnitTests.Square;

import GameModes.AntiFlagModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square anti flag mode
 */
public class SquareAntiFlagUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AntiFlagModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
