package GraphicsUtil.Triangle;

import Global.Global;

import java.awt.*;

public class TriangleLayout implements LayoutManager {
    /**
     * The width of each triangle
     */
    private int widthOfEach;
    /**
     * The height of each triangle
     */
    private int heightOfEach;

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
     * Removes a layout component
     * @param comp the container
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        // Not used
    }

    /**
     * Gets the preferred size
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
     * Gets the minimum size
     * @param parent the container
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);
    }

    /**
     * Updates the layout
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
