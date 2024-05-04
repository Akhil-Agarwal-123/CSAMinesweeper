package GraphicsUtil.Triangle;

import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class TriangleBoardGUI extends BoardGUI {
    /**
     * Gets the layout manager for a triangle board
     * @return the layout manager
     */
    @Override
    protected LayoutManager getLayoutManager() {
        double widthOfEach = Math.max(800, getWidth())/(Global.game.getDimension() + 0.5);
        double heightOfEach = Math.max(700, getHeight())/(0.25 + 0.75 * Global.game.getDimension());
        TriangleLayout layout = new TriangleLayout();
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
        if ((i + j) % 2 == 0) {
            return new TriangleLeftLabel(Global.game.getIcon(i, j));
        } else {
            return new TriangleRightLabel(Global.game.getIcon(i, j));
        }
    }
}
