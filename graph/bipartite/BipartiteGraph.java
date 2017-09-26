package template.graph.bipartite;

import template.graph.basic.AbstractDirectedGraph;

public class BipartiteGraph extends AbstractDirectedGraph {

  public int leftCnt, rightCnt;

  public BipartiteGraph() {
    super();
  }

  public BipartiteGraph(int leftCntCapacity, int rightCntCapacity, int edgeCapacity) {
    super(leftCntCapacity + rightCntCapacity, edgeCapacity, false);
    init(leftCntCapacity, rightCntCapacity);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {}

  @Override
  public void initVertexStorage(int vertexCnt) {}

  @Override
  public void init(int vertexCnt) {
    throw new UnsupportedOperationException();
  }

  public void init(int leftCnt, int rightCnt) {
    this.leftCnt = leftCnt;
    this.rightCnt = rightCnt;
    super.init(leftCnt + rightCnt);
  }

  @Override
  public void add(int fromIdx, int toIdx) {
    super.add(fromIdx, leftCnt + toIdx);
  }

  @Override
  public int toIdx(int edgeIdx) {
    return super.toIdx(edgeIdx) - leftCnt;
  }

  @Override
  public int lastIn(int nodeIdx) {
    return super.lastIn(leftCnt + nodeIdx);
  }

  @Override
  public int inDegree(int nodeIdx) {
    return super.inDegree(leftCnt + nodeIdx);
  }
}

