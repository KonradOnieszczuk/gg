package pl.edu.agh.gg.projekt1615czw.application.production.reference;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

import java.util.Optional;

@FunctionalInterface
public interface ProductionReferenceNodeFinder {
    Optional<HyperNode> findProductionReferenceNode(Graph<HyperNode, DefaultEdge> graph);
}
