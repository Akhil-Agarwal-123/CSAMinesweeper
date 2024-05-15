package Testing.UnitTests.Square;

import GameModes.BuildAWallModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square build a wall mode
 */
public class SquareBuildAWallUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new BuildAWallModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
