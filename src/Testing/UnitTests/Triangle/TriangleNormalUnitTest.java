package Testing.UnitTests.Triangle;

import GameModes.Game;
import GameModes.NormalModeGame;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle normal mode
 */
public class TriangleNormalUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new NormalModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
