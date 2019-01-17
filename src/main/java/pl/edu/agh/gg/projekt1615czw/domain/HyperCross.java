package pl.edu.agh.gg.projekt1615czw.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HyperCross {
    public HyperNode hyperEdge;
    public HyperNode northWestPoint;
    public HyperNode northEastPoint;
    public HyperNode southWestPoint;
    public HyperNode southEastPoint;
}
