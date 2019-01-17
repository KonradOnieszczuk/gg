package pl.edu.agh.gg.projekt1615czw.helpers;

import java.awt.Point;

public class MathHelper {
    public static Point getMiddlePoint(Point northWest, Point southWest, Point northEast){
        return new Point(
                (int) Math.floor((northWest.getX() + northEast.getX()) / 2),
                (int) Math.floor((northWest.getY() + southWest.getY()) / 2));
    }
}
