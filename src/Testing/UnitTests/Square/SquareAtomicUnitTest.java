package Testing.UnitTests.Square;

import GameModes.AtomicModeGame;
import GameModes.Game;
import GraphicsUtil.Square.SquareBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for square atomic mode
 */
public class SquareAtomicUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AtomicModeGame(SquareBoardGUI.class, 10, 20, 0, 0, 0);
    }
}

