package pl.edu.agh.gg.projekt1615czw;

import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionNotApplicableException;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionOne;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionTwo;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionThree;

import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionOneReferenceNodeFinder;
import pl.edu.agh.gg.projekt1615czw.application.tagging.HyperNodeToAdaptation;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.infrastructure.GraphAdapter;


import java.util.ArrayList;
import java.util.List;


@Slf4j
@PropertySource(value = "classpath:/application.properties", ignoreResourceNotFound = true)
@ComponentScan(basePackages = "pl.edu.agh.gg.projekt1615czw")
@Configuration
public class Main {
    private final ProductionOne productionOne;
    private final ProductionTwo productionTwo;
    private final ProductionThree productionThree;
    private final HyperNodeToAdaptation hyperNodeToAdaptation;

    @Autowired
    public Main(ProductionOne productionOne, ProductionTwo productionTwo, ProductionThree productionThree, HyperNodeToAdaptation hyperNodeToAdaptation) {
        this.productionOne = productionOne;
        this.productionTwo = productionTwo;
        this.productionThree = productionThree;
        this.hyperNodeToAdaptation = hyperNodeToAdaptation;
    }

    public static void main(String[] args) throws ProductionException {
        new AnnotationConfigApplicationContext(Main.class).getBean(Main.class).start();
    }

    private void start() throws ProductionException {
        // do stuff

        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        graph.addVertex(new HyperNode(HyperNodeLabel.S));
        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
                .orElseThrow(ProductionNotApplicableException::new);


        productionOne.applyProduction(graph, referenceNode);

    hyperNodeToAdaptation.tagHyperNodeToAdaption(graph, 0, 0);


 /*       HyperNode iNode = findVertex(graph, HyperNodeLabel.I);
        iNode.setBreakAttribute(1);*/
/*        productionTwo.applyProduction(graph, iNode);
       HyperNode bNode = findVertex(graph, HyperNodeLabel.B);
       productionThree.applyProduction(graph,bNode);
        List<HyperNode> listB=findAllVertex(graph,HyperNodeLabel.B);
        for(HyperNode B : listB){
            productionThree.applyProduction(graph,B);
        }*/

        org.graphstream.graph.Graph graphstreamGraph = new GraphAdapter("Graph 1", graph);
        graphstreamGraph.display();
    }
    private List<HyperNode> findAllVertex(Graph<HyperNode, DefaultEdge> graph, HyperNodeLabel label){
        List<HyperNode> list=new ArrayList<HyperNode>();
        for (HyperNode node : graph.vertexSet()){
            if (node.getLabel()==label) {
                list.add(node);
            }

        }
        return list;
    }

    private HyperNode findVertex(Graph<HyperNode, DefaultEdge> graph, HyperNodeLabel label){
        for (HyperNode node : graph.vertexSet()){
            if (node.getLabel()==label)

                return node;
        }
        return null;
    }
}
