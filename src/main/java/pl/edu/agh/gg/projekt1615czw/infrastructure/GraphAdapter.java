package pl.edu.agh.gg.projekt1615czw.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class GraphAdapter extends DefaultGraph {
    public GraphAdapter(String id, Graph<HyperNode, DefaultEdge> graph) {
        super(id, false, true);
        initializeFromGraph(graph);
        setAttribute("text-size", 20);
    }

    /**
     * Transforms the graph structure from passed graph to this adapter graph that is displayable
     * */
    private void initializeFromGraph(Graph<HyperNode, DefaultEdge> graph) {
        Map<HyperNode, Node> nodesMap = addNodes(graph);
        addEdges(graph, nodesMap);
    }

    /**
     * @return one-to-one mapping of nodes from the source graph to the nodes in this adapter graph
     * */
    private Map<HyperNode, Node> addNodes(Graph<HyperNode, DefaultEdge> graph) {
        Map<HyperNode, Node> nodesMap = new HashMap<>(graph.vertexSet().size());
        for (HyperNode node : graph.vertexSet()) {
            Node newNode;
            if (node.getType() == HyperNodeType.HYPER_EDGE) {
                newNode = addHyperEdgeNode(node);
            } else {
                newNode = addHyperNode(node);
            }
            nodesMap.put(node, newNode);
        }
        return nodesMap;
    }

    private void addEdges(Graph<HyperNode, DefaultEdge> graph, Map<HyperNode, Node> nodesMap) {
        for (DefaultEdge edge : graph.edgeSet()) {
            HyperNode edgeSource = graph.getEdgeSource(edge);
            HyperNode edgeTarget = graph.getEdgeTarget(edge);

            Node sourceNode = nodesMap.get(edgeSource);
            Node targetNode = nodesMap.get(edgeTarget);

            UUID uuid = UUID.randomUUID();
            addEdge(uuid.toString(), sourceNode, targetNode, false);

            log.debug("Adding edge ({})-({}) {}", edgeSource.getGeom(), edgeTarget.getGeom(), uuid);
        }
    }

    private Node addHyperEdgeNode(HyperNode hyperNode) {
        UUID uuid = UUID.randomUUID();
        log.debug("Adding hyper edge node {} {}" + hyperNode.getLabel(), uuid);
        Node node = addNode(uuid.toString());

        // Style
        String label = hyperNode.getLabel().toString();
        node.setAttribute("label", label);

        return node;
    }

    private Node addHyperNode(HyperNode hyperNode) {
        UUID uuid = UUID.randomUUID();
        log.debug("Adding hyper node {} {}", hyperNode.getGeom(), uuid);
        Node node = addNode(uuid.toString());

        // Style
        String label = String.format("geom=(%s, %s) R=%s,G=%s,B=%s",
                hyperNode.getGeom().x,
                hyperNode.getGeom().y,
                hyperNode.getColor().getRed(),
                hyperNode.getColor().getGreen(),
                hyperNode.getColor().getBlue());
        node.setAttribute("label", label);
        node.setAttribute("ui.style", String.format("fill-color: rgb(%d,%d,%d);",
                hyperNode.getColor().getRed(),
                hyperNode.getColor().getGreen(),
                hyperNode.getColor().getBlue()));
        return node;
    }
}
