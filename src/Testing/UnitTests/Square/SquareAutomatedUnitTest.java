package Testing.UnitTests.Square;

import GameModes.AutomatedBoardModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square automated board mode
 */
public class SquareAutomatedUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AutomatedBoardModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
