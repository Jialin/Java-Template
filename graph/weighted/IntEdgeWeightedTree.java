package template.graph.weighted;

public class IntEdgeWeightedTree
    extends AbstractIntEdgeWeightedBidirectionalGraph
    implements IntEdgeWeightedTreeInterface {

  public IntEdgeWeightedTree(int vertexCapacity) {
    super(vertexCapacity, vertexCapacity - 1);
  }
}
