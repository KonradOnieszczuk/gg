package pl.edu.agh.gg.projekt1615czw.domain;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HyperNode {
    private final HyperNodeType type;
    private HyperNodeLabel label;
    private final Point geom;
    private final Color color;
    private int breakAttribute;
    private Direction direction;

    public HyperNode(Color color, Point geom) {
        this.color = color;
        this.geom = geom;
        this.type = HyperNodeType.VERTEX;
        this.label = HyperNodeLabel.V;
    }

    public HyperNode(HyperNodeLabel hyperNodeLabel) {
        this.type = HyperNodeType.HYPER_EDGE;
        this.geom = null;
        this.color = null;
        this.breakAttribute = 0;
        this.label = hyperNodeLabel;
    }

    public HyperNode(Direction direction, HyperNodeLabel hyperNodeLabel) {
        this(hyperNodeLabel);
        this.direction = direction;
    }

    public Point getGeom() {
        return geom;
    }

    public Color getColor() {
        return color;
    }

    public HyperNodeType getType() {
        return type;
    }

    public HyperNodeLabel getLabel() {
        return label;
    }

    public int getBreakAttribute() {
        return breakAttribute;
    }

    public void setBreakAttribute(int breakAttribute) {
        this.breakAttribute = breakAttribute;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLabel(HyperNodeLabel label) { this.label = label; }

    @Override
    public String toString() {
        return "HyperNode{" +
                "type=" + type +
                ", label=" + label +
                ", geom=" + geom +
                ", color=" + color +
                ", breakAttribute=" + breakAttribute +
                ", direction=" + direction +
                '}';
    }
}