package template.graph.weighted;

public class BooleanEdgeWeightedTree
    extends AbstractBooleanEdgeWeightedBidirectionalGraph
    implements BooleanEdgeWeightedTreeInterface {

  public BooleanEdgeWeightedTree(int vertexCapacity) {
    super(vertexCapacity, vertexCapacity - 1);
  }
}
