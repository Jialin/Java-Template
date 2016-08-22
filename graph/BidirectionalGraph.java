package template.graph;

/**
 * A bidirectional graph.
 */
public class BidirectionalGraph<EDGE extends BidirectionalGraphEdge> extends DirectedGraph<EDGE> {

  public BidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  public void add(EDGE edge, EDGE reverseEdge) {
    add(edge);
    add(reverseEdge);
    edge.reverse = reverseEdge;
    reverseEdge.reverse = edge;
  }
}
