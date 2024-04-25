package Global;

import GameModes.Game;
import GraphicsUtil.MinesweeperGUI;

import java.util.Random;

public class Global {
    public static Game game;
    public static MinesweeperGUI minesweeperGUI;
    public static Random rand = new Random(System.currentTimeMillis());
}
