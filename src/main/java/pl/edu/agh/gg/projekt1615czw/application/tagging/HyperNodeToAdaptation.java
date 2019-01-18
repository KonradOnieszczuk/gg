package pl.edu.agh.gg.projekt1615czw.application.tagging;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.application.drawing.ApproximationErrorCalculator;
import pl.edu.agh.gg.projekt1615czw.application.production.Production4;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionThree;
import pl.edu.agh.gg.projekt1615czw.application.production.ProductionTwo;
import pl.edu.agh.gg.projekt1615czw.application.production.exception.ProductionException;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNodeLabel;

import java.util.ArrayList;
import java.util.List;

@Component
public class HyperNodeToAdaptation {

    private final ApproximationErrorCalculator approximationErrorCalculator;
    private final ProductionTwo productionTwo;
    private final ProductionThree productionThree;
    private final Production4 production4;

    @Autowired
    public HyperNodeToAdaptation(ApproximationErrorCalculator approximationErrorCalculator, ProductionTwo productionTwo, ProductionThree productionThree, Production4 production4) {
        this.approximationErrorCalculator = approximationErrorCalculator;
        this.productionTwo=productionTwo;
        this.productionThree=productionThree;
        this.production4=production4;

    }

    public void tagHyperNodeToAdaption (Graph<HyperNode, DefaultEdge> graph, double error, int maxstep) throws ProductionException {

        boolean exit = false;
        List<HyperNode> listI1=findAllVertex(graph, HyperNodeLabel.B);
        for (int i = 0; i<4; i++) {
/*            if (exit)
                break;*/
            for (HyperNode I1 : listI1) {
                if (I1.getLabel().equals(HyperNodeLabel.I)){
                    I1.setBreakAttribute(1);
                }
    /*            if (approximationErrorCalculator.calculateApproximationError(graph, I1) > error) {
                    //uruchom na tej hiperkrawędzi produkcję P5 (która ustawia break na 1)
                    //uruchom algorytm poprawiania złamań za pomocą produkcji P6
                }
                else {
                    exit = true;
                    break;
                }*/
            }
/*            if (exit)
                break;*/
            for (int j=0; j<4; j++) {
                List<HyperNode> listI2 = findAllVertex(graph, HyperNodeLabel.I);
                for (HyperNode I2 : listI2) {
                    try {
                    productionTwo.applyProduction(graph, I2);
                } catch(Exception e){
                        //
                    }
                }

                List<HyperNode> listB2 = findAllVertex(graph, HyperNodeLabel.B);
                for (HyperNode B2 : listB2) {
                    productionThree.applyProduction(graph, B2);
                }

                List<HyperNode> listF2 = findAllVertex(graph, HyperNodeLabel.F);
                for (HyperNode F2 : listF2) {
                    production4.applyProduction(graph, F2);
                }
            }
        }
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

}
