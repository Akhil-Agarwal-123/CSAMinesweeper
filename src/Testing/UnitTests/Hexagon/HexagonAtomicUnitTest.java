package Testing.UnitTests.Hexagon;

import GameModes.AtomicModeGame;
import GameModes.Game;
import GraphicsUtil.Hexagon.HexagonBoardGUI;
import Testing.UnitTests.UnitTest;

/**
 * Unit Test for hexagonal atomic mode
 */
public class HexagonAtomicUnitTest extends UnitTest {
    /**
     * Returns the initial game
     * @return the initial game
     * @author Kushaan
     */
    @Override
    protected Game getGame() {
        return new AtomicModeGame(HexagonBoardGUI.class, 10, 20, 0, 0, 0);
    }
}
