package pl.edu.agh.gg.projekt1615czw.application.drawing;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
import pl.edu.agh.gg.projekt1615czw.domain.HyperCross;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.helpers.HyperCrossOrienter;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class ApproximationErrorCalculator {
    private final BitmapProvider bitmapProvider;

    @Autowired
    public ApproximationErrorCalculator(BitmapProvider bitmapProvider) { this.bitmapProvider = bitmapProvider;}

    public Double calculateApproximationError(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode){
        HyperCross hyperCross = HyperCrossOrienter.orient(graph, referenceHyperEdgeNode);
        Double error = 0.0;

        int x1 = hyperCross.northEastPoint.getGeom().x;
        int x2 = hyperCross.northWestPoint.getGeom().x;

        int y1 = hyperCross.northEastPoint.getGeom().y;
        int y2 = hyperCross.southEastPoint.getGeom().y;

        List<Point> pointList = new ArrayList<>();

        for(int px : Arrays.asList(x1,x2)){
            for(int py : Arrays.asList(y1,y2)){
                pointList.add(new Point(px,py));
            }
        }

        for (Point point: pointList){
            ColoredErrors errors = new ColoredErrors();
            errors.redError = (double) bitmapProvider.getColorAt(point).getRed();
            errors.greenError = (double) bitmapProvider.getColorAt(point).getGreen();
            errors.blueError = (double) bitmapProvider.getColorAt(point).getBlue();

            Color color;
            Double xFactor = calculateFactor((double)point.x, (double)x1, (double)x2);
            Double yFactor = calculateFactor((double)point.y, (double)y1, (double)y2);

            color = hyperCross.northEastPoint.getColor();
            errors.redError -= color.getRed() * (1 - xFactor) * yFactor;
            errors.greenError -= color.getGreen() * (1 - xFactor) * yFactor;
            errors.blueError -= color.getBlue() * (1 - xFactor) * yFactor;

            color = hyperCross.northWestPoint.getColor();
            errors.redError -= color.getRed() * xFactor * yFactor;
            errors.greenError -= color.getGreen() * xFactor * yFactor;
            errors.blueError -= color.getBlue() * xFactor * yFactor;

            color = hyperCross.southEastPoint.getColor();
            errors.redError -= color.getRed() * (1 - xFactor) * (1 - yFactor);
            errors.greenError -= color.getGreen() * (1 - xFactor) * (1 - yFactor);
            errors.blueError -= color.getBlue() * (1 - xFactor) * (1 - yFactor);

            color = hyperCross.southWestPoint.getColor();
            errors.redError -= color.getRed() * xFactor * (1 - yFactor);
            errors.greenError -= color.getGreen() * xFactor * (1 - yFactor);
            errors.blueError -= color.getBlue() * xFactor * (1 - yFactor);

            error += 0.5 * Math.pow(errors.redError,2) + 0.3 * Math.pow(errors.greenError,2) + 0.2 * Math.pow(errors.blueError,2);
        }

        return error;
    }

    private class ColoredErrors{
        Double redError;
        Double blueError;
        Double greenError;
    }

    private Double calculateFactor(Double pa, Double a1, Double a2){
        return (pa-a1) / (a2 - a1);
    }
}
