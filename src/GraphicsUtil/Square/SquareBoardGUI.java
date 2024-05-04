package GraphicsUtil.Square;

import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class SquareBoardGUI extends BoardGUI {
    /**
     * Gets the layout manager for a square board
     * @return the layout manager
     */
    @Override
    protected LayoutManager getLayoutManager() {
        return new GridLayout(Global.game.getDimension(), Global.game.getDimension());
    }

    /**
     * Gets the label for a spot on the board
     * @param i the row index
     * @param j the column index
     * @return the label for the spot
     */
    @Override
    protected JLabel getLabel(int i, int j) {
        return new JLabel(Global.game.getIcon(i, j));
    }
}
