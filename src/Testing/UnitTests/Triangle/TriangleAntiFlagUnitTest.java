package Testing.UnitTests.Triangle;

import GameModes.AntiFlagModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle anti flag mode
 */
public class TriangleAntiFlagUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new AntiFlagModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
