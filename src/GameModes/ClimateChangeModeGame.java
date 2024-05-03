package GameModes;

import BoardUtil.GameStatus;
import BoardUtil.MinesweeperBoard;
import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;

public class ClimateChangeModeGame extends NormalModeGame {

    public ClimateChangeModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    protected void normalClick(int i, int j) {
        board.revealSpot(i, j);

        // don't regen board if game is over
        GameStatus gameState = board.getGameState();
        if (gameState != GameStatus.ONGOING) {
            return;
        }

        // store previous state of board
        int[][] previousStatuses = board.getStatuses();
        boolean[][] previousVisited = board.getAllVisited();
        boolean[][] previousFlagged = board.getAllFlagged();

        // get all board types except the current one
        ArrayList<Class<? extends BoardGUI>> allBoardGUITypes = new ArrayList<>();
        ArrayList<Class<? extends MinesweeperBoard>> allBoardTypes = new ArrayList<>();
        for (Class<? extends BoardGUI> boardGUIType : BOARD_TYPES.keySet()) {
            Class<? extends MinesweeperBoard> boardType = BOARD_TYPES.get(boardGUIType);

            // currently disabling triangle board because still in prototype phase
            if (boardType.getTypeName().equals("BoardUtil.TriangleBoard")) {
                continue;
            }

            if (!boardType.getTypeName().equals(board.getClass().getTypeName())) {
                allBoardGUITypes.add(boardGUIType);
                allBoardTypes.add(boardType);
            }
        }

        // randomly select a new board type
        int index = (int) (Math.random() * allBoardGUITypes.size());
        Class<? extends BoardGUI> newBoardGUIType = allBoardGUITypes.get(index);
        Class<? extends MinesweeperBoard> newBoardType = allBoardTypes.get(index);
        try {
            // setup board
            board = newBoardType.getDeclaredConstructor(int.class, int.class, double.class)
                    .newInstance(board.getDimension(), board.getNumMines(), board.getClusteringThreshold());
            board.setStatuses(previousStatuses);
            board.setAllVisited(previousVisited);
            board.setAllFlagged(previousFlagged);
            board.calculateNumbers();

            // setup boardGUI
            if (Global.minesweeperGUI.boardGUI != null) {
                Global.minesweeperGUI.remove(Global.minesweeperGUI.boardGUI);
            }
            Global.minesweeperGUI.boardGUI = newBoardGUIType.getDeclaredConstructor().newInstance();
            Global.minesweeperGUI.add(Global.minesweeperGUI.boardGUI);

            Global.minesweeperGUI.revalidate();
            Global.minesweeperGUI.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
