package GraphicsUtil.Triangle;

import javax.swing.*;
import java.awt.*;

public class TriangleLeftLabel extends JLabel {

    Polygon bounds;
    Icon icon;
    Color background;

    /**
     * Creates a hexagonal button with a single character label
     * @param icon
     */
    public TriangleLeftLabel(Icon icon) {
        super(icon);
        this.calculateBounds();
        this.icon = icon;
        this.setOpaque(true);
//        this.setBo(false);
//        this.setContentAreaFilled(false);
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Creates a hexagon of certain height / width and ratio multiplier
     * @param width
     * @param height
     * @return
     */
    private Polygon triangle(int width, int height) {
        Polygon tri = new Polygon();
        tri.addPoint(width, 0);
        tri.addPoint(width, height);
        tri.addPoint(0, height/2);
        return tri;
    }

    private void calculateBounds() {
        this.bounds = this.triangle(this.getWidth(), this.getHeight());
    }

    /**
     * Returns whether a certain point is within the bounds of this button.
     */
    @Override
    public boolean contains(Point p) {
        return this.bounds.contains(p);
    }

    /**
     * Returns whether supplied x,y coordinates is within the bounds of this button.
     */
    @Override
    public boolean contains(int x, int y) {
        return this.bounds.contains(x, y);
    }

    /**
     * Sets the dimension of the button
     */
    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        this.calculateBounds();
    }

    /**
     * Sets the dimension of the button
     */
    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.calculateBounds();
    }

    /**
     * Sets the bounds of the button
     */
    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        this.calculateBounds();
    }

    /**
     * Draws the button
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintComponent(g);
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        this.icon = icon;
    }
}