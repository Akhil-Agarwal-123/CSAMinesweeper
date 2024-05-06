package GraphicsUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles translating strings to icons (images) from files
 */
public class IconHandler {
    /**
     * A map from the string representation of a cell to the icon to be displayed
     */
    private final Map<String, ImageIcon> ICON_MAP = new HashMap<>();

    /**
     * Creates an icon handler with a certain height, width, and dimension
     * @param h the height of the icon
     * @param w the width of the icon
     * @param dim the dimension of the icon
     */
    public IconHandler(int h, int w, int dim) {
        regen(h, w, dim);
    }

    /**
     * Regenerates the icons with a certain height, width, and dimension
     * @param h the height of the icon
     * @param w the width of the icon
     * @param dim the dimension of the board
     */
    public void regen(int h, int w, int dim) {
        ICON_MAP.clear();
        String ICONS = "12345678BF-";
        for (int i = 0; i < ICONS.length(); i++) {
            try {
                // String extension = String.valueOf(ICONS.charAt(i)).matches("[1-8]") ? ".svg" : ".png";
                BufferedImage img = ImageIO.read(new File("assets/" + ICONS.charAt(i) + ".png"));
                int imgDim = Math.min(h - 50, w - 100) / dim;
                ImageIcon ii = new ImageIcon(img.getScaledInstance(imgDim, imgDim, 0));
                ICON_MAP.put(ICONS.charAt(i) + "", ii);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Returns the icon of a certain string
     * @param s the string to get the icon of
     * @return the icon of a certain string
     */
    public ImageIcon getIcon(String s) {
        return ICON_MAP.get(s.equals("0") || s.equals("W") ? "-" : s);
    }
}
