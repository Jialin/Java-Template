package template.graph;

/**
 * A bidirectional graph with integer weights.
 */
public class IntWeightedBidirectionalGraph extends IntWeightedDirectedGraph {

  public IntWeightedBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  @Override
  public void add(int u, int v) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int u, int v, int weight) {
    super.add(u, v, weight);
    super.add(v, u, weight);
  }
}
