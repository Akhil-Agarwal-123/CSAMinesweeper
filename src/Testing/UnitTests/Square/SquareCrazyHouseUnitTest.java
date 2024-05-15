package Testing.UnitTests.Square;

import GameModes.CrazyHouseModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square crazy house mode
 */
public class SquareCrazyHouseUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new CrazyHouseModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
