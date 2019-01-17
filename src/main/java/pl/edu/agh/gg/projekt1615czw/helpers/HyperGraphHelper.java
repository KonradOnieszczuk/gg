package pl.edu.agh.gg.projekt1615czw.helpers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionNotApplicableException;
import pl.edu.agh.gg.projekt1615czw.domain.Direction;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class HyperGraphHelper {
    // returns list of all hyperedges connected to the hypervertex
    static ArrayList<HyperNode> getHyperVertexHyperEdges(Graph<HyperNode, DefaultEdge> graph, HyperNode node) {
        ArrayList<HyperNode> hyperedges = new ArrayList<>();
        graph.edgesOf(node).forEach(defaultEdge -> {
            hyperedges.add(findHyperEdgeOnEdge(graph, defaultEdge));
        });
        return hyperedges;
    }

    // returns hyper edge connecting two given hyper nodes
    public static HyperNode findEdgeBetween(Graph<HyperNode, DefaultEdge> graph, HyperNode node1, HyperNode node2) {
        ArrayList<HyperNode> edges1 = getHyperVertexHyperEdges(graph, node1);
        ArrayList<HyperNode> edges2 = getHyperVertexHyperEdges(graph, node2);
        return edges1.stream()
                .filter(edges2::contains)
                .findAny()
                .orElseThrow(ProductionNotApplicableException::new);
    }

    public static HyperNode findFreeEdge(Graph<HyperNode, DefaultEdge> graph, HyperNode node, Direction direction) {
        return getHyperVertexHyperEdges(graph, node).stream()
                .filter(
                        hyperNode -> hyperNode.getType() == HyperNodeType.HYPER_EDGE
                                && hyperNode.getLabel() == HyperNodeLabel.F
                                && hyperNode.getDirection() == direction)
                .findAny()
                .orElseThrow(ProductionNotApplicableException::new);
    }

    public static HyperNode findHyperVertexOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
        HyperNode source = graph.getEdgeSource(edge);
        if (source.getType() == HyperNodeType.VERTEX) {
            return source;
        } else {
            return graph.getEdgeTarget(edge);
        }
    }

    public static HyperNode findHyperEdgeOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
        HyperNode source = graph.getEdgeSource(edge);
        if (source.getType() == HyperNodeType.HYPER_EDGE) {
            return source;
        } else {
            return graph.getEdgeTarget(edge);
        }
    }

    // returns a list of HyperVertexes connected to the HyperEdge
    public static ArrayList<HyperNode> getHyperEdgeEnds(Graph<HyperNode, DefaultEdge> graph, HyperNode hyperEdge) {
        ArrayList<HyperNode> returnVal = new ArrayList<>();


        Set<DefaultEdge> edges = graph.edgesOf(hyperEdge);
        for (DefaultEdge edge : edges) {
            returnVal.add(findHyperVertexOnEdge(graph, edge));
        }

        return returnVal;
    }

    // returns array of HyperVertexes that are connected with `node` by some HyperEdge
    public static ArrayList<HyperNode> getHyperVertexNeighbors(Graph<HyperNode, DefaultEdge> graph, HyperNode node) {
        ArrayList<HyperNode> returnVal = new ArrayList<>();

        ArrayList<HyperNode> nodeHyperEdges = getHyperVertexHyperEdges(graph, node);

        for (HyperNode edge : nodeHyperEdges) {
            ArrayList<HyperNode> ends = getHyperEdgeEnds(graph, edge);
            for (HyperNode end : ends) {
                if (end != node) {
                    returnVal.add(end);
                }
            }
        }

        return returnVal;
    }

    // returns all vertices in given graph
    public static Set<HyperNode> getAllHyperVertices(Graph<HyperNode, DefaultEdge> graph) {
        return graph.vertexSet().stream()
                .filter(hyperNode -> hyperNode.getType() == HyperNodeType.VERTEX)
                .collect(Collectors.toSet());
    }
}
