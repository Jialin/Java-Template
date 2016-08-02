package template.graph;

/**
 * Bidirectional graph.
 */
public class BidirectionalGraph extends DirectedGraph {

  public BidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  /**
   * Add 2 directed edges (i.e. between {@code end} and {@code otherEnd}).
   */
  @Override
  public void add(int end, int otherEnd) {
    super.add(end, otherEnd);
    super.add(otherEnd, end);
  }
}
