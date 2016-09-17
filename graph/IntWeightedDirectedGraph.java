package template.graph;

/**
 * A directed graph with integer weights.
 */
public class IntWeightedDirectedGraph extends DirectedGraph {

  public int[] weights;

  public IntWeightedDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
    weights = new int[edgeCapacity];
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    throw new UnsupportedOperationException();
  }

  public void add(int fromIdx, int toIdx, int weight) {
    weights[edgeCnt] = weight;
    super.add(fromIdx, toIdx);
  }
}
