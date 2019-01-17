package pl.edu.agh.gg.projekt1615czw.application.production;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.bitmap.BitmapProvider;
import pl.edu.agh.gg.projekt1615czw.domain.Direction;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeType;
import pl.edu.agh.gg.projekt1615czw.helpers.HyperGraphHelper;

import java.awt.*;
import java.util.ArrayList;

@Component
public class Production4 implements Production {

    private final BitmapProvider bitmapProvider;


    @Autowired
    public Production4(BitmapProvider bitmapProvider) {
        this.bitmapProvider = bitmapProvider;
    }

    // applyProduction expects to receive HyperEdge F1 as a referenceHyperEdgeNode
    @Override
    public void applyProduction(Graph<HyperNode, DefaultEdge> graph, HyperNode referenceHyperEdgeNode) {
        // parse subgraph
        OrientedGraphFragment fragment = new OrientedGraphFragment(graph, referenceHyperEdgeNode);

        // create new vertex
        int new_x = (fragment.p5.getGeom().x + fragment.p7.getGeom().x) / 2;
        int new_y = (fragment.p5.getGeom().y + fragment.p7.getGeom().y) / 2;
        Point point1 = new Point(new_x, new_y);
        HyperNode new_node = new HyperNode(bitmapProvider.getColorAt(point1), point1);
        graph.addVertex(new_node);

        // remove f_vertical from the graph
        graph.removeVertex(fragment.f_vertical);

        // add and link f_north and f_south
        HyperNode f_north = new HyperNode(HyperNodeLabel.F);
        HyperNode f_south = new HyperNode(HyperNodeLabel.F);
        f_north.setDirection(Direction.S);
        f_south.setDirection(Direction.N);

        graph.addVertex(f_north);
        graph.addVertex(f_south);
        graph.addEdge(f_north, fragment.p5);
        graph.addEdge(f_south, fragment.p7);


        // connect new vertex to the edges
        graph.addEdge(new_node, fragment.i1);
        graph.addEdge(new_node, fragment.i2);
        graph.addEdge(new_node, fragment.i3);
        graph.addEdge(new_node, fragment.i4);

        graph.addEdge(new_node, fragment.f_west);
        graph.addEdge(new_node, fragment.f_east);
        graph.addEdge(new_node, f_north);
        graph.addEdge(new_node, f_south);
    }

    class OrientedGraphFragment {
        // edges
        HyperNode i1;
        HyperNode i2;
        HyperNode i3;
        HyperNode i4;

        HyperNode f_west;
        HyperNode f_east;
        HyperNode f_vertical;

        // vertexes1
        HyperNode p5;
        HyperNode p7;
        protected HyperNode p6;
        protected HyperNode p8;

        public OrientedGraphFragment(Graph<HyperNode, DefaultEdge> graph, HyperNode f_vertical) {
            this.f_vertical = f_vertical;

            if (this.f_vertical.getType() != HyperNodeType.HYPER_EDGE || this.f_vertical.getLabel() != HyperNodeLabel.F) {
                throw new ProductionNotApplicableException();
            }

            ArrayList<HyperNode> f5f7 = HyperGraphHelper.getHyperEdgeEnds(graph, this.f_vertical);
            if (f5f7.size() != 2) {
                throw new ProductionNotApplicableException();
            }

            //detect orientation
            this.p5 = f5f7.get(0);
            this.p7 = f5f7.get(1);
            if (this.p5.getType() != HyperNodeType.VERTEX || this.p7.getType() != HyperNodeType.VERTEX) {
                throw new ProductionNotApplicableException();
            }

            boolean vertical;
            if (this.p5.getGeom().x == this.p7.getGeom().x) {
                // orientation vertical
                if (this.p5.getGeom().y < this.p7.getGeom().y) {
                    swapP5P7();
                }
                vertical = true;
            } else {
                // orientation horizontal
                if (this.p5.getGeom().x > this.p7.getGeom().x) {
                    swapP5P7();
                }
                vertical = false;
            }
            findP6P8(graph, vertical);
            findP4HyperEdges(graph, vertical);
        }

        private void swapP5P7() {
            HyperNode tmp = this.p5;
            this.p5 = this.p7;
            this.p7 = tmp;
        }


        private void findP6P8(Graph<HyperNode, DefaultEdge> graph, boolean vertical) {
            ArrayList<HyperNode> p5neighbors = HyperGraphHelper.getHyperVertexNeighbors(graph, this.p5);

            // find p6 and p8
            this.p8 = p5neighbors.stream().filter(neighbor ->
                    vertical ?
                            neighbor.getGeom().x < p5.getGeom().x && neighbor.getGeom().y < p5.getGeom().y
                            : neighbor.getGeom().x > p5.getGeom().x && neighbor.getGeom().y < p5.getGeom().y
            ).findAny().orElse(null);

            this.p6 = p5neighbors.stream().filter(neighbor ->
                    vertical ?
                            neighbor.getGeom().x > p5.getGeom().x && neighbor.getGeom().y < p5.getGeom().y
                            : neighbor.getGeom().x > p5.getGeom().x && neighbor.getGeom().y > p5.getGeom().y
            ).findAny().orElse(null);

            if (p8 == null || p6 == null || p8.getType() != HyperNodeType.VERTEX || p6.getType() != HyperNodeType.VERTEX) {
                throw new ProductionNotApplicableException();
            }
        }

        private void findP4HyperEdges(Graph<HyperNode, DefaultEdge> graph, boolean vertical) {
            this.f_east = HyperGraphHelper.findFreeEdge(graph, p6, vertical ? Direction.W : Direction.S);
            this.f_west = HyperGraphHelper.findFreeEdge(graph, p8, vertical ? Direction.E : Direction.N);

            this.i1 = HyperGraphHelper.findEdgeBetween(graph, this.p5, p8);
            this.i2 = HyperGraphHelper.findEdgeBetween(graph, this.p5, p6);
            this.i3 = HyperGraphHelper.findEdgeBetween(graph, this.p7, p8);
            this.i4 = HyperGraphHelper.findEdgeBetween(graph, this.p7, p6);

            if (this.i1.getType() != HyperNodeType.HYPER_EDGE
                    || this.i2.getType() != HyperNodeType.HYPER_EDGE
                    || this.i3.getType() != HyperNodeType.HYPER_EDGE
                    || this.i4.getType() != HyperNodeType.HYPER_EDGE) {
                throw new ProductionNotApplicableException();
            }

            if (this.i1.getLabel() != HyperNodeLabel.I
                    || this.i2.getLabel() != HyperNodeLabel.I
                    || this.i3.getLabel() != HyperNodeLabel.I
                    || this.i4.getLabel() != HyperNodeLabel.I) {
                throw new ProductionNotApplicableException();
            }
        }
    }
}
