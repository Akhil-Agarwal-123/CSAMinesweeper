package GraphicsUtil.Hexagon;

import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class HexagonBoardGUI extends BoardGUI {
    /**
     * Gets the layout manager for a hexagon board
     * @return the layout manager
     */
    @Override
    protected LayoutManager getLayoutManager() {
        double widthOfEach = Math.max(800, getWidth())/(Global.game.getDimension() + 0.5);
        double heightOfEach = Math.max(700, getHeight())/(0.25 + 0.75 * Global.game.getDimension());
        HexagonLayout layout = new HexagonLayout();
        layout.setWidthHeight(widthOfEach, heightOfEach);
        return layout;
    }

    /**
     * Gets the label for a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the label for the spot
     */
    @Override
    protected JLabel getLabel(int i, int j) {
        return new HexagonLabel(Global.game.getIcon(i, j));
    }
}
