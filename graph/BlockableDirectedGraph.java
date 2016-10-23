package template.graph;

/**
 * A directed graph with blockable edges.
 */
public class BlockableDirectedGraph extends DirectedGraph {

  public boolean[] blocked;

  public BlockableDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void create(int vertexCapacity, int edgeCapacity) {
    super.create(vertexCapacity, edgeCapacity);
    blocked = new boolean[edgeCapacity];
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    blocked[edgeCnt] = false;
    super.add(fromIdx, toIdx);
  }

  public void block(int edgeIdx) {
    blocked[edgeIdx] = true;
  }
}
