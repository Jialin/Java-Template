package template.graph.weighted;

import template.graph.basic.AbstractDirectedGraph;

public class IntEdgeWeightedDirectedGraph
    extends AbstractDirectedGraph
    implements IntEdgeWeightedDirectedGraphInterface {

  public int[] edgeWeights;

  public IntEdgeWeightedDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void createSubclass(int vertexCapacity, int edgeCapacity) {
    edgeWeights = new int[edgeCapacity];
  }

  @Override
  public void initSubclass(int vertexCnt) {}

  @Override
  public void add(int fromIdx, int toIdx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int fromIdx, int toIdx, int weight) {
    edgeWeights[currentEdgeCnt] = weight;
    super.add(fromIdx, toIdx);
  }

  @Override
  public int edgeWeight(int edgeIdx) {
    return edgeWeights[edgeIdx];
  }
}
