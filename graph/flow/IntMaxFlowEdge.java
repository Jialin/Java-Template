package template.graph.flow;

import template.graph.BidirectionalGraphEdge;

/**
 * An edge of a max flow system.
 */
public class IntMaxFlowEdge extends BidirectionalGraphEdge {
  public int capacity;
  public int flow;

  public IntMaxFlowEdge(int fromIdx, int toIdx, int capacity) {
    super(fromIdx, toIdx);
    this.capacity = capacity;
    this.flow = 0;
  }
}
