package pl.edu.agh.gg.projekt1615czw.application.drawing;

import java.awt.*;
import java.awt.image.BufferedImage;

import static pl.edu.agh.gg.projekt1615czw.application.drawing.BitmapColorApproximation.*;

public class HyperEdgeToBitmapConverter {
    public static BufferedImage convertToBitmap(HyperEdgeModel model) {
        int x1 = (int) model.getP1().getX();
        int y1 = (int) model.getP1().getY();
        int x2 = (int) model.getP2().getX();
        int y2 = (int) model.getP2().getY();

        int width = x2 - x1 + 1;
        int height = y2 - y1 + 1;

        for(int px = x1; px <= x2; px++) {
            for(int py = y1; py <= y2; py++) {
                double xFactor = (double)(px - x1) / (double)(x2 - x1);
                double yFactor = (double)(py - y1) / (double)(y2 - y1);

                APPROX_R[px][py] = 0;
                APPROX_G[px][py] = 0;
                APPROX_B[px][py] = 0;

                APPROX_R[px][py] += model.getC1().getRed() * (1 - xFactor) * yFactor;
                APPROX_G[px][py] += model.getC1().getGreen() * (1 - xFactor) * yFactor;
                APPROX_B[px][py] += model.getC1().getBlue() * (1 - xFactor) * yFactor;

                APPROX_R[px][py] += model.getC2().getRed() * xFactor * yFactor;
                APPROX_G[px][py] += model.getC2().getGreen() * xFactor * yFactor;
                APPROX_B[px][py] += model.getC2().getBlue() * xFactor * yFactor;

                APPROX_R[px][py] += model.getC3().getRed() * (1 - xFactor) * (1 -yFactor);
                APPROX_G[px][py] += model.getC3().getGreen() * (1 - xFactor) * (1 -yFactor);
                APPROX_B[px][py] += model.getC3().getBlue() * (1 - xFactor) * (1 - yFactor);

                APPROX_R[px][py] += model.getC4().getRed() * xFactor * (1 - yFactor);
                APPROX_G[px][py] += model.getC4().getGreen() * xFactor * (1 - yFactor);
                APPROX_B[px][py] += model.getC4().getBlue() * xFactor * (1 - yFactor);
            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int px = x1; px <= x2; px++) {
            for (int py = y1; py <= y2; py++) {
                image.setRGB(px - x1, py - y1, new Color(
                        APPROX_R[px][py],
                        APPROX_G[px][py],
                        APPROX_B[px][py]).getRGB()
                );
            }
        }

        return image;
    }
}
