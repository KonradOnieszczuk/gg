package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionOneReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;

import java.awt.*;

@Component
public class ProductionOne implements Production {
    private final BitmapProvider bitmapProvider;

    @Autowired
    public ProductionOne(BitmapProvider bitmapProvider) {
        this.bitmapProvider = bitmapProvider;
    }

    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) {
        referenceHyperEdgeNode.setLabel(HyperNodeLabel.I);
        referenceHyperEdgeNode.setBreakAttribute(0);

        // Add new vertices
        Point point1 = new Point(0, 0);
        Point point2 = new Point(bitmapProvider.getDimension().width - 1, 0);
        Point point3 = new Point(0, bitmapProvider.getDimension().height - 1);
        Point point4 = new Point(bitmapProvider.getDimension().width - 1, bitmapProvider.getDimension().height - 1);
        HyperNode node1 = new HyperNode(bitmapProvider.getColorAt(point1), point1);
        HyperNode node2 = new HyperNode(bitmapProvider.getColorAt(point2), point2);
        HyperNode node3 = new HyperNode(bitmapProvider.getColorAt(point3), point3);
        HyperNode node4 = new HyperNode(bitmapProvider.getColorAt(point4), point4);

        graph.addVertex(node1);
        graph.addVertex(node2);
        graph.addVertex(node3);
        graph.addVertex(node4);

        // Add new pseudo-nodes for hyper graph edges
        HyperNode edgeNodeUp = new HyperNode(HyperNodeLabel.B);
        HyperNode edgeNodeDown = new HyperNode(HyperNodeLabel.B);
        HyperNode edgeNodeLeft = new HyperNode(HyperNodeLabel.B);
        HyperNode edgeNodeRight = new HyperNode(HyperNodeLabel.B);

        graph.addVertex(edgeNodeUp);
        graph.addVertex(edgeNodeDown);
        graph.addVertex(edgeNodeLeft);
        graph.addVertex(edgeNodeRight);

        // Add new edges between vertices and pseudo-nodes for hyper graph edges
        graph.addEdge(edgeNodeLeft, node1);
        graph.addEdge(edgeNodeLeft, node3);

        graph.addEdge(edgeNodeUp, node1);
        graph.addEdge(edgeNodeUp, node2);

        graph.addEdge(edgeNodeRight, node2);
        graph.addEdge(edgeNodeRight, node4);

        graph.addEdge(edgeNodeDown, node3);
        graph.addEdge(edgeNodeDown, node4);

        graph.addEdge(referenceHyperEdgeNode, node1);
        graph.addEdge(referenceHyperEdgeNode, node2);
        graph.addEdge(referenceHyperEdgeNode, node3);
        graph.addEdge(referenceHyperEdgeNode, node4);
    }
}
