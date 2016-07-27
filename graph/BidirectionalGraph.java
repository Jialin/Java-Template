package template.graph;

import java.util.function.Function;

/**
 * Bidirectional graph.
 *
 * @param <INFO> Edge information
 */
public class BidirectionalGraph<INFO> extends DirectedGraph<INFO> {

  public BidirectionalGraph(int vertexCount, int edgeCapacity, Function<Integer, INFO[]> infoArrayFactory) {
    super(vertexCount, edgeCapacity << 1, infoArrayFactory);
  }

  /**
   * Add 2 directed edges (i.e. between {@code end} and {@code otherEnd}) with {@code info}.
   */
  @Override
  public void add(int end, int otherEnd, INFO info) {
    super.add(end, otherEnd, info);
    super.add(otherEnd, end, info);
  }
}
