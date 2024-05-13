package Testing.UnitTests.Triangle;

import GameModes.ClimateChangeModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle climate change mode
 */
public class TriangleClimateChangeUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new ClimateChangeModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
