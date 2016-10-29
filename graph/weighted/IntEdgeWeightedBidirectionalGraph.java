package template.graph.weighted;

public class IntEdgeWeightedBidirectionalGraph
    extends IntEdgeWeightedDirectedGraph
    implements IntEdgeWeightedBidirectionalGraphInterface {

  public IntEdgeWeightedBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  @Override
  public void add(int fromIdx, int toIdx, int weight) {
    super.add(fromIdx, toIdx, weight);
    super.add(toIdx, fromIdx, weight);
  }
}
