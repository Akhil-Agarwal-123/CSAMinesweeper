package Testing.UnitTests.Triangle;

import GameModes.CrazyHouseModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle crazy house mode
 */
public class TriangleCrazyHouseUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new CrazyHouseModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
