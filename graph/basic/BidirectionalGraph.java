package template.graph.basic;

public final class BidirectionalGraph extends AbstractBidirectionalGraph {

  public BidirectionalGraph() {
    super();
  }

  public BidirectionalGraph(int vertexCapacity, int edgeCapacity) {
    super(vertexCapacity, edgeCapacity);
  }

  @Override
  public void createVertexStorage(int vertexCapacity) {}

  @Override
  public void expandEdgeStorage(int edgeCapacity) {}

  @Override
  public void initVertexStorage(int vertexCnt) {}
}
