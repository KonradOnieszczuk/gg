package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.production.two.HyperEdgeNotInGraphException;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.production.two.ProductionTwoException;
import pl.edu.agh.gg.projekt1615czw.domain.Direction;
import pl.edu.agh.gg.projekt1615czw.domain.HyperCross;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.helpers.HyperCrossOrienter;
import pl.edu.agh.gg.projekt1615czw.helpers.MathHelper;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

@Component
public class ProductionTwo implements Production {
    private final BitmapProvider bitmapProvider;

    @Autowired
    public ProductionTwo(BitmapProvider bitmapProvider) {
        this.bitmapProvider = bitmapProvider;
    }

    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) throws ProductionException {
        //Check correctness
        if (referenceHyperEdgeNode.getBreakAttribute() != 1) {
            throw new ProductionTwoException(String.format("Break equals %d for HyperEdge %s", referenceHyperEdgeNode.getBreakAttribute(), referenceHyperEdgeNode.toString()));
        }

        if (!referenceHyperEdgeNode.getLabel().equals(HyperNodeLabel.I)){
            throw new ProductionTwoException(String.format("Referenced HyperEdge %s is labeled %s", referenceHyperEdgeNode.toString(), referenceHyperEdgeNode.getLabel().toString()));
        }
        Integer legs = graph.edgesOf(referenceHyperEdgeNode).size();

        if ( legs != 4){
            throw new ProductionTwoException(String.format("Referenced HyperEdge %s has %d legs", referenceHyperEdgeNode.toString(), legs));
        }

        //Create nodes
        HyperCross elements = HyperCrossOrienter.orient(graph, referenceHyperEdgeNode);
        HyperNode northEastHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode northWestHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode southEastHyperEdge = new HyperNode(HyperNodeLabel.I);
        HyperNode southWestHyperEdge = new HyperNode(HyperNodeLabel.I);

        HyperNode northHyperEdge = new HyperNode(Direction.N, HyperNodeLabel.F);
        HyperNode southHyperEdge = new HyperNode(Direction.S, HyperNodeLabel.F);
        HyperNode eastHyperEdge = new HyperNode(Direction.E, HyperNodeLabel.F);
        HyperNode westHyperEdge = new HyperNode(Direction.W, HyperNodeLabel.F);

        List<HyperNode> hyperEdges = Arrays.asList(
                northEastHyperEdge, northWestHyperEdge,
                southEastHyperEdge, southWestHyperEdge,
                northHyperEdge, southHyperEdge, eastHyperEdge, westHyperEdge);

        //Create middle point

        Point middlePointCoordinates = MathHelper.getMiddlePoint(
                elements.northWestPoint.getGeom(),
                elements.southWestPoint.getGeom(),
                elements.northEastPoint.getGeom());

        HyperNode middlePoint = new HyperNode(bitmapProvider.getColorAt(middlePointCoordinates), middlePointCoordinates);

        if (!graph.removeVertex(referenceHyperEdgeNode)) {
            throw new HyperEdgeNotInGraphException(String.format("No HyperEdge %s in graph", referenceHyperEdgeNode.toString()));
        }

        graph.addVertex(middlePoint);

        for (HyperNode hyperEdge : hyperEdges){
            graph.addVertex(hyperEdge);
            graph.addEdge(middlePoint, hyperEdge);
        }

        graph.addEdge(elements.northEastPoint, northEastHyperEdge);
        graph.addEdge(elements.northWestPoint, northWestHyperEdge);
        graph.addEdge(elements.southEastPoint, southEastHyperEdge);
        graph.addEdge(elements.southWestPoint, southWestHyperEdge);
    }
}