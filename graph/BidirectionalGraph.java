package template.graph;

/**
 * A bidirectional graph.
 */
public class BidirectionalGraph extends DirectedGraph {

  public BidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  @Override
  public void add(int u, int v) {
    super.add(u, v);
    super.add(v, u);
  }
}
