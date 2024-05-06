package GraphicsUtil.Hexagon;

import javax.swing.*;
import java.awt.*;

public class HexagonLabel extends JLabel {
    /**
     * The bounds for the hexagon label
     */
    Polygon bounds;
    /**
     * The icon to be displayed
     */
    Icon icon;
    /**
     * The background color
     */
    Color background;

    /**
     * Creates a hexagonal button with an icon
     * @param icon the icon to display
     */
    public HexagonLabel(Icon icon) {
        super(icon);
        this.calculateBounds();
        this.icon = icon;
        this.setOpaque(true);
    }

    /**
     * Sets the background color
     * @param background the desired background color
     */
    @Override
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Creates a hexagon of certain height / width and ratio multiplier
     * @param width the width of the hexagon
     * @param height the height of the hexagon
     * @return the hexagon polygon
     */
    private Polygon hexagon(int width, int height, double ratio) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            int x = (i == 1 || i == 4) ? (width/2) : ((i == 2 || i == 3) ? 0 : width);
            int y = height / 2 + (int)((height) / 2 * Math.sin(i * 2 * Math.PI / 6 + Math.PI/6) * ratio);
            hexagon.addPoint(x,y);
        }
        return hexagon;
    }

    /**
     * Calculates the bounds of the button
     */
    private void calculateBounds() {
        this.bounds = this.hexagon(this.getWidth(), this.getHeight(), 1);
    }

    /**
     * Returns whether a certain point is within the bounds of this button.
     * @param p the point
     * @return whether the point is within the bounds
     */
    @Override
    public boolean contains(Point p) {
        return this.bounds.contains(p);
    }

    /**
     * Returns whether supplied x,y coordinates is within the bounds of this button.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return whether the point is within the bounds
     */
    @Override
    public boolean contains(int x, int y) {
        return this.bounds.contains(x, y);
    }

    /**
     * Sets the dimension of the button
     * @param d the dimension
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.calculateBounds();
    }

    /**
     * Sets the dimension of the button
     * @param w the width
     * @param h the height
     */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width
     * @param height the height
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     * @param r the rectangle
     */
    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        this.calculateBounds();
    }

    /**
     * Draws the button
     * @param graphics the graphics object
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        calculateBounds();
        Polygon inside = bounds;
        graphics.setColor(background);
        graphics.drawPolygon(inside);
        graphics.fillPolygon(inside);

        icon.paintIcon(this, graphics, (getWidth() - icon.getIconWidth())/2, (getHeight() - icon.getIconHeight())/2);
    }

    /**
     * Paints the button
     * @param g the graphics object
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponent(g);
    }

    /**
     * Sets the icon of the button
     * @param icon the icon
     */
    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        this.icon = icon;
    }
}