package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.production.two.HyperEdgeNotInGraphException;
import pl.edu.agh.gg.projekt1615czw.domain.Direction;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductionThree implements Production
{

    private final BitmapProvider bitmapProvider;

    @Autowired
    public ProductionThree(BitmapProvider bitmapProvider) {


        this.bitmapProvider = bitmapProvider;
    }

    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) throws ProductionException {


        if (referenceHyperEdgeNode.getLabel()!=(HyperNodeLabel.B)) return;
        Set<DefaultEdge> edges = graph.edgesOf(referenceHyperEdgeNode);
        List<HyperNode> points = edges.stream().map(e -> findPointOnEdge(graph, e)).collect(Collectors.toList());

        HyperNode point1=points.remove(0);
        HyperNode point2=points.remove(0);

        Set<DefaultEdge> edges1 = graph.edgesOf(point1);
        List<HyperNode> points1 = edges1.stream().map(e -> findHyperEdgePointOnEdge(graph, e)).filter(s -> s.getLabel()==(HyperNodeLabel.I)).collect(Collectors.toList());
        Set<DefaultEdge> edges2 = graph.edgesOf(point2);
        List<HyperNode> points2 = edges2.stream().map(e -> findHyperEdgePointOnEdge(graph, e)).filter(s -> s.getLabel()==(HyperNodeLabel.I)).collect(Collectors.toList());


        for(HyperNode p1 : points1) {
            for (HyperNode p2 : points2) {
                if(p1==p2) return;

            }
        }

            List<DefaultEdge> edgesForINodeConnectedWithPoint1 = new ArrayList<DefaultEdge>();
             List<DefaultEdge> edgesForINodeConnectedWithPoint2 = new ArrayList<DefaultEdge>();
        for(HyperNode p1 : points1) {
            Set<DefaultEdge> edges1231=graph.edgesOf(p1);
            edgesForINodeConnectedWithPoint1.addAll(edges1231.stream().collect(Collectors.toList()));
        }
        for(HyperNode p1 : points2) {
            Set<DefaultEdge> edges1231=graph.edgesOf(p1);
            edgesForINodeConnectedWithPoint2.addAll(edges1231.stream().collect(Collectors.toList()));
        }

        int value;
        boolean horizontal=false;
        boolean vertical=false;

        if(point2.getGeom().getY()==point1.getGeom().getY()){ //poziom
            horizontal=true;
           value= (int) Math.floor((point1.getGeom().getX() + point2.getGeom().getX()) / 2);
        }

        else{ //pion
            vertical=true;
            value= (int) Math.floor((point1.getGeom().getY() + point2.getGeom().getY()) / 2);
        }


        List<DefaultEdge> list1 =new ArrayList<DefaultEdge>();
        List<DefaultEdge> list2 =new ArrayList<DefaultEdge>();
            if(horizontal) {
                 list1 = edgesForINodeConnectedWithPoint1.stream().filter(s->VertexExist(graph,s)).filter(s -> findPointOnEdge(graph, s).getGeom().getX() == value).collect(Collectors.toList());
                 list2 = edgesForINodeConnectedWithPoint2.stream().filter(s->VertexExist(graph,s)).filter(s -> findPointOnEdge(graph, s).getGeom().getX() == value).collect(Collectors.toList());
            }
            else{

                list1 = edgesForINodeConnectedWithPoint1.stream().filter(s->VertexExist(graph,s)).filter(s -> findPointOnEdge(graph, s).getGeom().getY() == value).collect(Collectors.toList());
                list2 = edgesForINodeConnectedWithPoint2.stream().filter(s->VertexExist(graph,s)).filter(s -> findPointOnEdge(graph, s).getGeom().getY() == value).collect(Collectors.toList());

            }

            DefaultEdge edgeForPoint1 = list1.remove(0);
            DefaultEdge edgeForPoint2 = list2.remove(0);

            HyperNode iNode1 = findHyperEdgePointOnEdge(graph,edgeForPoint1);
            HyperNode iNode2 = findHyperEdgePointOnEdge(graph,edgeForPoint2);
            HyperNode point3 = findPointOnEdge(graph,edgeForPoint2);
        List<HyperNode> ListF=new ArrayList<HyperNode>();
        Point point=new Point();

        if(horizontal && point1.getGeom().getY() == 0) {
            ListF = Graphs.neighborListOf(graph, point3).stream().filter(s -> s.getDirection() == Direction.S).collect(Collectors.toList());
            point.setLocation( (int) Math.floor((point1.getGeom().getX()+ point2.getGeom().getX())/2),0);
        }
        else if(horizontal && point1.getGeom().getY() != 0) {
            ListF = Graphs.neighborListOf(graph, point3).stream().filter(s -> s.getDirection() == Direction.N).collect(Collectors.toList());
            point.setLocation( (int) Math.floor((point1.getGeom().getX()+ point2.getGeom().getX())/2),(point1.getGeom().getY()));
        }

        else if (vertical && point1.getGeom().getX() == 0) {
            ListF = Graphs.neighborListOf(graph, point3).stream().filter(s -> s.getDirection() == Direction.E).collect(Collectors.toList());
            point.setLocation( 0,(int) Math.floor((point1.getGeom().getY()+ point2.getGeom().getY())/2));
        }
        else if(vertical && point1.getGeom().getX() != 0) {
            ListF = Graphs.neighborListOf(graph, point3).stream().filter(s -> s.getDirection() == Direction.W).collect(Collectors.toList());
            point.setLocation(point1.getGeom().getX() ,(int) Math.floor((point1.getGeom().getY()+ point2.getGeom().getY())/2));
        }
            HyperNode fNode=ListF.remove(0);



            HyperNode pointV = new HyperNode(bitmapProvider.getColorAt(point), point);
            HyperNode bNode1 = new HyperNode(HyperNodeLabel.B);
            HyperNode bNode2 = new HyperNode(HyperNodeLabel.B);
            //HyperNode pointV = new HyperNode(HyperNodeLabel.F);

        if (!graph.removeVertex(referenceHyperEdgeNode)) {
            throw new HyperEdgeNotInGraphException(String.format("No HyperEdge %s in graph", referenceHyperEdgeNode.toString()));
        }
        graph.addVertex(bNode1);
        graph.addVertex(pointV);
        graph.addVertex(bNode2);


        graph.addEdge(point1,bNode1);
        graph.addEdge(point2,bNode2);
        graph.addEdge(pointV,bNode1);
        graph.addEdge(pointV,bNode2);
        graph.addEdge(pointV,iNode1);
        graph.addEdge(pointV,iNode2);
        graph.addEdge(pointV,fNode);




    }

    boolean VertexExist(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge){
        HyperNode node = graph.getEdgeSource(edge);
        if (node.getType() == HyperNodeType.VERTEX) {
            return true;
        }
        node= graph.getEdgeTarget(edge);

        if (node.getType() == HyperNodeType.VERTEX) {
            return true;
        }
        return false;
    }

    HyperNode findPointOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
        HyperNode source = graph.getEdgeSource(edge);
        if (source.getType() == HyperNodeType.VERTEX) {
            return source;
        } else {
            return graph.getEdgeTarget(edge);
        }
    }

    HyperNode findHyperEdgePointOnEdge(Graph<HyperNode, DefaultEdge> graph, DefaultEdge edge) {
        HyperNode source = graph.getEdgeSource(edge);

        if (source.getType() == HyperNodeType.HYPER_EDGE ) {
            return source;
        } else {
            return graph.getEdgeTarget(edge);
        }
    }


}
