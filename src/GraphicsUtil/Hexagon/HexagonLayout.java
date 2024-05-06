package GraphicsUtil.Hexagon;

import Global.Global;

import java.awt.*;

/**
 * Handles the layout of all hexagonal labels for the board
 */
public class HexagonLayout implements LayoutManager {
    /**
     * The width of each hexagon
     */
    private int widthOfEach;
    /**
     * The height of each hexagon
     */
    private int heightOfEach;

    /**
     * Sets the width and height of each hexagon
     * @param wOfEach the width of each hexagon
     * @param hOfEach the height of each hexagon
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
                x = (((i+1)/Global.game.getDimension()) % 2 != 0) ? (size.width/2) : 0;
                y += 3 * size.height / 4; // Adjust for overlap
            }
            i++;
        }
        y += size.height / 4;

        parent.setPreferredSize(new Dimension(maxWidth, y));
    }
}
