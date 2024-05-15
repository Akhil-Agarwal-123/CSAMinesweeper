package Testing.UnitTests.Hexagon;

import GameModes.ClimateChangeModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal climate change mode
 */
public class HexagonClimateChangeUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new ClimateChangeModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
