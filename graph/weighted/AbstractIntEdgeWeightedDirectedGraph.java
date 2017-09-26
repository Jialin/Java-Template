package template.graph.weighted;

import template.array.IntArrayUtils;
import template.graph.basic.AbstractDirectedGraph;

public abstract class AbstractIntEdgeWeightedDirectedGraph
    extends AbstractDirectedGraph
    implements IntEdgeWeightedDirectedGraphInterface {

  public int[] edgeWeights;

  public AbstractIntEdgeWeightedDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {
    edgeWeights = IntArrayUtils.expand(edgeWeights, edgeCapacity);
  }

  @Override
  public void initVertexStorage(int vertexCnt) {}

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
