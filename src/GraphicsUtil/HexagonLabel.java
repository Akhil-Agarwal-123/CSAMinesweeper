package GraphicsUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HexagonLabel extends JLabel {

    Polygon bounds;
    Icon icon;
    Color background;

    /**
     * Creates a hexagonal button with a single character label
     * @param icon
     */
    public HexagonLabel(Icon icon) {
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
     * @param ratio
     * @return
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

    private void calculateBounds() {
        this.bounds = this.hexagon(this.getWidth(), this.getHeight(), 1);
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