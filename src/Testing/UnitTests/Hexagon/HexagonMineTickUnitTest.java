package Testing.UnitTests.Hexagon;

import GameModes.Game;
import GameModes.MineTickModeGame;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal mine tick mode
 */
public class HexagonMineTickUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new MineTickModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
