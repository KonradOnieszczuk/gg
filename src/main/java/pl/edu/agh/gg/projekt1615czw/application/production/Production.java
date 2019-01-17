package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

public interface Production {
    void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) throws ProductionException;
}
