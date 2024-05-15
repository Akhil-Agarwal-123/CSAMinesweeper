package Testing.UnitTests.Hexagon;

import GameModes.CrazyHouseModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal crazy house mode
 */
public class HexagonCrazyHouseUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new CrazyHouseModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
