package Testing.UnitTests.Triangle;

import GameModes.AutomatedBoardModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle automated board mode
 */
public class TriangleAutomatedUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AutomatedBoardModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
