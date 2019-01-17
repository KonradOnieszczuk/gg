package pl.edu.agh.gg.projekt1615czw.application.drawing;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Component;
import pl.edu.agh.gg.projekt1615czw.domain.HyperNode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;



@Component
public class BitmapApproximationDrawer {
    private GraphToBitmapConverter graphToBitmapConverter = new GraphToBitmapConverter();

    public void drawBitmap(Graph<HyperNode, DefaultEdge> graph) {
        BufferedImage image = graphToBitmapConverter.convertToBitmap(graph);

        JFrame editorFrame = new JFrame("Bitmap");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
    }
}
