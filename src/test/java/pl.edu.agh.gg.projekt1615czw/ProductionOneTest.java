//package pl.edu.agh.gg.projekt1615czw;
//
//import org.jgrapht.Graph;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.DefaultUndirectedGraph;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
//import pl.edu.agh.gg.projekt1615czw.application.production.ProductionNotApplicableException;
//import pl.edu.agh.gg.projekt1615czw.application.production.ProductionOne;
//import pl.edu.agh.gg.projekt1615czw.application.production.reference.ProductionOneReferenceNodeFinder;
//import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
//import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
//
//import java.awt.*;
//import java.io.IOException;
//import java.util.*;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel.B;
//import static pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel.I;
//
//public class ProductionOneTest {
//
//    @Test
//    public void shouldProduceGraphWithCorrectColors() throws IOException {
//        ProductionOne productionOne = new ProductionOne(new BitmapProvider("example.jpg"));
//
//        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph.addVertex(new HyperNode(HyperNodeLabel.S));
//
//        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
//                .orElseThrow(ProductionNotApplicableException::new);
//
//        productionOne.applyProduction(graph, referenceNode);
//
//        List<HyperNode> vertices = new ArrayList<>(graph.vertexSet());
//
//        List gotVerticesColors = vertices.stream().map(HyperNode::getColor).collect(Collectors.toList());
//
//        Assertions.assertEquals(gotVerticesColors, new ArrayList<Object>(Arrays.asList(
//                null,
//                new Color(40, 102, 175),
//                new Color(98, 142, 191),
//                new Color(63, 70, 89),
//                new Color(143, 148, 152),
//                null,
//                null,
//                null,
//                null)));
//    }
//
//    @Test
//    public void shouldProduceGraphWithCorrectPoints() throws IOException {
//        ProductionOne productionOne = new ProductionOne(new BitmapProvider("example.jpg"));
//
//        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph.addVertex(new HyperNode(HyperNodeLabel.S));
//
//        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
//                .orElseThrow(ProductionNotApplicableException::new);
//
//        productionOne.applyProduction(graph, referenceNode);
//
//        List<HyperNode> vertices = new ArrayList<>(graph.vertexSet());
//
//        List gotVerticesPoints = vertices.stream().map(HyperNode::getGeom).collect(Collectors.toList());
//
//        Assertions.assertEquals(gotVerticesPoints, new ArrayList<Object>(Arrays.asList(
//                null,
//                new Point(0, 0),
//                new Point(2788, 0),
//                new Point(0, 1504),
//                new Point(2788, 1504),
//                null,
//                null,
//                null,
//                null)));
//    }
//
//    @Test
//    public void shouldProduceGraphWithCorrectAttributes() throws IOException {
//        ProductionOne productionOne = new ProductionOne(new BitmapProvider("example.jpg"));
//
//        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph.addVertex(new HyperNode(HyperNodeLabel.S));
//
//        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
//                .orElseThrow(ProductionNotApplicableException::new);
//
//        productionOne.applyProduction(graph, referenceNode);
//
//        List<HyperNode> vertices = new ArrayList<>(graph.vertexSet());
//
//        List gotVerticesAttributes = vertices.stream().map(HyperNode::getAttributes).collect(Collectors.toList());
//
//        Assertions.assertEquals(gotVerticesAttributes, new ArrayList<Object>(Arrays.asList(
//                new HashSet(Collections.singletonList(I)),
//                Collections.emptySet(),
//                Collections.emptySet(),
//                Collections.emptySet(),
//                Collections.emptySet(),
//                new HashSet(Collections.singletonList(B)),
//                new HashSet(Collections.singletonList(B)),
//                new HashSet(Collections.singletonList(B)),
//                new HashSet(Collections.singletonList(B)))));
//    }
//
//    @Test
//    public void shouldNotApplyProductionIfNodeLabelIsOtherThanS() {
//        Graph<HyperNode, DefaultEdge> graph1 = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph1.addVertex(new HyperNode(HyperNodeLabel.F));
//
//        Assertions.assertThrows(ProductionNotApplicableException.class, () -> new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph1).orElseThrow(ProductionNotApplicableException::new));
//        Graph<HyperNode, DefaultEdge> graph2 = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph2.addVertex(new HyperNode(HyperNodeLabel.B));
//
//        Assertions.assertThrows(ProductionNotApplicableException.class, () -> new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph2).orElseThrow(ProductionNotApplicableException::new));
//        Graph<HyperNode, DefaultEdge> graph3 = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph3.addVertex(new HyperNode(HyperNodeLabel.I));
//
//        Assertions.assertThrows(ProductionNotApplicableException.class, () -> new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph3).orElseThrow(ProductionNotApplicableException::new));
//    }
//
//
//    @Test
//    public void shouldProduceGraphWithCorrectPointsForAnotherExample() throws IOException {
//        ProductionOne productionOne = new ProductionOne(new BitmapProvider("example2.jpg"));
//
//        Graph<HyperNode, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
//
//        graph.addVertex(new HyperNode(HyperNodeLabel.S));
//
//        HyperNode referenceNode = new ProductionOneReferenceNodeFinder().findProductionReferenceNode(graph)
//                .orElseThrow(ProductionNotApplicableException::new);
//
//        productionOne.applyProduction(graph, referenceNode);
//
//        List<HyperNode> vertices = new ArrayList<>(graph.vertexSet());
//
//        List gotVerticesPoints = vertices.stream().map(HyperNode::getGeom).collect(Collectors.toList());
//
//        Assertions.assertEquals(gotVerticesPoints, new ArrayList<Object>(Arrays.asList(
//                null,
//                new Point(0, 0),
//                new Point(1279, 0),
//                new Point(0, 632),
//                new Point(1279, 632),
//                null,
//                null,
//                null,
//                null)));
//    }
//
//}
