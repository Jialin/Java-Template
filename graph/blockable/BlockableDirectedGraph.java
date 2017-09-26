package template.graph.blockable;

import template.array.BooleanArrayUtils;
import template.graph.basic.AbstractDirectedGraph;

/**
 * A directed graph with blockable edges.
 */
public class BlockableDirectedGraph extends AbstractDirectedGraph implements BlockableDirectedGraphInterface {

  public boolean[] blocked;

  public BlockableDirectedGraph() {
    super();
  }

  public BlockableDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {
    blocked = BooleanArrayUtils.expand(blocked, edgeCapacity);
  }

  @Override
  public void initVertexStorage(int vertexCnt) {}

  @Override
  public void add(int fromIdx, int toIdx) {
    blocked[currentEdgeCnt] = false;
    super.add(fromIdx, toIdx);
  }

  @Override
  public void block(int edgeIdx) {
    blocked[edgeIdx] = true;
    blocked[edgeIdx ^ 1] = true;
  }

  @Override
  public boolean blocked(int edgeIdx) {
    return blocked[edgeIdx];
  }
}
