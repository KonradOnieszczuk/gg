package pl.edu.agh.gg.projekt1615czw.application.bitmap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BitmapProvider {
    private final BufferedImage image;
    private final Dimension dimension;

    public BitmapProvider(String bitmapResourcePath) throws IOException {
        URL url = getClass().getResource(bitmapResourcePath);
        this.image = ImageIO.read(url);
        this.dimension = new Dimension(image.getWidth(), image.getHeight());
    }

    public Color getColorAt(Point point) {
        return new Color(image.getRGB(point.x, point.y));
    }

    public Dimension getDimension() {
        return dimension;
    }
}
