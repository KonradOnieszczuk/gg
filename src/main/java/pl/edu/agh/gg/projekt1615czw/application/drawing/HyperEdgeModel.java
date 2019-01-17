package pl.edu.agh.gg.projekt1615czw.application.drawing;

import lombok.Getter;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMaxX;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMaxY;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMinX;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMinY;

/**
 * A model representing hyper edge for bitmap approximation.
 */

@Getter
class HyperEdgeModel {
    private Point p1;
    private Point p2;

    private Color c1;
    private Color c2;
    private Color c3;
    private Color c4;

    HyperEdgeModel(List<HyperNode> vertices) {
        List<Point> points = vertices
                .stream()
                .map(HyperNode::getGeom)
                .collect(Collectors.toList());

        int x1 = getMinX(points);
        int y1 = getMinY(points);
        int x2 = getMaxX(points);
        int y2 = getMaxY(points);

        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);

        this.c1 = getColors(x1, y2, vertices);
        this.c2 = getColors(x2, y2, vertices);
        this.c3 = getColors(x1, y1, vertices);
        this.c4 = getColors(x2, y1, vertices);
    }

    private Color getColors(int x, int y, List<HyperNode> nodes) {
        HyperNode node = getNode(x, y, nodes);

        if (node != null) {
            return node.getColor();
        } else {
            return new Color(
                    BitmapColorApproximation.APPROX_R[x][y],
                    BitmapColorApproximation.APPROX_G[x][y],
                    BitmapColorApproximation.APPROX_B[x][y]
            );
        }
    }

    private HyperNode getNode(int x, int y, List<HyperNode> nodes) {
        List<HyperNode> matchingNodes = nodes
                .stream()
                .filter(v -> v.getGeom().getX() == x && v.getGeom().getY() == y)
                .collect(Collectors.toList());

        if (matchingNodes.size() != 1) {
            return null;
        }

        return matchingNodes.get(0);
    }
}
