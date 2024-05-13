package Testing.UnitTests.Hexagon;

import GameModes.Game;
import GameModes.NormalModeGame;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal normal mode
 */
public class HexagonNormalUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     */
    @Override
    protected Game getGame() {
        return new NormalModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
