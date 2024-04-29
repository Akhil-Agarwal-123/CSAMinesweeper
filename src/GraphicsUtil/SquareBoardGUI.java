package GraphicsUtil;

import Global.Global;

import javax.swing.*;
import java.awt.*;

public class SquareBoardGUI extends BoardGUI {
    @Override
    protected LayoutManager getLayoutManager() {
        return new GridLayout(Global.game.getDimension(), Global.game.getDimension());
    }

    @Override
    protected JLabel getLabel(ImageIcon icon) {
        return new JLabel(icon);
    }
}
