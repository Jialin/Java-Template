package template.graph.weighted;

public class AbstractBooleanEdgeWeightedBidirectionalGraph
    extends AbstractBooleanEdgeWeightedDirectedGraph
    implements BooleanEdgeWeightedBidirectionalGraphInterface {

  public AbstractBooleanEdgeWeightedBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  @Override
  public void add(int fromIdx, int toIdx, boolean weight) {
    super.add(fromIdx, toIdx, weight);
    super.add(toIdx, fromIdx, weight);
  }
}
