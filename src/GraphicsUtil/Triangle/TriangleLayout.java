package GraphicsUtil.Triangle;

import Global.Global;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TriangleLayout implements LayoutManager {
    private Map<Component, Dimension> componentSizes = new HashMap<>();
    private int widthOfEach, heightOfEach;

    /**
     * Sets the width and height of each triangle
     * @param wOfEach the width of each triangle
     * @param hOfEach the height of each triangle
     */
    public void setWidthHeight(double wOfEach, double hOfEach) {
        widthOfEach = (int) wOfEach;
        heightOfEach = (int) hOfEach;
    }

    /**
     * Adds a layout component
     * @param name the name of the component
     * @param comp the component
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        // Not used
    }

    /**
     * Lays out the components
     * @param comp the container
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        componentSizes.remove(comp);
    }

    /**
     * Lays out the components
     * @param parent the container
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        if (parent.getSize().width == 0) {
            return new Dimension(800, 700);
        } else {
            return parent.getSize();
        }
    }

    /**
     * Lays out the components
     * @param parent the container
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    /**
     * Lays out the components
     * @param parent the container
     */
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
                // new line, reset x and adjust y
                x = 0;
                y += size.height / 2; // Adjust for overlap
            }
            i++;
        }
        y += size.height / 2;

        parent.setPreferredSize(new Dimension(maxWidth, y));
    }
}
