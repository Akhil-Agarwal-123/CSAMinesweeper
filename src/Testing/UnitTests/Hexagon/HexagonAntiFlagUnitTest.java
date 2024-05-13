package Testing.UnitTests.Hexagon;

import GameModes.AntiFlagModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal anti flag mode
 */
public class HexagonAntiFlagUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new AntiFlagModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
