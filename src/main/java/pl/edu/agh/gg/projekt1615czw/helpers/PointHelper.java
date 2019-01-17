package pl.edu.agh.gg.projekt1615czw.helpers;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class PointHelper {

    public static int getMinX(java.util.List<Point> points) {
        return (int) points
                .stream()
                .min(Comparator.comparing(Point::getX))
                .orElseThrow(NoSuchElementException::new)
                .getX();
    }

    public static int getMaxX(java.util.List<Point> points) {
        return (int) points
                .stream()
                .max(Comparator.comparing(Point::getX))
                .orElseThrow(NoSuchElementException::new)
                .getX();
    }

    public static int getMinY(java.util.List<Point> points) {
        return (int) points
                .stream()
                .min(Comparator.comparing(Point::getY))
                .orElseThrow(NoSuchElementException::new)
                .getY();
    }

    public static int getMaxY(List<Point> points) {
        return (int) points
                .stream()
                .max(Comparator.comparing(Point::getY))
                .orElseThrow(NoSuchElementException::new)
                .getY();
    }
}
