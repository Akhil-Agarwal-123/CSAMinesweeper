package Testing.UnitTests.Hexagon;

import GameModes.BuildAWallModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal build a wall mode
 */
public class HexagonBuildAWallUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new BuildAWallModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
