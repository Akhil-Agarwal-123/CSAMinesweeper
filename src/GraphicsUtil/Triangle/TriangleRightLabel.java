package GraphicsUtil.Triangle;

import javax.swing.*;
import java.awt.*;

public class TriangleRightLabel extends JLabel {
    /**
     * The bounds for the triangle label
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
     * Creates a triangular button with an icon
     * @param icon the icon to display
     */
    public TriangleRightLabel(Icon icon) {
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
     * Creates a triangle of certain height / width and ratio multiplier
     * @param width the width of the triangle
     * @param height the height of the triangle
     * @return the triangle polygon
     */
    private Polygon triangle(int width, int height) {
        Polygon tri = new Polygon();
        tri.addPoint(0, 0);
        tri.addPoint(0, height);
        tri.addPoint(width, height / 2);
        return tri;
    }

    /**
     * Calculates the bounds of the button
     */
    private void calculateBounds() {
        this.bounds = this.triangle(this.getWidth(), this.getHeight());
    }

    /**
     * Returns whether a certain point is within the bounds of this button.
     * @param p the point to check
     * @return whether the point is within the bounds of the button
     */
    @Override
    public boolean contains(Point p) {
        return this.bounds.contains(p);
    }

    /**
     * Returns whether a certain point is within the bounds of this button.
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @return whether the point is within the bounds of the button
     */
    @Override
    public boolean contains(int x, int y) {
        return this.bounds.contains(x, y);
    }

    /**
     * Sets the dimension of the button
     * @param d the dimension of the button
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.calculateBounds();
    }

    /**
     * Sets the size of the button
     * @param w the width of the button
     * @param h the height of the button
     */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     * @param x the x coordinate of the button
     * @param y the y coordinate of the button
     * @param width the width of the button
     * @param height the height of the button
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     * @param r the rectangle of the button
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
     * @param icon the icon to set
     */
    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        this.icon = icon;
    }
}