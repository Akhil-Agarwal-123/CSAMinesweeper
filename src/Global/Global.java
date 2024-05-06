package Global;

import GameModes.Game;
import GraphicsUtil.MinesweeperGUI;

import java.util.Random;

public class Global {
    /**
     * The game that is occurring
     */
    public static Game game;
    /**
     * The GUI which is handling the user's view
     */
    public static MinesweeperGUI minesweeperGUI;
    /**
     * The random object for everything to use
     */
    public static final Random rand = new Random(System.currentTimeMillis());
}
