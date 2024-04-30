package GraphicsUtil.Square;

import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class SquareBoardGUI extends BoardGUI {
    @Override
    protected LayoutManager getLayoutManager() {
        double widthOfEach = (double) Math.max(800, getWidth()) /Global.game.getDimension();
        double heightOfEach = (double) Math.max(800, getHeight()) /Global.game.getDimension();
        SquareLayout layout = new SquareLayout();
        layout.setWidthHeight(widthOfEach, heightOfEach);
        return layout;
    }

    @Override
    protected JLabel getLabel(int i, int j) {
        return new SquareLabel(Global.game.getIcon(i, j));
    }
}
