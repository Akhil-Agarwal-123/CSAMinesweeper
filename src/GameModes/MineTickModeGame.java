package GameModes;

import Global.Global;
import GraphicsUtil.BoardGUI;

import java.util.ArrayList;

public class MineTickModeGame extends NormalModeGame {
    public MineTickModeGame(Class<? extends BoardGUI> boardType, int dim, int mines, double clusteringThreshold, int h, int w) {
        super(boardType, dim, mines, clusteringThreshold, h, w);
    }

    protected void normalClick(int i, int j) {
        board.revealSpot(i, j);


        ArrayList<int[]> unvisitedSpots = new ArrayList<>();
        for (int a = 0; a < board.getDimension(); a++) {
            for (int b = 0; b < board.getDimension(); b++) {
                if (!board.getVisited(a, b) && !board.getMine(a, b)) {
                    unvisitedSpots.add(new int[]{a, b});
                }
            }
        }

        // If there are any unvisited spots, select one at random and place a mine there
        if (!unvisitedSpots.isEmpty()) {
            int[] randomSpot = unvisitedSpots.get(Global.rand.nextInt(unvisitedSpots.size()));
            board.placeMine(randomSpot[0], randomSpot[1]);
        }
    }
}
