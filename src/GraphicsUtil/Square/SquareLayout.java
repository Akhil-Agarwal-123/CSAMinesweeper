package GraphicsUtil.Square;

import Global.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class SquareLayout implements LayoutManager {
    private Map<Component, Dimension> componentSizes = new HashMap<>();
    private int widthOfEach, heightOfEach;
    private boolean firstTime = true;

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
        Point middle = new Point(parent.getWidth() / 2, parent.getHeight() / 2);
        for (Component comp : parent.getComponents()) {
            if (firstTime) {
                comp.setBounds(middle.x, middle.y, size.width, size.height);
                animate((JComponent) comp, new Point(x, y), 0.5, 30);
            } else {
                comp.setBounds(x, y, size.width, size.height);
            }

            x += size.width;
            maxWidth = Math.max(maxWidth, x);

            if ((i + 1) % Global.game.getDimension() == 0) {
                x = 0;
                y += size.height;
            }
            i++;
        }
        y += size.height;

        parent.setPreferredSize(new Dimension(maxWidth, y));
        firstTime = false;
    }

    private void animate(JComponent component, Point newPoint, double duration, double fps) {
        int frames = (int) (duration * fps);
        int interval = (int) (1000 / fps);

        Rectangle compBounds = component.getBounds();

        Point oldPoint = new Point(compBounds.x, compBounds.y);
        Point animFrame = new Point((newPoint.x - oldPoint.x) / frames, (newPoint.y - oldPoint.y) / frames);

        new Timer(interval, new ActionListener() {
            int currentFrame = 0;
            public void actionPerformed(ActionEvent e) {
                component.setBounds(oldPoint.x + (animFrame.x * currentFrame), oldPoint.y + (animFrame.y * currentFrame), compBounds.width, compBounds.height);

                if (currentFrame != frames) {
                    currentFrame++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();
    }
}
