package Testing;

import GameModes.Game;

public abstract class UnitTest {
    private final Game game;
    protected abstract Game getGame();
    public UnitTest() {
        game = getGame();
    }
}
