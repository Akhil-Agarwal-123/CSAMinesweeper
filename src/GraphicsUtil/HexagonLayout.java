package GraphicsUtil;

import Global.Global;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.HashMap;
import java.util.Map;

public class HexagonLayout implements LayoutManager {
    private Map<Component, Dimension> componentSizes = new HashMap<>();
    private int widthOfEach, heightOfEach;

    public void setWidthHeight(double wOfEach, double hOfEach) {
        widthOfEach = (int) wOfEach;
        heightOfEach = (int) hOfEach;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        // Not used
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        componentSizes.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent.getSize().width == 0) {
            return new Dimension(800, 700);
        } else {
            return parent.getSize();
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
        int x = 0;
        int y = 0;
        int maxWidth = 0;

        int i = 0;
        Dimension size = new Dimension(widthOfEach, heightOfEach);
        for (Component comp : parent.getComponents()) {
            comp.setBounds(x, y, size.width, size.height);

            x += size.width;
            maxWidth = Math.max(maxWidth, x);

            if ((i + 1) % Global.game.getDimension() == 0) {
                x = (((i+1)/Global.game.getDimension()) % 2 != 0) ? (size.width/2) : 0;
                y += 3 * size.height / 4; // Adjust for overlap
            }
            i++;
        }
        y += size.height / 4;

        parent.setPreferredSize(new Dimension(maxWidth, y));
    }
}
