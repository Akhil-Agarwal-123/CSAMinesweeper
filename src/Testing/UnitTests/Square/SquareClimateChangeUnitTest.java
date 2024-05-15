package Testing.UnitTests.Square;

import GameModes.ClimateChangeModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square climate change mode
 */
public class SquareClimateChangeUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new ClimateChangeModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
