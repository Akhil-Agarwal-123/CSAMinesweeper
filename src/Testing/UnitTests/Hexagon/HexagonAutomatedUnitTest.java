package Testing.UnitTests.Hexagon;

import GameModes.AutomatedBoardModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal automated board mode
 */
public class HexagonAutomatedUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new AutomatedBoardModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
