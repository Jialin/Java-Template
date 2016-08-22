package template.graph;

/**
 * An edge of a directed graph.
 */
public class DirectedGraphEdge {
  public int fromIdx, toIdx;
  public DirectedGraphEdge nextOutgoing;
  public DirectedGraphEdge nextIncoming;

  public DirectedGraphEdge(int fromIdx, int toIdx) {
    this.fromIdx = fromIdx;
    this.toIdx = toIdx;
  }
}
