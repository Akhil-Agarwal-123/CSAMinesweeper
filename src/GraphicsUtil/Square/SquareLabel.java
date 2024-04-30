package GraphicsUtil.Square;

import javax.swing.*;
import java.awt.*;

public class SquareLabel extends JLabel {

    Polygon bounds;
    Icon icon;
    Color background;

    /**
     * Creates a squareal button with a single character label
     * @param icon
     */
    public SquareLabel(Icon icon) {
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
     * Creates a square of certain height / width and ratio multiplier
     * @param width
     * @param height
     * @param ratio
     * @return
     */
    private Polygon square(int width, int height) {
        Polygon sq = new Polygon();
        sq.addPoint(0, 0);
        sq.addPoint(width, 0);
        sq.addPoint(width, height);
        sq.addPoint(0, height);
        return sq;
    }

    private void calculateBounds() {
        this.bounds = this.square(this.getWidth(), this.getHeight());
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