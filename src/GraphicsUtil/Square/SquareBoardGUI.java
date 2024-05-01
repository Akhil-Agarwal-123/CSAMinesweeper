package GraphicsUtil.Square;

import Global.Global;
import GraphicsUtil.BoardGUI;

import javax.swing.*;
import java.awt.*;

public class SquareBoardGUI extends BoardGUI {
    @Override
    protected LayoutManager getLayoutManager() {
        return new GridLayout(Global.game.getDimension(), Global.game.getDimension());
    }

    @Override
    protected JLabel getLabel(int i, int j) {
        return new JLabel(Global.game.getIcon(i, j));
    }
}
