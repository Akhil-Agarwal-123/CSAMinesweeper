package GraphicsUtil;

import Global.Global;

import javax.swing.*;
import java.awt.*;

public class HexagonBoardGUI extends BoardGUI {
    @Override
    protected LayoutManager getLayoutManager() {
        double widthOfEach = Math.max(800, getWidth())/(Global.game.getDimension() + 0.5);
        double heightOfEach = Math.max(700, getHeight())/(0.25 + 0.75 * Global.game.getDimension());
        HexagonLayout layout = new HexagonLayout();
        layout.setWidthHeight(widthOfEach, heightOfEach);
        return layout;
    }

    @Override
    protected JLabel getLabel(ImageIcon icon) {
        return new HexagonLabel(icon);
    }
}
