package template.graph;

/**
 * A bidirectional graph with blockable edges.
 */
public class BlockableBidirectionalGraph extends BlockableDirectedGraph {

  public BlockableBidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity << 1);
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    super.add(fromIdx, toIdx);
    super.add(toIdx, fromIdx);
  }

  @Override
  public void block(int edgeIdx) {
    super.block(edgeIdx);
    super.block(edgeIdx ^ 1);
  }
}
