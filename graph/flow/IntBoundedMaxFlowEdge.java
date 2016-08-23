package template.graph.flow;

/**
 * An edge of a max flow system with lower bound.
 */
public class IntBoundedMaxFlowEdge extends IntMaxFlowEdge {

  public int lowerBound, upperBound;

  public IntBoundedMaxFlowEdge(int fromIdx, int toIdx, int lowerBound, int upperBound) {
    super(fromIdx, toIdx, upperBound - lowerBound);
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }
}
