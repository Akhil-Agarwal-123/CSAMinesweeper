package Global;

import GameModes.Game;
import GraphicsUtil.MinesweeperGUI;

import java.util.Random;

public class Global {
    public static Game game;
    public static MinesweeperGUI minesweeperGUI;
    public static final Random rand = new Random(System.currentTimeMillis());
    public static final double FPS = 10;
}
