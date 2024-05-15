package Testing.UnitTests.Triangle;

import GameModes.AtomicModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle atomic mode
 */
public class TriangleAtomicUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AtomicModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
