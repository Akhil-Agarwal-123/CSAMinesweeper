package GraphicsUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class IconHandler {
    private final Map<String, ImageIcon> ICON_MAP = new HashMap<>();

    public IconHandler(int h, int w, int dim) {
        regen(h, w, dim);
    }

    public void regen(int h, int w, int dim) {
        ICON_MAP.clear();
        String ICONS = "012345678BF-";
        for (int i = 0; i < ICONS.length(); i++) {
            try {
                BufferedImage img = ImageIO.read(new File("resources/" + ICONS.charAt(i) + ".png"));
                int imgDim = Math.min(h - 50, w - 100) / dim;
                ImageIcon ii = new ImageIcon(img.getScaledInstance(imgDim, imgDim, 0));
                ICON_MAP.put(ICONS.charAt(i) + "", ii);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ImageIcon getIcon(String s) {
        return ICON_MAP.get(s);
    }
}
