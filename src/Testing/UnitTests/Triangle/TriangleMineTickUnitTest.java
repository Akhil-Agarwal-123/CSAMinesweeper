package Testing.UnitTests.Triangle;

import GameModes.Game;
import GameModes.MineTickModeGame;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle mine tick mode
 */
public class TriangleMineTickUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new MineTickModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
