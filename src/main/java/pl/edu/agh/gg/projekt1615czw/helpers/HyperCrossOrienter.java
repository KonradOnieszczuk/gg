package pl.edu.agh.gg.projekt1615czw.helpers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.domain.HyperCross;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HyperCrossOrienter {
    public static HyperCross orient(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode){
        HyperCross cross = new HyperCross();
        cross.hyperEdge = referenceHyperEdgeNode;
        Set<DefaultEdge> edges = graph.edgesOf(referenceHyperEdgeNode);
        List<HyperNode> points = edges.stream().map(e -> findPointOnEdge(graph, e)).sorted((HyperNode o1, HyperNode o2) -> {
            Point o1Point = o1.getGeom();
            Point o2Point = o2.getGeom();

            if (o1Point.getY() > o2Point.getY()) {
                return 1;
            } else if (o1Point.getY() < o2Point.getY()) {
                return -1;
            } else {
                return Double.compare(o1Point.getX(), o2Point.getX());
            }
        }).collect(Collectors.toList());

        cross.southWestPoint = points.remove(0);
        cross.southEastPoint = points.remove(0);
        cross.northWestPoint = points.remove(0);
        cross.northEastPoint = points.remove(0);

        return cross;
    }

    static HyperNode findPointOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
        HyperNode source = graph.getEdgeSource(edge);
        if (source.getGeom() != null) {
            return source;
        } else {
            return graph.getEdgeTarget(edge);
        }
    }
}
