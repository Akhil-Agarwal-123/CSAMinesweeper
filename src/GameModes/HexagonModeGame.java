package GameModes;

import BoardUtil.BoardType;

public class HexagonModeGame extends NormalModeGame {
    public HexagonModeGame(int dim, int mines, double clusteringThreshold, int h, int w) {
        super(BoardType.HEXAGON, dim, mines, clusteringThreshold, h, w);
    }
}
