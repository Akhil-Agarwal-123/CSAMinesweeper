package Testing.UnitTests.Triangle;

import GameModes.BuildAWallModeGame;
import GameModes.Game;
import GraphicsUtil.Triangle.TriangleBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for triangle build a wall mode
 */
public class TriangleBuildAWallUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new BuildAWallModeGame(TriangleBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
