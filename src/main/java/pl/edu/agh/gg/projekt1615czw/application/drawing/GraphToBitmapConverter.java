package pl.edu.agh.gg.projekt1615czw.application.drawing;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;
import pl.edu.agh.gg.projekt1615czw.helpers.HyperGraphHelper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

import static pl.edu.agh.gg.projekt1615czw.application.drawing.BitmapColorApproximation.*;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMaxX;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMaxY;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMinX;
import static pl.edu.agh.gg.projekt1615czw.helpers.PointHelper.getMinY;

@Component
public class GraphToBitmapConverter {
    public BufferedImage convertToBitmap(Graph<HyperNode, DefaultEdge> graph) {
        initializeColorApproximationArray(graph);

        List<HyperNode> nodes = graph.vertexSet()
                .stream()
                .filter(node -> node.getType() == HyperNodeType.HYPER_EDGE && node.getLabel() == HyperNodeLabel.I)
                .sorted((o1, o2) -> {
                    int area1 = getArea(graph, o1);
                    int area2 = getArea(graph, o2);

                    return Integer.compare(area2, area1);
                }).collect(Collectors.toList());

        nodes.forEach((node) -> {
            List<HyperNode> vertices = HyperGraphHelper.getHyperEdgeEnds(graph, node);
            HyperEdgeModel model = new HyperEdgeModel(vertices);
            HyperEdgeToBitmapConverter.convertToBitmap(model);
        });

        int width = APPROX_R.length;
        int height = APPROX_R[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, new Color(
                        APPROX_R[i][j],
                        APPROX_G[i][j],
                        APPROX_B[i][j]).getRGB()
                );
            }
        }

        return image;
    }

    private void initializeColorApproximationArray(Graph<HyperNode, DefaultEdge> graph) {
        List<Point> points = HyperGraphHelper.getAllHyperVertices(graph)
                .stream()
                .map(HyperNode::getGeom)
                .collect(Collectors.toList());

        int xmax = getMaxX(points) + 1;
        int ymax = getMaxY(points) + 1;

        APPROX_R = new int[xmax][ymax];
        APPROX_G = new int[xmax][ymax];
        APPROX_B = new int[xmax][ymax];
    }

    private int getArea(Graph<HyperNode, DefaultEdge> graph, HyperNode node) {
        List<Point> points = HyperGraphHelper.getHyperEdgeEnds(graph, node)
                .stream()
                .map(HyperNode::getGeom)
                .collect(Collectors.toList());

        int xmin = getMinX(points);
        int xmax = getMaxX(points);
        int ymin = getMinY(points);
        int ymax = getMaxY(points);

        return (xmax - xmin) * (ymax - ymin);
    }
}
