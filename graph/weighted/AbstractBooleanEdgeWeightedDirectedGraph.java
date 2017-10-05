package template.graph.weighted;

import template.array.BooleanArrayUtils;
import template.graph.basic.AbstractDirectedGraph;

public abstract class AbstractBooleanEdgeWeightedDirectedGraph
    extends AbstractDirectedGraph
    implements BooleanEdgeWeightedDirectedGraphInterface {

  public boolean[] edgeWeights;

  public AbstractBooleanEdgeWeightedDirectedGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {
    edgeWeights = BooleanArrayUtils.expand(edgeWeights, edgeCapacity);
  }

  @Override
  public void initVertexStorage(int vertexCnt) {}

  @Override
  public void add(int fromIdx, int toIdx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int fromIdx, int toIdx, boolean weight) {
    edgeWeights[currentEdgeCnt] = weight;
    super.add(fromIdx, toIdx);
  }

  @Override
  public boolean edgeWeight(int edgeIdx) {
    return edgeWeights[edgeIdx];
  }
}
