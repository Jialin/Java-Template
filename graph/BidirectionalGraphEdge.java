package template.graph;

/**
 * An one direction edge of a bidirectional graph.
 *
 * Along with {@code reverse}, it represents an edge of a bidirectional graph.
 */
public class BidirectionalGraphEdge extends DirectedGraphEdge {
  public BidirectionalGraphEdge reverse;

  public BidirectionalGraphEdge(int fromIdx, int toIdx) {
    super(fromIdx, toIdx);
  }
}
